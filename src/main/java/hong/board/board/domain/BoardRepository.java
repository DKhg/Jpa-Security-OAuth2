package hong.board.board.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    //게시물 상세 조회
    Board findByBoardId(Long boardId);

    //게시판 목록 조회 (검색조건 포함)
    @Query("SELECT b FROM Board b WHERE b.delYn = 'N' " +  // 공백 추가
            "AND (:title IS NULL OR b.title LIKE CONCAT('%',:title,'%')) " +
            "AND (:content IS NULL OR b.content LIKE CONCAT('%',:content,'%')) " +
            "AND (:authorId IS NULL OR b.author.memberId LIKE CONCAT('%',:authorId,'%'))")
    List<Board> searchBoardList(@Param("title") String title,
                                @Param("content") String content,
                                @Param("authorId") String authorId);


}
