package app;

import data_access.InstagramAPIDataAccessObject;
import entity.CommonUserFactory;
import entity.SocialMediaStats.InstagramStats;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepagePresenter;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.switchview.SwitchViewController;
import interface_adapter.switchview.SwitchViewPresenter;
import use_case.change_user_data.InstagramAPIIDataAccessInterface;
import use_case.change_user_data.*;
import use_case.switchView.SwitchViewInputBoundary;
import use_case.switchView.SwitchViewInteractor;
import use_case.switchView.SwitchViewOutputBoundary;
import view.homepage.*;

import javax.swing.*;
import java.io.IOException;

public class HomepageUseCaseFactory {
    public static HomepageView create(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
                                      HomepageViewModel homepageViewModel, HomePanelComponent homePanelComponent,
                                      RankingPanelComponent rankingPanelComponent, ExtensionPanelComponents extensionPanelComponents,
                                      SettingsPanelComponent settingsPanelComponent, ChangeDataAccessInterface changeDataAccessInterface){
        try {
            HomepageController homepageController = createHomepageUseCase(viewManagerModel, loginViewModel,
                    homepageViewModel, changeDataAccessInterface);
            SwitchViewController switchViewController = createSwitchViewController(viewManagerModel, loginViewModel);
            return new HomepageView(homepageViewModel, homePanelComponent, rankingPanelComponent, extensionPanelComponents, settingsPanelComponent, homepageController, switchViewController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static HomepageController createHomepageUseCase(
            ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
            HomepageViewModel homepageViewModel, ChangeDataAccessInterface changeDataAccessInterface
            ) throws IOException  {
        ChangeDataOutputBoundary changeDataOutputBoundary = new HomepagePresenter(loginViewModel, homepageViewModel, viewManagerModel);
        UserFactory userFactory = new CommonUserFactory();
        InstagramStats instagramStats = new InstagramStats();
        InstagramAPIIDataAccessInterface instagramAPIIDataAccessInterface = new InstagramAPIDataAccessObject("", instagramStats);
        ChangeDataInputBoundary changeDataInteractor = new ChangeDataInteractor(changeDataAccessInterface, changeDataOutputBoundary, instagramAPIIDataAccessInterface);
        return new HomepageController(changeDataInteractor);
    }

    private static SwitchViewController createSwitchViewController(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel){
        SwitchViewOutputBoundary switchViewOutputBoundary = new SwitchViewPresenter(loginViewModel, viewManagerModel);
        SwitchViewInputBoundary switchViewInteractor = new SwitchViewInteractor(switchViewOutputBoundary);
        return new SwitchViewController(switchViewInteractor);
    }
}
