package com.sparta.study.task01.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private List<Product> products = new ArrayList<>(); //상품목록
    private Map<String, Integer> countMap = new HashMap<>();
    private double totalPrice = 0;
    private int waitNo; //대기 번호

    public Order(List<Product> products, int waitNo) {
        this.products = products;
        this.waitNo = waitNo;

        products.forEach(product -> {
                    String key = product.getName() + product.getOption();
                    Integer count = countMap.getOrDefault(key, 0);

                    countMap.put(key, ++count);
                    totalPrice += product.getResultPrice();
                });
    }

    public String printTotal() {
        double total = products.stream()
                .mapToDouble(Product::getResultPrice)
                .sum();
        return String.format("W %.1f", total);
    }

    public String printOrderProductsInfo() {
        StringBuilder sb = new StringBuilder();
        products.stream()
                .distinct()
                .forEach(product -> {
                    String name = product.getName() + " " + product.getOption().getEng();
                            sb.append(
                                    String.format(
                                            "%-10s |  W %.1f  |  %d개  |  %s %n",
                                            name,
                                            product.getResultPrice(),
                                            countMap.get(product.getName() + product.getOption()),
                                            product.getDescription()
                                    )
                            );
                        }
                );
        return sb.toString();
    }
    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


    public int getWaitNo() {
        return waitNo;
    }
}
