package service;

import dto.AuthenticationDto;
import dto.MemberCreateDto;
import dto.PhoneCreateDto;
import dto.PhoneListDTO;
import util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainService {

    private final MemberService memberService;
    private final PhoneService phoneService;

    private final PhoneProcess phoneProcess;

    private final Scanner scanner = new Scanner(System.in);

    public MainService(MemberService memberService, PhoneService phoneService, PhoneProcess phoneProcess) {
        this.memberService = memberService;
        this.phoneService = phoneService;
        this.phoneProcess = phoneProcess;
    }

    public void run() {
        while(true) {
            System.out.print("main > ");
            String order = scanner.nextLine();

            if (order.equals("exit"))
                break;

            if (order.equals("member")) {
                memberExecute();
            }

            if (order.equals("phone")) {
                phoneExecute();
            }
        }
    }

    public void phoneExecute() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            while (true) {
                System.out.print("phone > ");
                String order = scanner.nextLine();

                if (order.equals("exit"))
                    break;

                if (order.equals("create")) {
                    AuthenticationDto authenticationDto = phoneProcess.inputEmailAndPassword();
                    boolean flag = memberService.existsMemberByEmailAndPassword(conn, authenticationDto.getEmail(), authenticationDto.getPassword());

                    if (flag) {
                        PhoneCreateDto phoneCreateDto = phoneProcess.inputPhoneDetails();
                        phoneService.createPhone(conn, phoneCreateDto);
                        System.out.println("=== 휴대폰 등록에 성공했습니다. ===");
                    } else {
                        System.out.println("권한이 부족하거나 아이디 또는 비밀번호가 일치하지 않습니다. \n 다시 진행해 주세요.");
                    }
                }

                if (order.equals("show")) {
                    List<PhoneListDTO> phones = phoneService.getAllPhones(conn);

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void memberExecute() {
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
