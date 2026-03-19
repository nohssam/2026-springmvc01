package org.study.myproject01.fileup.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.study.myproject01.fileup.vo.FileVO;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/fileup")
public class FileController {
    // application.properties 에 있는 파일 저장위치를 가져온다.
    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload2.path}")
    private String uploadPath2 ;

    private String getUpload2FullPath(){
        String fullPath = System.getProperty("user.dir").replace("\\", "/")+ "/"+ uploadPath2;
        return  fullPath;
    }

    // 업로드 폼으로 이동
    @GetMapping("/fileupForm")
    public String fileupForm(){
        return "fileup/upload";
    }
    @PostMapping("/uploadok")
    public String uploadok(FileVO fileVO, Model model){
        try{
            String ori_name = fileVO.getFile_name().getOriginalFilename();
            // 저장이름은 만들기
            String uuid = UUID.randomUUID().toString();
            String savename = uuid + "_" + ori_name;
            long size = fileVO.getFile_name().getSize();
            String contentType = fileVO.getFile_name().getContentType();

            String fileSize = String.format("%.1f KB",size / 1024.0) ;
            //String fileSize = String.format("%.1f MB",size / (1024.0 * 1024.0)) ;

            log.info("원본파일명: {}", ori_name);
            log.info("파일명: {}", savename);
            log.info("파일크기: {}", fileSize);
            log.info("파일타입: {}", contentType);

            // 실제 저장하기
            File dir = new File(uploadPath);
            // 없으면 폴더 생성
            if(!dir.exists()){dir.mkdirs();}

            fileVO.getFile_name().transferTo(new File(dir, savename));

            model.addAttribute("ori_name",ori_name);
            model.addAttribute("savename",savename);
            model.addAttribute("fileSize",fileSize);
            model.addAttribute("contentType",contentType);

            return "fileup/result";
        }catch (Exception e){
            // 다시 입력 폼으로 이동
            return "fileup/upload";
        }

    }
    @GetMapping("/download")
    public void download(@RequestParam String savename,
                         @RequestParam String ori_name,
                         HttpServletResponse response){
        try{
            // 파일 존재 여부 확인
            File file = new File(uploadPath + savename );
            if(!file.exists()){
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "파일을 찾을 수 없습니다.");
                return;
            }
            String encodeName = URLEncoder.encode(ori_name, StandardCharsets.UTF_8);
            // 브라우저에 다운로드 설정
            response.setHeader("Content-Disposition", "attachment; filename=" + encodeName);
            response.setContentType("application/octet-stream");

            // 실제 다운로드
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("파일 다운로드 오류 : {}", e.getMessage());
        }
    }

    @GetMapping("/upload2")
    public String upload2(){return "fileup/upload2";}

    @PostMapping("/uploadok2")
    public String uploadok2(FileVO fileVO, Model model){
        try{
            String ori_name = fileVO.getFile_name().getOriginalFilename();
            // 저장이름은 만들기
            String uuid = UUID.randomUUID().toString();
            String savename = uuid + "_" + ori_name;
            long size = fileVO.getFile_name().getSize();
            String contentType = fileVO.getFile_name().getContentType();

            String fileSize = String.format("%.1f KB",size / 1024.0) ;
            //String fileSize = String.format("%.1f MB",size / (1024.0 * 1024.0)) ;

            log.info("원본파일명: {}", ori_name);
            log.info("파일명: {}", savename);
            log.info("파일크기: {}", fileSize);
            log.info("파일타입: {}", contentType);

            String fullPath = getUpload2FullPath();
            // 실제 저장하기
            File dir = new File(fullPath);
            // 없으면 폴더 생성
            if(!dir.exists()){dir.mkdirs();}

            fileVO.getFile_name().transferTo(new File(dir, savename));

            model.addAttribute("ori_name",ori_name);
            model.addAttribute("savename",savename);
            model.addAttribute("fileSize",fileSize);
            model.addAttribute("contentType",contentType);

            return "fileup/result2";
        }catch (Exception e){
            // 다시 입력 폼으로 이동
            return "fileup/upload2";
        }
    }

}
