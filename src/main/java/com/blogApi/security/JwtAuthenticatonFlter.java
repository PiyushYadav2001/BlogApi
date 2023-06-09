package com.blogApi.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticatonFlter extends OncePerRequestFilter {

      @Autowired
       private UserDetailsService userDetailsService;

      @Autowired
        private  JwtTokenHelper jwtTokenHelper;

      @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                      FilterChain filterChain) throws ServletException, IOException {

         // System.out.println(request.getHeader("Authorization"));

          // 1.get token
     final   String  requestToken=request.getHeader("Authorization");

          System.out.println(requestToken+"     token    "+request);
          String username=null;
          String token=null;

          if (requestToken !=null  && requestToken.startsWith("Bearer ")){
              token= requestToken.substring(7);
              try {
                  username = this.jwtTokenHelper.getUsernameFromToken(token);
              }catch (IllegalArgumentException e){
                  System.out.println("Unable to get jwt token ");
              }
              catch (ExpiredJwtException e){
                  System.out.println("Jwt Token get has expried ");
              }catch (MalformedJwtException  e){
                  System.out.println("invalid jwt");
              }
          }else {
              System.out.println("Jwt token does not begin with bearear");
          }
      //Once We Get After Validate-
          if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
              UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
              if (this.jwtTokenHelper.validateToken(token,userDetails)){
                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                       usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                       SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
              }else {
                  System.out.println("Invalid Jwt Token");
              }
          }else {
              System.out.println("UserName is Null or Context is not null ");
          }
             filterChain.doFilter(request,response);
    }


}
