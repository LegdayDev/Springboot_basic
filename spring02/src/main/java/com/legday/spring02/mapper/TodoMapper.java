package com.legday.spring02.mapper;

import com.legday.spring02.domain.Todo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
    Todo selectTodo(int tno) throws Exception;
    List<Todo> selectTodos() throws Exception;
}
