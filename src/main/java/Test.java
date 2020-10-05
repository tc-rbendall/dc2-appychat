import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.MongoClientSettings;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static com.mongodb.client.model.Filters.eq;

public class Test {
    public static void main(String [] args) {

        ConnectionString connString = new ConnectionString(
                //"mongodb+srv://<username>:<password>@<cluster-address>/test?w=majority"
                "mongodb://localhost"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase("local");
        MongoCollection<Document> collection = database.getCollection("test");


        //// to change a record:
        collection.updateOne(new Document("name", "frank"),
                new Document("$set", new Document("age", 47)));
        //// end change record


        //// to find a record:
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name2", "george"); // no 'put' = returns full collection
        MongoCursor cursor = collection.find(searchQuery).cursor();
        System.out.println("begin output search results:");
        while (cursor.hasNext()) {
            Document doc = (Document) cursor.next();
            for (String o : doc.keySet()) {
                //outputValues(doc.get(o));
                System.out.printf("%s: %s\n", o, doc.get(o).toString());
            }
        }
        System.out.println("end output search results.");
        //// end find record
    }

    public static void outputValues(Object doc) {
        System.out.println("outputValues");

    }
}
