package org.apache.spark.examples.mySparkExamples

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD


object rddOperation{
  def readTextFile(sc: SparkContext) = {
    val logFile = "/home/junius/git_hub/spark/examples/src/main/resources/news.txt"
    val logData = sc.textFile(logFile, 4)
    val wordData: RDD[String] = logData.flatMap(i => i.split(" "))
    val letterData = logData.flatMap(line => {
      var all: Array[Char] = new Array[Char](line.length())
      
      (0 until (line.length())).map(i => (all.update(i, line.charAt(i))))
      all
      
    })
    letterData.map(println).count
  }
  
  def readNumbers(sc: SparkContext) = {
    val logFile = "/home/junius/git_hub/spark/examples/src/main/resources/number.txt"
    val logData = sc.textFile(logFile, 4)
    logData.flatMap(i => i.split(" ")).map(println)
    logData
  }
  
  def wordCount(sc: SparkContext){
    val logFile = "/home/junius/git_hub/spark/examples/src/main/resources/news.txt"
    val logData = sc.textFile(logFile, 4)
    val valcounts = logData.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_ + _, 2)
  }
  
  def main(args: Array[String]) {
	val logFile = "/home/junius/git_hub/spark/examples/src/main/resources/number.txt" // Should be some file on your system
    val sc = new SparkContext("local[4]", "Simple App")
	readTextFile(sc)
	
	val logData = readNumbers(sc)
    //join
    val kvRDD1 = logData.map( i => (i, i))
    val kvRDD2 = logData.map( i => (i, i + i)).join(kvRDD1)
    //kvRDD2.map(print).count
    
    sc.stop
    
    
  }
}