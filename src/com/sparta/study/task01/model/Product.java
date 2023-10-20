package com.sparta.study.task01.model;
//커피, 디저트
public class Product extends Menu{
    private double price;

    public Product(String name, String description, double price) {
        super(name, description);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
    public String printInfo() {
        return String.format("%-38s |  W %.1f  |  %s", name, price, description);
    }
}
