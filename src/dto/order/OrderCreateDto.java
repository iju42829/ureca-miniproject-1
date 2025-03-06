package dto.order;

public class OrderCreateDto {

    private Long memberId;
    private Long phoneId;

    private int quantity;
    private int orderRegularPrice;
    private int orderDiscountAmount;

    public OrderCreateDto(Long memberId, Long phoneId, int quantity, int orderRegularPrice, int orderDiscountAmount) {
        this.memberId = memberId;
        this.phoneId = phoneId;
        this.quantity = quantity;
        this.orderRegularPrice = orderRegularPrice;
        this.orderDiscountAmount = orderDiscountAmount;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
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

