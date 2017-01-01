package com.rahulamodkar.sparkone;

/* SimpleApp.java */
import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import java.io.*;
import com.sun.jna.*;
import com.rahulamodkar.sparkone.Ren;

public class SparkJNAApp {
  public static void main(String[] args) {
    String logFile = "C:\\Users\\Rahul\\Downloads\\spark\\README.md"; 
    //SparkConf conf = new SparkConf().setAppName("Simple Application");
    SparkConf conf = new SparkConf().setAppName("SimpleApplication").setMaster("local[2]").set("spark.executor.memory","1g");
    
    JavaSparkContext sc = new JavaSparkContext(conf);
    JavaRDD<String> logData = sc.textFile(logFile).cache();

    long numAs = logData.filter(new Function<String, Boolean>() {
      public Boolean call(String s) { return s.contains("Application"); }
    }).count();

    long numBs = logData.filter(new Function<String, Boolean>() {
      public Boolean call(String s) {
    	  moveFileLocal();
    	  return s.contains("b"); 
      }
    }).count();

    System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
  }
  
  public static void moveFileLocal() {
	  try {
		  Ren.moveFile();
	  } catch(Exception ex ) {
		  
	  }
	  
  }
  
  
}
