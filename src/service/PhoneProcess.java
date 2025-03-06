package service;

import dto.PhoneCreateDto;
import dto.PhoneListDTO;

import java.util.List;
import java.util.Scanner;

public class PhoneProcess {

    private final Scanner scanner = new Scanner(System.in);

    public PhoneProcess() {}

    public PhoneCreateDto inputPhoneDetails() {
        System.out.println("휴대폰을 등록합니다.");
        System.out.print("name : ");
        String name = scanner.nextLine();

        System.out.print("brand : ");
        String brand = scanner.nextLine();

        System.out.print("regularPrice : ");
        int regularPrice = Integer.parseInt(scanner.nextLine());

        System.out.print("discountAmount : ");
        int discountAmount = Integer.parseInt(scanner.nextLine());

        System.out.print("stock : ");
        int stock = Integer.parseInt(scanner.nextLine());

        return new PhoneCreateDto(name, brand, regularPrice, discountAmount, stock);
    }

    public int inputStock() {
        System.out.print("추가 수량 입력 : ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String inputPhoneName() {
        System.out.print("기기 이름 : ");
        return scanner.nextLine();
    }

    public int inputQuantityForOrder() {
        System.out.print("구매 수량 : ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String inputPhoneNameForDelete() {
        System.out.print("판매 중지할 휴대폰 이름 : ");
        return scanner.nextLine();
    }

    public void printPhoneList(List<PhoneListDTO> phones) {
        System.out.println("=== 휴대폰 목록 ===");
        for (PhoneListDTO phone : phones) {
            System.out.println(
                    "이름: " + phone.getName() +
                            ",\n브랜드: " + phone.getBrand() +
                            ",\n가격: " + phone.getRegularPrice() +
                            ",\n할인액: " + phone.getDiscountAmount() +
                            ",\n재고: " + phone.getStock()
            );
            System.out.println("----------------------------");
        }
    }
}
