package br.com.rbarbioni.vertx.repository;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

public class ProductRepository {

    private static final String COLLECTION_NAME= "product";

    private final MongoClient mongoClient;

    public ProductRepository (){
        mongoClient = MongoClient.createNonShared(Vertx.vertx(),
            new JsonObject()
                .put("connection_string", "mongodb://localhost:27017/test")
                .put("db_name", "test")
                .put("username", "test")
                .put("password", "test")
        );
    }


    public void save (RoutingContext context){
        this.mongoClient.save(COLLECTION_NAME, context.getBodyAsJson(), res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                throw new RuntimeException(res.cause());
            }
        });
    }

    public void remove (RoutingContext context){
        this.mongoClient.removeDocument(COLLECTION_NAME, context.getBodyAsJson(), res -> {

            if (res.succeeded()) {
                context.response().setStatusCode(200).end();
            }else{
                throw new RuntimeException(res.cause());
            }
        });
    }

    public void findAll (RoutingContext context){
        this.mongoClient.find(COLLECTION_NAME, new JsonObject(), res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                throw new RuntimeException(res.cause());
            }
        });
    }

    public void findById (RoutingContext context){
        this.mongoClient.find(COLLECTION_NAME, new JsonObject().put("id", context.request().getParam("id")), res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                throw new RuntimeException(res.cause());
            }
        });
    }
}
