package com.sparta.study.task01.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> products = new ArrayList<>(); //상품목록
    private int waitNo; //대기 번호

    public void addCart(Product product) {
        products.add(product);
    }

    public double getTotal() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }
}
