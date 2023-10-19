package com.sparta.study.task01.app;

import com.sparta.study.task01.constant.MenuCategory;
import com.sparta.study.task01.exception.MenuNotFoundException;
import com.sparta.study.task01.model.Menu;
import com.sparta.study.task01.model.Order;
import com.sparta.study.task01.model.Product;

import java.util.*;

public class KioskApp {
    private final List<Menu> menus = new ArrayList<>();
    private final Order order = new Order();

    public KioskApp() {
        init();
    }

    public boolean start() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println(logo);
        showMenu();
        int select;
        try {
            select = sc.nextInt();
            //선택한 번호가 메뉴 카테고리 길이보다 크면, 주문/주문취소 선택
            int menuLength = MenuCategory.values().length;
            if (select == 0) {
                return true;
            }
            //주문 입력창 수를 벗어난 경우
            if (select > menuLength + 2) {
                throw new IndexOutOfBoundsException();
            }

            if (select > menuLength) {
                //주문 메뉴
                if (select == menuLength + 1) {
                    //주문
                } else {

                    //주문취소
                }
            } else {
                //상품 메뉴
                showProductMenu(MenuCategory.values()[select - 1]);
            }
        } catch (Exception ex) {
            throw new Exception("다시 입력해주세요.");
        }
        return true;
    }

    void showMenu() {
        int idx = 1;
        System.out.println("\"투썸플레이스에 오신걸 환영합니다.\"");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println("[ TWOSOME MENU ]");
        for (int i = 0; i < menus.size(); i++) {
            System.out.printf("%d. %s %n", idx++, menus.get(i).printMenu());
        }
        System.out.println("[ ORDER MENU ]");
        System.out.printf("%d. Order       | 장바구니를 확인 후 주문합니다. %n", idx++);
        System.out.printf("%d. Cancel      | 진행중인 주문을 취소합니다. %n", idx++);

        System.out.printf("%n0. Exit        | 종료합니다. %n", idx++);

    }

    private void showProductMenu(MenuCategory category) throws MenuNotFoundException {
        List<Product> products = menus.stream()
                .filter(m -> m.getName().equals(category.name()))
                .findFirst()
                .orElseThrow(MenuNotFoundException::new)
                .getProducts();

        if (products.isEmpty()) {
            System.out.println("메뉴가 없습니다.");
            return;
        }
        System.out.println("\"투썸플레이스에 오신걸 환영합니다.\"");
        System.out.println("아래 상품메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.printf("[ %s MENU ] %n", category);
        int idx = 1;
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%d. %s %n", idx++, products.get(i).printMenu());
        }
    }

    private void init() {
        //메뉴 등록
        Arrays.stream(MenuCategory.values())
                .forEach(menuCategory -> {
                            Menu menu = new Menu(menuCategory.name(), menuCategory.getDescription());
                            //메뉴 카테고리별 상품 등록
                            menu.addProducts(menuCategory);
                            menus.add(menu);
                        }
                );
    }

    public static final String logo =
            """
                      ___    _____  _    _  _____  _____  _____ ___  ___ _____  ______  _       ___   _____  _____\s
                     / _ \\  |_   _|| |  | ||  _  |/  ___||  _  ||  \\/  ||  ___| | ___ \\| |     / _ \\ /  __ \\|  ___|
                    / /_\\ \\   | |  | |  | || | | |\\ `--. | | | || .  . || |__   | |_/ /| |    / /_\\ \\| /  \\/| |__ \s
                    |  _  |   | |  | |/\\| || | | | `--. \\| | | || |\\/| ||  __|  |  __/ | |    |  _  || |    |  __|\s
                    | | | |   | |  \\  /\\  /\\ \\_/ //\\__/ /\\ \\_/ /| |  | || |___  | |    | |____| | | || \\__/\\| |___\s
                    \\_| |_/   \\_/   \\/  \\/  \\___/ \\____/  \\___/ \\_|  |_/\\____/  \\_|    \\_____/\\_| |_/ \\____/\\____/\s
                                                                                                                  \s
                    """;
}
