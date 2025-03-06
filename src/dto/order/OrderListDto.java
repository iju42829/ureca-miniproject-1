package dto.order;

import java.time.LocalDateTime;

public class OrderListDto {
    private String name;
    private String brand;

    private LocalDateTime orderDate;

    private int quantity;
    private int orderRegularPrice;
    private int orderDiscountAmount;

    public OrderListDto(String name, String brand, LocalDateTime orderDate, int quantity, int orderRegularPrice, int orderDiscountAmount) {
        this.name = name;
        this.brand = brand;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.orderRegularPrice = orderRegularPrice;
        this.orderDiscountAmount = orderDiscountAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderRegularPrice() {
        return orderRegularPrice;
    }

    public void setOrderRegularPrice(int orderRegularPrice) {
        this.orderRegularPrice = orderRegularPrice;
    }

    public int getOrderDiscountAmount() {
        return orderDiscountAmount;
    }

    public void setOrderDiscountAmount(int orderDiscountAmount) {
        this.orderDiscountAmount = orderDiscountAmount;
    }

    @Override
    public String toString() {
        return "기종: " + name + " (" + brand + ")\n" +
                "주문 날짜: " + orderDate + "\n" +
                "수량: " + quantity + "개\n" +
                "정가: " + orderRegularPrice + "원\n" +
                "할인 금액: " + orderDiscountAmount + "원\n" +
                "총 가격: " + (orderRegularPrice - orderDiscountAmount) * quantity + "원\n";
    }
}
