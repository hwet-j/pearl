package com.pits.auction.global.upload;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.UUID;
import java.io.IOException;


@Component
public class AudioUpload {

    /* 이미지 업로드의 기능에서 경로와 메서드명만 바꿔 오디오 업로드 기능으로 구현 */
    public String uploadAudio(MultipartFile file, Long id) {
        // 오디오 저장 폴더 -> 오디오는 경매글에만 존재
        String saveFolder = "/auction_audios";

        String uploadDirectory = "C:/Auction/Audio" + saveFolder;

        File directory = new File(uploadDirectory);
        // 저장 경로가 없으면 생성
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // UUID 기능을 사용해서 고유한 파일명 생성
        // String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        
        // 글번호에 따라 오디오가 저장되도록 설정 -> 고유값은 굳이 필요없을듯 함
        String uniqueFileName = id + "_" + file.getOriginalFilename();

        // 저장폴더와 파일명을 합쳐 저장경로 작성
        String filePath = uploadDirectory + File.separator + uniqueFileName;
        File dest = new File(filePath);

        // 저장
        try {
            FileCopyUtils.copy(file.getBytes(), dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "/audios"  + saveFolder + "/" + uniqueFileName;

    }


}
