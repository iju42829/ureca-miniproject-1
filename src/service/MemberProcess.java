package service;

import dto.AuthenticationDto;
import dto.MemberCreateDto;

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

    public MemberCreateDto inputMemberDetails() {
        System.out.println("회원가입을 진행합니다.");
        System.out.print("email : ");
        String email = scanner.nextLine();

        System.out.print("password : ");
        String password = scanner.nextLine();

        System.out.print("name : ");
        String name = scanner.nextLine();

        return new MemberCreateDto(name, password, email);
    }

    public String inputEmail() {
        System.out.print("email : ");
        return scanner.nextLine();
    }

    public String inputCurrentPassword() {
        System.out.print("currentPassword : ");
        return scanner.nextLine();
    }

    public String inputNewPassword() {
        System.out.print("newPassword : ");
        return scanner.nextLine();
    }

    public void printMemberHelp() {
        System.out.println("=== 회원 메뉴 도움말 ===");
        System.out.println("create           : 회원가입");
        System.out.println("changePassword   : 비밀번호 변경");
        System.out.println("exit             : 회원 메뉴에서 빠져나가기");
    }
}
