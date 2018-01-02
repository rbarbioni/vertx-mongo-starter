package br.com.rbarbioni.vertx.route;

import br.com.rbarbioni.vertx.service.ProductService;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class RouteVerticle extends AbstractVerticle {

    private static final String PATH = "/product";

    public RouteVerticle() {
        this.productService = new ProductService();
    }

    private final ProductService productService;

    @Override
    public void start() {

        Router router = Router.router(super.vertx);

        // Http Filter with Error Handling
        router.route("/*").handler(routingContext -> httpFilter(routingContext));
        router.exceptionHandler(e -> e.printStackTrace());

        router.route().handler(BodyHandler.create());
        router.get(PATH).handler(productService::findAll);
        router.get( PATH.concat("/:id")).handler(productService::findById);
        router.post(PATH).handler(productService::create);
        router.put(PATH.concat("/:id")).handler(productService::update);
        router.delete(PATH.concat("/:id")).handler(productService::delete);

        router.exceptionHandler(throwable -> throwable.printStackTrace());

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(8888);
    }

    private void httpFilter(RoutingContext routingContext){
        System.out.println("Http Filter");
        routingContext.next();
    }

    private void sendResponseError(RoutingContext context){
        final JsonObject json = new JsonObject()
            .put("timestamp", System.nanoTime())
            .put("status", context.statusCode())
            .put("error", HttpResponseStatus.valueOf(context.statusCode()).reasonPhrase())
            .put("path", context.request().path());

        final String message = context.get("message");

        if(message != null) {
            json.put("message", message);
        }

        context.response().putHeader(HttpHeaders.CONTENT_TYPE,"application/json; charset=utf-8");
        context.response().end(json.encodePrettily());
    }
}
