package service;

import dto.OrderCreateDto;
import dto.OrderListDto;
import entity.Orders;
import repository.OrderRepository;

import java.sql.Connection;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Connection conn, OrderCreateDto orderCreateDto) {
        Orders order = new Orders(orderCreateDto.getMemberId(),
                orderCreateDto.getPhoneId(),
                orderCreateDto.getQuantity(),
                orderCreateDto.getOrderRegularPrice(),
                orderCreateDto.getOrderDiscountAmount());

        orderRepository.save(conn, order);
    }

    public List<OrderListDto> getOrdersByMemberId(Connection conn, Long memberId) {
        return orderRepository.findOrdersByMemberId(conn, memberId);
    }
}
