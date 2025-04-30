package hong.board.file;

import hong.board.board.domain.Board;
import hong.board.board.web.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    //저장경로
    @Value("${file.upload.path}")
    private String uploadPath;

    //파일 저장
    //DB에 여러 파일 저장하는 경우 중간에 실패하면 롤백되게 처리
    @Transactional
    public List<File> saveFiles(List<MultipartFile> multipartFiles, Board board) throws IOException{
        
        //파일이 없을 경우
        if(multipartFiles == null || multipartFiles.isEmpty()) {
            return List.of();
        }

        List<File> fileList = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles) {
            try {
                //파일 기존이름, 저장될 파일 이름
                String originalFilename = multipartFile.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String savedFilename = uuid + "_" + originalFilename;

                //실제 파일 저장
                java.io.File saveFile = new java.io.File(uploadPath, savedFilename);
                multipartFile.transferTo(saveFile);

                //DB 저장
                File file = File.builder()
                        .board(board)
                        .fileName(originalFilename)
                        .filePath(savedFilename)
                        .build();
                fileRepository.save(file);

                //저장된 파일 리스트에 추가
                fileList.add(file);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 실패", e);
            }
        }
        return fileList;
    }

    //파일 조회
    public File findFileById(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));
    }

}
