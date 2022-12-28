package com.asd.tecnical_test_asd.config;

import com.asd.tecnical_test_asd.dto.RestResponse;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private static final String AUTORIZATION = "Authorization";
	private static final String HEADER = "Authorization";
	private static final String PREFIX = "Bearer ";
	private static final String SECRET = "developSeed";

	private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

	/**
	 * @date(24/01/2022)
	 * @author Carolina Arias
	 * @description función encargada de validar el token
	 **/
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		filterChain.doFilter(request, response);
//		final String regex = "([a-z/]+api/user/login|[a-z/]+api/user/my-ip)";
//		final String servletPath = request.getServletPath();
//		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
//		final Matcher matcher = pattern.matcher(servletPath);
//		RestResponse restResponse;
//		logger.info("Ok pus");
//		if(matcher.find()) {
//

//		} else {
//			String authorizationHeader = request.getHeader(AUTORIZATION);
//			if(authorizationHeader != null && authorizationHeader.startsWith(PREFIX)) {
//				try {
//					Claims token = validateToken(request);
//					log.info("TOKEN: {}", token);
//					log.info("token.get(\"ip\"): {}",token.get("ip"));
//					if(token.get("ip").equals(request.getRemoteAddr())) {
//						authUser(token);
//						filterChain.doFilter(request, response);
//					} else {
//						log.info("No se permitén peticiones de dos ips distintas");
//						response.setStatus(HttpServletResponse.SC_OK);
//						restResponse = new RestResponse(HttpStatus.FORBIDDEN.value(), MessagesHandler.UNAUTHORIZED, null);
//						response.setContentType(APPLICATION_JSON_VALUE);
//						new ObjectMapper().writeValue(response.getOutputStream(), restResponse);
//					}
//				} catch (ExpiredJwtException | SignatureException | MalformedJwtException |
//						UnsupportedJwtException | IllegalArgumentException e) {
//					logger.info(e);
//					log.info("Error token, message: {} - class: {}", e.getMessage(), e.getClass());
//					response.setStatus(HttpServletResponse.SC_OK);
//					restResponse = new RestResponse(HttpStatus.FORBIDDEN.value(), MessagesHandler.UNAUTHORIZED, null);
//					response.setContentType(APPLICATION_JSON_VALUE);
//					new ObjectMapper().writeValue(response.getOutputStream(), restResponse);
//				} catch (Exception e) {
//					logger.info(e);
//					log.warn("Error general: {} - class {}", e.getMessage(),e.getClass());
//					response.setStatus(HttpServletResponse.SC_OK);
//					restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessagesHandler.UNAUTHORIZED, null);
//					response.setContentType(APPLICATION_JSON_VALUE);
//					new ObjectMapper().writeValue(response.getOutputStream(), restResponse);
//				}
//			} else {
//				response.setStatus(HttpServletResponse.SC_OK);
//				restResponse = new RestResponse(HttpStatus.FORBIDDEN.value(), MessagesHandler.UNAUTHORIZED, null);
//				response.setContentType(APPLICATION_JSON_VALUE);
//				new ObjectMapper().writeValue(response.getOutputStream(), restResponse);
//			}
//		}
	}

	private Claims validateToken(HttpServletRequest request) {
		try {
			String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
			return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
		} catch (AccessDeniedException | SignatureException e) {
			logger.info(e);
			logger.info("ERROR TOKEN VALIDATE: " + e.getMessage());
			return Jwts.claims();
		}
	}

	private void authUser(Claims token) {
		String username = token.getSubject();
		List<String> roles = (List<String>) token.get("authorities");
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
			log.info("role: {}", role);
		});
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
		log.info("authenticationToken: {} - {} - {}",authenticationToken.isAuthenticated(), authenticationToken.getPrincipal(), authenticationToken.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
}

