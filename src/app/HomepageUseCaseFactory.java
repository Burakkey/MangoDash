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
import use_case.change_user_data.InstagramAPIDataAccessInterface;
import use_case.change_user_data.*;
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
            return new HomepageView(homepageViewModel, homePanelComponent, rankingPanelComponent, extensionPanelComponents, settingsPanelComponent, homepageController, viewManagerModel);
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
        InstagramAPIDataAccessInterface instagramAPIDataAccessInterface = new InstagramAPIDataAccessObject("", instagramStats);
        ChangeDataInputBoundary changeDataInteractor = new ChangeDataInteractor(changeDataAccessInterface, changeDataOutputBoundary, instagramAPIDataAccessInterface);
        return new HomepageController(changeDataInteractor);
    }
}
