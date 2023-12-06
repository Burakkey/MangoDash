package use_case.change_user_data;

/**
 * ChangeDataOutputBoundary is an interface defining the contract for presenting changing-user-info-related responses
 * and output to the HomepagePresenter after successful changing of user info.
 */
public interface ChangeDataOutputBoundary {
    /**
     * The response when changing user info is unsuccessful (error message pops up)
     * @param error
     */
    void prepareFailView(String error);

    /**
     * The response when changing user info (password, name, bio) is successful
     * @param changeDataOutput
     */
    void prepareSuccessView(ChangeDataOutput changeDataOutput);
}
