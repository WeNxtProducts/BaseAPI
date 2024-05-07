package com.wenxt.base.custMaster;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wenxt.base.commonUtils.LM_CUSTOMER;
import com.wenxt.base.userMaster.LM_MENU_USERS;

@Repository

public interface CustomerRepository extends JpaRepository<LM_CUSTOMER, Integer> {

	Optional<LM_CUSTOMER> findByCustCode(Integer custCode);
}