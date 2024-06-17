package com.legday.backboard.repository;

import com.legday.backboard.entity.Board;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(BoardRepositoryTest.class);

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Rollback(value = false)
    public void bookSave() throws Exception {
        //given
        Board book = Board.builder().title("첫번 째 테스트").content("어쩌라구").createDate(LocalDateTime.now()).build();
        //when
        Board bookPS = boardRepository.save(book);

        log.info("book.id = {}", book.getBno());
        log.info("book.title = {}", book.getTitle());
        log.info("book.content = {}", book.getContent());

        log.info("bookPS.id = {}", bookPS.getBno());
        log.info("bookPS.title = {}", bookPS.getTitle());
        log.info("bookPS.content = {}", bookPS.getContent());
        //then
        assertThat(bookPS.getContent()).isEqualTo(book.getContent());
        assertThat(bookPS.getTitle()).isEqualTo(book.getTitle());
    }

}