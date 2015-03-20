name := "twitter spark"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
                      "org.apache.spark" %% "spark-core" % "1.2.1",
                      "org.apache.spark" %% "spark-streaming" % "1.2.1",
                      "org.apache.spark" %% "spark-streaming-twitter" % "1.2.1",
		      "org.apache.spark" %% "spark-sql" % "1.2.1"
                    )
