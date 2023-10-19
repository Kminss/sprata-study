package com.sparta.study.task01.constant;
/*
* 메뉴를 enum으로 만들어 사용하는 이유
*  - 메뉴는 자주 변경이 되니, Kisosk에서 출력문을 수정하는 것 보다 enum 코드를 변경하여
*    출력 로직에 영향이 없게끔 하기 위함  + 메뉴 관리가 편함
* */
public enum MenuCategory {
    COFFEE("커피 & 라떼 "), DRINK("에이드 & 주스"), CAKE("케이크");
    private final String description;

    MenuCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
