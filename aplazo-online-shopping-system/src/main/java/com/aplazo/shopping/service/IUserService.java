/**
 * 
 */
package com.aplazo.shopping.service;

import com.aplazo.shopping.model.dao.User;

/**
 * @author CesarSalazar
 */
public interface IUserService {
	User findByEmail(String email);
	User save(User user);
}
