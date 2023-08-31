package com.pits.auction.global.upload;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.*;
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
        String saveFolder = "\\images\\uploaded_images";
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


    // 오디오 파일을 1분으로 자르는 메서드
    /*private String cutAudio(String inputFilePath) {
        try {
            AudioFile audioFile = AudioFileIO.read(new File(inputFilePath));
            long frameRate = audioFile.getAudioHeader().getSampleRateAsNumber();
            long startFrame = 0; // 시작 프레임
            long endFrame = frameRate * 60; // 1분에 해당하는 프레임 수

            String outputFileName = UUID.randomUUID().toString() + "_cut" + getFileExtension(inputFilePath);
            String outputPath = inputFilePath.replace(".mp3", "_cut.mp3"); // 기존 파일 경로에서 _cut.mp3로 수정
            File outputFile = new File(outputPath);

            AudioFileIO.write(audioFile, outputFile, startFrame, endFrame - startFrame);

            return outputPath;
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException | CannotWriteException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    // 파일 확장자를 가져오는 메서드
    private String getFileExtension(String filePath) {
        int lastIndex = filePath.lastIndexOf(".");
        if (lastIndex != -1) {
            return filePath.substring(lastIndex);
        }
        return ".mp3"; // 기본 확장자는 MP3로 설정
    }



}