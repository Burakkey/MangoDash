package interface_adapter.switchview;

import use_case.switchView.SwitchViewInputBoundary;
import use_case.switchView.SwitchViewInputData;

public class SwitchViewController {
    final SwitchViewInputBoundary switchViewUseCaseInteractor;

    public SwitchViewController(SwitchViewInputBoundary switchViewUseCaseInteractor) {
        this.switchViewUseCaseInteractor = switchViewUseCaseInteractor;
    }

    public void execute(String buttonName){
        SwitchViewInputData switchViewInputData = new SwitchViewInputData(buttonName);
        switchViewUseCaseInteractor.execute(switchViewInputData);
    }
}
