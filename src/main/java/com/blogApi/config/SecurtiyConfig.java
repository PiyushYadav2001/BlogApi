package com.blogApi.config;

import com.blogApi.security.CustomUserDetailSevice;
import com.blogApi.security.JwtAuthencitionEntryPoint;
import com.blogApi.security.JwtAuthenticatonFlter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurtiyConfig {

        @Autowired
        private CustomUserDetailSevice customUserDetailSevice;

        @Autowired
        private JwtAuthencitionEntryPoint jwtAuthencitionEntryPoint;

        @Autowired
        private JwtAuthenticatonFlter jwtAuthenticatonFlter;

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     /*  http.csrf()
               .disable().
               authorizeHttpRequests().
               requestMatchers("/api/v1/auth/login").permitAll().
                anyRequest().
               authenticated().
               and().
               exceptionHandling().
               authenticationEntryPoint(this.jwtAuthencitionEntryPoint)
               .and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
         http
                 .csrf().disable()
                 .exceptionHandling()
                 .authenticationEntryPoint(this.jwtAuthencitionEntryPoint)
                 .and()
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .authorizeHttpRequests()
                 .requestMatchers("/api/v1/auth/login").permitAll()
                 .anyRequest().authenticated()
                 .and()
                 .httpBasic();



       http.addFilterBefore(this.jwtAuthenticatonFlter, UsernamePasswordAuthenticationFilter.class);
      http.authenticationProvider(daoAuthenticationProvider());
         DefaultSecurityFilterChain build = http.build();
         return build;
    }
   /*  @Bean
    public SecurityFilterChain authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
         DaoAuthenticationConfigurer<AuthenticationManagerBuilder, CustomUserDetailSevice> authenticationManagerBuilderCustomUserDetailSeviceDaoAuthenticationConfigurer = auth.userDetailsService(this.customUserDetailSevice).passwordEncoder(passwordEncoder());

         return (SecurityFilterChain) authenticationManagerBuilderCustomUserDetailSeviceDaoAuthenticationConfigurer;
     }
*/

    public  AuthenticationManager authenticationManager(HttpSecurity httpSecurity, UserDetailsService userDetailsService) throws Exception {
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder, CustomUserDetailSevice> configurer = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(this.customUserDetailSevice).passwordEncoder(passwordEncoder());
        return (AuthenticationManager) configurer;
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
         return  new BCryptPasswordEncoder();
    }

    @Bean
   public  AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
   }

   @Bean
   public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailSevice);
         provider.setPasswordEncoder(passwordEncoder());
         return provider;
   }
}
