package br.com.rbarbioni.vertx.service;

import br.com.rbarbioni.vertx.repository.ProductRepository;
import io.vertx.ext.web.RoutingContext;


public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public void findAll(RoutingContext context){
        this.productRepository.findAll(context);
    }

    public void findById(RoutingContext context){
        this.productRepository.findById(context);
    }

    public void update(RoutingContext context){
        this.productRepository.save(context);
    }

    public void create(RoutingContext context){
        this.productRepository.save(context);
    }
}
