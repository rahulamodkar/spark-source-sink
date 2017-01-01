package com.rahulamodkar.sparkone;

/* SimpleApp.java */
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;

public class CustomProcessApp {
  public static void main(String[] args) {
    String logFile = "C:\\Users\\Rahul\\Downloads\\spark\\README.md"; 
    //SparkConf conf = new SparkConf().setAppName("Simple Application");
    SparkConf conf = new SparkConf().setAppName("SimpleApplication").setMaster("local[2]").set("spark.executor.memory","1g");
    
    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<String> logData = sc.textFile(logFile).cache();

    long numAs = logData.filter(new Function<String, Boolean>() {
      public Boolean call(String s) { return customProcess(s, "Application"); }
    }).count();

    long numBs = logData.filter(new Function<String, Boolean>() {
      public Boolean call(String s) { return customProcess(s, "b"); }
    }).count();

    System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
  }
  
  private static boolean customProcess(String sText, String searchString) {
	  
	  return sText.contains(searchString);
  }
}
