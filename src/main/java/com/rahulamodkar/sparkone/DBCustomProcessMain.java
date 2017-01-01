package com.rahulamodkar.sparkone;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class DBCustomProcessMain implements Serializable {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DBCustomProcessMain.class);
   
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL_USERNAME = "sqluser123";
    private static final String MYSQL_PWD = "sqluserpw";
    private static final String MYSQL_CONNECTION_URL =
            "jdbc:mysql://localhost/feedback?user=" + MYSQL_USERNAME + "&password=" + MYSQL_PWD;

    private static final JavaSparkContext sc =
            new JavaSparkContext(new SparkConf().setAppName("SparkJdbcDs").setMaster("local[*]"));

    private static final SQLContext sqlContext = new SQLContext(sc);

    public static void main(String[] args) {
        //Data source options
        Map<String, String> options = new HashMap<String, String>();
        options.put("driver", MYSQL_DRIVER);
        options.put("url", MYSQL_CONNECTION_URL);
        options.put("dbtable",
                    "(SELECT id, myuser, email, webpage, datum, summary, COMMENTS from feedback.comments ) as fcomments");
        //WHERE myuser='Test15'
        options.put("partitionColumn", "id");
        options.put("lowerBound", "1");
        options.put("upperBound", "100");
        options.put("numPartitions", "1");

        //Load MySQL query result as DataFrame
        DataFrame jdbcDF = sqlContext.load("jdbc", options);
        jdbcDF = jdbcDF.cache();
        Column column = new Column("webpage");
        //DataFrame tableRows = jdbcDF.foreach(customprintln());
        DataFrame tableRows = sqlContext.load("jdbc", options);
        /*try {
        	Thread.sleep(60000);
        } catch(Exception ex) {
        	System.out.println("exception ...");
        }*/
        List<Row> employeeFullNameRows = tableRows.collectAsList();

        System.out.println();
        System.out.println("rows with website equals testWebPage99");
        for (Row employeeFullNameRow : employeeFullNameRows) {
            LOGGER.info(employeeFullNameRow);
            System.out.println(employeeFullNameRow.toString());
        }
        System.out.println();
        System.out.println();
        
        Column columnID = new Column("id");
        DataFrame tableRows1 = jdbcDF.filter(columnID.gt("1504"));
        
        List<Row> employeeFullNameRows1 = tableRows1.collectAsList();

        System.out.println();
        System.out.println("rows with id grt 1504");
        for (Row employeeFullNameRow : employeeFullNameRows1) {
            LOGGER.info(employeeFullNameRow);
            System.out.println(employeeFullNameRow.toString());
        }
        System.out.println();
        System.out.println();
        
    }
    
    /*private static boolean customprintln() {
    	
    }*/
}

