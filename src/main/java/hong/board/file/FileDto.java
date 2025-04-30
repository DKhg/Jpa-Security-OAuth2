package hong.board.file;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {

    private Long fileId;
    private String fileName;
    private String filePath;

    public FileDto(File file) {
        this.fileId = file.getFileId();
        this.fileName = file.getFileName();
        this.filePath = file.getFilePath();
    }
}