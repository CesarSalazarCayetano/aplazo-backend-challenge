/**
 * 
 */
package com.aplazo.shopping.security.util;

import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

/**
 * @author CesarSalazar
 */
@Log4j2
@Component
public final class JWTUtils {
	
	public static final String PREFIX_ROLE = "ROLE_";
	private static final String PREFIX = "Bearer ";
	private static final String SECRET_KEY = "2a177702a80f9ffbb8ce748aa7e05a8b74abd2cdf1606f9432e45fc2a4cdf63792ad338ac8b9f722491f21015fbe1c98d953db9da5613ed690d16a2fd74ecca0eecf0dfee87737d6f548c7ac1d8f91151a55365160a791194333083fd10b74aa7ba52ed8182a81d7a79b8dd82eda1d1bc5994dc5a42e0ade979234114fb7ad0b28cecc7744bc6de8d02f4a2c186e147973368193152c3d391a30973e28738257aa12a1d944ba7b26d23dcc482126f188731673421bfbbc1329160f517c1e749e36910b0a17616257e62fd6ee7f5cc6f454ed7c94567a3f2dbb4575733bc47658a0fc6ff1d484ea7fc1f8edaa69d764c8e0016d3386ebef50f8711acd9cd6d479";
	private static final String HEADER = "Authorization";

	public static String getPrefix() {
		return PREFIX;
	}
	
	public static String generateToken(String email, UserDetails userDetails) {
//		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts.builder().subject(email)
				.claim("authorities", 
						userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
				)
				.issuedAt(new Date(System.currentTimeMillis()))
				.signWith(JWTUtils.getSecretKey()).compact();
		return PREFIX + token;
	}
	
	private static SecretKey getSecretKey() {
		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		return secretKey;
	}
	
	public static String existHeaderJWT(HttpServletRequest request) {
		String token = request.getHeader(HEADER);
		if(token == null || !token.startsWith(JWTUtils.getPrefix())) {
			return null;
		}
		return token.replace(JWTUtils.getPrefix(), "");
	}
	
	public static boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUserNameFromToken(token);
		if(!userName.equals(userDetails.getUsername())) {
			return false; 
		}
		return true;
	}
	
	public static String getUserNameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}
	
	private static Claims getAllClaimsFromToken(String token) {
		try {
			return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
		} catch (JwtException ex) { 
			log.error("The jwt cannot be parsed or validated.");
		} catch (IllegalArgumentException ex) {
			log.error("The jwt is null or empty.");
		}
		return null;
	}
}
