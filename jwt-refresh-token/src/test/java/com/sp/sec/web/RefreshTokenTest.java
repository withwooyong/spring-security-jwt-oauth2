package com.sp.sec.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sp.sec.board.domain.SpBoard;
import com.sp.sec.board.domain.SpBoardSummary;
import com.sp.sec.board.service.SpBoardService;
import com.sp.sec.board.service.SpBoardTestHelper;
import com.sp.sec.web.config.JWTUtil;
import com.sp.sec.web.util.RestResponsePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * user1 이 두개의 게시물을 올린다.
 */
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RefreshTokenTest extends SpJwtRefreshableTwoUserIntegrationTest {

    @Autowired
    private SpBoardService boardService;

    @Autowired
    private JWTUtil jwtUtil;

    @BeforeEach
    void before() throws URISyntaxException {
        prepareTwoUsers();
        boardService.clearBoards();
        Tokens tokens = userLogin("user1");
        writeBoard(tokens, SpBoardTestHelper.makeBoard(USER1, "title1", "content1"));
        writeBoard(tokens, SpBoardTestHelper.makeBoard(USER1, "title2", "content2"));
    }

    private void writeBoard(Tokens tokens, SpBoard board1) throws URISyntaxException {
        ResponseEntity<SpBoard> response = restTemplate.exchange(uri("/board/save"),
                HttpMethod.POST, getPostAuthHeaderEntity(tokens.getAccessToken(), board1),
                SpBoard.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @DisplayName("1. user2 가 게시물을 조회하고, 일정 시간이 지나 토큰이 만료된 후 다시 조회한다.")
    @Test
    void test_1() throws URISyntaxException, JsonProcessingException, InterruptedException {
        setTokenTimeTo1Sec();

        final Tokens firstToken = userLogin("user2");
        boardWritingCount2(firstToken);

        Thread.sleep(2000);

        assertThrows(HttpClientErrorException.class, () -> {
            boardWritingCount2(firstToken);
        });

        Tokens refreshToken = getRefreshToken(firstToken.getRefreshToken());
        boardWritingCount2(refreshToken);
    }

    private void boardWritingCount2(Tokens tokens) throws URISyntaxException, JsonProcessingException {
        ResponseEntity<String> response = restTemplate.exchange(uri("/board/list"),
                HttpMethod.GET, getAuthHeaderEntity(tokens.getAccessToken()), String.class);
        assertEquals(200, response.getStatusCodeValue());
        RestResponsePage<SpBoardSummary> page = objectMapper.readValue(response.getBody(),
                new TypeReference<RestResponsePage<SpBoardSummary>>() {
                });
        assertEquals(2, page.getTotalElements());
    }

    private Tokens userLogin(String name) throws URISyntaxException {
        return getToken(name + "@test.com", name + "123");
    }

    private void setTokenTimeTo1Sec() {
        jwtUtil.getProperties().setTokenLifeTime(1);
    }


}
