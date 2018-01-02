package br.com.rbarbioni.vertx.service;

import br.com.rbarbioni.vertx.exception.RouteException;
import br.com.rbarbioni.vertx.repository.ProductRepository;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;


public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public void update(RoutingContext context){

      this.productRepository.findOneAndUpdate(context, res -> {
		  if (res.succeeded() || res.result() != null) {
			  context.response().end(Json.encodePrettily(res.result()));
          }else if(res.cause() != null){
              context.fail(new RouteException(res.cause()));
		  }else{
			  context.fail(new RouteException(HttpResponseStatus.INTERNAL_SERVER_ERROR));
		  }
      });
    }

    public void create(RoutingContext context){
        this.productRepository.save(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                context.fail(new RouteException(HttpResponseStatus.INTERNAL_SERVER_ERROR));
            }
        });
    }

    public void findAll(RoutingContext context){
        this.productRepository.findAll(context, res -> {

            if (res.succeeded()) {
                context.response().end(Json.encodePrettily(res.result()));
            }else{
                context.fail(new RouteException(HttpResponseStatus.INTERNAL_SERVER_ERROR));
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
                    .orElseGet(() -> {
                        context.fail(new RouteException(HttpResponseStatus.NOT_FOUND));
                        return null;
                    });
            }else{
                context.fail(new RouteException(HttpResponseStatus.INTERNAL_SERVER_ERROR));
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
                                context.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code()).end();
                            }else{
                                context.fail(new RouteException(HttpResponseStatus.INTERNAL_SERVER_ERROR));
                            }
                        });
                        return null;
                    })
                    .orElseThrow(() -> new RouteException(HttpResponseStatus.NOT_FOUND));
            }else{
                context.fail(new RouteException(HttpResponseStatus.INTERNAL_SERVER_ERROR));
            }
        });
    }
}