package com.rahulamodkar.sparkone;

import java.io.Serializable;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.bson.BSONObject;
import com.mongodb.hadoop.MongoInputFormat; 

public class MongoSpark implements Serializable {

     public static void main(String[] args) {
        
    	JavaSparkContext sc = new JavaSparkContext("local[2]", "Spark Test");

    	Configuration config = new Configuration();
    	config.set("mongo.input.uri", "mongodb://127.0.0.1:27017/myDB.myCollection");

    	JavaPairRDD<Object, BSONObject> mongoRDD = sc.newAPIHadoopRDD(config, com.mongodb.hadoop.MongoInputFormat.class, Object.class, BSONObject.class);

    	long numberOfFilteredElements = mongoRDD.filter(myCollectionDocument -> myCollectionDocument._2().get("site").equals("marfeel.com")).count();

    	System.out.format("Filtered collection size: %d%n", numberOfFilteredElements);
        System.out.println();
        System.out.println("Done !!!");
    }
}

