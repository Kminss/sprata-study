package com.sparta.study.task01.exception;

public class MenuNotFoundException extends RuntimeException{
    public MenuNotFoundException() {
        super("메뉴를 찾을 수 없습니다.");
    }
}
