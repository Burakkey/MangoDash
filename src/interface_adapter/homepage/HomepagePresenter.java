package interface_adapter.homepage;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.DataGetter;
import use_case.change_api_data.ChangeAPIDataOutputBoundary;
import use_case.change_api_data.facebook.ChangeFacebookDataOutput;
import use_case.change_api_data.instagram.ChangeInstagramDataOutput;
import use_case.change_user_data.ChangeDataOutput;
import use_case.change_user_data.ChangeDataOutputBoundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomepagePresenter implements ChangeDataOutputBoundary, ChangeAPIDataOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final HomepageViewModel homepageViewModel;
    private ViewManagerModel viewManagerModel;

    /**
     * Creates a new HomepagePresenter
     * @param loginViewModel data structure that contains the data in the appropriate format for the LoginView to display
     * @param homepageViewModel data structure that contains the data in the appropriate format for the HomepageView to display
     * @param viewManagerModel contains all the different views, changes the active view when needed (in response to user)
     */
    public HomepagePresenter(LoginViewModel loginViewModel, HomepageViewModel homepageViewModel,
                             ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.homepageViewModel = homepageViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * if the input data does not have any errors, then the password change is successful and prepareSuccessView is called.
     * @param changeDataOutput
     */
    @Override
    public void prepareSuccessView(ChangeDataOutput changeDataOutput) {
        // On success, switch to the logged in view.
        HomepageState homepageState = homepageViewModel.getState();
        System.out.println(changeDataOutput.getName());
        homepageState.setName(changeDataOutput.getName());
        homepageState.setUsername(changeDataOutput.getUsername());
        homepageState.setBio(changeDataOutput.getBio());
        homepageState.setErrorMessage("");
        this.homepageViewModel.setState(homepageState);
        this.viewManagerModel.setActiveView(homepageViewModel.getViewName());
        this.homepageViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * If the input API key is valid, then prepareAPIView is called
     * @param changeDataOutput
     */
    @Override
    public void prepareAPIView(DataGetter changeDataOutput) {

        HomepageState homepageState = homepageViewModel.getState();
        homepageState.setErrorMessage("");

        if (changeDataOutput instanceof ChangeFacebookDataOutput) {
            HashMap<String, Object> facebookStatsHashMap = makeFacebookStatsHashmap(changeDataOutput);
            homepageState.setFacebookStatsHashMap(facebookStatsHashMap);
            homepageState.setFacebookKeyError((Boolean) changeDataOutput.getData().get("keyError"));
        } else if (changeDataOutput instanceof ChangeInstagramDataOutput) {
            HashMap<String, Object> instagramStatsHashMap = makeInstagramStatsHashmap(changeDataOutput);
            homepageState.setInstagramStatsHashMap(instagramStatsHashMap);
            homepageState.setInstagramKeyError((Boolean) changeDataOutput.getData().get("keyError"));
        }

        this.homepageViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();
    }

    public static HashMap<String, Object> makeFacebookStatsHashmap(DataGetter changeDataOutput) {
        JSONArray arrayFollowers = (JSONArray) changeDataOutput.getData().get("followers");
        JSONArray arrayPosts = (JSONArray) changeDataOutput.getData().get("posts");
        JSONArray arrayUsername = (JSONArray) changeDataOutput.getData().get("username");
        System.out.println(changeDataOutput.getData());

        // Assume the first element of arrayFollowers is the total friend count
        int followersCount = !arrayFollowers.isEmpty() ? arrayFollowers.getInt(0) : 0;
        int maxLikes = 0;
        int maxComments = 0;
        int totalLikes = 0;
        int totalComments = 0;

        String username = null;
        if (arrayUsername != null && !arrayUsername.isEmpty()) {
            username = (String) arrayUsername.get(0);
        }
        List<Integer> likesPerPost = new ArrayList<>();
        List<Integer> commentsPerPost = new ArrayList<>();

        for (int i = 0; i < arrayPosts.length(); i++) {
            JSONObject post = arrayPosts.getJSONObject(i);
            int likes = post.getInt("like_count");
            int comments = post.getInt("comments_count");

            if (likes > maxLikes) {
                maxLikes = likes;
            }
            likesPerPost.add(likes);
            commentsPerPost.add(comments);
            if (comments > maxComments) {
                maxComments = comments;
            }
            totalLikes += likes;
            totalComments += comments;
        }

        int totalPosts = arrayPosts.length();
        double averageLikes = totalPosts > 0 ? (double) totalLikes / totalPosts : 0;
        double averageComments = totalPosts > 0 ? (double) totalComments / totalPosts : 0;

        HashMap<String, Object> facebookStatsHashMap = new HashMap<>();

        // Add the calculated statistics to the hashmap
        facebookStatsHashMap.put("followersCount", followersCount);
        facebookStatsHashMap.put("maxLikes", maxLikes);
        facebookStatsHashMap.put("maxComments", maxComments);
        facebookStatsHashMap.put("totalLikes", totalLikes);
        facebookStatsHashMap.put("totalComments", totalComments);
        facebookStatsHashMap.put("averageLikes", averageLikes);
        facebookStatsHashMap.put("averageComments", averageComments);
        facebookStatsHashMap.put("likesPerPost", likesPerPost);
        facebookStatsHashMap.put("commentsPerPost", commentsPerPost);
        facebookStatsHashMap.put("totalPosts", totalPosts);
        facebookStatsHashMap.put("username", username);
        return facebookStatsHashMap;
    }

    public static HashMap<String, Object> makeInstagramStatsHashmap(DataGetter changeDataOutput) {
        JSONArray arrayFollowers = (JSONArray) changeDataOutput.getData().get("followers");
        JSONArray arrayPosts = (JSONArray) changeDataOutput.getData().get("posts");
        JSONArray arrayUsername = (JSONArray) changeDataOutput.getData().get("username");
        System.out.println(changeDataOutput.getData());

        // Assuming the first element of arrayFollowers is the total follower count
        int followersCount = !arrayFollowers.isEmpty() ? arrayFollowers.getInt(0) : 0;

        int maxLikes = 0;
        int maxComments = 0;
        int totalLikes = 0;
        int totalComments = 0;
        String username = null;
        if (arrayUsername != null && !arrayUsername.isEmpty()) {
            username = (String) arrayUsername.get(0);
        }
        List<Integer> likesPerPost = new ArrayList<>();
        List<Integer> commentsPerPost = new ArrayList<>();

        for (int i = 0; i < arrayPosts.length(); i++) {
            JSONObject post = arrayPosts.getJSONObject(i);
            int likes = post.getInt("like_count");
            int comments = post.getInt("comments_count");

            if (likes > maxLikes) {
                maxLikes = likes;
            }
            likesPerPost.add(likes);
            commentsPerPost.add(comments);
            if (comments > maxComments) {
                maxComments = comments;
            }
            totalLikes += likes;
            totalComments += comments;
        }

        int totalPosts = arrayPosts.length();
        double averageLikes = totalPosts > 0 ? (double) totalLikes / totalPosts : 0;
        double averageComments = totalPosts > 0 ? (double) totalComments / totalPosts : 0;

        HashMap<String, Object> instagramStatsHashMap = new HashMap<>();

        // Add the calculated statistics to the hashmap
        instagramStatsHashMap.put("followersCount", followersCount);
        instagramStatsHashMap.put("maxLikes", maxLikes);
        instagramStatsHashMap.put("maxComments", maxComments);
        instagramStatsHashMap.put("totalLikes", totalLikes);
        instagramStatsHashMap.put("totalComments", totalComments);
        instagramStatsHashMap.put("averageLikes", averageLikes);
        instagramStatsHashMap.put("averageComments", averageComments);
        instagramStatsHashMap.put("likesPerPost", likesPerPost);
        instagramStatsHashMap.put("commentsPerPost", commentsPerPost);
        instagramStatsHashMap.put("totalPosts", totalPosts);
        instagramStatsHashMap.put("username", username);
        return instagramStatsHashMap;
    }

    /**
     * If the input data has errors, then the data changes are unsuccessful
     * @param error A String containing the case-specific error message
     */
    @Override
    public void prepareFailView(String error){
        HomepageState currentState = homepageViewModel.getState();
        currentState.setErrorMessage(error);
        this.homepageViewModel.setState(currentState);

    }
}
