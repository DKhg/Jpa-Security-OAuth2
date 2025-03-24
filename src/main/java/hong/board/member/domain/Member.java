package hong.board.member.domain;

import hong.board.board.domain.Board;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "member")
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    //SEQUENCE

    @Column(name = "member_id", unique = true, nullable = false)
    private String memberId;            //사용자 로그인 아이디
    @Column(name = "member_pw", nullable = false)
    private String memberPw;            //사용자 패스워드
    @Column(name = "member_nm", nullable = false)
    private String memberNm;            //사용자 이름
    @Column(name = "member_email", nullable = false)
    private String memberEmail;         //사용자 이메일
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;   //가입날짜
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;   //수정날짜

    @Enumerated(EnumType.STRING)        //역할 STRING 권장
    private MemberRole role;

    //@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Board> boardList; //사용자가 작성한 게시글 리스트
    
    //엔티티가 변경될 때 자동으로 갱신
    @PreUpdate
    public void updateDateTime() {
        this.updateDate = LocalDateTime.now();
    }

    @Builder
    public Member(String memberId, String memberPw, String memberNm, String memberEmail, MemberRole role) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberNm = memberNm;
        this.memberEmail = memberEmail;
        this.createDate = LocalDateTime.now(); // 생성 시점의 시간 설정
        this.updateDate = LocalDateTime.now(); // 생성 시점의 시간 설정
        this.role = role;
    }
}
