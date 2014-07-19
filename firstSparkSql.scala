package org.apache.spark.examples.mySparkExamples

import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

//case class Person(name: String, age: Int)

object firstSparkSql {
  def main(args: Array[String]) {
    val sc = new SparkContext("local[4]", "Simple App") // An existing SparkContext.
    val sqlContext = new SQLContext(sc)
  }
}