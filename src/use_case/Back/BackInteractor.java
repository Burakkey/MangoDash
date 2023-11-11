package use_case.Back;

public class BackInteractor implements BackInputBoundary {
    final BackOutputBoundary backPresenter;

    public BackInteractor(BackOutputBoundary backPresenter) {

        this.backPresenter = backPresenter;
    }

    @Override
    public void execute(BackInputData backInputData) {
        BackOutputData data = new BackOutputData();
        backPresenter.prepareSuccessView(data);
    }
}
