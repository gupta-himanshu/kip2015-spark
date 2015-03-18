import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.StreamingContext._

/**
 * Object with App to to run application its setup spark context and streaming context and with the help of
 * TwitterUtils steam the tweets and collect # (hastags) from it and save in a text file
 */

object TwitterSpark extends App {
  val conf = new SparkConf().setAppName("myStream").setMaster("local[2]").set("spark.hadoop.validateOutputSpecs", "false")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(2))
  val client = new TwitterClient()
  val tweetauth = client.start()
  val inputDstream = TwitterUtils.createStream(ssc, Option(tweetauth.getAuthorization))
  val statuses = inputDstream.map { x => x.getText }
  val words = statuses.flatMap { x => x.split(" ") }
  val hastag = words.filter { x => x.startsWith("#")}
  val tweet=sc.textFile("tweet")
  hastag.foreachRDD(x=>x.coalesce(1, true).saveAsTextFile("temp"))
  val temp=sc.textFile("temp")
  val file=tweet++temp
  file.saveAsTextFile("tweet")
 // hastag.saveAsTextFiles("tweets/tweets")
  ssc.start()
  ssc.awaitTermination()  
}
