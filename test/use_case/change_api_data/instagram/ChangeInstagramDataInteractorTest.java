package use_case.change_api_data.instagram;

import data_access.APIDataAccessInterface;
import data_access.InstagramAPIDataAccessObject;
import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import use_case.change_api_data.ChangeAPIDataInput;
import use_case.change_api_data.ChangeAPIDataOutputBoundary;
import use_case.change_user_data.ChangeDataAccessInterface;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class ChangeInstagramDataInteractorTest {

    @Mock
    private ChangeDataAccessInterface mockChangeDataAccessInterface;
    @Mock
    private ChangeAPIDataOutputBoundary mockHomepagePresenter;
    @Mock
    private APIDataAccessInterface mockInstagramAPIDataAccessInterface;

    private ChangeInstagramDataInteractor interactor;

    private CommonUserFactory userFactory;

    private final String token = "EAAMw2YKsBFwBO4pt7KgLmZBeEzVHOYB90bh1HM9xJN1QZAo0qWZBEn8M0A1q5tdXXOjNDE3a8SbZAdxkCqXp4XoJb1QwCKDbXkn0S4jbGNV4UpRa4pqHLhNc1nbBmewt9Ri0l1EmKU29JtY6D3nZCiHeLcvpZB48DR9LpMSVrQvQUMJmtxVLgr8nlc"; // Replace with actual token

    @Before
    public void setUp() throws ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        interactor = new ChangeInstagramDataInteractor(mockChangeDataAccessInterface, mockHomepagePresenter, mockInstagramAPIDataAccessInterface);
        userFactory = new CommonUserFactory();
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("Facebook", "");
        apiKeys.put("Instagram", "");
        User mockUser = userFactory.create("name", "username", "password", "bio", apiKeys, LocalDateTime.now());
        SQLiteUserDataAccessObject userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        userRepository.save(mockUser);
    }
    @Test
    public void testSuccessfulApiTokenUpdateAndDataFetch() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "username", "name");
        interactor.executeAPIChanges(mockInput);
    }

    @Test(expected = RuntimeException.class)
    public void testMalformedURLExceptionHandling() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput("wrong token", "Username", "Name");
        doThrow(new MalformedURLException()).when(mockInstagramAPIDataAccessInterface).fetchData();

        interactor.executeAPIChanges(mockInput);
    }

    @Test
    public void testDataParsingAndViewPreparation() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "Username", "Name");
        HashMap<String, JSONArray> mockStats = new HashMap<>();
        mockStats.put("followers", new JSONArray()); // Use actual or mock data
        when(mockInstagramAPIDataAccessInterface.getStats().getStats()).thenReturn(mockStats);

        interactor.executeAPIChanges(mockInput);

        verify(mockHomepagePresenter).prepareAPIView(any());
    }



    // Test methods go here
}
