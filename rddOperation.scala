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
  
  def partitionSum(sc: SparkContext){
    val logFile = "/home/junius/git_hub/spark/examples/src/main/resources/number.txt"
    val logData = sc.textFile(logFile, 4).cache
    //println(logData.map(num => num.toInt).reduce(_ + _).toString)
    val tmpData = logData.map(num => num.toInt).mapPartitionsWithIndex((i, nums) => nums.map(n => (i, n)), false)
    
    val tmp2Data = tmpData.reduceByKey(_ + _).map(i => { 
      println(i._1.toString + " partitionSum " + i._2)      
    }).count
   
    
    /*
    logData.map(num => num.toInt).mapPartitions(nums => { println("one part is over")
      nums.map(i => println( "junius " + i))      
      }).count
    */
  }
  
  def myCoalesce(sc: SparkContext){
      val logFile = "/home/junius/git_hub/spark/examples/src/main/resources/number.txt"
      val logData = sc.textFile(logFile, 3).cache
      val logData1 = logData.flatMap(i => {
        val a: Array[String] = new Array[String](10)
        (0 until 10).map(j => a(j) = i)
        a        
      })
      //logData.mapPartitions(items => items.map(str => str + " junius"), true)
      logData1.mapPartitionsWithIndex((i, nums) => nums.map(n => println(i.toString + " partitionSum " + n)), false).count
      
      val reLogData = logData1.coalesce(4, true)
      
      reLogData.mapPartitionsWithIndex((i, nums) => nums.map(n => println(i.toString + " partitionSum " + n)), false).count
  }
  
  def pairRddReduce(sc: SparkContext){
      val logFile = "/home/junius/git_hub/spark/examples/src/main/resources/news.txt"
      val logData = sc.textFile(logFile, 3).cache
      val wordData = logData.flatMap(line => line.split(" ")).map(word => (word, 1))
      def s(i: Int, j: Int): Int =  i + j
      val result = wordData.combineByKey(n => n, s,s).map(counts => {
        println(counts._1 + " has " + counts._2)
      }).count
    
  }
  
  def pairCountByKey(sc: SparkContext){
      val logFile = "/home/junius/git_hub/spark/examples/src/main/resources/news.txt"
      val logData = sc.textFile(logFile, 3).cache
      val wordData = logData.flatMap(line => line.split(" ")).map(word => (word, 1)).countByKey.map(counts => {
        println(counts._1 + " has " + counts._2)
      })    
  }
  

  def main(args: Array[String]) {
	val logFile = "/home/junius/git_hub/spark/examples/src/main/resources/number.txt" // Should be some file on your system
    val sc = new SparkContext("local[4]", "Simple App")
	pairRddReduce(sc)
	
	val logData = readNumbers(sc)
    //join
    val kvRDD1 = logData.map( i => (i, i))
    val kvRDD2 = logData.map( i => (i, i + i)).join(kvRDD1)
    //kvRDD2.map(print).count
    
    sc.stop

  }
}