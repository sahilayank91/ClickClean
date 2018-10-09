package sahil.clickclean.DatabaseConnection;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class DBconfig {
    static String urilink = "mongodb://"+"clickclean6@gmail.com"+":"+"click@123"+"@cluster0-shard-00-00-r1tkp.mongodb.net:27017,cluster0-shard-00-01-r1tkp.mongodb.net:27017,cluster0-shard-00-02-r1tkp.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true";
    public static  MongoClientURI uri = new MongoClientURI(
            urilink);

    public static  MongoClient mongoClient = new MongoClient(uri);
    public static MongoDatabase database = mongoClient.getDatabase("cleanclick");


    public static MongoDatabase getDatabase() {
        return database;
    }


}
