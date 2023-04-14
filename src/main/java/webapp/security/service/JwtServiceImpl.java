//package webapp.security.service;
//
//import java.util.Date;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import javax.crypto.SecretKey;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//@Service
//public class JwtServiceImpl {
//
//    @Value("${jwt.secret.key}")
//    private String secretKey;
//
//    @Value("${jwt.expiration}")
//    private Integer expiration;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    private SecretKey getKey() {
//        return Keys.hmacShaKeyFor(secretKey.getBytes());
//    }
//
//    public String generateToken(String username) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        return Jwts.builder()
//            .setClaims(extractRole(userDetails)) // optional: set custom key:value into payload
//            .setIssuer("") // optional
//            .setSubject("") // optional
//            .setAudience("") // optional
//            .setSubject(userDetails.getUsername()) // mandatory
//            .setIssuedAt(new Date(System.currentTimeMillis())) // optional
//            .setExpiration(new Date(System.currentTimeMillis() + expiration)) // optional
//            .signWith(getKey(), // choice secret key and algo to gene signature
//                SignatureAlgorithm.HS512)
//            .compact(); // generate jwt string
//    }
//
//
//    public Claims getClaims(String token) {
//        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
//    }
//
//    private Map<String, List> extractRole(UserDetails userDetails) {
//        List<String> roles = userDetails.getAuthorities()
//            .stream()
//            .map(role -> role.getAuthority())
//            .collect(Collectors.toList());
//        Map<String, List> roleMap = new HashMap<>();
//        roleMap.put("role", roles);
//        return roleMap;
//    }
//}
//
