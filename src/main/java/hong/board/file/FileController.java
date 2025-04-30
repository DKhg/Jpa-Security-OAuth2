package hong.board.file;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    @Value("${file.upload.path}")
    private String uploadPath;

    private final FileService fileService;

    @GetMapping("/{fileId}/download")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable("fileId") Long fileId, HttpServletResponse response) throws IOException {

        //파일 조회
        File file = fileService.findFileById(fileId);

        //파일 경로 조합
        Path path = Paths.get(uploadPath, file.getFilePath());

        //실제 저장된 파일 경로에서 파일 가져오기
        java.io.File savedFile = path.toFile();

        //파일명이 한글일 경우 인코딩
        String encodedFileName = URLEncoder.encode(file.getFileName(), "UTF-8").replaceAll("\\+", "%20");

        //파일 다운로드
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(savedFile.length())
                .body(new InputStreamResource(new FileInputStream(savedFile)));
    }
}
