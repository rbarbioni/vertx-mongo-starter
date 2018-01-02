package br.com.rbarbioni.vertx;

import br.com.rbarbioni.vertx.route.RouteVerticle;
import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();
        vertx.exceptionHandler(e -> e.printStackTrace());
        vertx.deployVerticle(new RouteVerticle());

    }
}
