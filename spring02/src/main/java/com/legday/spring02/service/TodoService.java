package com.legday.spring02.service;


import com.legday.spring02.domain.Todo;

import java.util.List;

public interface TodoService {
    public List<Todo> getTodos() throws Exception;
    public Todo getTodo(int tno) throws Exception;
}
