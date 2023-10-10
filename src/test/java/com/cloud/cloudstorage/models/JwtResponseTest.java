package com.cloud.cloudstorage.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtResponseTest {
    private final String authToken = "test";

    @Test
    void getterTest() {
        final JwtResponse jwtResponseTest = new JwtResponse("test");
        Assertions.assertEquals(jwtResponseTest.getAuthToken(), authToken);
    }
}
