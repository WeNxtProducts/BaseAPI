package com.wenxt.base.sample;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LM_PROG_FIELD_DEFN")
public class ProgramFieldModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Sys_Id")
	private Integer sysId;
	
	@Column(name = "pfd_prog_code")
	private String programCode;
	
	@Column(name = "pfd_pb_blk_name")
	private String blockName;
	
	@Column(name = "pfd_fld_name")
	private String fieldName;     
	
	@Column(name = "pfd_fld_desc")
	private String fieldDesc;   
	
	@JsonProperty("blockFieldDesc")
	private String pfd_bl_fld_desc;    
	
	@JsonProperty("sysDefnNum")
	private Integer pfd_sys_defn_yn_num;
	
	@JsonProperty("baseTabNum")
	private Integer pfd_base_tab_yn_num;         
	
	@JsonProperty("fieldType")
	private String pfd_fld_type;   
	
	@JsonProperty("hideNum")
	private Integer pfd_hide_yn_num;  
	
	@JsonProperty("enterableNum")
	private Integer pfd_enterable_yn_num;
	
	@JsonProperty("updateableNum")
	private Integer pfd_updateable_yn_num;
	
	@JsonProperty("mandatoryNum")
	private Integer pfd_mandatory_yn_num;
	
	@Column(name = "pfd_data_type")
	private String dataType;
	
	@JsonProperty("formItemType")
	private String pfd_form_item_type;
	
	@JsonProperty("fieldLen")
	private Integer pfd_fld_len;          
	
	@JsonProperty("dispLen")
	private Integer pfd_disp_len;             
	
	@JsonProperty("dispHt")
	private Integer pfd_disp_ht;            
	
	@JsonProperty("fixedLenNum")
	private Integer pfd_fixed_len_yn_num;
	
	@JsonProperty("uppercaseNum")
	private Integer pfd_uppercase_yn_num;          
	
	@JsonProperty("caseQryNum")
	private Integer pfd_case_insens_qry_num;
	
	@JsonProperty("hint")
	private String pfd_hint;              
	
	@JsonProperty("blockHint")
	private String pfd_bl_hint;                  
	
	@JsonProperty("prompt")
	private String pfd_promt;              
	
	@JsonProperty("blockPrompt")
	private String pfd_bl_prompt;            
	
	@JsonProperty("promptDispStyle")
	private String pfd_promt_disp_style;  
	
	@JsonProperty("promptEdge")
	private String pfd_promt_edge;
	
	@JsonProperty("promptEdgeAlgn")
	private String pfd_prompt_edge_algn;
	
	@JsonProperty("promptEdgeOffset")
	private String pfd_promt_edge_offset;
	
	@JsonProperty("defltValue")
	private String pfd_dflt_value;
	
	@JsonProperty("canvasName")
	private String pfd_canvas_name;
	
	@JsonProperty("tabName")
	private String pfd_tab_name;        
	
	@JsonProperty("xpos")
	private Integer pfd_xpos;
	
	@JsonProperty("ypos")	
	private Integer pfd_ypos;
	
	@JsonProperty("keyNavNum")
	private Integer pfd_keyb_nav_yn_num;
	
	@JsonProperty("recsDisp")
	private Integer pfd_recs_disp;     
	
	@JsonProperty("seqNum")	
	private Integer pfd_seq_no;          
	
	@JsonProperty("radioGrpName")	
	private String pfd_radio_grp_name;  
	
	@JsonProperty("promptTextAlign")	
	private String pfd_prompt_text_algn;          
	
	@JsonProperty("bpNum")	
	private Integer pfd_bp_yn_num;           
	
	@JsonProperty("className")	
	private String pfd_class_name;    
	
	@JsonProperty("promptAlignOffset")	
	private String pfd_prompt_align_offset;  
	
	@JsonProperty("cruid")	
	private String pfd_cr_uid;                 
	
	@JsonProperty("curDate")
	private LocalDate pfd_cr_dt;       
	
	@JsonProperty("updUid")
	private String pfd_upd_uid;  
	
	@JsonProperty("updDate")
	private LocalDate pfd_upd_dt;                      
	
	@JsonProperty("fontSize")
	private Integer pfd_font_size; 
	
	@JsonProperty("promptFontSize")
	private Integer pfd_prompt_font_size; 
	
	@JsonProperty("height")
	private Integer pfd_height;           
	
	@JsonProperty("levelType")
	private String pfd_bevel_type;   
	
	@JsonProperty("backGroundColour")
	private String pfd_background_color;        
	
	@JsonProperty("flex")
	private String pfd_flex;
	
