package br.com.rbarbioni.vertx.repository;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.MongoClientDeleteResult;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

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


    public void save (RoutingContext context, Handler<AsyncResult<String>> handler){
        this.mongoClient.save(COLLECTION_NAME, context.getBodyAsJson(), handler);
    }

    public void findOneAndUpdate (RoutingContext context, Handler<AsyncResult<JsonObject>>  handler){
        this.mongoClient.findOneAndUpdate(COLLECTION_NAME,  new JsonObject().put("id", context.request().getParam("id")), context.getBodyAsJson(), handler);
    }

    public void delete (RoutingContext context, Handler<AsyncResult<JsonObject>> handler){
        this.mongoClient.findOneAndDelete(COLLECTION_NAME, new JsonObject().put("id", context.request().getParam("id")), handler);
    }

    public void findAll (RoutingContext context, Handler<AsyncResult<List<JsonObject>>> handler){
        this.mongoClient.find(COLLECTION_NAME, new JsonObject(), handler);
    }

    public void findById (RoutingContext context, Handler<AsyncResult<List<JsonObject>>> handler){
        this.mongoClient.find(COLLECTION_NAME, new JsonObject().put("id", context.request().getParam("id")), handler);
    }
}
