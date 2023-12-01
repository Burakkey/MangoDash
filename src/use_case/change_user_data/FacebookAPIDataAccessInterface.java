package use_case.change_user_data;

import entity.SocialMediaStats.FacebookStats;

import java.net.MalformedURLException;

public interface FacebookAPIDataAccessInterface {
    void fetchData() throws MalformedURLException;
    void setApi(String apiKey);
    FacebookStats getFacebookStats();
}