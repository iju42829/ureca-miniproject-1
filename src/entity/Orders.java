package entity;

import java.time.LocalDateTime;

public class Orders {

    private Long orderId;
    private Long memberId;
    private Long phoneId;

    private LocalDateTime orderDate;
    private int quantity;
    private int orderRegularPrice;
    private int orderDiscountAmount;

    public Orders() {}

    public Orders(Long memberId, Long phoneId, int quantity, int orderRegularPrice, int orderDiscountAmount) {
        this.memberId = memberId;
        this.phoneId = phoneId;
        this.quantity = quantity;
        this.orderRegularPrice = orderRegularPrice;
        this.orderDiscountAmount = orderDiscountAmount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
}
