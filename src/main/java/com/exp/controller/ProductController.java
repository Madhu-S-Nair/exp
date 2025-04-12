package com.exp.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private record Product(Integer productId,String ProductName,double price){

    }
    List<Product> products = new ArrayList<>(List.of(new Product(1,"iphone",999.9),
    new Product(2,"macpro",1999.9)));


    @GetMapping
    public List<Product> getProducts(){
        return products;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product){
        products.add(product);
        return product;
    }



}
