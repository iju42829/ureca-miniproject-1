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
    private final OrderProcess orderProcess;
    private final Scanner scanner = new Scanner(System.in);

    public MainService(OrderService orderService, MemberService memberService, PhoneService phoneService, PhoneProcess phoneProcess, MemberProcess memberProcess, OrderProcess orderProcess) {
        this.orderService = orderService;
        this.memberService = memberService;
        this.phoneService = phoneService;
        this.phoneProcess = phoneProcess;
        this.memberProcess = memberProcess;
        this.orderProcess = orderProcess;
    }

    public void run() {
        while(true) {
            System.out.print("main > ");
            String order = scanner.nextLine();

            if (order.equals("exit"))
                break;
            if (order.equals("member"))
                memberExecute();
            if (order.equals("phone"))
                phoneExecute();
            if (order.equals("order"))
                orderExecute();
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
                        orderProcess.printOrderList(orders);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
                orderProcess.printOrderHelp();
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
                    } else
                        System.out.println("권한이 부족하거나 아이디 또는 비밀번호가 일치하지 않습니다. \n 다시 진행해 주세요.");
                }

                if (order.equals("show")) {
                    List<PhoneListDTO> phones = phoneService.getAllPhones(conn);
                    phoneProcess.printPhoneList(phones);
                }

                if (order.equals("updateStock")) {
                    AuthenticationDto authenticationDto = memberProcess.inputEmailAndPassword();
                    boolean flag = memberService.existsMemberByEmailAndPasswordAndRole(conn, authenticationDto.getEmail(), authenticationDto.getPassword());

                    if (flag) {
                        System.out.println("=== 수량 업데이트 ===");
                        phoneService.editStock(conn, phoneProcess.inputPhoneName(), phoneProcess.inputStock());
                    } else
                        System.out.println("권한이 부족하거나 아이디 또는 비밀번호가 일치하지 않습니다. \n 다시 진행해 주세요.");
                }

                if (order.equals("order")) {
                    AuthenticationDto authenticationDto = memberProcess.inputEmailAndPassword();
                    boolean flag = memberService.existsMemberByEmailAndPassword(conn, authenticationDto.getEmail(), authenticationDto.getPassword());

                    if (flag) {
                        String name = phoneProcess.inputPhoneName();
                        int quantity = phoneProcess.inputQuantityForOrder();
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
                        PhoneDto phoneDto = phoneService.getPhoneByName(conn, phoneProcess.inputPhoneNameForDelete());
                        if (phoneDto == null) {
                            System.out.println("해당 기기가 존재하지 않습니다.");
                            continue;
                        }
                        phoneService.removePhone(conn, phoneDto.getPhoneId());

                    } else {
                        System.out.println("권한이 부족하거나 아이디 또는 비밀번호가 일치하지 않습니다. \n 다시 진행해 주세요.");
                    }
                }
                else
                    phoneProcess.printPhoneHelp();
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
                    MemberCreateDto memberCreateDto = memberProcess.inputMemberDetails();
                    memberService.createMember(conn, memberCreateDto);
                    System.out.println("=== 회원가입에 성공했습니다. ===");
                }

                if (order.equals("changePassword")) {
                    System.out.println("비밀번호 변경을 진행합니다.");
                    memberService.changeMemberPassword(conn, memberProcess.inputEmail(), memberProcess.inputCurrentPassword(), memberProcess.inputNewPassword());
                    System.out.println("=== 비밀번호 변경에 성공했습니다. ===");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
