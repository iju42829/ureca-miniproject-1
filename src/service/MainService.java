package service;

import dto.*;
import util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainService {

    private final OrderService orderService;
    private final MemberService memberService;
    private final PhoneService phoneService;

    private final PhoneProcess phoneProcess;
    private final MemberProcess memberProcess;

    private final Scanner scanner = new Scanner(System.in);

    public MainService(OrderService orderService, MemberService memberService, PhoneService phoneService, PhoneProcess phoneProcess, MemberProcess memberProcess) {
        this.orderService = orderService;
        this.memberService = memberService;
        this.phoneService = phoneService;
        this.phoneProcess = phoneProcess;
        this.memberProcess = memberProcess;
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

            if (order.equals("order")) {
                orderExecute();
            }
        }
    }

    public void orderExecute() {
        while (true) {
            System.out.print("order > ");
            String order = scanner.nextLine();

            if (order.equals("exit"))
                break;

            if (order.equals("show")) {
                try (Connection conn = DBConnectionUtil.getConnection()) {
                    AuthenticationDto authenticationDto = memberProcess.inputEmailAndPassword();
                    boolean flag = memberService.existsMemberByEmailAndPassword(conn, authenticationDto.getEmail(), authenticationDto.getPassword());

                    if (flag) {
                        MemberDto member = memberService.getMember(conn, authenticationDto.getEmail(), authenticationDto.getPassword());
                        List<OrderListDto> orders = orderService.getOrdersByMemberId(conn, member.getMemberId());

                        System.out.println("=== 주문 목록 ===");

                        for (OrderListDto orderListDto : orders) {
                            System.out.println(orderListDto);
                            System.out.println("----------------------------");
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
                    AuthenticationDto authenticationDto = memberProcess.inputEmailAndPassword();
                    boolean flag = memberService.existsMemberByEmailAndPasswordAndRole(conn, authenticationDto.getEmail(), authenticationDto.getPassword());

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

                if (order.equals("updateStock")) {
                    AuthenticationDto authenticationDto = memberProcess.inputEmailAndPassword();
                    boolean flag = memberService.existsMemberByEmailAndPasswordAndRole(conn, authenticationDto.getEmail(), authenticationDto.getPassword());

                    if (flag) {
                        System.out.println("=== 수량 업데이트 ===");
                        System.out.print("기기 이름 입력: ");
                        String name = scanner.nextLine();

                        System.out.print("수량 입력 : ");
                        int stock = Integer.parseInt(scanner.nextLine());

                        phoneService.editStock(conn, name, stock);
                    } else {
                        System.out.println("권한이 부족하거나 아이디 또는 비밀번호가 일치하지 않습니다. \n 다시 진행해 주세요.");

                    }
                }

                if (order.equals("order")) {
                    AuthenticationDto authenticationDto = memberProcess.inputEmailAndPassword();
                    boolean flag = memberService.existsMemberByEmailAndPassword(conn, authenticationDto.getEmail(), authenticationDto.getPassword());

                    if (flag) {
                        System.out.println("=== 주문 ===");
                        System.out.print("기기 이름 : ");
                        String name = scanner.nextLine();

                        System.out.print("구매 수량 : ");
                        int quantity = Integer.parseInt(scanner.nextLine());

                        PhoneDto phoneDto = phoneService.getPhoneByName(conn, name);

                        if (phoneDto == null) {
                            System.out.println("해당 기기가 존재하지 않습니다.");

                        } else {
                            if (phoneDto.getStock() < quantity) {
                                System.out.println("수량이 부족합니다.");
                                continue;
                            }

                            phoneService.editStock(conn, name, -quantity);
                            MemberDto member = memberService.getMember(conn, authenticationDto.getEmail(), authenticationDto.getPassword());
                            OrderCreateDto orderCreateDto = new OrderCreateDto(member.getMemberId(), phoneDto.getPhoneId(), quantity, phoneDto.getRegularPrice(), phoneDto.getDiscountAmount());
                            orderService.createOrder(conn, orderCreateDto);
                        }
                    }
                }

                if (order.equals("delete")) {
                    AuthenticationDto authenticationDto = memberProcess.inputEmailAndPassword();
                    boolean flag = memberService.existsMemberByEmailAndPasswordAndRole(conn, authenticationDto.getEmail(), authenticationDto.getPassword());

                    if (flag) {
                        System.out.println("=== 휴대폰 판매 중지 ===");
                        System.out.print("판매 중지할 휴대폰 이름 : ");
                        String name = scanner.nextLine();

                        PhoneDto phoneDto = phoneService.getPhoneByName(conn, name);

                        if (phoneDto == null) {
                            System.out.println("해당 기기가 존재하지 않습니다.");
                            continue;
                        }
                        phoneService.removePhone(conn, phoneDto.getPhoneId());

                    } else {
                        System.out.println("권한이 부족하거나 아이디 또는 비밀번호가 일치하지 않습니다. \n 다시 진행해 주세요.");
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
