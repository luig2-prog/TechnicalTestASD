package com.asd.tecnical_test_asd.service;

import java.util.Collections;

import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserDetailsAuthenticationImpl extends AbstractUserDetailsAuthenticationProvider {

	private static Logger LOG = LoggerFactory.getLogger(UserDetailsAuthenticationImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEnconder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		LOG.info("username: {}, password: {}", username, password);

		UserDTO userSaved = this.userService.findByUsername(username);
		if (userSaved == null) {
			throw new BadCredentialsException("El usuario no existe en el aplicativo");
		}

		if(!passwordEncoder.matches(password, userSaved.getPassword())) {
			throw new BadCredentialsException("Credenciales incorrectas");
		}

		if (userSaved == null) {
			throw new BadCredentialsException("Usuario o contrasena invalido.");
		}

		if (!passwordEnconder.matches(password, userSaved.getPassword())) {
			throw new BadCredentialsException("Usuario o contrasena invalido.");
		}

		return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(new SimpleGrantedAuthority(userSaved.getRole().getName())));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		return null;
	}

}
