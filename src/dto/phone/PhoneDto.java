package dto.phone;

public class PhoneDto {
    private Long phoneId;
    private String name;
    private String brand;
    private int regularPrice;
    private int discountAmount;
    private int stock;

    public PhoneDto(Long phoneId, String name, String brand, int regularPrice, int discountAmount, int stock) {
        this.phoneId = phoneId;
        this.name = name;
        this.brand = brand;
        this.regularPrice = regularPrice;
        this.discountAmount = discountAmount;
        this.stock = stock;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
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

    public int getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(int regularPrice) {
        this.regularPrice = regularPrice;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
