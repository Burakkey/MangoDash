package use_case.switchView;

public class SwitchViewInteractor implements SwitchViewInputBoundary{
    final SwitchViewOutputBoundary switchViewPresenter;

    public SwitchViewInteractor(SwitchViewOutputBoundary switchViewPresenter) {
        this.switchViewPresenter = switchViewPresenter;
    }


    @Override
    public void execute(SwitchViewInputData switchViewInputData) {
        String buttonName = switchViewInputData.getButtonName();
        switch (buttonName) {
            case "Logout":
                switchViewPresenter.prepareLogoutView();
                break;
//            case "Button2":
//                // Handle logic for other buttons in the future
//                break;
//            case "Button3":
//                // Handle logic for other buttons in the future
//                break;
            default:
                // Handle the default case (if buttonName doesn't match any of the specified cases)
                break;
        }
    }
}
