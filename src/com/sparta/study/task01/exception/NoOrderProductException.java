package com.sparta.study.task01.exception;

public class NoOrderProductException extends RuntimeException {
    public NoOrderProductException() {
        super("주문할 상품이 없습니다.");
    }
}
