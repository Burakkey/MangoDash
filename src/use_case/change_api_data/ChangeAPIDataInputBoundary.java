package use_case.change_api_data;

import use_case.change_user_data.ChangeDataInput;

public interface ChangeAPIDataInputBoundary {
    void executeAPIChanges(ChangeAPIDataInput changeAPIDataInput);
}
