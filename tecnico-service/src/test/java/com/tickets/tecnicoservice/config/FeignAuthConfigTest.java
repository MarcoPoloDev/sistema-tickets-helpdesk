package com.tickets.tecnicoservice.config;

import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeignAuthConfigTest {
    @AfterEach
    void limpiar() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void reenviaBearerHaciaAuthService() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer token-de-prueba");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        RequestTemplate template = new RequestTemplate();
        new FeignAuthConfig().forwardBearerToken().apply(template);
        assertTrue(template.headers().get("Authorization").contains("Bearer token-de-prueba"));
    }

    @Test
    void noAgregaAuthorizationCuandoNoHaySolicitudHttp() {
        RequestTemplate template = new RequestTemplate();
        new FeignAuthConfig().forwardBearerToken().apply(template);
        assertFalse(template.headers().containsKey("Authorization"));
    }
}
