package com.legday.backboard.controller;

import com.legday.backboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    /**
     * BOARD 테이블에 모든 데이터를 갖고와서 Model 객체에 담아 View 에 뿌려준다.
     * <li>@GetMapping("/list") 와 똑같은 기능을 하는 매핑방법이 아래 방법이다.</li>
     * <li>@RequestMapping(value = "/list", method = RequestMethod.GET)</li>
     * @param model
     * @return board/list
     */
    @GetMapping("/list")
    public String boardForm(Model model){
        model.addAttribute("boards", boardService.boardList());
        return "board/list";
    }

    @GetMapping("/detail/{bno}")
    public String detailForm(@PathVariable("bno") Long bno, Model model){
        model.addAttribute("board", boardService.findBoard(bno));
        return "board/detail";
    }
}
