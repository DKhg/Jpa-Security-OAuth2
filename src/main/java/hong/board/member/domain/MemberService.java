package hong.board.member.domain;

import hong.board.member.web.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public Member joinMember(MemberDto memberDto) {

        Member member = Member.builder()
                .memberId(memberDto.getMemberId())
                .memberPw(passwordEncoder.encode(memberDto.getMemberPw()))
                .memberNm(memberDto.getMemberNm())
                .memberEmail(memberDto.getMemberEmail())
                .role(MemberRole.USER)
                .build();

        return memberRepository.save(member);
    }

    //아이디 중복체크
    public int idCheck(String memberId) {
        //중복검사
        boolean isExistsId = memberRepository.existsByMemberId(memberId);

        return isExistsId ? 1 : 0;
    }

    //회원 찾기
    public Member findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }

}
