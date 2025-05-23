package hong.board.board.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hong.board.board.domain.BoardService;
import hong.board.member.domain.Member;
import hong.board.security.auth.MemberPrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    //게시물 목록 조회 페이지
    @GetMapping("/boardList")
    public String boardList() {

        return "board/boardList";
    }

    //게시물 목록 조회(검색조건포함)
    @GetMapping("/boardList/data")
    @ResponseBody
    public List<BoardDto> getSearchBoardList(@RequestParam(name = "title", required = false) String title,
                                             @RequestParam(name = "content", required = false) String content,
                                             @RequestParam(name = "authorId", required = false) String authorId) {
        return boardService.getSearchBoardList(title, content, authorId);
    }

    //게시물 상세 조회
    @GetMapping("/boardDetail/{boardId}")
    public String getBoardDetail(@PathVariable("boardId") Long boardId, Principal principal, Model model) throws JsonProcessingException {
        BoardDto boardDto = boardService.findByBoardId(boardId);

        //작성자인지 체크
        boolean isAuthor = boardDto.getAuthorId().equals(principal.getName());
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("authorYn", isAuthor ? "Y" : "N");

        if(isAuthor) {
            //작성자일때 JSON 형태로 파일리스트 넘기기 (Dropzone 용)
            String serverFileJson = new ObjectMapper().writeValueAsString(boardDto.getFileList());
            model.addAttribute("serverFilesJSon", serverFileJson);
        }

        return "board/boardDetail";
    }

    //게시물 수정
    @PostMapping("/updateBoard/{boardId}")
    @ResponseBody
    public Map<String, Object> updateBoard(@PathVariable("boardId") Long boardId, @ModelAttribute BoardDto boardDto, @RequestParam(name = "file", required = false) List<MultipartFile> files) {

        Map<String, Object> resultMap = new HashMap<>();

        try {
            boardService.updateBoard(boardId, boardDto, files);
            resultMap.put("status", "success");
            resultMap.put("message", "게시물 수정 완료");
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("message", "수정 오류: " + e.getMessage());
        }

        return resultMap;
    }

    //게시물 삭제
    @PostMapping("/deleteBoard/{boardId}")
    @ResponseBody
    public Map<String, Object> deleteBoard(@PathVariable("boardId") Long boardId) {

        Map<String, Object> resultMap = new HashMap<>();

        try {
            boardService.deleteBoard(boardId);
            resultMap.put("status", "success");
            resultMap.put("message", "게시물 삭제 완료");
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("message", "삭제 오류: " + e.getMessage());
        }

        return resultMap;
    }

    //게시물 등록 페이지
    @GetMapping("/writeBoard")
    public String writeBoard(Authentication authentication, Model model) {
        //로그인한 사용자 정보 가져오기
        MemberPrincipalDetails memberPrincipalDetails = (MemberPrincipalDetails) authentication.getPrincipal();
        Member member = memberPrincipalDetails.getMember();

        model.addAttribute("member", member);

        return "board/writeBoard";
    }

    //게시물 등록
    @PostMapping("/saveBoard")
    @ResponseBody
    public Map<String, Object> saveBoard(@ModelAttribute BoardDto boardDto, @RequestParam(name = "file", required = false) List<MultipartFile> files) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();

        try {
            boardService.saveBoard(boardDto, files);
            resultMap.put("status", "success");
            resultMap.put("message", "게시물이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("message", "게시물 등록에 실패했습니다.");
        }

        return resultMap;
    }

}
