/**
 * 
 */
package com.aplazo.shopping.service;

import com.aplazo.shopping.model.dao.User;
import com.aplazo.shopping.security.config.request.LoginRequest;

/**
 * @author CesarSalazar
 */
public interface IUserService {
	User findByEmail(String email);
	User save(LoginRequest signUpRequest);
	boolean validateEmailExist(String email);
}
