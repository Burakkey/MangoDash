package use_case.change_api_data.facebook;

import use_case.DataGetter;

import java.util.HashMap;

public class ChangeFacebookDataOutput implements DataGetter {
    private HashMap<String, Object> facebookData;
    public ChangeFacebookDataOutput(HashMap<String, Object> instagramData){
        this.facebookData = instagramData;
    }

    public HashMap<String, Object> getData() {
        return facebookData;
    }
}
