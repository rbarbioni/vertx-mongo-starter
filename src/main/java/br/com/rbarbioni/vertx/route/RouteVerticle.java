package br.com.rbarbioni.vertx.route;

import br.com.rbarbioni.vertx.service.ProductService;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class RouteVerticle extends AbstractVerticle {

    private final ProductService productService;

    public RouteVerticle() {
        this.productService = new ProductService();
    }

    @Override
    public void start() {

        Router router = Router.router(super.vertx);
        router.route("/*").handler(routingContext -> {
            System.out.println("Http Filter");
            routingContext.next();
        });

        router.get("/product").handler(productService::findAll);
        router.get("/product/:id").handler(productService::findById);
        router.post("/product").handler(productService::create);
        router.put("/product/:id").handler(productService::update);

        router.exceptionHandler(throwable -> throwable.printStackTrace());
        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(8888);
    }
}
