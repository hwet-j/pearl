package com.pits.auction.global.upload;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Component
public class ImageUpload {

    // 이미지 업로드를 위한 메서드 - 참고 홈페이지 https://velog.io/@orol116/Spring-%EC%9D%B4%EB%AF%B8%EC%A7%80%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C
    public String uploadImage(MultipartFile file) {
        // 웹 어플리케이션의 경로 가져오기
        String webAppPath = System.getProperty("user.dir");

        // 이미지를 저장할 경로 --> 필요하다면 추가적으로 별도의 폴더를 더 생성하여 구분
        String saveFolder = "\\pictures\\uploaded_images";
        String uploadDirectory = webAppPath + "\\src\\main\\resources\\static" +  saveFolder;

        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // 폴더가 없으면 생성
        }

        // 동일한 파일명이 들어올 가능성이 있기 때문에 UUID를 사용해서 고유한 파일명을 생성한다.
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = uploadDirectory + File.separator + uniqueFileName;
        File dest = new File(filePath);

        try {
            // 업로드된 파일의 내용을 경로에 복사하여 이미지 파일을 저장
            FileCopyUtils.copy(file.getBytes(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFolder + "\\" + uniqueFileName;
    }
}