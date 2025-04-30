package hong.board.board.web;

import hong.board.board.domain.Board;
import hong.board.file.File;
import hong.board.file.FileDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<File> fileList;

    @Builder
    public BoardDto(Board board, List<File> fileList) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.authorId = board.getAuthor().getMemberId();
        this.createDate = board.getCreateDate();
        this.updateDate = board.getUpdateDate();
        this.delYn = board.getDelYn();
        this.content = board.getContent();
        //리스트 복사만 하고 변환은 하지 않음
        this.fileList = fileList != null ? new ArrayList<>(fileList) : new ArrayList<>();
    }
}
