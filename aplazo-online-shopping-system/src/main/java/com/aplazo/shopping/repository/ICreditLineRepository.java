/**
 * 
 */
package com.aplazo.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplazo.shopping.model.dao.CreditLine;

/**
 * @author CesarSalazar
 */
@Repository
public interface ICreditLineRepository extends JpaRepository<CreditLine, Long> {

}
