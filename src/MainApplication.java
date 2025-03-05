import repository.MemberRepository;
import service.MainService;
import service.MemberService;

public class MainApplication {
    public static void main(String[] args) {
        // repository
        MemberRepository memberRepository = new MemberRepository();

        // service
        MemberService memberService = new MemberService(memberRepository);

        // main
        MainService mainService = new MainService(memberService);

        mainService.run();
    }
}
