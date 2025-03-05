package service;

import dto.MemberCreateDto;
import util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MainService {

    private final MemberService memberService;
    private final Scanner scanner = new Scanner(System.in);

    public MainService(MemberService memberService) {
        this.memberService = memberService;
    }

    public void run() {
        while(true) {
            System.out.print("main > ");
            String order = scanner.nextLine();

            if (order.equals("exit"))
                break;

            if (order.equals("member")) {
                memberProcess();
            }
        }
    }

    public void memberProcess() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            while (true) {
                System.out.print("member > ");
                String order = scanner.nextLine();

                if (order.equals("exit"))
                    break;

                if (order.equals("create")) {
                    System.out.println("회원가입을 진행합니다.");
                    System.out.print("email : ");
                    String email = scanner.nextLine();

                    System.out.print("password : ");
                    String password = scanner.nextLine();

                    System.out.print("name : ");
                    String name = scanner.nextLine();

                    MemberCreateDto memberCreateDto = new MemberCreateDto(name, password, email);

                    memberService.createMember(conn, memberCreateDto);

                    System.out.println("=== 회원가입에 성공했습니다. ===");
                }

                if (order.equals("changePassword")) {
                    System.out.println("비밀번호 변경을 진행합니다.");
                    System.out.print("email : ");
                    String email = scanner.nextLine();

                    System.out.print("currentPassword : ");
                    String currentPassword = scanner.nextLine();

                    System.out.print("newPassword : ");
                    String newPassword = scanner.nextLine();

                    memberService.changeMemberPassword(conn, email, currentPassword, newPassword);

                    System.out.println("=== 비밀번호 변경에 성공했습니다. ===");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
