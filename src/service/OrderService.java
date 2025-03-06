package service;

import dto.OrderCreateDto;
import entity.Orders;
import repository.OrderRepository;

import java.sql.Connection;

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
}
