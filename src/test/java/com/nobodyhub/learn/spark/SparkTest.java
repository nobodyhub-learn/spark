package com.nobodyhub.learn.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import scala.Tuple2;

import java.util.Arrays;

/**
 * @author yan_h
 * @since 2018/6/28
 */
public class SparkTest {

    @Test
    public void test() {
        SparkConf conf = new SparkConf()
                .setMaster("local")
                .setAppName("spark-learn");
        try (JavaSparkContext sc = new JavaSparkContext(conf)) {
            JavaRDD<String> input = sc.textFile(getClass().getResource("count.txt").getPath());
            JavaRDD<String> words = input.flatMap(x -> Arrays.asList(x.split(" ")).iterator());
            JavaPairRDD<String, Integer> counts = words.mapToPair(x -> new Tuple2<>(x, 1))
                    .reduceByKey((x, y) -> x + y);
            counts.saveAsTextFile("target/spark-output");
        }
    }

}