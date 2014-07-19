package org.apache.spark.examples.mySparkExamples

object simplePrac{
  def sub(i: Int, j:Int) = i - j
  
  def main(args : Array[String]){
    println("we started")
    println(System.getProperty("java.library.path"))
    var str = System.getProperty("java.library.path")
    str += ":/home/junius/mesos-0.18.0/build/src/"
    System.setProperty("java.library.path", str)
    println(System.getProperty("java.library.path"))
    
  }
}

