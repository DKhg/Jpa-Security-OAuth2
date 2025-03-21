package hong.board.security.auth;

import hong.board.member.domain.Member;
import hong.board.member.domain.MemberRepository;
import hong.board.member.domain.MemberRole;
import hong.board.member.domain.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    
    //소셜 로그인 후 호출
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        //소셜 로그인 사용자 정보를 가져옴(구글)
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String id = "";
        if (email != null) {
            id = email.split("@")[0];
        }

        //DB 에서 해당 사용자 존재 확인
        Member member = memberRepository.findByMemberId(id);

        //사용자가 존재하지 않으면 자동 회원가입
        if(member == null) {
            member = Member.builder()
                    .memberId(id)
                    .memberNm(name)
                    .memberPw("") // 소셜 로그인 사용자는 비밀번호를 사용 X
                    .memberEmail(email)
                    .role(MemberRole.USER)
            .build();

            member = memberRepository.save(member);
        }

        //[member] 와 [oAuth2User.getAttributes() (소셜 로그인 유저정보)] 를 MemberPrincipalDetails 객체로 반환
        //클래스 분리하지않고 확장하기위해 두개를 주입
        return new MemberPrincipalDetails(member, oAuth2User.getAttributes());
    }
}
