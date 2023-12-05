package use_case.change_user_data;

import entity.SocialMediaStats.SocialMediaStats;

import java.net.MalformedURLException;

public interface APIDataAccessInterface {
    void fetchData() throws MalformedURLException;

    void setAPI(String apiUrl);

    boolean isApiError();

    SocialMediaStats getStats();
}
