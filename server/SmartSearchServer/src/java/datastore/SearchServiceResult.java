/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datastore;

/**
 *
 * @author saurabh
 */
public class SearchServiceResult extends SearchResult{
    private String title;
    private String summary;
    private String url;
    
   
    public SearchServiceResult(){
        
    }
    
    public SearchServiceResult(String title, String summary, String url) {
        this.title = title;
        this.summary = summary;
        this.url = url;
    }
    
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getSearchResultXML(){
        StringBuilder xml= new StringBuilder();
        xml.append("<result>\n");
        xml.append("<type>" + type + "</type>\n");
        xml.append("<title>" + title + "</title>\n");
        xml.append("<summary>" + summary + "</summary>\n");
        xml.append("<url>" + url + "</url>\n");
        xml.append("</result>\n");
        return xml.toString();
    }
}
