package hong.board.member.web;

import hong.board.member.domain.EmailService;
import hong.board.member.domain.Member;
import hong.board.member.domain.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    //회원가입폼
    @GetMapping("/joinForm")
    public String joinForm(MemberDto memberDto, Model model) {

        return "/member/joinForm";
    }

    //회원가입
    @PostMapping("/join")
    public String joinMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            model.addAttribute("memberDto", memberDto);
            return "member/joinForm";
        }

        log.info("memberDto={}", memberDto);
        memberService.joinMember(memberDto);

        return "redirect:/loginPage";
    }
    
    //이메일 인증번호 전송
    @ResponseBody
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestParam("email") String email) throws Exception {

        try {
            int authCode = emailService.sendEmail(email);
            return ResponseEntity.ok(String.valueOf(authCode));
        } catch (RuntimeException e) {
            //클라이언트에게 메시지 전달
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    //아이디 중복체크
    @ResponseBody
    @PostMapping("/idCheck")
    public Map<String, Object> idCheck(@RequestParam("memberId") String memberId) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        int result = memberService.idCheck(memberId);

        if(result == 1) {
            resultMap.put("status", "error");
            resultMap.put("message", "이미 사용중인 아이디입니다.");
        } else if(result == 0) {
            resultMap.put("status", "success");
            resultMap.put("message", "사용 가능한 아이디입니다.");
        }
        return resultMap;
    }

    //회원수정 폼
    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("memberId") String memberId, @Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
        
        //회원 찾기
        Member member = memberService.findByMemberId(memberId);
        model.addAttribute("memberDto", member);
        return "member/updateForm";
    }

    //회원수정
    @PostMapping("/update")
    public String updateMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("memberDto", memberDto);
            return "member/updateForm";
        }
        
        //수정
        memberService.updateMember(memberDto);
        return "redirect:/loginPage";
    }

}
