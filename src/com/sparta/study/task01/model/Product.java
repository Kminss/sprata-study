package com.sparta.study.task01.model;
import com.sparta.study.task01.constant.ProductOption;

//커피, 디저트
public class Product extends Menu{
    private double price;
    private ProductOption option;
    private double resultPrice;

    private Product(String name, String description, double price, ProductOption option) {
        super(name, description);
        this.price = price;
        this.option = option;

        if (option != null) {
            this.resultPrice = option.calculate(this.price);
        }
    }
    public static Product of(String name, String description, double price) {
        return new Product(name, description, price, null);
    }
    public static Product of(Product menuProduct, ProductOption option) {
        return new Product(
                menuProduct.getName(),
                menuProduct.getDescription(),
                menuProduct.getPrice(),
                option
                );
    }
    public double getPrice() {
        return price;
    }


    public String printInfo() {
        return String.format("%-38s |  W %.1f  |  %s", name, price, description);
    }

    public String printInfoWithOption() {
        return String.format("%s (%s) %10s |  W %.1f  |  %s", name, option.getEng(), " ", option.calculate(price), description);
    }
    public ProductOption getOption() {
        return option;
    }

    public double getResultPrice() {
        return resultPrice;
    }
}
