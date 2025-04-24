package hong.board.board.web;

import hong.board.board.domain.Board;
import hong.board.board.domain.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    public String getBoardDetail(@PathVariable("boardId") Long boardId, Principal principal, Model model) {
        BoardDto boardDto = boardService.findByBoardId(boardId);
        //사용자 아이디
        String memberId = principal.getName();
        //게시물 작성자 아이디
        String authorId = boardDto.getAuthorId();
        //사용자와 게시물 작성자 일치 여부
        String authorYn = "";

        if(memberId.equals(authorId)) {
            authorYn = "Y";
        } else {
            authorYn = "N";
        }

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("authorYn", authorYn);
        return "board/boardDetail";
    }
}
