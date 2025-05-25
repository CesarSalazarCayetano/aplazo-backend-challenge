/**
 * 
 */
package com.aplazo.shopping.service;

import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.security.config.request.SignUpRequest;

/**
 * @author CesarSalazar
 */
public interface IUserService {
	User findByEmail(String email);
	User save(SignUpRequest signUpRequest);
}
