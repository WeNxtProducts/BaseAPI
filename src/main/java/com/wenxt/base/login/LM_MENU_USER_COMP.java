package com.wenxt.base.login;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LM_MENU_USER_COMP
{
	
	@Id
	@Column(name = "muc_id")
	private int _muc_id;
	public int getmuc_id()
	{
		return this._muc_id;
	}
	public void setmuc_id(int value)
	{
		this._muc_id = value;
	}

	@Column(name = "muc_user_id")
	private String _muc_user_id;
	public String getmuc_user_id()
	{
		return this._muc_user_id;
	}
	public void setmuc_user_id(String value)
	{
		this._muc_user_id = value;
	}

	@Column(name = "muc_comp_code")
	private String _muc_comp_code;
	public String getmuc_comp_code()
	{
		return this._muc_comp_code;
	}
	public void setmuc_comp_code(String value)
	{
		this._muc_comp_code = value;
	}

	@Column(name = "muc_cr_uid")
	private String _muc_cr_uid;
	public String getmuc_cr_uid()
	{
		return this._muc_cr_uid;
	}
	public void setmuc_cr_uid(String value)
	{
		this._muc_cr_uid = value;
	}

	@Column(name = "muc_cr_dt")
	private java.sql.Date _muc_cr_dt;
	public java.sql.Date getmuc_cr_dt()
	{
		return this._muc_cr_dt;
	}
	public void setmuc_cr_dt(java.sql.Date value)
	{
		this._muc_cr_dt = value;
	}

	@Column(name = "muc_upd_uid")
	private String _muc_upd_uid;
	public String getmuc_upd_uid()
	{
		return this._muc_upd_uid;
	}
	public void setmuc_upd_uid(String value)
	{
		this._muc_upd_uid = value;
	}

	@Column(name = "muc_upd_dt")
	private java.sql.Date _muc_upd_dt;
	public java.sql.Date getmuc_upd_dt()
	{
		return this._muc_upd_dt;
	}
	public void setmuc_upd_dt(java.sql.Date value)
	{
		this._muc_upd_dt = value;
	}

	@Column(name = "muc_role_sys_id")
	private double _muc_role_sys_id;
	public double getmuc_role_sys_id()
	{
		return this._muc_role_sys_id;
	}
	public void setmuc_role_sys_id(double value)
	{
		this._muc_role_sys_id = value;
	}

	@Column(name = "muc_client_id")
	private String _muc_client_id;
	public String getmuc_client_id()
	{
		return this._muc_client_id;
	}
	public void setmuc_client_id(String value)
	{
		this._muc_client_id = value;
	}

	@Column(name = "muc_security_code")
	private String _muc_security_code;
	public String getmuc_security_code()
	{
		return this._muc_security_code;
	}
	public void setmuc_security_code(String value)
	{
		this._muc_security_code = value;
	}

	@Column(name = "muc_inst_code")
	private String _muc_inst_code;
	public String getmuc_inst_code()
	{
		return this._muc_inst_code;
	}
	public void setmuc_inst_code(String value)
	{
		this._muc_inst_code = value;
	}

	@Column(name = "muc_dflt_yn")
	private String _muc_dflt_yn;
	public String getmuc_dflt_yn()
	{
		return this._muc_dflt_yn;
	}
	public void setmuc_dflt_yn(String value)
	{
		this._muc_dflt_yn = value;
	}

}