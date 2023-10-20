package com.sparta.study.task01.constant;

public enum ProductOption {
    //Coffee & Drink Options
    REGULAR("레귤러", "Regular", price -> price),
    LARGE("라지", "Large", price -> price + 0.5),

    //Cake Options
    PIECE("1조각", "Piece", price -> price / 8),
    HALF("절반", "Half", price -> price / 2),
    WHOLE("전체", "Whole", price -> price);

    private final String ko;
    private final String eng;
    private final PriceCalculator priceCalculator;

    ProductOption(String ko, String eng, PriceCalculator priceCalculator) {
        this.ko = ko;
        this.eng = eng;
        this.priceCalculator = priceCalculator;
    }

    public String getKo() {
        return ko;
    }

    public String getEng() {
        return eng;
    }

    public double calculate(double price) {
        return this.priceCalculator.calculate(price);
    }

    @FunctionalInterface
    public interface PriceCalculator {
        double calculate(double price);
    }
}
