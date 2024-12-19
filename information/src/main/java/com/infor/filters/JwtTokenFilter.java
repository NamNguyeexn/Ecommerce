package com.infor.filters;

import com.infor.services.IRedisInfoService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static java.util.Optional.ofNullable;
import static org.aspectj.util.LangUtil.isEmpty;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter implements Filter {
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private IRedisInfoService redisInfoService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenService.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }
        int id = jwtTokenService.getAccountId(token);
//        UserDetails userDetails = redisInfoService.getAccount(id);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                id, null,
//                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(new ArrayList<>())
                Collections.emptyList()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}