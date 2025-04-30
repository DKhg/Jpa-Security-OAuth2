package hong.board.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    //첨부파일 목록 조회
    List<File> findByBoard_BoardId(Long boardId);

}
