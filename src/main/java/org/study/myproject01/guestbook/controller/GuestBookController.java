package org.study.myproject01.guestbook.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.study.myproject01.guestbook.service.GuestBookService;
import org.study.myproject01.guestbook.vo.GuestBookVO;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
    @Autowired
    private GuestBookService guestBookService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // resources/upload 때문에 추가
     @Value("${file.upload2.path}")
     private String uploadPath2;

    // resources/upload 때문에 추가
     private String getUpload2FilePath(){
         return System.getProperty("user.dir").replace("\\", "/")+ "/"+ uploadPath2;
     }

    // @GetMapping  => Get방식만
    // @PostMapping => Post 방식만
    // @RequestMapping => GET,POST,PUT,DELETE 등 모두 다

    // @ResponseBody
    // @ResponseBody는 Spring 프레임워크에서 메서드의 반환값을
    //  HTTP 응답 본문(Body)에 직접 기록하도록 지정하는 어노테이션입니다
    @GetMapping("/hello")
    @ResponseBody
    public String getHello(){
        return "hello";
    }

    @GetMapping("/hi")
    @ResponseBody
    public String getHi(){
        return "안녕하세요";
    }

    // @ResponseBody 가 없으면 jsp를 찾는다.
    // application.properites 에서 jsp 설정 한것이 있다.
    // spring.mvc.view.prefix=/WEB-INF/views   (접두어)
    // spring.mvc.view.suffix=.jsp             (접미어)

    // @GetMapping("/") =>  첫페이지 => homepage
    @GetMapping("/")
    public String getBye(){
        // /WEB-INF/views/index.jsp
        return "index";
    }

    @GetMapping("/list")
    public ModelAndView list(){
        // Model => 데이터 저장
        // View => jsp 위치
        ModelAndView mv = new ModelAndView("guestbook/list");

        // WEB-INF/views/guestbook/list.jsp
        // DB처리하자
        List<GuestBookVO> list = guestBookService.selectAll();
        log.info("list : {}", list);

        // jsp에서는
        // req.setAttribute("list", list);

        mv.addObject("list", list);
        return mv;
    }

    @GetMapping("/list2")
    public ModelAndView list2(){
        // Model => 데이터 저장
        // View => jsp 위치
        ModelAndView mv = new ModelAndView();
        mv.setViewName("guestbook/list");

        // WEB-INF/views/guestbook/list.jsp
        // DB처리하자
        List<GuestBookVO> list = guestBookService.selectAll();
        log.info("list : {}", list);

        // jsp에서는
        // req.setAttribute("list", list);

        mv.addObject("list", list);
        return mv;
    }
    @GetMapping("/list3")
    public String list3(Model model){
        // Model => 데이터 저장

        // WEB-INF/views/guestbook/list.jsp
        // DB처리하자
        List<GuestBookVO> list = guestBookService.selectAll();

        // jsp에서는
        // req.setAttribute("list", list);
        model.addAttribute("list", list);
        return "guestbook/list";
    }

    @GetMapping("/luck")
    public String getLuck(Model model){
        String luck =  guestBookService.getLuck();

        // 저장
        model.addAttribute("luck",luck);
        // /WEB-INF/views/today.jsp
        return "today";
    }

    @GetMapping("/write")
    public String write(){
        // DB X
        // 일처리 X
        // 다른 페이지로 이동 O
        // /WEB-INF/views/guestbook/write.jsp
        return "guestbook/write";
    }

    // jsp에서 넘어오는 파라미터이름과 GuestBookVO에 변수이름이 같으면
    // 자동으로 GuestBookVO 에 저장된다.

    @PostMapping("/writeok")
    public String writeok(GuestBookVO gvo, Model model){
//       정보를 제대로 받았는지 확인하자.
//        log.info("g_write : {}", gvo.getG_writer());
//        log.info("g_subject : {}", gvo.getG_subject());
//        log.info("g_pwd : {}", gvo.getG_pwd());
//        log.info("g_email :{}", gvo.getG_email() );
//        log.info("g_content : {}", gvo.getG_content());
//        리다이렉트를 이용해서 /guestbook/list 로 이동한다.
//         파일 이전 코딩
//        int result = guestBookService.insertGuestBook(gvo);
//        if(result > 0){
//            return "redirect:/guestbook/list";
//        }else{
//            return "guestbook/error";
//        }

        // 첨부파일이 있는 경우
        if(gvo.getFile_name() != null && !gvo.getFile_name().isEmpty()) {
            try {
                String ori_name = gvo.getFile_name().getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String save_name = uuid + "_" + ori_name;

                // resources 안에 upload 에 넣기 위해서  수정
                String fullPath = getUpload2FilePath();
                File dir = new File(fullPath);

                if(!dir.exists()){dir.mkdirs();}

                // 실제 폴더 이미지 저장
                gvo.getFile_name().transferTo(new File(dir, save_name));

                // DB에 들어갈 이름 넣기
                gvo.setF_name(save_name);
            } catch (Exception e) {
                log.error("파일 업로드 오류: {}", e.getMessage());
                model.addAttribute("msg", "파일 업로드 중 오류 발생:" + e.getMessage());
                return "guestbook/error";
            }
        }else{
            // 첨부파일이 없을때 처리
            gvo.setF_name("");
        }
        // 비밀번호 BCrypt 암호화 후 저장
//        String k1 = gvo.getG_pwd();
//        log.info("k1: {}", k1);
//        String k2 = passwordEncoder.encode(k1);
//        log.info("k2: {}", k2);
//        gvo.setG_pwd(k2);

        gvo.setG_pwd(passwordEncoder.encode(gvo.getG_pwd()));
        int result = guestBookService.insertGuestBook(gvo);
        if(result > 0){
            return "redirect:/guestbook/list";
        }else{
            return "guestbook/error";
        }
    }
    @RequestMapping("/detail")
    public String detail(GuestBookVO gvo, Model model){
        GuestBookVO guestBookVO = guestBookService.selectDetail(gvo);
        if(guestBookVO != null){
            model.addAttribute("guestBookVO",guestBookVO);
            return "guestbook/detail";
        }else{
            return "guestbook/error";
        }
    }
    @PostMapping("/deleteForm")
    public String deleteForm(GuestBookVO gvo, Model model){
        model.addAttribute("g_idx",gvo.getG_idx());
//        model.addAttribute("g_pwd",gvo.getG_pwd());
        return "guestbook/delete";
    }
    @PostMapping("/deleteok")
    public String deleteok(GuestBookVO gvo, Model model){
        // g_idx, g_pwd 가 넘어왔다.
        // 일딴, g_idx를 이용해서 DB에 있는 암호화 된 패스워드를 구하자
        GuestBookVO guestBookVO = guestBookService.selectDetail(gvo);
        String db_pwd = guestBookVO.getG_pwd();

        // 사용자가 입력한 g_pwd
        String client_pwd = gvo.getG_pwd();

        // 사용자가 입력한 g_pwd 와 DB 에서 암호화 된 패스워드를 비교해서 같은지 판별하자
        if(passwordEncoder.matches(client_pwd, db_pwd)){
            // 참이면 비밀번호 맞다
            int result = guestBookService.deleteGuestBook(gvo);
            if(result > 0){
                return "redirect:/guestbook/list3?msg=deleted";
            }else{
                return "guestbook/error";
            }
        }else{
           // 비밀번호 틀리다.
          model.addAttribute("pwdchk", "fail");
          model.addAttribute("g_idx", gvo.getG_idx());
          return "guestbook/delete";
        }
    }
    @PostMapping("/updateForm")
    public String updateForm(GuestBookVO gvo, Model model){
        GuestBookVO guestBookVO = guestBookService.selectDetail(gvo);
        if(guestBookVO != null){
            model.addAttribute("guestBookVO",guestBookVO);
            return "guestbook/update";
        }else{
            return "guestbook/error";
        }

    }
    @PostMapping("/updateok")
    public String updateok(GuestBookVO gvo, Model model){
        // 비밀번호 검증
        GuestBookVO guestBookVO = guestBookService.selectDetail(gvo);
        String db_pwd = guestBookVO.getG_pwd();
        String client_pwd = gvo.getG_pwd();

        if(!passwordEncoder.matches(client_pwd, db_pwd)){
            // 비밀번호 틀림 → 수정 폼으로 돌아가기
            model.addAttribute("pwdchk", "fail");
            model.addAttribute("guestBookVO", guestBookVO);
            return "guestbook/update";
        }

         // 수정했을 때
         if(gvo.getFile_name() != null && !gvo.getFile_name().isEmpty()) {
             try{
                 String ori_name = gvo.getFile_name().getOriginalFilename();
                 String uuid = UUID.randomUUID().toString();
                 String save_name = uuid + "_" + ori_name;

                 // resources 안에 upload 에 넣기 위해서  수정
                 String fullPath = getUpload2FilePath();
                 File dir = new File(fullPath);
                 if(!dir.exists()){dir.mkdirs();}

                 // 실제 폴더 이미지 저장
                 gvo.getFile_name().transferTo(new File(dir, save_name));

                 // DB에 들어갈 이름 넣기
                 gvo.setF_name(save_name);
             } catch (Exception e) {
                 return "guestbook/error";
             }
         }
        int result = guestBookService.updateGuestBook(gvo);
        if(result > 0){
            return  "redirect:/guestbook/detail?g_idx="+gvo.getG_idx();
        }else{
            return "guestbook/error";
        }
    }

    @GetMapping("/downlaod")
    public void  downlaod(@RequestParam String f_name, HttpServletResponse response){
        try{
            // 파일 존재 여부 확인
            File file = new File(uploadPath2+ f_name);
            if(! file.exists()){
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "파일이 존재하지 않습니다.");
                return;
            }

            String encodeName = URLEncoder.encode(f_name, StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=" + encodeName);
            response.setContentType("application/octet-stream");

            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
            response.getOutputStream().flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
