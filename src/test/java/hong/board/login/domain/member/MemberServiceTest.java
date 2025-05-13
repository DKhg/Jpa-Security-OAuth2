package hong.board.login.domain.member;

import hong.board.member.domain.Member;
import hong.board.member.domain.MemberRepository;
import hong.board.member.domain.MemberRole;
import hong.board.member.domain.MemberService;
import hong.board.member.web.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    void 회원가입() {
        MemberDto memberDto = new MemberDto("test123", "password", "홍길동", "test@example.com", null, null);

        Member member = Member.builder()
                .memberId(memberDto.getMemberId())
                .memberPw(memberDto.getMemberPw())
                .memberNm(memberDto.getMemberNm())
                .memberEmail(memberDto.getMemberEmail())
                .role(MemberRole.USER)
                .build();

        //회원 가입 실행
        Member savedMember = memberService.joinMember(memberDto);
        System.out.println("savedMember = " + savedMember);

        Assertions.assertThat(savedMember.getMemberId()).isEqualTo(member.getMemberId());
        //Assertions.assertThat(savedMember.getMemberPw()).isEqualTo(member.getMemberPw());
        Assertions.assertThat(savedMember.getMemberNm()).isEqualTo(member.getMemberNm());
        Assertions.assertThat(savedMember.getMemberEmail()).isEqualTo(member.getMemberEmail());

    }
}