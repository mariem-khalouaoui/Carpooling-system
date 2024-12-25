package com.tunisiologia.TunisiologiaApp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tunisiologia.TunisiologiaApp.models.UserPrincipal;
import com.tunisiologia.TunisiologiaApp.services.JwtService;
import com.tunisiologia.TunisiologiaApp.services.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	 JwtService jwtService;
	@Autowired 
	MyUserDetailsService userPrincipal;
	
	
	
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain
			)
			throws ServletException, IOException {
				String authHeader= request.getHeader("Authorization"); 
				if(authHeader == null || !authHeader.startsWith("Bearer ")) {
					filterChain.doFilter(request, response);
					return;
				}
				String token = authHeader.substring(7);
				String username = jwtService.extractUserName(token);
				
				
				if(username != null && 	SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userPrincipal.loadUserByUsername(username);
					if(jwtService.isValid(token, userDetails)) {
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
								userDetails,null,userDetails.getAuthorities()
								);
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);
						
					}
				}
				filterChain.doFilter(request, response);
	}

}
