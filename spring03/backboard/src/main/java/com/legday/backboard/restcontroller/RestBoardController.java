package com.legday.backboard.restcontroller;


import com.legday.backboard.dto.BoardDto;
import com.legday.backboard.dto.Header;
import com.legday.backboard.dto.PagingDto;
import com.legday.backboard.dto.ReplyDto;
import com.legday.backboard.entity.Board;
import com.legday.backboard.entity.Category;
import com.legday.backboard.entity.Reply;
import com.legday.backboard.service.BoardService;
import com.legday.backboard.service.CategoryService;
import com.legday.backboard.service.MemberService;
import com.legday.backboard.validation.ReplyForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
@Log4j2
public class RestBoardController {

    private final BoardService boardService; // 중간 연결책
    private final MemberService memberService;   // 사용자 정보
    private final CategoryService categoryService;  // 카테고리 사용

    @GetMapping("/list/{category}")
    @ResponseBody
    public Header<List<BoardDto>> list(@PathVariable(value = "category") String category,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "kw", defaultValue = "") String keyword) {

        Category cate = this.categoryService.findCategory(category);     // cate는 Category 객체, 변수사용 X
        Page<Board> pages = this.boardService.boardList(page, keyword, cate);   // 검색 및 카테고리 추가
        // List<Board> list = pages.getContent();

        PagingDto paging = new PagingDto(pages.getTotalElements(), pages.getNumber() + 1, 10, 10);

        List<BoardDto> result = new ArrayList<BoardDto>();
        long curNum = pages.getTotalElements() - (pages.getNumber() * 10);
        for (Board origin : pages) {
            List<ReplyDto> subList = new ArrayList<>();

            BoardDto bdDto = new BoardDto();
            bdDto.setNum(curNum--);
            bdDto.setBno(origin.getBno());
            bdDto.setTitle(origin.getTitle());
            bdDto.setContent(origin.getContent());
            bdDto.setCreateDate(origin.getCreateDate());
            bdDto.setModifyDate(origin.getModifyDate());
            bdDto.setWriter(origin.getWriter() != null ? origin.getWriter().getUsername() : "");
            bdDto.setHit(origin.getHit());
            if (origin.getReplies().size() > 0) {
                for (Reply reply : origin.getReplies()) {
                    ReplyDto replyDto = new ReplyDto();
                    replyDto.setRno(reply.getRno());
                    replyDto.setContent(reply.getContent());
                    replyDto.setCreateDate(reply.getCreateDate());
                    replyDto.setModifyDate(reply.getModifyDate());
                    replyDto.setWriter(reply.getWriter() != null ? reply.getWriter().getUsername() : "");

                    subList.add(replyDto);
                }
                bdDto.setReplyList(subList);
            }
            result.add(bdDto);
        }

        log.info(String.format(">>>>>> result에서 넘긴 게시글 수 %s", result.size()));
        // model.addAttribute("pages", pages);
        // model.addAttribute("kw", keyword);
        // model.addAttribute("category", category);

        // Header<> 에 담는다.
        Header<List<BoardDto>> last = Header.OK(result, paging);

        return last;
    }

    @GetMapping("/detail/{bno}")
    public BoardDto detailForm(@PathVariable("bno") Long bno, HttpServletRequest request) {
        String prevUrl = request.getHeader("referer");
        Board boardPS = boardService.hitBoard(bno);
        BoardDto board = BoardDto.builder()
                .bno(boardPS.getBno())
                .title(boardPS.getTitle())
                .content(boardPS.getContent())
                .createDate(boardPS.getCreateDate())
                .modifyDate(boardPS.getModifyDate())
                .hit(boardPS.getHit())
                .writer(boardPS.getWriter().getUsername())
                .build();

        System.out.println("========");
        System.out.println(board.getReplyList());
        System.out.println("========");
        List<ReplyDto> replyList = new ArrayList<>();
        if (boardPS.getReplies() != null) {
            boardPS.getReplies().forEach(
                    reply -> replyList.add(ReplyDto.builder().
                            content(reply.getContent()).
                            createDate(reply.getCreateDate()).
                            modifyDate(reply.getModifyDate()).
                            rno(reply.getRno()).
                            writer(reply.getWriter().getUsername()).
                            build())
            );
        }
        board.setReplyList(replyList);
        return board;
    }
}