//	private List<CodeDescModel> options;
	
	private String values;
	
	private String tableName;
	
	private String columnName;
		
	public Integer getSysId() {
		return sysId;
	}

	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setPfd_prog_code(String programCode) {
		this.programCode = programCode;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setPfd_pb_blk_name(String blockName) {
		this.blockName = blockName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setPfd_fld_name(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setPfd_fld_desc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getPfd_bl_fld_desc() {
		return pfd_bl_fld_desc;
	}

	public void setPfd_bl_fld_desc(String pfd_bl_fld_desc) {
		this.pfd_bl_fld_desc = pfd_bl_fld_desc;
	}

	public Integer getPfd_sys_defn_yn_num() {
		return pfd_sys_defn_yn_num;
	}

	public void setPfd_sys_defn_yn_num(Integer pfd_sys_defn_yn_num) {
		this.pfd_sys_defn_yn_num = pfd_sys_defn_yn_num;
	}

	public Integer getPfd_base_tab_yn_num() {
		return pfd_base_tab_yn_num;
	}

	public void setPfd_base_tab_yn_num(Integer pfd_base_tab_yn_num) {
		this.pfd_base_tab_yn_num = pfd_base_tab_yn_num;
	}

	public String getPfd_fld_type() {
		return pfd_fld_type;
	}

	public void setPfd_fld_type(String pfd_fld_type) {
		this.pfd_fld_type = pfd_fld_type;
	}

	public Integer getPfd_hide_yn_num() {
		return pfd_hide_yn_num;
	}

	public void setPfd_hide_yn_num(Integer pfd_hide_yn_num) {
		this.pfd_hide_yn_num = pfd_hide_yn_num;
	}

	public Integer getPfd_enterable_yn_num() {
		return pfd_enterable_yn_num;
	}

	public void setPfd_enterable_yn_num(Integer pfd_enterable_yn_num) {
		this.pfd_enterable_yn_num = pfd_enterable_yn_num;
	}

	public Integer getPfd_updateable_yn_num() {
		return pfd_updateable_yn_num;
	}

	public void setPfd_updateable_yn_num(Integer pfd_updateable_yn_num) {
		this.pfd_updateable_yn_num = pfd_updateable_yn_num;
	}

	public Integer getPfd_mandatory_yn_num() {
		return pfd_mandatory_yn_num;
	}

	public void setPfd_mandatory_yn_num(Integer pfd_mandatory_yn_num) {
		this.pfd_mandatory_yn_num = pfd_mandatory_yn_num;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPfd_form_item_type() {
		return pfd_form_item_type;
	}

	public void setPfd_form_item_type(String pfd_form_item_type) {
		this.pfd_form_item_type = pfd_form_item_type;
	}

	public Integer getPfd_fld_len() {
		return pfd_fld_len;
	}

	public void setPfd_fld_len(Integer pfd_fld_len) {
		this.pfd_fld_len = pfd_fld_len;
	}

	public Integer getPfd_disp_len() {
		return pfd_disp_len;
	}

	public void setPfd_disp_len(Integer pfd_disp_len) {
		this.pfd_disp_len = pfd_disp_len;
	}

	public Integer getPfd_disp_ht() {
		return pfd_disp_ht;
	}

	public void setPfd_disp_ht(Integer pfd_disp_ht) {
		this.pfd_disp_ht = pfd_disp_ht;
	}

	public Integer getPfd_fixed_len_yn_num() {
		return pfd_fixed_len_yn_num;
	}

	public void setPfd_fixed_len_yn_num(Integer pfd_fixed_len_yn_num) {
		this.pfd_fixed_len_yn_num = pfd_fixed_len_yn_num;
	}

	public Integer getPfd_uppercase_yn_num() {
		return pfd_uppercase_yn_num;
	}

	public void setPfd_uppercase_yn_num(Integer pfd_uppercase_yn_num) {
		this.pfd_uppercase_yn_num = pfd_uppercase_yn_num;
	}

	public Integer getPfd_case_insens_qry_num() {
		return pfd_case_insens_qry_num;
	}

	public void setPfd_case_insens_qry_num(Integer pfd_case_insens_qry_num) {
		this.pfd_case_insens_qry_num = pfd_case_insens_qry_num;
	}

	public String getPfd_hint() {
		return pfd_hint;
	}

	public void setPfd_hint(String pfd_hint) {
		this.pfd_hint = pfd_hint;
	}

	public String getPfd_bl_hint() {
		return pfd_bl_hint;
	}

	public void setPfd_bl_hint(String pfd_bl_hint) {
		this.pfd_bl_hint = pfd_bl_hint;
	}

	public String getPfd_promt() {
		return pfd_promt;
	}

	public void setPfd_promt(String pfd_promt) {
		this.pfd_promt = pfd_promt;
	}

	public String getPfd_bl_prompt() {
		return pfd_bl_prompt;
	}

	public void setPfd_bl_prompt(String pfd_bl_prompt) {
		this.pfd_bl_prompt = pfd_bl_prompt;
	}

	public String getPfd_promt_disp_style() {
		return pfd_promt_disp_style;
	}

	public void setPfd_promt_disp_style(String pfd_promt_disp_style) {
		this.pfd_promt_disp_style = pfd_promt_disp_style;
	}

	public String getPfd_promt_edge() {
		return pfd_promt_edge;
	}

	public void setPfd_promt_edge(String pfd_promt_edge) {
		this.pfd_promt_edge = pfd_promt_edge;
	}

	public String getPfd_prompt_edge_algn() {
		return pfd_prompt_edge_algn;
	}

	public void setPfd_prompt_edge_algn(String pfd_prompt_edge_algn) {
		this.pfd_prompt_edge_algn = pfd_prompt_edge_algn;
	}

	public String getPfd_promt_edge_offset() {
		return pfd_promt_edge_offset;
	}

	public void setPfd_promt_edge_offset(String pfd_promt_edge_offset) {
		this.pfd_promt_edge_offset = pfd_promt_edge_offset;
	}

	public String getPfd_dflt_value() {
		return pfd_dflt_value;
	}

	public void setPfd_dflt_value(String pfd_dflt_value) {
		this.pfd_dflt_value = pfd_dflt_value;
	}

	public String getPfd_canvas_name() {
		return pfd_canvas_name;
	}

	public void setPfd_canvas_name(String pfd_canvas_name) {
		this.pfd_canvas_name = pfd_canvas_name;
	}

	public String getPfd_tab_name() {
		return pfd_tab_name;
	}

	public void setPfd_tab_name(String pfd_tab_name) {
		this.pfd_tab_name = pfd_tab_name;
	}

	public Integer getPfd_xpos() {
		return pfd_xpos;
	}

	public void setPfd_xpos(Integer pfd_xpos) {
		this.pfd_xpos = pfd_xpos;
	}

	public Integer getPfd_ypos() {
		return pfd_ypos;
	}

	public void setPfd_ypos(Integer pfd_ypos) {
		this.pfd_ypos = pfd_ypos;
	}

	public Integer getPfd_keyb_nav_yn_num() {
		return pfd_keyb_nav_yn_num;
	}

	public void setPfd_keyb_nav_yn_num(Integer pfd_keyb_nav_yn_num) {
		this.pfd_keyb_nav_yn_num = pfd_keyb_nav_yn_num;
	}

	public Integer getPfd_recs_disp() {
		return pfd_recs_disp;
	}

	public void setPfd_recs_disp(Integer pfd_recs_disp) {
		this.pfd_recs_disp = pfd_recs_disp;
	}

	public Integer getPfd_seq_no() {
		return pfd_seq_no;
	}

	public void setPfd_seq_no(Integer pfd_seq_no) {
		this.pfd_seq_no = pfd_seq_no;
	}

	public String getPfd_radio_grp_name() {
		return pfd_radio_grp_name;
	}

	public void setPfd_radio_grp_name(String pfd_radio_grp_name) {
		this.pfd_radio_grp_name = pfd_radio_grp_name;
	}

	public String getPfd_prompt_text_algn() {
		return pfd_prompt_text_algn;
	}

	public void setPfd_prompt_text_algn(String pfd_prompt_text_algn) {
		this.pfd_prompt_text_algn = pfd_prompt_text_algn;
	}

	public Integer getPfd_bp_yn_num() {
		return pfd_bp_yn_num;
	}

	public void setPfd_bp_yn_num(Integer pfd_bp_yn_num) {
		this.pfd_bp_yn_num = pfd_bp_yn_num;
	}

	public String getPfd_class_name() {
		return pfd_class_name;
	}

	public void setPfd_class_name(String pfd_class_name) {
		this.pfd_class_name = pfd_class_name;
	}

	public String getPfd_prompt_align_offset() {
		return pfd_prompt_align_offset;
	}

	public void setPfd_prompt_align_offset(String pfd_prompt_align_offset) {
		this.pfd_prompt_align_offset = pfd_prompt_align_offset;
	}

	public String getPfd_cr_uid() {
		return pfd_cr_uid;
	}

	public void setPfd_cr_uid(String pfd_cr_uid) {
		this.pfd_cr_uid = pfd_cr_uid;
	}

	public LocalDate getPfd_cr_dt() {
		return pfd_cr_dt;
	}

	public void setPfd_cr_dt(LocalDate pfd_cr_dt) {
		this.pfd_cr_dt = pfd_cr_dt;
	}

	public String getPfd_upd_uid() {
		return pfd_upd_uid;
	}

	public void setPfd_upd_uid(String pfd_upd_uid) {
		this.pfd_upd_uid = pfd_upd_uid;
	}

	public LocalDate getPfd_upd_dt() {
		return pfd_upd_dt;
	}

	public void setPfd_upd_dt(LocalDate pfd_upd_dt) {
		this.pfd_upd_dt = pfd_upd_dt;
	}

	public Integer getPfd_font_size() {
		return pfd_font_size;
	}

	public void setPfd_font_size(Integer pfd_font_size) {
		this.pfd_font_size = pfd_font_size;
	}

	public Integer getPfd_prompt_font_size() {
		return pfd_prompt_font_size;
	}

	public void setPfd_prompt_font_size(Integer pfd_prompt_font_size) {
		this.pfd_prompt_font_size = pfd_prompt_font_size;
	}

	public Integer getPfd_height() {
		return pfd_height;
	}

	public void setPfd_height(Integer pfd_height) {
		this.pfd_height = pfd_height;
	}

	public String getPfd_bevel_type() {
		return pfd_bevel_type;
	}

	public void setPfd_bevel_type(String pfd_bevel_type) {
		this.pfd_bevel_type = pfd_bevel_type;
	}

	public String getPfd_background_color() {
		return pfd_background_color;
	}

	public void setPfd_background_color(String pfd_background_color) {
		this.pfd_background_color = pfd_background_color;
	}

	public String getPfd_flex() {
		return pfd_flex;
	}

	public void setPfd_flex(String pfd_flex) {
		this.pfd_flex = pfd_flex;
	}

//	public List<CodeDescModel> getOptions() {
//		return options;
//	}
//
//	public void setOptions(List<CodeDescModel> options) {
//		this.options = options;
//	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}


