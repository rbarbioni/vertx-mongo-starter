package br.com.rbarbioni.vertx;

import br.com.rbarbioni.vertx.route.RouteVerticle;
import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

    public MainVerticle(){
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
    }
    @Override
    public void start() throws Exception {
        super.start();
        vertx.deployVerticle(new RouteVerticle());

    }
}
