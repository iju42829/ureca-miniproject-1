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
}
