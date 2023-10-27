package com.sparta.study.task01.app;

import com.sparta.study.task01.constant.MenuCategory;
import com.sparta.study.task01.constant.OrderCommand;
import com.sparta.study.task01.constant.ProductOption;
import com.sparta.study.task01.exception.MenuNotFoundException;
import com.sparta.study.task01.exception.NoOrderProductException;
import com.sparta.study.task01.model.Menu;
import com.sparta.study.task01.model.Order;
import com.sparta.study.task01.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class KioskApp {
    private final List<Menu> menus = new ArrayList<>();
    private final List<Order> sales = new ArrayList<>();
    private final List<Product> cart = new ArrayList<>();
    private int waitNo = 1;

    //
    private static final int SALES_MENU_SIZE = -1;
    private static final int ORDER_MENU_SIZE = 2;
    private static final int MENU_CATEGORY_SIZE = MenuCategory.values().length;
    private static final int INVALID_MENU_SELECT_SIZE = MENU_CATEGORY_SIZE + ORDER_MENU_SIZE;

    //Exception Msg
    private static final String INVALID_SELECT_MESSAGE = "다시 입력해주세요";
    private final Scanner sc = new Scanner(System.in);

    public KioskApp() {
        init();
    }

    public boolean start() {
        showMenu();
        int selection = handleInputMenu(INVALID_MENU_SELECT_SIZE);

        if (selection == SALES_MENU_SIZE) {
            salesMenuProcess();
        }

        if (selection >= MENU_CATEGORY_SIZE) {
            orderMenuProcess(OrderCommand.values()[selection - MENU_CATEGORY_SIZE]);
        } else {
            productMenuProcess(MenuCategory.values()[selection]);
        }

        return true;
    }

    private int handleInputMenu(int menuSize) {
        int select;
        //주문 입력창 수를 벗어난 경우
        while (true) {
            try {
                select = sc.nextInt();
                if (select > menuSize || select < 0) {
                    throw new IndexOutOfBoundsException();
                }
                break;
            } catch (RuntimeException exception) {
                System.out.println(INVALID_SELECT_MESSAGE);
            }
        }
        return select - 1;
    }

    private void salesMenuProcess() {
        System.out.println("1. 총 판매금액 조회        2. 총 판매상품 목록 조회     3. 돌아가기");

        int selection = handleInputMenu(3);
        switch (selection) {
            case 0 -> totalSalesPriceProcess();
            case 1 -> salesListProcess();
            case 2 -> this.start();
        }
    }

    private void salesListProcess() {
        showTotalSalesProducts();

        System.out.println("1. 돌아가기");
        int selection = handleInputMenu(1);

        //메뉴가 한개라 if 문이 필요가 없어졌지만 가독성을 위해 넣어봄..
        if (selection == 0) {
            salesMenuProcess();
        }
    }

    private void showTotalSalesProducts() {
        System.out.println("[ 총 판매상품 목록 현황 ]");

        for (Order order : sales) {
            for (Product product : order.getProducts()) {
                System.out.printf("- %-38s | W %.1f %n", product.getName(), product.getResultPrice());
            }
        }
    }


    private void totalSalesPriceProcess() {
        double totalSalesPrice = getTotalSales();
        showTotalSalesAmount(totalSalesPrice);

        System.out.println("1. 돌아가기");
        int selection = handleInputMenu(1);

        if (selection == 0) {
            salesMenuProcess();
        }
    }


    private void productMenuProcess(MenuCategory category) {
        //메뉴보기
        Menu menu = menus.stream()
                .filter(m -> m.getName().equals(category.name()))
                .findFirst()
                .orElseThrow(MenuNotFoundException::new);
        showProductsMenu(menu);

        productProcess(menu.getProducts(), category);
    }

    private void showProductsMenu(Menu menu) {
        System.out.println("\"투썸플레이스에 오신걸 환영합니다.\"");
        System.out.println("아래 상품메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println();
        System.out.printf("[ %s MENU ] %n", menu.getName());
        menu.printProductsMenu();
    }

    private void productProcess(List<Product> products, MenuCategory category) {
        Product product = makeSelectProduct(products, category);

        //구입할 상품 show
        product.printInfoWithOption();

        //주문 결정
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");

        int selection = handleInputMenu(2);
        OrderCommand command = OrderCommand.values()[selection];
        switch (command) {
            case ORDER -> addCart(product);
            case CANCEL -> this.start();
        }
    }

    private Product makeSelectProduct(List<Product> products, MenuCategory category) {
        int selection = handleInputMenu(products.size());
        Product selectedProduct = products.get(selection);

        //상품 선택
        selectedProduct.printInfo();
        System.out.println("위 메뉴의 어떤 옵션으로 추가하시겠습니까?");
        category.printOptions(selectedProduct.getPrice());

        //선택상품 생성
        selection = handleInputMenu(category.getOptions().size());
        ProductOption option = category.getOptions().get(selection);
        return Product.of(selectedProduct, option);
    }

    private void orderMenuProcess(OrderCommand command) {
        switch (command) {
            case ORDER -> orderProcess();
            case CANCEL -> cancelOrder();
        }
    }

    private void orderProcess() {
        Order order = new Order(new ArrayList<>(cart), waitNo);

        showOrder(order);
        System.out.println("1. 주문      2. 메뉴판");

        int selection = handleInputMenu(2);
        OrderCommand command = OrderCommand.values()[selection];
        switch (command) {
            case ORDER -> requestOrder(order);
            case CANCEL -> this.start();
        }
    }



    private void requestOrder(Order order) {
        if (cart.isEmpty()) {
            throw new NoOrderProductException();
        }

        System.out.println("주문이 완료되었습니다!");
        sales.add(order);
        cart.clear();
        waitNo++;

        System.out.printf("대기번호는 [ %d ] 번 입니다. %n", order.getWaitNo());
        try {
            for (int i = 3; i >= 1; i--) {
                System.out.printf("%d초 후 메뉴판으로 돌아갑니다. %n", i);
                Thread.sleep(1000);
            }
            this.start();
        } catch (InterruptedException ignored) {
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
    private void showOrder(Order order) {
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println("[ Orders ]");
        order.printOrderProductsInfo();
        System.out.println("[ Total ]");
        order.printTotal();
    }
    private void showTotalSalesAmount(double totalSalesPrice) {
        System.out.println("[ 총 판매금액 현황 ]");
        System.out.printf("현재까지 총 판매된 금액은 [ W %.1f ] 입니다. %n", totalSalesPrice);
    }

    private double getTotalSales() {
        return sales.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    private void showMenu() {
        System.out.println("\"투썸플레이스에 오신걸 환영합니다.\"");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println("[ TWOSOME MENU ]");
        int idx = 1;
        for (Menu menu : menus) {
            System.out.printf("%d.", idx);
            menu.printMenu();
        }
        System.out.println("[ ORDER MENU ]");
        System.out.printf("%d. Order       | 장바구니를 확인 후 주문합니다. %n", idx++);
        System.out.printf("%d. Cancel      | 진행중인 주문을 취소합니다. %n", idx);
    }

    private void addCart(Product product) {
        cart.add(product);
    }

    private void cancelOrder() {
        System.out.println("진행하던 주문이 취소되었습니다.");
        cart.clear();
        this.start();
    }
}
