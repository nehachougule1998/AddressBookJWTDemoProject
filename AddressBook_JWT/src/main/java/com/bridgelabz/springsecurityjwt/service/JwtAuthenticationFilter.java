package com.bridgelabz.springsecurityjwt.service;

import com.bridgelabz.springsecurityjwt.helper.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            //get the header
            //check if it starts with Bearer
            //extract the jwt token
            //get user based on token
            //authenticate that user using UsernamePasswordAuthenticationToken class
        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            System.out.println(jwtToken);
            try{
            username = jwtUtil.getUsernameFromToken(jwtToken);
            System.out.println(username);
        }catch (Exception e){
                e.printStackTrace();
            }
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            //security
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if(jwtUtil.validateToken(jwtToken, userDetails)){
                    //here we are authenticating the token extracted from the header
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
                            userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            else {
                System.out.println("Token is invalid");
            }

            }

        filterChain.doFilter(request, response);
    }

}