/**
 * 
 */
package com.aplazo.shopping.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aplazo.shopping.enums.ErrorCode;
import com.aplazo.shopping.exception.ApiException;
import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.repository.IUserRepository;

/**
 * Class to validate the user with a repository.
 * If user is valid set a new grant whit his role.
 * @author CesarSalazar
 */
@Service
public class JWTUserDetailsService implements UserDetailsService {

	private static final String PREFIX_ROLE = "ROLE_";
	
	@Autowired
	private IUserRepository iUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<GrantedAuthority> authorithies = new ArrayList<>();
		User user = this.iUserRepository.findByEmail(email);
		if(user == null) {
			throw new ApiException(ErrorCode.USER_NOT_FOUND);
		}
		authorithies.add(new SimpleGrantedAuthority(PREFIX_ROLE + user.getRole()));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorithies);
	}

}
