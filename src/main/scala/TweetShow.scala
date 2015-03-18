import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import java.io.File
import org.apache.spark.rdd.RDD

object TweetShow extends App {

  def getListOfSubDirectories(directoryName: String): Array[String] = {
    return (new File(directoryName)).listFiles.filter(_.isDirectory).map(_.getName)
  }

  def rddList(list: List[String]): List[RDD[String]] = {
    list match {
      case Nil     => List(sc.parallelize(Nil))
      case y :: ys => sc.textFile("tweets/" + y) :: rddList(ys)
    }
  }

  def reduceRDDList(list: List[RDD[String]]): RDD[String] = {
    list match {
      case Nil     => sc.parallelize(Nil)
      case y :: ys => sc.union(y, reduceRDDList(ys))
    }
  }

  val conf = new SparkConf().setAppName("SparkSQL").setMaster("local[2]").set("spark.hadoop.validateOutputSpecs", "false")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)
  import sqlContext.createSchemaRDD
  val tree = getListOfSubDirectories("tweets")
  val RDDList = rddList(tree.toList)
  val RDDFinal = reduceRDDList(RDDList)
  val schemaString = "tweets"
  val schema = StructType(schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, true)))
  val tweetRDD = RDDFinal.filter { x => x.startsWith("#") }.map(_.split(" ")).map(p => Row(p(0)))
  val tweetSchemaRDD = sqlContext.applySchema(tweetRDD, schema)
  tweetSchemaRDD.registerTempTable("tweetsTable")
  val result = sqlContext.sql("SELECT tweets FROM tweetsTable")
  val top = result.map(t => (t(0), 1)).reduceByKey((a, b) => a + b).sortBy(_._2, false).take(10)
  //result.map(t => "Tweets: " + t(0)).collect().foreach(println)
  top.foreach(x => println("Hashtag  " + x._1 + " is used " + x._2 + " times."))

}