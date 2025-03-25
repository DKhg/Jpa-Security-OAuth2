package hong.board.board.domain;

import hong.board.board.web.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //게시물 목록 조회
    public List<BoardDto> getSearchBoardList(String title,String content, String authorId) {
        List<Board> boardList = boardRepository.searchBoardList(title, content, authorId);
        
        //Board 객체를 BoardDto 객체로 변환
        return boardList.stream()
                .map(board -> {
                    BoardDto boardDto = new BoardDto();
                    boardDto.setBoardId(board.getBoardId());
                    boardDto.setTitle(board.getTitle());
                    boardDto.setAuthorId(board.getAuthor().getMemberId());
                    boardDto.setCreateDate(board.getCreateDate());
                    boardDto.setUpdateDate(board.getUpdateDate());
                    boardDto.setContent(board.getContent());
                    boardDto.setDelYn(board.getDelYn());
                    return boardDto;
                })
                .collect(Collectors.toList());
    }

    //게시물 상세 조회
    public BoardDto findByBoardId(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);

        if (board == null) {
            throw new RuntimeException("게시물을 찾을 수 없습니다.");
        }

        return BoardDto.builder()
                .boardId(board.getBoardId())
                .authorId(board.getAuthor().getMemberId())
                .title(board.getTitle())
                .content(board.getContent())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .delYn(board.getDelYn())
        .build();
    }
}
