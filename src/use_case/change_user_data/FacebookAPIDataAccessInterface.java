package use_case.change_user_data;

import entity.SocialMediaStats.FacebookStats;

import java.net.MalformedURLException;

/**
 * FacebookAPIDataAccessInterface is an interface that details all the methods that are required to use the API to obtain the Facebook data
 */
public interface FacebookAPIDataAccessInterface {
    /**
     * retrieves the relevant data from the user's Facebook account
     * @throws MalformedURLException
     */
    void fetchData() throws MalformedURLException;
    void setApi(String apiKey);
    boolean isApiError();
    FacebookStats getFacebookStats();
}