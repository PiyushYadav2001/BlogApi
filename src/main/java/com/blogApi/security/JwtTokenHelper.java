package com.blogApi.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

     public  static  final  long JWT_TOKEN_VALIDITY= 70000;

     public static final String secret="secret";



     public String  getUsernameFromToken(String token){
         return  getClaimFromToken(token,Claims::getSubject);
     }

     public Date getExpirationDateFromToken(String token)   {
         return getClaimFromToken(token,Claims::getExpiration);
     }

     public  <T> T getClaimFromToken(String token, Function<Claims ,T> claimsResolver){
         final  Claims claims=getAllClaimsFromToken(token);
         return claimsResolver.apply(claims);
     }

     private  Claims getAllClaimsFromToken(String token){
         return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
     }

     private Boolean isTokenExpried(String token){
         final  Date expiration =getExpirationDateFromToken(token);
         return  expiration.before(new Date());
     }

     public  String generateToken(UserDetails userDetails){
         Map<String,Object> claims=new HashMap<>();
         String s = doGenerateToken(claims, userDetails.getUsername());
         return s;
     }
     private  String  doGenerateToken(Map<String,Object> claims,String subject) {
//         String compact = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                 .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)).signWith(SignatureAlgorithm.HS512, secret).compact();
//         return compact;
         JwtBuilder builder =  Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()));
         builder = builder.setExpiration(new Date(System.currentTimeMillis() + 1000*60 * 60 * 10));
         builder = builder.signWith(SignatureAlgorithm.HS512, secret);  //error on this line
         return builder.compact();
     }
     public  Boolean validateToken(String token,UserDetails userDetails){
         final String username=getUsernameFromToken(token);
         return (username.equals(userDetails.getUsername()) && !isTokenExpried(token));
     }
}
