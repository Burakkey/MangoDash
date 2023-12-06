package use_case.change_api_data;

import use_case.DataGetter;
import use_case.change_user_data.ChangeDataOutput;

public interface ChangeAPIDataOutputBoundary {

    void prepareAPIView(DataGetter changeDataOutput);
}
