package hong.board.security.provider;

import hong.board.member.domain.Member;
import hong.board.security.auth.MemberPrincipalDetailService;
import hong.board.security.auth.MemberPrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MemberAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberPrincipalDetailService memberPrincipalDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();                   //사용자가 입력한 memberId
        String password = (String) authentication.getCredentials();   //사용자가 입력한 password

        //생성한 MemberPrincipalDetailService 에서 loadUserByUsername 메서드를 호출하여 DB 에서 사용자 정보 가져옴
        MemberPrincipalDetails memberPrincipalDetails = (MemberPrincipalDetails) memberPrincipalDetailService.loadUserByUsername(username);

        //사용자가 입력한 password 와 DB 에 저장된 password 비교
        String dbPassword = memberPrincipalDetails.getPassword();
        //암호화 방식을 사용하여 비밀번호 비교
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //일치X
        if(!passwordEncoder.matches(password, dbPassword)) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        //인증이 성공하면 UsernamePasswordAuthenticationToken 객체를 반환
        return new UsernamePasswordAuthenticationToken(memberPrincipalDetails, password, memberPrincipalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
