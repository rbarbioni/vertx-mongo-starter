package br.com.rbarbioni.vertx.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    public Product(Long id, String name, BigDecimal price) {
        this();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product() {
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
}
