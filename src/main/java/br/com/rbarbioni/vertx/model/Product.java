package br.com.rbarbioni.vertx.model;

import br.com.rbarbioni.vertx.exception.RouteException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @JsonCreator
    public Product(
    @JsonProperty("name") String name,
    @JsonProperty("price") BigDecimal price) {
        this();
        this.name = name;
        this.price = price;
    }

    protected Product() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static void validate(RoutingContext routingContext) {
        try{
            Product product = Json.decodeValue(routingContext.getBodyAsString(), Product.class);
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            if(!validator.validate(product).isEmpty()) {
                throw new RouteException(HttpResponseStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (Exception e){
            throw new RouteException(HttpResponseStatus.UNPROCESSABLE_ENTITY, e);
        }
    }
}
