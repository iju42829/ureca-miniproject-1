import repository.MemberRepository;
import repository.PhoneRepository;
import service.MainService;
import service.MemberService;
import service.PhoneProcess;
import service.PhoneService;

public class MainApplication {
    public static void main(String[] args) {
        // repository
        MemberRepository memberRepository = new MemberRepository();
        PhoneRepository phoneRepository = new PhoneRepository();

        // service
        MemberService memberService = new MemberService(memberRepository);
        PhoneService phoneService = new PhoneService(phoneRepository);
        PhoneProcess phoneProcess = new PhoneProcess();

        // main
        MainService mainService = new MainService(memberService, phoneService, phoneProcess);

        mainService.run();
    }
}
