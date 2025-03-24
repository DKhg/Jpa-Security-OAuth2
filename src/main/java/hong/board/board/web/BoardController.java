package hong.board.board.web;

import hong.board.board.domain.Board;
import hong.board.board.domain.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boardList")
    public String boardList() {
        List<Board> boards = boardService.getBoardList();
        for (Board board : boards) {
            log.info("board={}",board);
        }
        return "/board/boardList";
    }
}
