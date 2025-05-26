/**
 * 
 */
package com.aplazo.shopping.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aplazo.shopping.security.service.JWTUserDetailsService;
import com.aplazo.shopping.security.util.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * @author CesarSalazar
 */
@Log4j2
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUserDetailsService jwtUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String userName = null;
		String token = JWTUtils.existHeaderJWT(request.getHeader(JWTUtils.HEADER));
		
		if(token != null) {
			userName = JWTUtils.getUserNameFromToken(token);
		} else {
			SecurityContextHolder.clearContext();
			log.error("JWT Token does not begin with Bearer String");
		}
			
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);
			
			if(!JWTUtils.validateToken(token, userDetails)) {
				log.error("JWT Token is not valid.");
				return;
			}
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
	}

}
