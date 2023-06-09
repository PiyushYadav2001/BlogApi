package com.blogApi.Controller;

import com.blogApi.playload.JwtAuthRequest;
import com.blogApi.playload.JwtAuthResponnse;
import com.blogApi.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthCOntroller {

        @Autowired
        private JwtTokenHelper jwtTokenHelper;

        @Autowired
        private UserDetailsService  userDetailsService;

        @Autowired
         private AuthenticationManager authenticationManager;

      @PostMapping("/login")
      public ResponseEntity<JwtAuthResponnse>  createToken(@RequestBody JwtAuthRequest request) throws Exception {

            this.authenticate(request.getUsername(),request.getPassword());
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername() );
            String token = this.jwtTokenHelper. generateToken(userDetails);
            JwtAuthResponnse jwtAuthResponnse=new JwtAuthResponnse();
            jwtAuthResponnse.setToken(token);
            return new ResponseEntity<JwtAuthResponnse>(jwtAuthResponnse, HttpStatus.OK);

      }

      private void authenticate(String username, String password) throws Exception {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password);
          try {
              this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
          }catch (BadCredentialsException e){
              System.out.println("Invalid Deatils ");
              throw new BadCredentialsException("invalid Password");
          }


      }

}
