package com.pits.auction.global.upload;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Component
public class ImageUpload {

    // 로컬 컴퓨터에 저장
    public String uploadImage(MultipartFile file, Long id, String type) {
        // 이미지를 저장할 경로 --> 타입별 별도의 폴더를 더 생성하여 구분
        String saveFolder = "";

        if (type.equals("member")){
            saveFolder = "/profile_images";
        } else if (type.equals("auction")){
            saveFolder = "/auction_images";
        }



        String uploadDirectory = "/home/opc/asset/Auction/Image" +  saveFolder;

        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // 폴더가 없으면 생성
        }

        // 동일한 파일명이 들어올 가능성이 있기 때문에 UUID를 사용해서 고유한 파일명을 생성한다.
        //String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        //String filePath = uploadDirectory + File.separator + uniqueFileName;

        String uniqueFileName = id + "_" + file.getOriginalFilename();

        String filePath = uploadDirectory + File.separator + uniqueFileName;
        File dest = new File(filePath);

        try {
            // 업로드된 파일의 내용을 경로에 복사하여 이미지 파일을 저장
            FileCopyUtils.copy(file.getBytes(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/images"  + saveFolder + "/" + uniqueFileName;
    }
}