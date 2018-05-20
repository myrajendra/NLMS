package com.jeevasamruddhi.telangana.nlms.android.model;


public class BasicCard extends AbstractBaseDTO{
	private String name;
	private String aadharNo;
	private String village;
	private String mandal;
	private String fatherORHusband,mobile,address,income,gender,caste,ifscode,dist,bankname,accountNo,disability,appiledDate;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAadhaar() {
		return aadharNo;
	}
	public void setAadhaar(String aadhaar) {
		this.aadharNo = aadhaar;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getMandal() {
		return mandal;
	}
	public void setMandal(String mandal) {
		this.mandal = mandal;
	}

	public void setFatherORHusband(String fatherORHusband) {
		this.fatherORHusband = fatherORHusband;
	}

	public String getFatherORHusband() {
		return fatherORHusband;
	}

	public void setmobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setaddress(String address) {
		this.address = address;
	}

	public void setgender(String gender) {
		this.gender = gender;
	}

	public void setcaste(String caste) {
		this.caste = caste;
	}

	public void setincome(String income) {
		this.income = income;
	}

	public void setdisability(String disability) {
		this.disability = disability;
	}

	public void setbankname(String bankname) {
		this.bankname = bankname;
	}

	public void setifscode(String ifscode) {
		this.ifscode = ifscode;
	}

	public void setaccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public void setappiledDate(String appiledDate) {
		this.appiledDate = appiledDate;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getAddress() {
		return address;
	}

	public String getIncome() {
		return income;
	}

	public String getGender() {
		return gender;
	}

	public String getCaste() {
		return caste;
	}

	public String getIfscode() {
		return ifscode;
	}

	public String getDist() {
		return dist;
	}

	public String getBankname() {
		return bankname;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public String getDisability() {
		return disability;
	}

	public String getAppiledDate() {
		return appiledDate;
	}
}
