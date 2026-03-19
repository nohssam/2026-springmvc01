package org.study.myproject01.board.controller;

import lombok.extern.slf4j.Slf4j;
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
import org.study.myproject01.board.service.BoardService;
import org.study.myproject01.board.vo.BoardVO;
import org.study.myproject01.common.Paging;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BBSService bbsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 파일 저장 처리
    @Value("${file.upload2.path}")
    private String upload2Path;
    @Autowired
    private BoardService boardService;

    private String getUpload2Path(){
        return System.getProperty("user.dir").replace("\\", "/")+ "/"+ upload2Path;
    }

   @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int nowPage,
                       Model model){
       Paging paging = new Paging();
       paging.setNowPage(nowPage);

       // 1. 전체 게시물의 수
       int count = boardService.getBoardCount();
       paging.setTotalRecord(count);

       // 2. 전체 페이지의 수
       if(paging.getTotalRecord() <= paging.getNumPerPage()){
           paging.setTotalPage(1);
       }else{
            int cnt = paging.getTotalRecord()/paging.getNumPerPage();
            if(paging.getTotalRecord()%paging.getNumPerPage() != 0){
                paging.setTotalPage(++cnt);
            }else{
                paging.setTotalPage(cnt);
            }
       }
       // 3. offset 구하기 (MySQL, MariaDB)
       // offset = (현재페이지-1) * limit(numPerPage)
       paging.setOffset((paging.getNowPage() - 1) * paging.getNumPerPage());

       // 5. 시작 블록과 끝 블록
       // 1-3,  4-6,  7-9, 10-12, 13-16, 17-20
       paging.setBeginBlock((int)(((paging.getNowPage()-1)/paging.getPagePerBlock()) * paging.getPagePerBlock()+1));
       paging.setEndBlock(paging.getBeginBlock()+paging.getPagePerBlock()-1);

       // 6.주의 사항
       if(paging.getEndBlock() >= paging.getTotalPage()){
            paging.setEndBlock((paging.getTotalPage()));
       }

       // 7. 필요한 만큼 게시물 가져오기
       List<BoardVO> list = boardService.getBoardList(paging.getNumPerPage(), paging.getOffset());

       model.addAttribute("paging", paging);
       model.addAttribute("list", list);
       return "board/list";
   }

   @GetMapping("/write")
    public String write(){
        return "board/write";
   }

   @PostMapping("/writeok")
   public String writeok(BoardVO boardVO){
        // 첨부파일 처리
       if(boardVO.getFile_name() != null && !boardVO.getFile_name().isEmpty()){
           try{
               String ori_name = boardVO.getFile_name().getOriginalFilename();
               String uuid = UUID.randomUUID().toString();
               String save_name = uuid + "_" +  ori_name;
               boardVO.setF_name(save_name);

               // 실제 이미지 저장
               String fullPath = getUpload2Path();
               File dir = new File(fullPath);
               if(! dir.exists()){dir.mkdirs();}

               boardVO.getFile_name().transferTo(new File(dir, save_name));
           } catch (Exception e) {
               return "board/error";
           }
       }
       // 비밀번호 암호화
       boardVO.setPwd(passwordEncoder.encode(boardVO.getPwd()));
       // DB 저장
       int result = boardService.getBoardInsert(boardVO);
     if(result > 0){
         return "redirect:/board/list";
     }else{
         return "board/error";
     }
   }
   @GetMapping("/detail")
    public String detail(@RequestParam("nowPage") String nowPage,
                         @RequestParam(required = false)String pwd_error, BoardVO boardVO, Model model){
        // pwd  점검
       if(pwd_error != null && !pwd_error.isEmpty()){
           model.addAttribute("pwd_error", pwd_error);
       }
        // 조회수 증가
       int result = boardService.getBoardHitUp(boardVO);
       // 상세보기 정보 가져오기
       BoardVO bvo = boardService.getBoardDetail(boardVO);
       if(result > 0 && bvo != null){
           model.addAttribute("bvo", bvo);
           return "board/detail";
       }else{
           return "board/error";
       }
   }
   @PostMapping("/AnsWriteForm")
    public String AnsWriteForm(@RequestParam("nowPage") String nowPage,
                               BoardVO boardVO,Model model){
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("b_idx", boardVO.getB_idx());
       return  "board/answrite";
   }
   @PostMapping("/answriteok")
    public String answriteok(@RequestParam("nowPage") String nowPage,BoardVO boardVO){
        // 1. Board Detail로 해당 정보를 가져오자
        // 2. step, lev 를 수정해야 된다.
       BoardVO bvo = boardService.getBoardDetail(boardVO);
       int b_groups = Integer.parseInt(bvo.getB_groups());
       int b_step =  Integer.parseInt(bvo.getB_step());
       int b_lev =   Integer.parseInt(bvo.getB_lev());

       b_step++;
       b_lev++;

       // DB 에 증가한 정보를 수정하자
       Map<String,Integer> map = new HashMap<>();
       map.put("b_groups",b_groups);
       map.put("b_lev",b_lev);

       // 원글에 대한 첫 답글은 업데이트를 하지 않는다.
       boardService.getLevUp(map);
       boardVO.setB_groups(String.valueOf(b_groups));
       boardVO.setB_step(String.valueOf(b_step));
       boardVO.setB_lev(String.valueOf(b_lev));

       //========= 이후부터는 원글 삽입과 같음  ======================
       // 파일 존재하면
       if(boardVO.getFile_name() != null && !boardVO.getFile_name().isEmpty()){
           try{
               String ori_name = boardVO.getFile_name().getOriginalFilename();
               String uuid = UUID.randomUUID().toString();
               String save_name = uuid + "_" +  ori_name;

               String fullPath = getUpload2Path();
               File dir = new File(fullPath);
               if(! dir.exists()){dir.mkdirs();}

               boardVO.getFile_name().transferTo(new File(save_name));

           }catch (Exception e){
               log.info(e.getMessage());
               return "board/error";
           }

       }
       // 비밀번호 암호화
       boardVO.setPwd(passwordEncoder.encode(boardVO.getPwd()));
       // DB 저장
       int result = boardService.getBoardAnsInsert(boardVO);
       if(result > 0){
           return "redirect:/board/list?nowPage="+nowPage;
       }else{
           return "board/error";
       }
   }
   @PostMapping("/deleteForm")
    public String deleteForm(@RequestParam("nowPage") String nowPage,
                             BoardVO boardVO, Model model){
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("b_idx", boardVO.getB_idx());
        return  "board/delete";
   }

   @PostMapping("/deleteok")
    public String deleteok(@RequestParam("nowPage") String nowPage,
                           BoardVO boardVO){
       BoardVO bvo = boardService.getBoardDetail(boardVO);
       if(passwordEncoder.matches(boardVO.getPwd(),bvo.getPwd())){
            // 삭제하기
            boardService.getDelete(bvo);
           return "redirect:/board/list?nowPage="+nowPage;
       }else{
           return "redirect:/board/detail?b_idx="+boardVO.getB_idx()+"&nowPage="+nowPage+"&pwd_error=pwd_error";
       }
   }

}
