package hong.board.security.auth;

import hong.board.member.domain.Member;
import hong.board.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

//UserDetailsService 구현한 클래스
@Service
@RequiredArgsConstructor
public class MemberPrincipalDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    
    //username 이 폼의 name 과 같아야함
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //넘겨받은 memberId 로 DB 에서 회원 정보를 찾음
        Member member = memberRepository.findByMemberId(username);
        //없을 경우
        if(member == null) {
            throw new UsernameNotFoundException(username + "을 찾을 수 없습니다.");
        }
        
        //[member]와 [Map.of() (비어있는 Map 객체)]를 MemberPrincipalDetails 객체로 반환
        //소셜 로그인이 아니기 때문에 [Map.of() (비어있는 Map 객체)]를 넘김
        //클래스 분리하지않고 확장하기위해 두개를 주입
        return new MemberPrincipalDetails(member, Map.of());
    }
}
