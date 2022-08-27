package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    // uploadFileName이 겹칠 수도 있으니까 storeFileName으로 구분
    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
