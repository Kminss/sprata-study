package com.sparta.study.task01;

public class Main {


    public static void main(String[] args) {
        boolean ended = false;

        while (!ended) {
            try {
                ended = new KioskApp().start();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
