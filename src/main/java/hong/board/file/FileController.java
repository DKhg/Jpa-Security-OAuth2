package hong.board.file;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    //파일 다운로드
    @GetMapping("/{fileId}/download")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable("fileId") Long fileId, HttpServletResponse response) throws IOException {

        //파일 조회
        File file = fileService.findFileById(fileId);

        //실제 저장된 파일 경로에서 파일 가져오기
        java.io.File savedFile = new java.io.File(file.getFilePath());

        //파일명이 한글일 경우 인코딩
        String encodedFileName = URLEncoder.encode(file.getFileName(), "UTF-8").replaceAll("\\+", "%20");

        //파일 다운로드
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(savedFile.length())
                .body(new InputStreamResource(new FileInputStream(savedFile)));
    }

    //파일 삭제
    @PostMapping("/deleteFile/{fileId}")
    @ResponseBody
    public Map<String, Object> deleteFile(@PathVariable("fileId") Long fileId) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            fileService.deleteFile(fileId);
            resultMap.put("status", "success");
            resultMap.put("message", "첨부파일이 삭제 되었습니다.");
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("message", "삭제 오류: " + e.getMessage());
        }

        return resultMap;
    }
}
