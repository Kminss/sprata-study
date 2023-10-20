package com.sparta.study.task01.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private List<Product> products = new ArrayList<>(); //상품목록
    private Map<String, Integer> countMap = new HashMap<>();
    private int waitNo; //대기 번호

    public void addProduct(Product product) {
        String key = product.getName() + product.getOption();
        Integer count = countMap.getOrDefault(key, 0);

        products.add(product);
        countMap.put(key, ++count);
    }

    public String printTotal() {
        double total = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
        return String.format("W %.1f", total);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setWaitNoAndClearCart(int waitNo) {
        this.waitNo = waitNo;
        products.clear();
        countMap.clear();
    }

    public String printOrderProductsInfo() {
        StringBuilder sb = new StringBuilder();
        products.stream()
                .distinct()
                .forEach(product ->
                        sb.append(
                                String.format(
                                        "%s (%s) %10s |  W %.1f  |  %d개  |  %s %n",
                                        product.getName(),
                                        product.getOption()
                                                .getEng(),
                                        " ",
                                        product.getResultPrice(),
                                        countMap.get(product.getName() + product.getOption()),
                                        product.getDescription()
                                )
                        )
                );
        return sb.toString();
    }
    public void cancel() {
        products.clear();
    }

    public int getWaitNo() {
        return waitNo;
    }
}
