package com.sparta.study.task01.model;

import com.sparta.study.task01.constant.MenuCategory;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    protected String name;
    protected String description;
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

    public String printMenu() {
        return String.format("%-15s | %s", name, description);
    }

    public void addProducts(MenuCategory category) {

        switch (category) {
            case COFFEE -> this.products =
                    List.of(
                            Product.of("Sinchon Coffee", "맛있는 신촌커피", 6.1),
                            Product.of("Americano", "맛있는 아메리카노", 4.5),
                            Product.of("Coffee Latte", "맛있는 카페라떼", 5.0)
                    );
            case DRINK -> this.products =
                    List.of(
                            Product.of("GrapeFruit Ade", "맛있는 자몽에이드", 5.5),
                            Product.of("Shine Muscat Green Grape Ade", "맛있는 샤인머스캣청포도에이드", 6.3),
                            Product.of("Jeju Matcha Frappe", "맛있는 제주말차프라페", 5.8)
                    );

            case CAKE -> this.products =
                    List.of(
                            Product.of("Strawberry Chocolate Fresh Cream Cake", "한가득 올린 상큼한 딸기, 크런치 초코볼이 초콜릿 생크림 사이사이 씹히는 투썸 시그니처 케이크", 35.0),
                            Product.of("Mascarpone Tiramisu", "마스카포네 치즈 크림과 에스프레소 커피 시럽이 촉촉하게 어우러진 이탈리안 무스 케이크", 36.0),
                            Product.of("New York Cheese Cake", "부드럽고 촉촉한 식감과 진한 치즈의 맛을 느낄 수 있는 정통 뉴욕 스타일의 구움 치즈 케이크", 35.0)
                    );
        }
    }
}
