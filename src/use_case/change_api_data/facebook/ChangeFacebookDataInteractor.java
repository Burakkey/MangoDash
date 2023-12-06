package use_case.change_api_data.facebook;


import data_access.APIDataAccessInterface;
import use_case.DataGetter;
import use_case.change_api_data.ChangeAPIDataInput;
import use_case.change_api_data.ChangeAPIDataInputBoundary;
import use_case.change_api_data.ChangeAPIDataOutputBoundary;
import use_case.change_api_data.facebook.ChangeFacebookDataOutput;
import use_case.change_user_data.ChangeDataAccessInterface;
import org.json.JSONArray;
import use_case.change_user_data.*;

import java.net.MalformedURLException;
import java.util.HashMap;

public class ChangeFacebookDataInteractor implements ChangeAPIDataInputBoundary {
    final ChangeDataAccessInterface changeDataAccessInterface;

    final ChangeAPIDataOutputBoundary homepagePresenter;

    final APIDataAccessInterface facebookAPIDataAccessInterface;

    public ChangeFacebookDataInteractor(ChangeDataAccessInterface changeDataAccessInterface, ChangeAPIDataOutputBoundary homepagePresenter,
                                         APIDataAccessInterface facebookAPIDataAccessInterface) {
        this.changeDataAccessInterface = changeDataAccessInterface;
        this.homepagePresenter = homepagePresenter;
        this.facebookAPIDataAccessInterface = facebookAPIDataAccessInterface;
    }
    @Override
    public void executeAPIChanges(ChangeAPIDataInput changeDataInput) {
        String username = changeDataInput.getUsername();
        String facebookAPIToken = changeDataInput.getAPIToken();
        changeDataAccessInterface.modifyUserAPI(username, facebookAPIToken, null);
        facebookAPIDataAccessInterface.setAPI(facebookAPIToken);
        try {
            facebookAPIDataAccessInterface.fetchData();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


        HashMap<String, JSONArray> facebookStats = facebookAPIDataAccessInterface.getStats().getStats();

        // Creating new HashMaps
        HashMap<String, Object> facebookData = new HashMap<>();

        // Iterating over facebook data
        for (String key : facebookStats.keySet()) {
            JSONArray value = facebookStats.get(key);
            facebookData.put(key, value);
        }


        // Adding additional facebook data
        facebookData.put("apiKey", facebookAPIToken);
        facebookData.put("keyError", facebookAPIDataAccessInterface.isApiError());

        DataGetter changeDataOutput = new ChangeFacebookDataOutput(facebookData);
        homepagePresenter.prepareAPIView(changeDataOutput);
    }
}
