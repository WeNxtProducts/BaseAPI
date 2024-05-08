package com.wenxt.base.dto;

import com.wenxt.base.custMaster.CustRequestData.FrontForm;

public class CustomerRequestDto {

	private FrontForm frontForm;
	private FrontForm physical_address;
	private FrontForm postal_address;
	private FrontForm bank_details;
	private FrontForm headerInfo;
	private FrontForm address;
	private FrontForm Relation_History;
	private FrontForm Customer;
	private FrontForm more_info;
	private FrontForm contact_details;
	private FrontForm personal_details;
	private FrontForm tax_info;
	private FrontForm staticDetails;
	private String[] mrvListingId;
	private FrontForm Branch;
	private FrontForm Currency;
	private FrontForm corporate_details;
	private FrontForm employer_information;
	public FrontForm getFrontForm() {
		return frontForm;
	}
	public void setFrontForm(FrontForm frontForm) {
		this.frontForm = frontForm;
	}
	public FrontForm getPhysical_address() {
		return physical_address;
	}
	public void setPhysical_address(FrontForm physical_address) {
		this.physical_address = physical_address;
	}
	public FrontForm getPostal_address() {
		return postal_address;
	}
	public void setPostal_address(FrontForm postal_address) {
		this.postal_address = postal_address;
	}
	
	public FrontForm getBank_details() {
		return bank_details;
	}
	public void setBank_details(FrontForm bank_details) {
		this.bank_details = bank_details;
	}

	public FrontForm getHeaderInfo() {
		return headerInfo;
	}
	public void setHeaderInfo(FrontForm headerInfo) {
		this.headerInfo = headerInfo;
	}
	public FrontForm getAddress() {
		return address;
	}
	public void setAddress(FrontForm address) {
		this.address = address;
	}
	public FrontForm getRelation_History() {
		return Relation_History;
	}
	public void setRelation_History(FrontForm relation_History) {
		Relation_History = relation_History;
	}
	public FrontForm getCustomer() {
		return Customer;
	}
	public void setCustomer(FrontForm customer) {
		Customer = customer;
	}
	public FrontForm getMore_info() {
		return more_info;
	}
	public void setMore_info(FrontForm more_info) {
		this.more_info = more_info;
	}
	public FrontForm getContact_details() {
		return contact_details;
	}
	public void setContact_details(FrontForm contact_details) {
		this.contact_details = contact_details;
	}
	public FrontForm getPersonal_details() {
		return personal_details;
	}
	public void setPersonal_details(FrontForm personal_details) {
		this.personal_details = personal_details;
	}
	public FrontForm getTax_info() {
		return tax_info;
	}
	public void setTax_info(FrontForm tax_info) {
		this.tax_info = tax_info;
	}
	public FrontForm getStaticDetails() {
		return staticDetails;
	}
	public void setStaticDetails(FrontForm staticDetails) {
		this.staticDetails = staticDetails;
	}
	public String[] getMrvListingId() {
		return mrvListingId;
	}
	public void setMrvListingId(String[] mrvListingId) {
		this.mrvListingId = mrvListingId;
	}
	public FrontForm getBranch() {
		return Branch;
	}
	public void setBranch(FrontForm branch) {
		Branch = branch;
	}
	public FrontForm getCurrency() {
		return Currency;
	}
	public void setCurrency(FrontForm currency) {
		Currency = currency;
	}
	public FrontForm getCorporate_details() {
		return corporate_details;
	}
	public void setCorporate_details(FrontForm corporate_details) {
		this.corporate_details = corporate_details;
	}
	public FrontForm getEmployer_information() {
		return employer_information;
	}
	public void setEmployer_information(FrontForm employer_information) {
		this.employer_information = employer_information;
	}
	
	
}
