package com.airbnb_2.config;

import com.airbnb_2.entity.PropertyUser;
import com.airbnb_2.repository.PropertyUserRepository;
import com.airbnb_2.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;

    private PropertyUserRepository userRepository;

    public JWTRequestFilter(JWTService jwtService, PropertyUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(8,tokenHeader.length()-1);
            System.out.println(token);
            String username = jwtService.getUsername(token);
            Optional<PropertyUser> opUser = userRepository.findByUserName(username);
            if(opUser.isPresent()){
                PropertyUser user = opUser.get();
                //To Keep track of current user logged in
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user,null, Collections.singleton(new SimpleGrantedAuthority(user.getUserRole())));
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request,response);
    }
}
