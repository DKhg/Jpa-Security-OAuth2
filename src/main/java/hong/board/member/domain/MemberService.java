package hong.board.member.domain;

import hong.board.member.web.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //회원 수정 (save() 사용하지 않고 영속성 컨텍스트에서 자동감지를 통해 update
    //Transactional 안에서 엔티티 값을 변경하면 JPA 가 자동으로 update 실행
    @Transactional
    public void updateMember(MemberDto memberDto) {
        Member member = findByMemberId(memberDto.getMemberId());
        member.setMemberEmail(memberDto.getMemberEmail());
        member.setMemberPw(passwordEncoder.encode(memberDto.getMemberPw()));
        member.setMemberNm(memberDto.getMemberNm());
    }
}
