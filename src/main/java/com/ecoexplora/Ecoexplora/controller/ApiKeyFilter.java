package com.ecoexplora.Ecoexplora.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@Component
@WebFilter("/*") // Aplica o filtro a todas as requisições
public class ApiKeyFilter implements Filter {

    @Value("${api.key}") // Obtém a API Key do application.properties
    private String apiKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestApiKey = req.getHeader("X-API-KEY");

        if (requestApiKey == null || !requestApiKey.equals(apiKey)) {
            throw new ServletException("API Key inválida ou ausente!");
        }

        chain.doFilter(request, response); // Permite a requisição continuar
    }
}
