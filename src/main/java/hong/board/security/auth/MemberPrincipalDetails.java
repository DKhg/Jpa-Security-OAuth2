package hong.board.security.auth;

import hong.board.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


//Spring Security 에 있는 UserDetails 를 구현한 클래스
//Spring Security 에서 사용자의 정보를 가져옴
public class MemberPrincipalDetails implements UserDetails, OAuth2User {

    @Getter
    private final Member member;
    //OAuth2MemberService 에서 받아온 사용자 정보
    private final Map<String, Object> oAuth2Attributes;

    //MemberPrincipalDetailService 와 OAuth2MemberService 에서 반환된 생성자
    public MemberPrincipalDetails(Member member, Map<String, Object> oAuth2Attributes) {
        this.member = member;
        this.oAuth2Attributes = oAuth2Attributes;
    }

    //소셜 로그인에서 받은 사용자 정보
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes;
    }

    //소셜 로그인에서 제공하는 사용자 이름
    @Override
    public String getName() {
        return (String) oAuth2Attributes.get("name");
    }

    //계정의 권한을 담아둠
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //enum 타입이라 toString() 해줘야 함
        authorities.add(new SimpleGrantedAuthority(member.getRole().toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getMemberPw();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
