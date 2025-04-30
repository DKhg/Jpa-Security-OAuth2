package hong.board.board.domain;

import hong.board.file.File;
import hong.board.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")
@ToString
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", unique = true, nullable = false)
    private Long boardId;               //게시물 아이디

    @ManyToOne(fetch = FetchType.EAGER) //지연로딩(Lazy) : 실제로 데이터가 필요할 때 데이터를 가져오는 방식
    @JoinColumn(name = "author_id", referencedColumnName = "member_id", nullable = false)
    private Member author;             //작성자

    @Column(name = "title", nullable = false)
    private String title;               //게시물 제목

    @Column(name = "content", nullable = false)
    private String content;             //게시물 내용

    @Column(name = "delYn", nullable = false)
    private String delYn;               //삭제 여부

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;   //작성날짜

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;   //수정날짜

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<File> fileList;        //게시물에 첨부된 파일리스트(다대일관계)

    //엔티티가 변경될 때 자동으로 갱신
    @PreUpdate
    public void updateDateTime() {
        this.updateDate = LocalDateTime.now();
    }

    @Builder
    public Board(Member author, String title, String content, List<File> fileList) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.createDate = LocalDateTime.now(); // 생성 시점의 시간 설정
        this.updateDate = LocalDateTime.now(); // 생성 시점의 시간 설정
        this.delYn = "N";
        this.fileList = fileList;
    }
}
