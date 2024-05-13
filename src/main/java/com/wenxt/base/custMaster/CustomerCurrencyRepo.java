package com.wenxt.base.custMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wenxt.base.model.LM_CUST_CURR;

@Repository
public interface CustomerCurrencyRepo extends JpaRepository<LM_CUST_CURR, Integer> {

}
