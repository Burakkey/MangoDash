package use_case.clear_users;

// TODO Complete me

import interface_adapter.login.LoginState;

import java.util.List;

public class ClearInteractor implements ClearInputBoundary{
    final ClearUserDataAccessInterface userDataAccessObject;
    final ClearOutputBoundary clearPresenter;

    public ClearInteractor(ClearUserDataAccessInterface userDataAccessInterface,
                           ClearOutputBoundary clearOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.clearPresenter = clearOutputBoundary;
    }

    @Override
    public void execute(ClearInputData clearInputData) {
        List usernames = userDataAccessObject.clear();
        ClearOutputData clearOutputData = new ClearOutputData(usernames, false);
        clearPresenter.prepareSuccessView(clearOutputData);

    }
}

