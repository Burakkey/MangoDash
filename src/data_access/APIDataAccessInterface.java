package data_access;

import entity.SocialMediaStats.SocialMediaStats;

import java.net.MalformedURLException;

/**
 * APIDataAccessInterface is an interface that details all the methods that are required to use the API to obtain the relevant social media data
 */
public interface APIDataAccessInterface {

   /**
     * retrieves the relevant data from the user's social media account using an API
     * @throws MalformedURLException
     */
    void fetchData() throws MalformedURLException;

    void setAPI(String apiUrl);

    boolean isApiError();

    SocialMediaStats getStats();
}
