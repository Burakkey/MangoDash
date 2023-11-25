package interface_adapter.back_in_signup;


import use_case.Back.BackInputBoundary;
import use_case.Back.BackInputData;

public class BackInSignupController  {
    final BackInputBoundary userBackInSignupUseCaseInteractor;
    public BackInSignupController(BackInputBoundary userBackInSignupUseCaseInteractor){
        this.userBackInSignupUseCaseInteractor = userBackInSignupUseCaseInteractor;
    }

    public void execute(){
        BackInputData backInputData = new BackInputData();
        userBackInSignupUseCaseInteractor.execute(backInputData);
    }
}
