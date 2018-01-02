package br.com.rbarbioni.vertx.service;

import br.com.rbarbioni.vertx.repository.ProductRepository;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;


public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public void update(RoutingContext context){

        this.productRepository.findById(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));

                
                res.result().stream()
                    .findFirst()
                    .or


            }else{
                throw new RuntimeException(res.cause());
            }
        });

        this.productRepository.save(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                throw new RuntimeException(res.cause());
            }
        });
    }

    public void create(RoutingContext context){
        this.productRepository.save(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                throw new RuntimeException(res.cause());
            }
        });
    }

    public void findAll(RoutingContext context){
        this.productRepository.findAll(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                throw new RuntimeException(res.cause());
            }
        });
    }

    public void findById(RoutingContext context){
        this.productRepository.findById(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                throw new RuntimeException(res.cause());
            }
        });
    }

    public void remove (RoutingContext context){
        this.productRepository.remove(context, res -> {

            if (res.succeeded()) {
                context.response().setStatusCode(200).end();
            }else{
                throw new RuntimeException(res.cause());
            }
        });
    }
}
