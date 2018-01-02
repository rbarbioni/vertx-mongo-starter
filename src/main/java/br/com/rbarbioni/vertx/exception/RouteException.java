package br.com.rbarbioni.vertx.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.VertxException;
import io.vertx.ext.web.handler.impl.HttpStatusException;

public class RouteException extends VertxException {

    private int statusCode = 500;

    public RouteException(HttpResponseStatus status) {
        super(status.reasonPhrase(), new HttpStatusException(status.code()));
        this.statusCode = status.code();
    }

    public RouteException(String message) {
        super(message);
    }

    public RouteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteException(Throwable cause) {
        super(cause);
    }

    public int getStatusCode() {
        return statusCode;
    }
}