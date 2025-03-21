package hong.board.security.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class MemberAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //실패 메시지를 담기 위한 세션 생성
        HttpSession session = request.getSession();
        //세션에 실패 메시지 담기
        session.setAttribute("loginErrorMessage", "아이디나 비밀번호가 맞지 않습니다.");
        session.setAttribute("error", "true");
        //로그인 실패시 이동할 페이지
        response.sendRedirect("/loginPage");
    }
}
