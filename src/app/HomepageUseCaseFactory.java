package app;

import data_access.FacebookAPIDataAccessObject;
import data_access.InstagramAPIDataAccessObject;
import entity.CommonUserFactory;
import entity.SocialMediaStats.FacebookStats;
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

/**
 * HomepageUseCaseFactory is only called in main, it creates a new SignupView
 */
public class HomepageUseCaseFactory {
    public static HomepageView create(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
                                      HomepageViewModel homepageViewModel, InstagramPanel instagramPanel, FacebookPanel facebookPanel,
                                      RankingPanelComponent rankingPanelComponent, ExtensionPanelComponents extensionPanelComponents,
                                      SettingsPanelComponent settingsPanelComponent, ChangeDataAccessInterface changeDataAccessInterface,
                                      JFrame application){
        try {
            HomepageController homepageController = createHomepageUseCase(viewManagerModel, loginViewModel,
                    homepageViewModel, changeDataAccessInterface);
            return new HomepageView(homepageViewModel, instagramPanel, facebookPanel, rankingPanelComponent, extensionPanelComponents, settingsPanelComponent, homepageController, viewManagerModel, application);
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
        FacebookStats facebookStats = new FacebookStats();
        InstagramAPIDataAccessInterface instagramAPIDataAccessInterface = new InstagramAPIDataAccessObject("", instagramStats);
        FacebookAPIDataAccessInterface facebookAPIDataAccessInterface = new FacebookAPIDataAccessObject("", facebookStats);
        ChangeDataInputBoundary changeDataInteractor = new ChangeDataInteractor(changeDataAccessInterface, changeDataOutputBoundary, instagramAPIDataAccessInterface, facebookAPIDataAccessInterface);
        return new HomepageController(changeDataInteractor);
    }
}
