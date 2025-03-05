package service;

import dto.MemberCreateDto;
import entity.Member;
import repository.MemberRepository;

import java.sql.Connection;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createMember(Connection conn, MemberCreateDto memberCreateDto) {
        Member member = new Member(memberCreateDto.getName(),
                memberCreateDto.getEmail(),
                memberCreateDto.getPassword(),
                "user");

        memberRepository.save(conn, member);
    }

    public void changeMemberPassword(Connection conn, String email, String password, String newPassword) {
        Member member = memberRepository.findByEmailAndPassword(conn, email, password);

        if (member == null) {
            System.out.println("해당 계정이 존재하지 않습니다.");
            return;
        }

        memberRepository.updatePassword(conn, member.getMemberId(), newPassword);
    }
}
