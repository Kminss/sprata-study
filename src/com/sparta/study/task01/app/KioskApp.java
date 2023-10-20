package com.sparta.study.task01.app;

import com.sparta.study.task01.constant.MenuCategory;
import com.sparta.study.task01.constant.OrderCommand;
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

    //Exception Msg
    private static final String INVALID_SELECT_MESSAGE = "다시 입력해주세요";

    public KioskApp() {
        init();
    }

    public boolean start() {
        Scanner sc = new Scanner(System.in);
        try {
            showMenu();
            int select = sc.nextInt() - 1;
            //주문 입력창 수를 벗어난 경우
            if (select > INVALID_SELECT || select <= 0) {
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
        if (select >= MENU_CATEGORY_SIZE) {
            orderMenuProcess(sc, OrderCommand.values()[select - MENU_CATEGORY_SIZE]);
        } else {
            productMenuProcess(sc, MenuCategory.values()[select]);
        }
    }

    private void productMenuProcess(Scanner sc, MenuCategory category) {
        //메뉴보기
        Menu menu = menus.stream()
                .filter(m -> m.getName().equals(category.name()))
                .findFirst()
                .orElseThrow(MenuNotFoundException::new);

        //상품 메뉴
        showProductMenu(menu);
        productProcess(menu.getProducts(), sc);
    }

    private void productProcess(List<Product> products, Scanner sc) {
        //상품 선택
        int select = sc.nextInt();
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

    private void orderMenuProcess(Scanner sc, OrderCommand command) {
        //주문 메뉴
        switch (command) {
            case ORDER -> orderProcess(sc);
            case CANCEL -> cancelOrder();
            default -> throw new MenuNotFoundException();
        }
    }

    private void orderProcess(Scanner sc) {
        showOrder();
        int select = sc.nextInt();
        switch (select) {
            case 1 -> requestOrder();
            case 2 -> this.start();
            default -> throw new IndexOutOfBoundsException(INVALID_SELECT_MESSAGE);
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
            for (int i = 3; i > 1; i--) {
                System.out.printf("%d초 후 메뉴판으로 돌아갑니다.", i);
                Thread.sleep(1000);
            }
            this.start();
        } catch (InterruptedException ignored) {
        }
    }


    private void showMenu() {
        System.out.println("\"투썸플레이스에 오신걸 환영합니다.\"");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println("[ TWOSOME MENU ]");
        int idx = 1;
        for (Menu menu: menus) {
            System.out.printf("%d. %s %n", idx++, menu.printMenu());
        }
        System.out.println("[ ORDER MENU ]");
        System.out.printf("%d. Order       | 장바구니를 확인 후 주문합니다. %n", idx++);
        System.out.printf("%d. Cancel      | 진행중인 주문을 취소합니다. %n", idx);
    }

    private void showBuyProduct(Product product) {
       /* "Hamburger     | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거"
        위 메뉴를 장바구니에 추가하시겠습니까?
        1. 확인        2. 취소*/
        System.out.println(product.printInfo());
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
    }

    private void showOrder() {
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println("[ Orders ]");
        System.out.println(order.printOrderProductsInfo());

        System.out.println("[ Total ]");
        System.out.println(order.printTotal());

        System.out.println("1. 주문      2. 메뉴판");
    }

    private void showProductMenu(Menu menu) {

        System.out.println("\"투썸플레이스에 오신걸 환영합니다.\"");
        System.out.println("아래 상품메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.printf("[ %s MENU ] %n", menu.getName());
        int idx = 1;
        for (Product product : menu.getProducts()) {
            System.out.printf("%d. %s %n", idx++, product.printInfo());
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
