package hong.board.member.domain;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "rhghdrms1@gmail.com";
    private static int authCode;

    //인증번호 만들기
    public static void createAuthCode() {
        //6자리 숫자 랜덤코드
        authCode = (int) (Math.random() * (90000)) + 100000;
    }

    //인증메일 폼생성
    public MimeMessage createEmailForm(String email) throws MessagingException {
        createAuthCode();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + authCode + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            throw new MessagingException("이메일 발송 오류");
        }

        return message;
    }

    //인증메일 전송
    public int sendEmail(String email) throws Exception {
        MimeMessage message = createEmailForm(email);

        try {
            javaMailSender.send(message);
            log.info("이메일이 정상적으로 발송되었습니다. 이메일={}", email);
        } catch (RuntimeException e) {
            throw new RuntimeException("이메일 발송 오류", e);
        }
        return authCode;
    }
}
