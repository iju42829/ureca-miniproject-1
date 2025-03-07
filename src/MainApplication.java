import repository.MemberRepository;
import repository.OrderRepository;
import repository.PhoneRepository;
import service.*;
import service.process.MemberProcess;
import service.process.OrderProcess;
import service.process.PhoneProcess;

public class MainApplication {
    public static void main(String[] args) {
        // repository
        MemberRepository memberRepository = new MemberRepository();
        PhoneRepository phoneRepository = new PhoneRepository();
        OrderRepository orderRepository = new OrderRepository();

        // service
        MemberService memberService = new MemberService(memberRepository);
        PhoneService phoneService = new PhoneService(phoneRepository);
        OrderService orderService = new OrderService(orderRepository);
        PhoneProcess phoneProcess = new PhoneProcess();
        MemberProcess memberProcess = new MemberProcess();
        OrderProcess orderProcess = new OrderProcess();

        // main
        MainService mainService = new MainService(orderService, memberService, phoneService, phoneProcess, memberProcess, orderProcess);

        mainService.run();
    }
}
