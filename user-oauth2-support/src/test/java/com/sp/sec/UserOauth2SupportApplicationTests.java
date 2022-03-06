package com.sp.sec;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@SpringBootTest
class UserOauth2SupportApplicationTests {

    @Test
    void contextLoads() {
    }

}
