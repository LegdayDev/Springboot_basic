package com.legday.spring02.controller;

import com.legday.spring02.domain.Todo;
import com.legday.spring02.service.TodoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TodoController {

    @Resource
    TodoService todoService;

    @GetMapping("/todos")
    public String getTodos(Model model) throws Exception {
        model.addAttribute("todos", todoService.getTodos());
        return "todolist";
    }

    @GetMapping("/todo/{tno}")
    @ResponseBody
    public Todo getTodo(@PathVariable int tno) throws Exception {
        return todoService.getTodo(tno);
    }
}
