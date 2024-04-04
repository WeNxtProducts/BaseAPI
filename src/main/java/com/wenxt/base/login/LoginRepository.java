package com.wenxt.base.login;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LM_MENU_USER_COMP, Integer> {
	
	  @Query("select _muc_comp_code from LM_MENU_USER_COMP where _muc_user_id = ?1")
	  List<String> findBy_muc_user_id(String muc_user_id);
	
}
