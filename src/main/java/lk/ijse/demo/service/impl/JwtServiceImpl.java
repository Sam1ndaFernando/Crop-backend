package lk.ijse.demo.service.impl;//package lk.ijse.demo.service.impl;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lk.ijse.demo.service.JwtService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//@Service
//public class JwtServiceImpl implements JwtService {
//    @Value("${spring.jwtKey}")
//    private String jwtSecu;
//
//    @Override
//    public String extractUerName(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    @Override
//    public String genarateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    @Override
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String userName = extractUerName(token);
//        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolvers.apply(claims);
//    }
//    @Override
//    public String refreshToken(UserDetails userDetails) {
//        return refreshToken(new HashMap<>(),userDetails);
//    }
//
//    private String refreshToken(Map<String, Object> extractClaims, UserDetails userDetails) {
//        extractClaims.put("role", userDetails.getAuthorities());
//        Date now = new Date();
//        Date refreshExpire = new Date(now.getTime() + 1000 * 600 * 600);
//
//        return Jwts.builder().setClaims(extractClaims)
//                .setSubject(userDetails.getUsername())
//                .setExpiration(refreshExpire)
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
//    }
//
//    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 50000 * 10000))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecu);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//}
