package data_access;

import entity.SocialMediaStats.SocialMediaStats;

import java.net.MalformedURLException;

public interface APIDataAccessInterface {
    void fetchData() throws MalformedURLException;

    void setAPI(String apiUrl);

    boolean isApiError();

    SocialMediaStats getStats();
}
