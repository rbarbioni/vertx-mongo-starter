package br.com.rbarbioni.vertx.route;

import br.com.rbarbioni.vertx.exception.RouteException;
import br.com.rbarbioni.vertx.model.Product;
import br.com.rbarbioni.vertx.service.ProductService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteVerticle extends AbstractVerticle {

  private Logger log = LoggerFactory.getLogger(getClass());

  private static final String PATH = "/product";


  public RouteVerticle() {
    this.productService = new ProductService();
  }

  private final ProductService productService;

  @Override
  public void start() {

    Router router = Router.router(getVertx());

    // setup
    router.route("/*").handler(this::httpFilter);
    router.route().failureHandler(this::sendResponseError);
    router.exceptionHandler(this::logError);
    router.route().handler(BodyHandler.create());

    // routes
    router.get(PATH).handler(productService::findAll);
    router.get(PATH.concat("/:id")).handler(productService::findById);

    router.post().handler(Product::validate);
    router.post(PATH).handler(productService::create);

    router.put(PATH.concat("/:id")).handler(productService::update);
    router.delete(PATH.concat("/:id")).handler(productService::delete);

    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
  }

  private void logError(Throwable throwable) {
    log.error("Error", throwable);
  }

  private void httpFilter(RoutingContext context) {
    log.debug("Routing {}", context.request().rawMethod() + " - " + context.request().path());
    context.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
    context.next();
  }

  private void sendResponseError(RoutingContext context) {

    final RouteException exception = (RouteException) context.failure();

    log.error("Error", exception.getCause());

    final JsonObject json = new JsonObject()
      .put("timestamp", System.nanoTime())
      .put("status", exception.getStatusCode())
      .put("error", exception.getMessage())
      .put("path", context.request().path());

    String error = json.encodePrettily();
    context.response().end(error);
    log.debug("Routing Error {}", error);
  }
}
