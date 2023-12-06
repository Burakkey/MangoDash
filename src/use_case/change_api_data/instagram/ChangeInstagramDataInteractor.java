package use_case.change_api_data.instagram;

import data_access.APIDataAccessInterface;
import use_case.DataGetter;
import use_case.change_api_data.ChangeAPIDataInput;
import use_case.change_api_data.ChangeAPIDataInputBoundary;
import use_case.change_api_data.ChangeAPIDataOutputBoundary;
import use_case.change_user_data.ChangeDataAccessInterface;
import org.json.JSONArray;
import use_case.change_user_data.*;

import java.net.MalformedURLException;
import java.util.HashMap;

public class ChangeInstagramDataInteractor implements ChangeAPIDataInputBoundary {
    final ChangeDataAccessInterface changeDataAccessInterface;

    final ChangeAPIDataOutputBoundary homepagePresenter;

    final APIDataAccessInterface instagramAPIDataAccessInterface;

    public ChangeInstagramDataInteractor(ChangeDataAccessInterface changeDataAccessInterface, ChangeAPIDataOutputBoundary homepagePresenter,
                                APIDataAccessInterface instagramAPIDataAccessInterface) {
        this.changeDataAccessInterface = changeDataAccessInterface;
        this.homepagePresenter = homepagePresenter;
        this.instagramAPIDataAccessInterface = instagramAPIDataAccessInterface;
    }
    @Override
    public void executeAPIChanges(ChangeAPIDataInput changeDataInput) {
        String username = changeDataInput.getUsername();
        String instagramAPIToken = changeDataInput.getAPIToken();
        changeDataAccessInterface.modifyUserAPI(username, null, instagramAPIToken);
        instagramAPIDataAccessInterface.setAPI(instagramAPIToken);
        try {
            instagramAPIDataAccessInterface.fetchData();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


        HashMap<String, JSONArray> instagramStats = instagramAPIDataAccessInterface.getStats().getStats();

        // Creating new HashMaps
        HashMap<String, Object> instagramData = new HashMap<>();

        // Iterating over Instagram data
        for (String key : instagramStats.keySet()) {
            JSONArray value = instagramStats.get(key);
            instagramData.put(key, value);
        }


        // Adding additional Instagram data
        instagramData.put("apiKey", instagramAPIToken);
        instagramData.put("keyError", instagramAPIDataAccessInterface.isApiError());

        DataGetter changeDataOutput = new ChangeInstagramDataOutput(instagramData);
        homepagePresenter.prepareAPIView(changeDataOutput);
    }
}