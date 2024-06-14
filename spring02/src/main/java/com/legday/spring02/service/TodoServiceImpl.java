package com.legday.spring02.service;

import com.legday.spring02.domain.Todo;
import com.legday.spring02.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService{

    private final TodoMapper todoMapper;

    @Override
    public List<Todo> getTodos() throws Exception {
        return todoMapper.selectTodos();
    }

    @Override
    public Todo getTodo(int tno) throws Exception {
        return todoMapper.selectTodo(tno);
    }
}
