package org.study.myproject01.bbs.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.study.myproject01.bbs.service.BBSService;
import org.study.myproject01.bbs.vo.BBSVO;
import org.study.myproject01.bbs.vo.CommentVO;
import org.study.myproject01.common.Paging;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/bbs")
public class BBSController {
    @Autowired
    private BBSService bbsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 파일 저장 처리
    @Value("${file.upload2.path}")
    private String upload2Path;
    private String getUpload2Path(){
        return System.getProperty("user.dir").replace("\\", "/")+ "/"+ upload2Path;
    }
    // 나중에 paging  처리

//    페이징 기법 사용하기 전
//    @RequestMapping("/list")
//    public String list(Model model) {
//        List<BBSVO> list = bbsService.getBBSList();
//        model.addAttribute("list", list);
//      return "bbs/list";
//    }

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int nowPage,   Model model) {
        Paging paging = new Paging();
        // 현재 페이지
        paging.setNowPage(nowPage);

        // List<BBSVO> list = bbsService.getBBSList();
        // 1. 전체 게시물의 수 구하자
        int count = bbsService.getCount();
        paging.setTotalRecord(count);

        // 2. 전체 페이지의 수 구하자
        if(paging.getTotalRecord() <= paging.getNumPerPage()){
            paging.setTotalPage(1);
        }else{
            int cnt = paging.getTotalRecord()/paging.getNumPerPage();
            if(paging.getTotalRecord() % paging.getNumPerPage() != 0){
                paging.setTotalPage(++cnt);
            }else{
                paging.setTotalPage(cnt);
            }
        }

        // 3. offset 구하기 ( MySQL, MariaDB)
        // 현재 페이지가 1페이지-> 0, 2페이지-> 4, 3페이지->8
        // offset = limit * (현재페이지-1) ;
        // limit = numPerPage
        paging.setOffset((paging.getNowPage() - 1) * paging.getNumPerPage());

        // 5. 시작 블록과 끝 블록
        // 1-3,  4-6,  7-9, 10-12,
        paging.setBeginBlock((int)(((paging.getNowPage()-1)/paging.getPagePerBlock()) * paging.getPagePerBlock()+1));
        paging.setEndBlock(paging.getBeginBlock()+paging.getPagePerBlock()-1);

        // 주의 사항
        // endBlock(3,6,9,12)
        // totalPage 는 3,6,9 로 끝나지 않은경우가 있다.
        if(paging.getEndBlock() >= paging.getTotalPage()){
            paging.setEndBlock(paging.getTotalPage());
        }

        // 7. 필요한 만큼 게시물을 가져오자
        List<BBSVO> list = bbsService.getBBSList(paging.getNumPerPage(), paging.getOffset());

        // 확인
//        log.info("count : {}", count );
//        log.info("list : {}", list.size() );
//        log.info("beginBlock : {}", paging.getBeginBlock() );
//        log.info("endBlock : {}", paging.getEndBlock() );

        model.addAttribute("list", list);
        model.addAttribute("paging", paging);
        return "bbs/list";
    }

    @GetMapping("/write")
    public String write() {
        return "bbs/write";
    }
    @PostMapping("/writeok")
    public String writeok(BBSVO bbsVO) {

        if(bbsVO.getFile_name() != null && !bbsVO.getFile_name().isEmpty()) {
            try {
                // 첨부파일이 있는 경우
                String ori_name = bbsVO.getFile_name().getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String save_name = uuid + "_" + ori_name;
                // 첨부파일이 있는 경우
                bbsVO.setF_name(save_name);

                // 실제 이미지 저장
                String fullPath = getUpload2Path();
                File dir = new File(fullPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                bbsVO.getFile_name().transferTo(new File(dir, save_name));
            } catch (Exception e) {
                //  throw new RuntimeException(e);
                log.info(e.getMessage());
                return "bbs/error";
            }
        }

        // 비밀번호 암호화
        bbsVO.setPwd(passwordEncoder.encode(bbsVO.getPwd()));

        // DB에 저장
        int result = bbsService.getBBSInsert(bbsVO);
        if (result > 0) {
            return "redirect:/bbs/list";
        }else{
            return "bbs/error";
        }
    }
    @GetMapping("/detail")
    public String detail(@RequestParam("nowPage") String nowPage,
                         @RequestParam(required = false)String pwd_error,  BBSVO bbsvo, Model model) {
        //  pwd_error 가 올수도 있고, 안올수도 있다.
         if(pwd_error != null && !pwd_error.isEmpty()) {
             model.addAttribute("pwd_error", pwd_error);
         }
        // 조회수 증가
        int result = bbsService.getBBSHitUpdate(bbsvo);

        // 상세 보기
        BBSVO bvo = bbsService.getBBSDetail(bbsvo);

        // 댓글 리스트
        List<CommentVO> c_list = bbsService.getCommentList(bbsvo);

        // 조회수 증가 하고 상세보기 가져왔을 때
        if (result > 0 && bvo != null) {
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("bvo", bvo);
            model.addAttribute("c_list", c_list);
            model.addAttribute("c_count", c_list.size());
            return "bbs/detail";
        }else{
            return "bbs/error";
        }
    }
    @GetMapping("/download")
    public void download(@RequestParam String f_name, HttpServletResponse response) {
        try{
            // 파일의 존재 여부 확인
            File file = new File(upload2Path + f_name);
            if(! file.exists()){
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "파일이 존재하지 않습니다.");
            }
            String ecodeName = URLEncoder.encode(f_name, StandardCharsets.UTF_8);
            // 브라우저에 첨부파일 있다고 설정
            response.setHeader("Content-Disposition", "attachment; filename=" + ecodeName);
            response.setContentType("application/octet-stream");

            // 실제 다운로드 처리
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
            response.getOutputStream().flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/c_insert")
    public String c_insert(@RequestParam("nowPage") String nowPage, CommentVO commentVO) {
        // 비밀번호 암호화
        commentVO.setPwd(passwordEncoder.encode(commentVO.getPwd()));
        bbsService.getCommentInsert(commentVO);
        return "redirect:/bbs/detail?b_idx="+commentVO.getB_idx()+"&nowPage="+nowPage;
    }
    @PostMapping("/c_delete")
    public String c_delete(@RequestParam("nowPage") String nowPage, CommentVO commentVO) {
        // 비밀번호 검증 하기 위해서  c_idx로 정보를 가져오자
        CommentVO cvo = bbsService.getCommentDetail(commentVO);
        // 비밀번호 검증
        if(passwordEncoder.matches(commentVO.getPwd(), cvo.getPwd())) {
            // 댓글 삭제 (소프트삭제 = active => 1)
            bbsService.getCommentDelete(commentVO);
            return "redirect:/bbs/detail?b_idx="+commentVO.getB_idx()+"&nowPage="+nowPage;
        }else{
            return "redirect:/bbs/detail?b_idx="+commentVO.getB_idx()+"&nowPage="+nowPage+"&pwd_error="+commentVO.getC_idx();
        }
    }
    // 원글 삭제 폼 (비밀번호 확인 화면으로 이동)
    @PostMapping("/deleteForm")
    public String deleteForm(@RequestParam("nowPage") String nowPage,
                             BBSVO  bbsVO, Model model) {
        // 받은 정보 넘기자
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("b_idx", bbsVO.getB_idx());
        return "bbs/delete";
    }
    @PostMapping("/deleteok")
    public String deleteok(@RequestParam("nowPage") String nowPage,
                           BBSVO  bbsVO, Model model){
        // 비밀번호 검사하기 위해서 기존 글 가져오기
        BBSVO bvo = bbsService.getBBSDetail(bbsVO);
        if(passwordEncoder.matches(bbsVO.getPwd(), bvo.getPwd())) {
            // 소프트 삭제 (active = 1)
            bbsService.deleteBBS(bbsVO);
            return "redirect:/bbs/list?nowPage="+nowPage;
        }else{
            return "redirect:/bbs/detail?b_idx="+bbsVO.getB_idx()+"&nowPage="+nowPage+"&pwd_error=pwd_error";
        }

    }
    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("nowPage") String nowPage,
                             BBSVO  bbsVO, Model model) {
        BBSVO bvo = bbsService.getBBSDetail(bbsVO);
        // 받은 정보 넘기자
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("bvo", bvo);
        return "bbs/update";
    }
    // 원글 수정
    @PostMapping("/updateok")
    public String updateok(@RequestParam("nowPage")String nowPage, BBSVO  bbsVO, Model model){
        // 비밀번호 검증
        BBSVO bvo = bbsService.getBBSDetail(bbsVO);
        if(! passwordEncoder.matches(bbsVO.getPwd(), bvo.getPwd())) {
            return "redirect:/bbs/detail?b_idx="+bbsVO.getB_idx()+"&nowPage="+nowPage+"&pwd_error=pwd_error";
        }
       // 새 첨부파일 처리
        if(bbsVO.getFile_name() != null && !bbsVO.getFile_name().isEmpty()){
            try{
                String ori_name = bbsVO.getFile_name().getOriginalFilename();
                String uuid =  UUID.randomUUID().toString();
                String save_name = uuid + "_" + ori_name;
                bbsVO.setF_name(save_name);

                String fullPath = getUpload2Path();
                File dir = new File(fullPath);
                if(!dir.exists()){ dir.mkdirs(); }
                // 실제 저장
                bbsVO.getFile_name().transferTo(new File(dir,save_name));
            }catch (Exception e){
                return "bbs/error";
            }
        }
        int result = bbsService.updateBBS(bbsVO);
        if(result > 0){
            return "redirect:/bbs/detail?b_idx="+bbsVO.getB_idx()+"&nowPage="+nowPage;
        }else{
            return "bbs/error";
        }
    }
}
