package com.marcio.ionicmc.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerateAndValidateToken() {
        String token = jwtUtil.generateToken("maria@gmail.com");
        assertNotNull(token);
        assertTrue(jwtUtil.tokenValido(token));
        assertEquals("maria@gmail.com", jwtUtil.getUsername(token));
    }
}
