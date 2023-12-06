package use_case.change_user_data;

public interface ChangeDataOutputBoundary {
    void prepareFailView(String s);

    void prepareSuccessView(ChangeDataOutput changeDataOutput);
}
