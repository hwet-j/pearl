package com.pits.auction.global.upload;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Component
public class AudioUpload {

    public String uploadAudio(MultipartFile file) {
        // 웹 어플리케이션 경로
        String webAppPath = System.getProperty("user.dir");
        
        // 첨부파일 저장 폴더
        String saveFolder = "\\audios\\uploaded_audios";
        String uploadDirectory = webAppPath + "\\src\\main\\resources\\static" + saveFolder;

        File directory = new File(uploadDirectory);
        // 저장 경로가 없으면 생성
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // UUID 기능을 사용해서 고유한 파일명 생성
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        // 저장폴더와 파일명을 합쳐 저장경로 작성
        String filePath = uploadDirectory + File.separator + uniqueFileName;
        File dest = new File(filePath);

        // 저장
        try {
            FileCopyUtils.copy(file.getBytes(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFolder + "\\" + uniqueFileName;
    }
}
