package com.locafy.locafy_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        System.out.println("=== FILTRO JWT EJECUTADO ===");
        System.out.println("URL peticion: " + request.getRequestURI());
        System.out.println("header authorization: " + header);

        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            try {


                String username = jwtUtil.extraerUsername(token);
                String rol = jwtUtil.extraerRol(token);

                System.out.println("Usuario extraido del token: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    System.out.println("!!TOKEN VALIDO DEJANDO PASAR A: " + username);
                    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                System.out.println("El token es invalido o expiró: " + e.getMessage());
            }
        }else{
            System.out.println("No se encontró la palabra 'Bearer' en el header");
        }

        chain.doFilter(request, response);
    }
}
