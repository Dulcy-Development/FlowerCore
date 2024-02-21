/*package me.emmiesa.flowercore.database;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;


@Getter
public class MongoHandler {

    private final MongoClient client;
    private final MongoDatabase database;

    public MongoHandler(boolean uri, String connectionString, String host, int port, String database, boolean authentication, String username, String password){
        if (uri){
            client = new MongoClient(new MongoClientURI(connectionString));
        } else {
            if (authentication){
                client = new MongoClient(new ServerAddress(host, port), MongoCredential.createCredential(username, database, password.toCharArray()), MongoClientOptions.builder().build());
            } else{
                client = new MongoClient(host, port);
            }
        }
        this.database = client.getDatabase(database);
    }

}*/
