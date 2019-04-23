package io.bankbridge.model;

public class BankModel {
	
	public String bic;
	public String name;
	public String countryCode;
	public String auth;

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}
