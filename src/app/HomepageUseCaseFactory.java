package app;

import data_access.APIDataAccessInterface;
import use_case.change_api_data.ChangeAPIDataInputBoundary;
import use_case.change_api_data.ChangeAPIDataOutputBoundary;
import use_case.change_api_data.facebook.ChangeFacebookDataInteractor;
import use_case.change_api_data.instagram.ChangeInstagramDataInteractor;
import use_case.change_user_data.ChangeDataAccessInterface;
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
import use_case.change_user_data.*;
import view.homepage.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * HomepageUseCaseFactory is only called in main, it creates a new SignupView
 */
public class HomepageUseCaseFactory {
    public static HomepageView create(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
                                      HomepageViewModel homepageViewModel, InstagramPanel instagramPanel, FacebookPanel facebookPanel,
                                      ExtensionPanelComponents extensionPanelComponents,
                                      SettingsPanelComponent settingsPanelComponent, ChangeDataAccessInterface changeDataAccessInterface,
                                      JFrame application){
        try {
            HomepageController homepageController = createHomepageUseCase(viewManagerModel, loginViewModel,
                    homepageViewModel, changeDataAccessInterface);
            return new HomepageView(homepageViewModel, instagramPanel, facebookPanel, extensionPanelComponents, settingsPanelComponent, homepageController, viewManagerModel, application);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static HomepageController createHomepageUseCase(
            ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
            HomepageViewModel homepageViewModel, ChangeDataAccessInterface changeDataAccessInterface
            ) throws IOException  {
        HomepagePresenter homepagePresenter = new HomepagePresenter(loginViewModel, homepageViewModel, viewManagerModel);
        UserFactory userFactory = new CommonUserFactory();
        InstagramStats instagramStats = new InstagramStats();
        FacebookStats facebookStats = new FacebookStats();

        
        ChangeDataInputBoundary changeDataInteractor = new ChangeDataInteractor(changeDataAccessInterface, homepagePresenter);

        HashMap<String, ChangeAPIDataInputBoundary> apiDataInputMap = new HashMap<>();
        
        APIDataAccessInterface instagramAPIDataAccessInterface = new InstagramAPIDataAccessObject("", instagramStats);
        ChangeAPIDataInputBoundary changeAPIDataInteractor = new ChangeInstagramDataInteractor(changeDataAccessInterface, homepagePresenter, instagramAPIDataAccessInterface);
        apiDataInputMap.put("Instagram", changeAPIDataInteractor);

        APIDataAccessInterface facebookAPIDataAccessInterface = new FacebookAPIDataAccessObject("", facebookStats);
        changeAPIDataInteractor = new ChangeFacebookDataInteractor(changeDataAccessInterface, homepagePresenter, facebookAPIDataAccessInterface);
        apiDataInputMap.put("Facebook", changeAPIDataInteractor);
        
        return new HomepageController(changeDataInteractor, apiDataInputMap);
    }
}
