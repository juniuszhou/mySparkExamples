package org.apache.spark.examples.mySparkExamples

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SchemaRDDLike
import org.apache.spark.sql.SchemaRDD

case class Person(name: String, age: Int){}

case class Record(key: Int, value: String)

object simpleSql {  
  
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "Simple App")
    val sqlContext = new SQLContext(sc)
    val people = sc.textFile("/home/junius/develop/spark-1.0.0/examples/src/main/resources/people.txt", 4).map(_.split(",")).map(p => Person(p(0), p(1).trim.toInt))
    //val other: SchemaRDD = new SchemaRDD(people)
    val other = new SchemaRDD(sqlContext, null)
    other.registerAsTable("people")
    
    //people.registerAsTable("people")
    val out = people.map(p => println(p.name + " " + p.age))
    out.count 
    
    val rdd = sc.parallelize((1 to 100).map(i => Record(i, s"val_$i")))
    // Any RDD containing case classes can be registered as a table.  The schema of the table is
    // automatically inferred using scala reflection.
    rdd.registerAsTable("records")
    
    //people.registerAsTable("people")
  }
}