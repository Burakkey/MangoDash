package use_case.change_user_data;

/**
 * ChangeDataInputBoundary is an interface with methods that detail what to do with the input data
 */
public interface ChangeDataInputBoundary {
    /**
     * This method uses the changeDataInput to execute the changes the user makes to their name, username, bio, and/or password
     * @param changeDataInput
     */
    void executeSaveChanges(ChangeDataInput changeDataInput);

    void executeLogout(ChangeDataInput changeDataInput);

    /**
     * This method uses the changeDataInput to execute the changes the user makes to their Facebook and/or Instagram API key
     * @param changeDataInput
     */
    void executeAPIChanges(ChangeDataInput changeDataInput);
}
