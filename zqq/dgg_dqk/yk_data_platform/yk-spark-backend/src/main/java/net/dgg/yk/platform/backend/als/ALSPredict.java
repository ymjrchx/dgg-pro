package net.dgg.yk.platform.backend.als;

import com.google.gson.JsonObject;
import lombok.*;
import net.dgg.yk.common.Toolkit;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
public class ALSPredict {

    private JavaSparkContext sc;
    private SparkSession session;

    private ALSModel alsModel;

    private int trainMaxIteration = 5;
    private double trainRegularization = 0.01;

    public void train(JavaRDD<Rating> ratings) {
        ALS als = new ALS().setMaxIter(trainMaxIteration).setRegParam(trainRegularization).setUserCol("user").setItemCol("item").setRatingCol("rating").setPredictionCol("predictValue");
        Dataset<Row> trainData = session.createDataFrame(ratings, Rating.class);
        alsModel = als.fit(trainData);
        alsModel.setColdStartStrategy("drop");
    }

    public Double predict(@NonNull Integer user, @NonNull Integer item) {
        JavaRDD<Rating> ratings = sc.parallelize(Arrays.asList(new Rating(user, item, null, Clock.systemUTC().millis())));
        SQLContext sql = session.sqlContext();
        Dataset<Row> ratingRow = sql.createDataFrame(ratings, Rating.class);
        JsonObject jsonObject = Toolkit.JSON.parseJsonObject(alsModel.transform(ratingRow).toJSON().toJavaRDD().take(1).get(0));
        return jsonObject.get("predictValue").getAsDouble();
    }

    public JavaRDD<String> recommendAllUser(int items) {
        Dataset<Row> rows = alsModel.recommendForAllUsers(items);
        return rows.toJSON().toJavaRDD();
    }

    public JavaRDD<String> recommendAllItem(int users) {
        Dataset<Row> rows = alsModel.recommendForAllItems(users);
        return rows.toJSON().toJavaRDD();
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rating implements Serializable {

        private Integer user;

        private Integer item;

        private Float rating;

        private Long timestamp;

    }

    @SneakyThrows
    public static void main(String[] args) {

        SparkConf conf = new SparkConf();
        conf.set("spark.master", "local[16]");

        SparkSession session = SparkSession.builder().config(conf).getOrCreate();
        JavaSparkContext sc = new JavaSparkContext(session.sparkContext());

        ALSPredict latentFactorModel = ALSPredict.builder().sc(sc).session(session).trainMaxIteration(10).trainRegularization(0.01).build();

        List<String> ratingRaw = new ArrayList<>();

        ratingRaw.add("1,1,0.5");
        ratingRaw.add("1,2,0.7");
        ratingRaw.add("1,3,0.6");

        ratingRaw.add("2,2,0.7");
        ratingRaw.add("2,3,0.6");
        ratingRaw.add("2,1,0.7");

        JavaRDD<Rating> train = sc.parallelize(ratingRaw)
                .map(line -> line.split(","))
                .map(cols -> new Rating(Integer.parseInt(cols[0]), Integer.parseInt(cols[1]), Float.parseFloat(cols[2]), Clock.systemUTC().millis()));

        latentFactorModel.train(train);

        JavaRDD<String> recommandItemList = latentFactorModel.recommendAllUser(3);
        recommandItemList.collect().forEach(System.out::println);

        /**
         JavaRDD<String> recommendUserList = latentFactorModel.recommendAllItem(3);
         recommendUserList.collect().forEach(System.out::println);
         */
    }

}
