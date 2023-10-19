package com.sparta.study.task01;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    protected String name;
    protected String description;
    protected static final int TAB_SIZE = 17;
    private List<Product> products = new ArrayList<>();

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public List<Product> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String printMenu() {

        return String.format("%-15s | %s", name, description);
    }
}
