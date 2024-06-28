package com.legday.backboard.service;

import com.legday.backboard.common.NotFoundException;
import com.legday.backboard.entity.Reset;
import com.legday.backboard.repository.ResetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class ResetService {

    private final ResetRepository resetRepository;

    public void saveReset(String uuid, String email) {
        resetRepository.save(
                Reset.builder().
                        uuid(uuid).
                        email(email).
                        regDate(LocalDateTime.now()).
                        build());
        log.info("ResetService = saveReset() 메서드 성공");
    }

    public Reset findResetByUuid(String uuid) {
        return resetRepository.findByUuid(uuid).orElseThrow(() -> {
            log.warn("ResetService = findResetByUuid() 메서드 실패 !");
            throw new NotFoundException("찾는 객체가 없습니다!");
        });
    }
}
