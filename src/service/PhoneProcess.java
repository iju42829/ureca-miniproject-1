package service;

import dto.AuthenticationDto;
import dto.PhoneCreateDto;

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
}
