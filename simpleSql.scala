// bin/run-example org.apache.spark.examples.mySparkExamples.simpleSql

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

    val people = sc.textFile("/home/junius/git_hub/spark/examples/src/main/resources/people.txt", 4).map(_.split(",")).map(p => Person(p(0), p(1).trim.toInt))
    val other: SchemaRDD = sqlContext.createSchemaRDD(people)
    //val other = new SchemaRDD(sqlContext, null)
    other.registerAsTable("people")
    
    
    /*(0 to 10).map( age => {
      sqlContext.sql("INSERT INTO people (name,age) VALUES(" + age.toString + age + ")" )
    })
    */
    
    //val teenagers = sqlContext.sql("SELECT name, age FROM people WHERE age > 10")
    val teenagers = sqlContext.sql("SET name = junius WHERE age > 29")
    print(teenagers.count)
    
    val tt = sqlContext.sql("SELECT name, age FROM people WHERE age > 10")
    tt.map(t => {print(t(0).toString + " " + t(1))}).count
    //teenagers.saveAsTextFile("/home/junius/git_hub/spark/examples/src/main/resources/people.txt")
    
   
    //people.registerAsTable("people")
  }
}