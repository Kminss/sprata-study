package com.sparta.study.task01.constant;

import java.util.List;
import java.util.Set;

import static com.sparta.study.task01.constant.ProductOption.*;

/*
* 메뉴를 enum으로 만들어 사용하는 이유
*  - 메뉴는 자주 변경이 되니, Kisosk에서 출력문을 수정하는 것 보다 enum 코드를 변경하여
*    출력 로직에 영향이 없게끔 하기 위함  + 메뉴 관리가 편함
* */
public enum MenuCategory {
    COFFEE("커피 & 라떼 ", List.of(REGULAR, LARGE)),
    DRINK("에이드 & 주스", List.of(REGULAR, LARGE)),
    CAKE("케이크", List.of(PIECE, HALF, WHOLE));
    private final String description;
    private final List<ProductOption> options;

    MenuCategory(String description, List<ProductOption> options) {
        this.description = description;
        this.options = options;
    }

    public String getDescription() {
        return description;
    }

    public List<ProductOption> getOptions() {
        return options;
    }

    public String printOptions(double price) {
        StringBuilder sb = new StringBuilder();
        int idx = 1;
        for (ProductOption option : options) {
            sb.append(String.format("%d. %s(W %.1f) \t", idx++, option.getEng(), option.calculate(price)));
        }
        return sb.toString();
    }
}
