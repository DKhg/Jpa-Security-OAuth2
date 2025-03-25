package hong.board.board.web;

import hong.board.board.domain.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

    private Long boardId;
    private String title;
    private String authorId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String content;
    private String delYn;

    @Builder
    public BoardDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.authorId = board.getAuthor().getMemberId();
        this.createDate = board.getCreateDate();
        this.updateDate = board.getUpdateDate();
        this.delYn = board.getDelYn();
        this.content = board.getContent();
    }
}
