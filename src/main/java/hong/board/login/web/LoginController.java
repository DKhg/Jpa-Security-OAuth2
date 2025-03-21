package hong.board.login.web;

import hong.board.member.domain.Member;
import hong.board.member.domain.MemberService;
import hong.board.security.auth.MemberPrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {

        //인증되지 않은 경우 로그인 페이지
        if(authentication == null || !authentication.isAuthenticated()) {
            return "login/loginPage";
        }
        //로그인한 사용자 객체 반환
        MemberPrincipalDetails memberPrincipalDetails = (MemberPrincipalDetails) authentication.getPrincipal();
        Member member = memberPrincipalDetails.getMember();
        log.info("member={}", member);

        model.addAttribute("member", member);

        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        
        //아이디나 비밀번호가 맞지 않을때 MemberAuthFailureHandler 담아온 메시지를 세션에서 가지고옴
        String loginErrorMessage = (String) session.getAttribute("loginErrorMessage");
        String error = (String) session.getAttribute("error");
        log.info("loginErrorMessage={}, error={}",loginErrorMessage,error);

        model.addAttribute("error", error);
        model.addAttribute("loginErrorMessage", loginErrorMessage);

        //로그인 첫페이지에서 에러 메시지를 띄우지 않기위해 세션 제거
        session.removeAttribute("error"); // 메시지 한 번만 표시
        session.removeAttribute("loginErrorMessage"); // 메시지 한 번만 표시

        return "login/loginPage";
    }

}
