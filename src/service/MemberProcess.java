package service;

import dto.AuthenticationDto;

import java.util.Scanner;

public class MemberProcess {

    private final Scanner scanner = new Scanner(System.in);

    public MemberProcess() {}

    public AuthenticationDto inputEmailAndPassword() {
        System.out.println("권한을 체크합니다.");
        System.out.print("email : ");
        String email = scanner.nextLine();

        System.out.print("password : ");
        String password = scanner.nextLine();

        return new AuthenticationDto(email, password);
    }
}
