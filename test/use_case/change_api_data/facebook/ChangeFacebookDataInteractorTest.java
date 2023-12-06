package use_case.change_api_data.facebook;

import data_access.APIDataAccessInterface;
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

public class ChangeFacebookDataInteractorTest {

    @Mock
    private ChangeDataAccessInterface mockChangeDataAccessInterface;
    @Mock
    private ChangeAPIDataOutputBoundary mockHomepagePresenter;
    @Mock
    private APIDataAccessInterface mockFacebookAPIDataAccessInterface;

    private ChangeFacebookDataInteractor interactor;

    private CommonUserFactory userFactory;

    private String token = "EAAE1wmc7D2IBOZBbbpXUrnwZCaRRQWsoHIsnnbxP32W1NQrjSPGtbBHY7wfFzfF2LFNiT4WSC28n66g0eEp6q8lfX9l68qtHwZBJgIg4YDBiTo9omqsfFRZCZA8t5CHXdFca8AEZAzP38nufQOFWeq6kJZBAo2ZCP7zSNpIgXgha1gCnVc32P010GPE3";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new ChangeFacebookDataInteractor(mockChangeDataAccessInterface, mockHomepagePresenter, mockFacebookAPIDataAccessInterface);
        userFactory = new CommonUserFactory();
    }

    @Test
    public void testSuccessfulApiTokenUpdateAndDataFetch() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "Username", "name" );
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("Facebook", token);
        when(mockChangeDataAccessInterface.get("username")).thenReturn(userFactory.create("name", "username", "password", "bio", apiKeys, LocalDateTime.now()));

        interactor.executeAPIChanges(mockInput);
        verify(mockChangeDataAccessInterface).modifyUserAPI("username", null, "newFacebookToken");
        verify(mockFacebookAPIDataAccessInterface).setAPI("newFacebookToken");
        verify(mockFacebookAPIDataAccessInterface).fetchData();
        verify(mockHomepagePresenter).prepareAPIView(any(ChangeFacebookDataOutput.class));
    }

    @Test(expected = RuntimeException.class)
    public void testMalformedURLExceptionHandling() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "Username", "name" );
        doThrow(new MalformedURLException()).when(mockFacebookAPIDataAccessInterface).fetchData();

        interactor.executeAPIChanges(mockInput);

        verify(mockHomepagePresenter, never()).prepareAPIView(any(ChangeFacebookDataOutput.class));
    }

    @Test
    public void testApiErrorHandling() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "Username", "name" );
        when(mockFacebookAPIDataAccessInterface.isApiError()).thenReturn(true);

        interactor.executeAPIChanges(mockInput);

        verify(mockHomepagePresenter).prepareAPIView(any(ChangeFacebookDataOutput.class));
    }

    @Test
    public void testDataParsingAndViewPreparation() throws MalformedURLException {
        ChangeAPIDataInput mockInput = new ChangeAPIDataInput(token, "Username", "name" );
        HashMap<String, JSONArray> mockStats = new HashMap<>();
        mockStats.put("likes", new JSONArray(/* some data */));
        when(mockFacebookAPIDataAccessInterface.getStats().getStats()).thenReturn(mockStats);

        interactor.executeAPIChanges(mockInput);

        verify(mockHomepagePresenter).prepareAPIView(any(ChangeFacebookDataOutput.class));
    }




}
