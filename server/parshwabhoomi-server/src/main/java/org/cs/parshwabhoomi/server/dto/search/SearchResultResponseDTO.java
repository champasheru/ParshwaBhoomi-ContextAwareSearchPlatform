/**
 * parshwabhoomi-server	10-Nov-2017:12:53:30 PM
 */
package org.cs.parshwabhoomi.server.dto.search;

import org.cs.parshwabhoomi.server.dto.AbstractResponseDTO;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class SearchResultResponseDTO extends AbstractResponseDTO {
	private String title;
	private String htmlTitle;
	private String link;
	private String displayLink;
	private String snippet;
	private String htmlSnippet;
	private String formattedUrl;
	private String htmlFormattedUrl;
	private String imageUrl;
	//Preferred(location+prefs),Generic, Google preferred, Google generic.
	private String type;
	//Provider name in case the search result is provided by search engine like Google, Bing, Yahoo etc.
	private String provider;
	private String businessCategory;
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the htmlTitle
	 */
	public String getHtmlTitle() {
		return htmlTitle;
	}
	/**
	 * @param htmlTitle the htmlTitle to set
	 */
	public void setHtmlTitle(String htmlTitle) {
		this.htmlTitle = htmlTitle;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the displayLink
	 */
	public String getDisplayLink() {
		return displayLink;
	}
	/**
	 * @param displayLink the displayLink to set
	 */
	public void setDisplayLink(String displayLink) {
		this.displayLink = displayLink;
	}
	/**
	 * @return the snippet
	 */
	public String getSnippet() {
		return snippet;
	}
	/**
	 * @param snippet the snippet to set
	 */
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	/**
	 * @return the htmlSnippet
	 */
	public String getHtmlSnippet() {
		return htmlSnippet;
	}
	/**
	 * @param htmlSnippet the htmlSnippet to set
	 */
	public void setHtmlSnippet(String htmlSnippet) {
		this.htmlSnippet = htmlSnippet;
	}
	/**
	 * @return the formattedUrl
	 */
	public String getFormattedUrl() {
		return formattedUrl;
	}
	/**
	 * @param formattedUrl the formattedUrl to set
	 */
	public void setFormattedUrl(String formattedUrl) {
		this.formattedUrl = formattedUrl;
	}
	/**
	 * @return the htmlFormattedUrl
	 */
	public String getHtmlFormattedUrl() {
		return htmlFormattedUrl;
	}
	/**
	 * @param htmlFormattedUrl the htmlFormattedUrl to set
	 */
	public void setHtmlFormattedUrl(String htmlFormattedUrl) {
		this.htmlFormattedUrl = htmlFormattedUrl;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
	/**
	 * @return the businessCategory
	 */
	public String getBusinessCategory() {
		return businessCategory;
	}
	/**
	 * @param businessCategory the businessCategory to set
	 */
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchResultResponseDTO [title=" + title + ", htmlTitle=" + htmlTitle + ", link=" + link
				+ ", displayLink=" + displayLink + ", snippet=" + snippet + ", htmlSnippet=" + htmlSnippet
				+ ", formattedUrl=" + formattedUrl + ", htmlFormattedUrl=" + htmlFormattedUrl + ", imageUrl=" + imageUrl
				+ ", type=" + type + ", provider=" + provider + ", businessCategory=" + businessCategory + "]";
	}
	
}
