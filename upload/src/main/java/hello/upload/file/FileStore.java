package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        // 여러 파일들이 들어왔을 때, storeFile로 생성된 UploadFile들을 리스트로 담아준다.
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    // MultipartFile로 파일을 저장한 다음, UploadFile로 반환한다.
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename(); // image.png
        String storeFileName = createStoreFileName(originalFileName); // "qwe-qwe-123-qwer.png"
        multipartFile.transferTo(new File(getFullPath((storeFileName))));

        return new UploadFile(originalFileName, storeFileName);
    }

    private String createStoreFileName(String originalFileName) {
        // 서버에 저장하는 파일명. UUID로 생성할 것이지만, 확장자명도 가져오고 싶다.
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFileName);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf("."); // .의 위치
        return originalFileName.substring(pos + 1); // 확장자 명
    }
}
