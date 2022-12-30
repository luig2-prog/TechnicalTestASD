package com.asd.tecnical_test_asd.config;

import com.asd.tecnical_test_asd.dto.RestResponse;
import com.asd.tecnical_test_asd.model.Role;
import com.asd.tecnical_test_asd.service.JWT;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Esta clase contiene los atributos y metodos de un empleado
 * @author Luis Hernandez
 * @version 1.0
 * @see OncePerRequestFilter
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

//	@Value("${app.jwt-secret}")
	private String secret = "OKnMDTnyD43bJejxqD1eEGjCsmtZvcB1dViv3vOmScI";
	private static final String AUTORIZATION = "Authorization";
	private static final String HEADER = "Authorization";
	private static final String PREFIX = "Bearer ";
	private Set<String> skipUrls = new HashSet<>(Arrays.asList("/api/v1/user/login"));
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
	private RestResponse restResponse;

	/**
	 * @description función encargada de hacer un filtro por donde pasan todas las peticiones HTTP
	 * @author Luis Hernandez
	 * @date(26/12/2022)
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		log.info("Filter:::::: {}", request.getMethod());
		if(request.getMethod().equals("GET")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authorizationHeader = request.getHeader(AUTORIZATION);
		if(authorizationHeader == null) {
			forbidden(response);
			return;
		}

		Claims token = validateToken(request);
		log.info("TOKENNNNN: {}", token);
		if(token == null) {
			forbidden(response);
			return;
		}

		log.info("TOKEN: {}", token);
		log.info("token.get(\"ip\"): {}",token.get("ip"));
		if(token.get("ip").equals(request.getRemoteAddr())) {
			authUser(token);
			filterChain.doFilter(request, response);
		} else {
			log.info("No se permitén peticiones de dos ips distintas");
			response.setStatus(HttpServletResponse.SC_OK);
			restResponse = new RestResponse(HttpStatus.FORBIDDEN.value(), MessagesHandler.UNAUTHORIZED, null);
			response.setContentType(APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), restResponse);
		}
	}

	protected boolean shouldNotFilterAsyncDispatch() {
		return false;
	}


	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return skipUrls.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
	}

	public void forbidden(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		restResponse = new RestResponse(HttpStatus.FORBIDDEN.value(), MessagesHandler.UNAUTHORIZED, null);
		response.setContentType(APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), restResponse);
	}

	/**
	 * @description función encargada de decodificar el token, se valida si es valido el token
	 * @author Luis Hernandez
	 * @date(26/12/2022)
	 * @param request
	 * @return Claims jwt
	 */
	public Claims validateToken(HttpServletRequest request) {
		try {
			log.info("OE: {}",secret);
			String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(jwtToken).getBody();
		} catch (AccessDeniedException | SignatureException | ExpiredJwtException |
				MalformedJwtException | UnsupportedJwtException e) {
			log.error("ERROR TOKEN VALIDATE: " + e.getMessage());
			return null;
		}
	}

	/**
	 * @description función encargada de logear al usuario
	 * @author Luis Hernandez
	 * @date(26/12/2022)
	 * @param token
	 */
	private void authUser(Claims token) {
		String username = token.getSubject();
		log.info("Roles: {}", token.get("authorities").toString());
		List<String> roles = (List<String>) token.get("authorities");
		log.info("ROLES: {} - {}", roles.isEmpty(), roles.size());
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(token.get("authorities").toString()));
//			log.info("role: {}", role);
//		});
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
		log.info("authenticationToken: {} - {} - {}",authenticationToken.isAuthenticated(), authenticationToken.getPrincipal(), authenticationToken.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	private Collection<? extends GrantedAuthority> mapearRoles(Role rol){
		return Collections.singleton(new SimpleGrantedAuthority(rol.getName()));
	}

}

