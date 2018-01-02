package br.com.rbarbioni.vertx.service;

import br.com.rbarbioni.vertx.repository.ProductRepository;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.HttpStatusException;


public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public void update(RoutingContext context){

        this.productRepository.findById(context, findRes -> {

            if (findRes.succeeded()) {
                findRes.result()
                    .stream()
                    .findFirst()
                    .map(data -> {
                        this.productRepository.save(context, res -> {

                            if (res.succeeded()) {
                                context.response().end(Json.encodePrettily(res.result()));
                            }else{
                                context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
                            }
                        });
                        return null;
                    })
                    .orElseGet(() -> {
                        context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
                        return null;
                    });
            }else{
                context.response().setStatusCode(500).end();
            }
        });

        this.productRepository.save(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                context.response().setStatusCode(500).end();
            }
        });
    }

    public void create(RoutingContext context){
        this.productRepository.save(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                context.response().setStatusCode(500).end();
            }
        });
    }

    public void findAll(RoutingContext context){
        this.productRepository.findAll(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                context.response().setStatusCode(500).end();
            }
        });
    }

    public void findById(RoutingContext context){
        this.productRepository.findById(context, res -> {

            if (res.succeeded() && res.result() != null) {
                res.result()
                    .stream()
                    .findFirst()
                    .map(data -> {
                        context.response().end(Json.encodePrettily(data));
                        return null;
                    })
                    .orElseThrow(() -> new HttpStatusException(HttpResponseStatus.NOT_FOUND.code()));
            }else{
                context.response().setStatusCode(500).end();
            }
        });
    }

    public void delete (RoutingContext context){
        this.productRepository.findById(context, resFind -> {

            if (resFind.succeeded()) {
                resFind.result()
                    .stream()
                    .findFirst()
                    .map(data -> {
                        this.productRepository.delete(context, res -> {

                            if (res.succeeded()) {
                                context.response().setStatusCode(200).end();
                            }else{
                                context.response().setStatusCode(500).end();
                            }
                        });
                        return null;
                    })
                    .orElseThrow(() -> new HttpStatusException(HttpResponseStatus.NOT_FOUND.code()));;
            }else{
                context.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
            }
        });
    }
}
