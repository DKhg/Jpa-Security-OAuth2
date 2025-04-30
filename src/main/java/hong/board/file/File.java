package hong.board.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hong.board.board.domain.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", unique = true, nullable = false)
    private Long fileId;        //파일아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "board_id", nullable = false)
    @JsonIgnore //Jackson 기준 순환 방지
    private Board board;        //게시물 (다대일관계)

    @Column(name = "file_name", nullable = false)
    private String fileName;    //파일명

    @Column(name = "file_path", nullable = false)
    private String filePath;    //파일경로

    @Builder
    public File(Board board, String fileName, String filePath) {
        this.board = board;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
