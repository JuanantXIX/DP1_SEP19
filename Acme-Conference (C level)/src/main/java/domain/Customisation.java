
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Customisation extends DomainEntity {

	private String				systemName;
	private String				bannerUrl;
	private String				welcomeMessageEng;
	private String				welcomeMessageEsp;
	private String				phoneNumberCode;
	private Collection<String>	creditCardMakes;


	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	public String getBannerUrl() {
		return this.bannerUrl;
	}

	public void setBannerUrl(final String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	@NotBlank
	public String getWelcomeMessageEng() {
		return this.welcomeMessageEng;
	}

	public void setWelcomeMessageEng(final String welcomeMessageEng) {
		this.welcomeMessageEng = welcomeMessageEng;
	}
	@NotBlank
	public String getWelcomeMessageEsp() {
		return this.welcomeMessageEsp;
	}

	public void setWelcomeMessageEsp(final String welcomeMessageEsp) {
		this.welcomeMessageEsp = welcomeMessageEsp;
	}
	@NotBlank
	public String getPhoneNumberCode() {
		return this.phoneNumberCode;
	}

	public void setPhoneNumberCode(final String phoneNumberCode) {
		this.phoneNumberCode = phoneNumberCode;
	}

	@ElementCollection
	public Collection<String> getCreditCardMakes() {
		return this.creditCardMakes;
	}

	public void setCreditCardMakes(final Collection<String> creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}

	/*
	 * @NotNull
	 * 
	 * @Range(min = 1, max = 24)
	 * public Integer getFinderDuration() {
	 * return this.finderDuration;
	 * }
	 * 
	 * public void setFinderDuration(final Integer finderDuration) {
	 * this.finderDuration = finderDuration;
	 * }
	 * 
	 * @NotNull
	 * 
	 * @Range(min = 10, max = 100)
	 * public Integer getResultsNumber() {
	 * return this.resultsNumber;
	 * }
	 * 
	 * public void setResultsNumber(final Integer resultsNumber) {
	 * this.resultsNumber = resultsNumber;
	 * }
	 */

}
