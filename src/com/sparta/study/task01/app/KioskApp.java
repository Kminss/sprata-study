package com.sparta.study.task01.app;

import com.sparta.study.task01.constant.MenuCategory;
import com.sparta.study.task01.exception.MenuNotFoundException;
import com.sparta.study.task01.exception.NoOrderProductException;
import com.sparta.study.task01.model.Menu;
import com.sparta.study.task01.model.Order;
import com.sparta.study.task01.model.Product;

import java.util.*;

public class KioskApp {
    private final List<Menu> menus = new ArrayList<>();
    private final Order order = new Order();
    private int waitNo = 1;

    //
    private static final int ORDER_MENU_SIZE = 2;
    private static final int MENU_CATEGORY_SIZE = MenuCategory.values().length;
    private static final int INVALID_SELECT = MENU_CATEGORY_SIZE + ORDER_MENU_SIZE;

    //COMMAND
    private static final int ORDER = 1;
    private static final int CANCEL = 2;

    //Exception Msg
    private static final String INVALID_SELECT_MESSAGE = "다시 입력해주세요";

    public KioskApp() {
        init();
    }

    public boolean start() {
        Scanner sc = new Scanner(System.in);
        try {
            showMenu();
            int select;
            select = sc.nextInt();
            //주문 입력창 수를 벗어난 경우
            if (select > INVALID_SELECT || select < 1) {
                throw new IndexOutOfBoundsException(INVALID_SELECT_MESSAGE);
            }

            menuProcess(sc, select);
            //선택한 번호가 메뉴 카테고리 길이보다 크면, 주문/주문취소 선택
        } catch (InputMismatchException ex) {
            throw new InputMismatchException(INVALID_SELECT_MESSAGE);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            this.start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            sc.close();
            return false;
        }
        return true;
    }

    private void menuProcess(Scanner sc, int select) {
        if (select > MENU_CATEGORY_SIZE) {
            orderMenuProcess(sc, select);
        } else {
            productMenuProcess(sc, select);
        }
    }

    private void productMenuProcess(Scanner sc, int select) {
        //메뉴보기
        MenuCategory category = MenuCategory.values()[select - 1];
        List<Product> products = menus.stream()
                .filter(m -> m.getName().equals(category.name()))
                .findFirst()
                .orElseThrow(MenuNotFoundException::new)
                .getProducts();

        //상품 메뉴
        showProductMenu(products, category);
        select = sc.nextInt();

        //상품 선택
        Product product = products.get(select - 1);
        showBuyProduct(product);
        select = sc.nextInt();
        //주문 결정
        switch (select) {
            case 1 -> addCart(product);
            case 2 -> this.start();
            default -> throw new IndexOutOfBoundsException(INVALID_SELECT_MESSAGE);
        }
    }

    private void orderMenuProcess(Scanner sc, int select) {
        //주문 메뉴
        switch (command) {
            case ORDER -> orderProcess(sc);
            case CANCEL -> cancelOrder();
            default -> throw new MenuNotFoundException();
        }
    }

    private void addCart(Product product) {
        order.addProduct(product);
        this.start();
    }

    private void cancelOrder() {
        System.out.println("진행하던 주문이 취소되었습니다.");
        order.cancel();
        this.start();
    }

    private void requestOrder() {

        if (order.getProducts().isEmpty()) {
            throw new NoOrderProductException();
        }

        System.out.println("주문이 완료되었습니다!");
        order.setWaitNoAndClearCart(waitNo++);
        System.out.printf("대기번호는 [ %d ] 번 입니다.", order.getWaitNo());
        try {
            for (int i = 3; i == 1; i--) {
                System.out.printf("%d초 후 메뉴판으로 돌아갑니다.", i);
                Thread.sleep(1000);
            }
            this.start();
        } catch (InterruptedException ignored) {
        }
    }


    private void showMenu() {
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
    }

    private void showBuyProduct(Product product) {
       /* "Hamburger     | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거"
        위 메뉴를 장바구니에 추가하시겠습니까?
        1. 확인        2. 취소*/
        System.out.println(product.printMenu());
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
    }

    private void showOrder() {
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println("[ Orders ]");

        int idx = 1;
        for (Product product : order.getProducts()) {
            System.out.printf("%d. %s %n", idx++, product.printMenu());
        }

        System.out.println("[ Total ]");
        System.out.printf("W %.1f %n", order.getTotal());

        System.out.println("1. 주문      2. 메뉴판");
    }

    private void showProductMenu(List<Product> products, MenuCategory category) {

        System.out.println("\"투썸플레이스에 오신걸 환영합니다.\"");
        System.out.println("아래 상품메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.printf("[ %s MENU ] %n", category);
        int idx = 1;
        for (Product product : products) {
            System.out.printf("%d. %s %n", idx++, product.printMenu());
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
}
