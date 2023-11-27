package use_case.change_user_data;

public interface ChangeDataInputBoundary {
    void executeSaveChanges(ChangeDataInput changeDataInput);

    void executeLogout(ChangeDataInput changeDataInput);

    void executeAPIChanges(ChangeDataInput changeDataInput);
}
