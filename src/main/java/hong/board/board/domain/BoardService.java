package hong.board.board.domain;

import hong.board.board.web.BoardDto;
import hong.board.file.File;
import hong.board.file.FileRepository;
import hong.board.file.FileService;
import hong.board.member.domain.Member;
import hong.board.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

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
                    boardDto.setFileList(board.getFileList()); //순환 참조(양방향)가 일어나 File 객체에 @JsonIgnore 어노테이션 추가 
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

        //게시물에 관련된 첨부파일 목록 조회
        List<File> fileList = fileRepository.findByBoard_BoardId(boardId);

        return new BoardDto(board, fileList);
    }

    //게시물 수정
    public void updateBoard(Long boardId, BoardDto boardDto) {

        //업데이트 되지 않는 부분을 조회하기 위함
        Board board = boardRepository.findByBoardId(boardId);

        if(board == null) {
            throw new RuntimeException("게시물을 찾을 수 없습니다.");
        }

        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());


        boardRepository.save(board);
    }

    //게시물 삭제
    public void deleteBoard(Long boardId) {

        //업데이트 되지 않는 부분을 조회하기 위함
        Board board = boardRepository.findByBoardId(boardId);

        if(board == null) {
            throw new RuntimeException("게시물을 찾을 수 없습니다.");
        }

        board.setDelYn("Y");

        boardRepository.save(board);
    }

    //게시물 등록
    @Transactional
    public void saveBoard(BoardDto boardDto, List<MultipartFile> files) {
        
        //작성자 정보 조회
        Member member = memberRepository.findByMemberId(boardDto.getAuthorId());
        if(member == null) {
            throw new IllegalArgumentException("해당 작성자가 존재하지 않습니다.");
        }

        //게시물 엔티티 생성 및 저장
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .author(member)
                .build();
        //미리 게시물을 생성해야 boardId를 토대로 file 저장 가능
        boardRepository.save(board);
        
        //첨부파일 저장
        if(!files.isEmpty()) {
            try {
                List<File> fileList = fileService.saveFiles(files, board);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
            }
        }
    }
}
