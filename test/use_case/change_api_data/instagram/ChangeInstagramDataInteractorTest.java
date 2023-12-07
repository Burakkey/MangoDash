package use_case.change_api_data.instagram;

import data_access.APIDataAccessInterface;
import data_access.FacebookAPIDataAccessObject;
import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.SocialMediaStats.InstagramStats;
import entity.SocialMediaStats.SocialMediaStats;
import entity.User;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import use_case.change_api_data.ChangeAPIDataInput;
import use_case.change_api_data.ChangeAPIDataOutputBoundary;
import use_case.change_user_data.ChangeDataAccessInterface;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class ChangeInstagramDataInteractorTest {


    private ChangeDataAccessInterface changeDataAccessInterface;

    private APIDataAccessInterface instagramAPIDataAccess;

    private ChangeInstagramDataInteractor interactor;

    private CommonUserFactory userFactory;

    @Mock
    private ChangeAPIDataOutputBoundary mockHomepagePresenter;




    private final String token = "EAAMw2YKsBFwBO4pt7KgLmZBeEzVHOYB90bh1HM9xJN1QZAo0qWZBEn8M0A1q5tdXXOjNDE3a8SbZAdxkCqXp4XoJb1QwCKDbXkn0S4jbGNV4UpRa4pqHLhNc1nbBmewt9Ri0l1EmKU29JtY6D3nZCiHeLcvpZB48DR9LpMSVrQvQUMJmtxVLgr8nlc"; // Replace with actual token

    @Before
    public void setUp() throws ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        userFactory = new CommonUserFactory();
        instagramAPIDataAccess = spy(new FacebookAPIDataAccessObject("", new InstagramStats()));
        changeDataAccessInterface = spy(new SQLiteUserDataAccessObject("testusers.db", userFactory));
        interactor = new ChangeInstagramDataInteractor(changeDataAccessInterface, mockHomepagePresenter, instagramAPIDataAccess);
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("Facebook", "");
        apiKeys.put("Instagram", "");
        User mockUser = userFactory.create("name", "username", "password", "bio", apiKeys, LocalDateTime.now());
        ((SQLiteUserDataAccessObject) changeDataAccessInterface).save(mockUser);
    }
    @Test
    public void testSuccessfulApiTokenUpdateAndDataFetch() throws MalformedURLException {
        // Arrange
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "username", "name");
        // Stub the mock to do nothing when prepareAPIView is called
        doNothing().when(mockHomepagePresenter).prepareAPIView(any());

        // Act
        interactor.executeAPIChanges(mockInput);

        // Assert
        verify(mockHomepagePresenter).prepareAPIView(any());
    }


    @Test(expected = RuntimeException.class)
    public void testMalformedURLExceptionHandling() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput("wrong token", "Username", "Name");
        doThrow(new MalformedURLException()).when(instagramAPIDataAccess).fetchData();

        interactor.executeAPIChanges(mockInput);
    }

    @Test
    public void testDataParsingAndViewPreparation() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "Username", "Name");
        HashMap<String, JSONArray> mockStats = new HashMap<>();
        mockStats.put("followers", new JSONArray()); // Use actual or mock data

        // Create mock SocialMediaStats
        SocialMediaStats mockSocialMediaStats = mock(SocialMediaStats.class);
        // Stub getStats() of SocialMediaStats to return mockStats
        when(mockSocialMediaStats.getStats()).thenReturn(mockStats);
        // Stub getStats() of APIDataAccessInterface to return mockSocialMediaStats
        when(instagramAPIDataAccess.getStats()).thenReturn(mockSocialMediaStats);

        interactor.executeAPIChanges(mockInput);

        verify(mockHomepagePresenter).prepareAPIView(any());
    }






    @Test(expected = RuntimeException.class)
    public void testApiTokenUpdateFailureDueToDatabaseIssue() {
        // Arrange
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "username", "name");
        // Assuming that modifyUserAPI throws ClassNotFoundException when failing
        doThrow(new ClassNotFoundException()).when(changeDataAccessInterface).modifyUserAPI(anyString(), anyString(), anyString());

        // Act
        interactor.executeAPIChanges(mockInput);
        // The expected exception should be thrown by the line above
        fail("A RuntimeException wrapping a ClassNotFoundException was expected to be thrown");
    }


    @Test
    public void testApiErrorHandlingWhenApiReturnsError() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "username", "name");

        // Use doReturn for setting up spy behavior
        doReturn(true).when(instagramAPIDataAccess).isApiError();

        interactor.executeAPIChanges(mockInput);

        verify(mockHomepagePresenter).prepareAPIView(any());
    }


    @Test
    public void testDataParsingAndViewPreparationWithActualData() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "Username", "Name");
        JSONArray followersData = new JSONArray("[{\"count\": 100}]");
        HashMap<String, JSONArray> mockStats = new HashMap<>();
        mockStats.put("followers", followersData);

        SocialMediaStats mockedSocialMediaStats = mock(SocialMediaStats.class);
        when(mockedSocialMediaStats.getStats()).thenReturn(mockStats);
        when(instagramAPIDataAccess.getStats()).thenReturn(mockedSocialMediaStats);

        interactor.executeAPIChanges(mockInput);

        verify(mockHomepagePresenter).prepareAPIView(argThat(argument -> {
            ChangeInstagramDataOutput output = (ChangeInstagramDataOutput) argument;
            JSONArray actualFollowersData = (JSONArray) output.getData().get("followers");
            return actualFollowersData.toString().equals(followersData.toString());
        }));
    }



}
