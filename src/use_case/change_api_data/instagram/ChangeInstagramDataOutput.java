package use_case.change_api_data.instagram;

import use_case.DataGetter;

import java.util.HashMap;

public class ChangeInstagramDataOutput implements DataGetter {
    private HashMap <String, Object> instagramData;
    public ChangeInstagramDataOutput(HashMap<String, Object> instagramData){
        this.instagramData = instagramData;
    }

    public HashMap<String, Object> getData() {
        return instagramData;
    }
}
