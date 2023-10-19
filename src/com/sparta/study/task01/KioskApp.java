package com.sparta.study.task01;

import java.util.*;

import static com.sparta.study.task01.MenuCategory.*;

public class KioskApp {
    private final List<Menu> menus = new ArrayList<>();
    private final Order order = new Order();

    public KioskApp() {
        init();
    }

    boolean start() throws Exception {
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

    private void addProducts(Menu menu) {
        MenuCategory category = MenuCategory.valueOf(menu.getName());

        switch (category) {
            case COFFEE -> menu.setProducts(
                    List.of(
                            new Product("Sinchon Coffee", "맛있는 신촌커피", 6.1),
                            new Product("Americano", "맛있는 아메리카노", 4.5),
                            new Product("Coffee Latte", "맛있는 카페라떼", 5.0)
                    )
            );
            case DRINK -> menu.setProducts(
                    List.of(
                            new Product("GrapeFruit Ade", "맛있는 자몽에이드", 5.5),
                            new Product("Shine Muscat Green Grape Ade", "맛있는 샤인머스캣청포도에이드", 6.3),
                            new Product("Jeju Matcha Frappe", "맛있는 제주말차프라페", 5.8)
                    )
            );
            case CAKE -> menu.setProducts(
                    List.of(
                            new Product("Strawberry Chocolate Fresh Cream Cake", "한가득 올린 상큼한 딸기, 크런치 초코볼이 초콜릿 생크림 사이사이 씹히는 투썸 시그니처 케이크", 35.0),
                            new Product("Mascarpone Tiramisu", "마스카포네 치즈 크림과 에스프레소 커피 시럽이 촉촉하게 어우러진 이탈리안 무스 케이크", 36.0),
                            new Product("New York Cheese Cake", "부드럽고 촉촉한 식감과 진한 치즈의 맛을 느낄 수 있는 정통 뉴욕 스타일의 구움 치즈 케이크", 35.0)
                    )
            );
        }
    }


    private void init() {
        //메뉴 등록
        Arrays.stream(MenuCategory.values())
                .forEach(menuCategory -> menus.add(
                        new Menu(menuCategory.name(), menuCategory.getDescription())
                ));

        //상품 메뉴 등록
        menus.stream()
                .forEach(menu ->
                        addProducts(menu));
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
