package use_case.change_user_data;

import entity.SocialMediaStats.InstagramStats;

import java.net.MalformedURLException;

/**
 * InstagramAPIDataAccessInterface is an interface that details all the methods that are required to use the API to obtain the Instagram data
 */
public interface InstagramAPIDataAccessInterface {
    /**
     * retrieves the relevant data from the user's Instagram account
     * @throws MalformedURLException
     */
    void fetchData() throws MalformedURLException;

    void setAPI(String apiUrl);

    boolean isApiError();

    InstagramStats getInstagramStats();
}
