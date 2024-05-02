package com.wenxt.base.login;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LM_MENU_USER_COMP, Integer> {

	@Query("select _muc_comp_code from LM_MENU_USER_COMP where muc_user_id.userId = :userId")
	List<String> findBy_muc_user_id(@Param("userId") String muc_user_id);

}
