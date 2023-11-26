package use_case.change_user_data;

import entity.SocialMediaStats.InstagramStats;

import java.net.MalformedURLException;

public interface InstagramAPIIDataAccessInterface {
    void fetchData() throws MalformedURLException;

    void setAPI(String apiUrl);

    InstagramStats getInstagramStats();
}
