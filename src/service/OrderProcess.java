package service;

import dto.OrderListDto;

import java.util.List;

public class OrderProcess {

    public OrderProcess() {}

    public void printOrderList(List<OrderListDto> orders) {
        System.out.println("=== 주문 목록 ===");
        for (OrderListDto orderListDto : orders) {
            System.out.println(orderListDto);
            System.out.println("----------------------------");
        }
    }

    public void printOrderHelp() {
        System.out.println("=== 주문 메뉴 도움말 ===");
        System.out.println("help : 명령어 목록을 보여줍니다.");
        System.out.println("show : 본인의 주문 목록을 조회합니다.");
        System.out.println("exit : 주문 메뉴에서 빠져나옵니다.");
    }
}
