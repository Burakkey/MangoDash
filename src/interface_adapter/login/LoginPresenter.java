package interface_adapter.login;

import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.ViewManagerModel;

import org.json.JSONArray;
import org.json.JSONObject;
import use_case.change_user_data.ChangeDataOutput;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final HomepageViewModel homepageViewModel;
    private ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          HomepageViewModel homepageViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel = homepageViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the logged in view.
        HomepageState homepageState = new HomepageState();
        homepageState.setName(response.getName());
        homepageState.setUsername(response.getUsername());
        homepageState.setBio(response.getBio());
        homepageState.setFacebookToken(response.getFacebookAPI());
        homepageState.setInstagramToken(response.getInstagramAPI());
        homepageState.setInstagramStatsHashMap(makeInstagramStatsHashmap(response));
        this.homepageViewModel.setState(homepageState);
        this.viewManagerModel.setActiveView(homepageViewModel.getViewName());
        this.loginViewModel.setState(new LoginState());
        this.loginViewModel.firePropertyChanged();
        this.homepageViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();
    }

    private static HashMap<String, Object> makeInstagramStatsHashmap(LoginOutputData loginOutPutData) {
        JSONArray arrayFollowers = loginOutPutData.getInstagramFollowers();
        JSONArray arrayPosts = loginOutPutData.getInstagramPosts();

        // Assuming the first element of arrayFollowers is the total follower count
        int followersCount = arrayFollowers.length() > 0 ? arrayFollowers.getInt(0) : 0;

        int maxLikes = 0;
        int maxComments = 0;
        int totalLikes = 0;
        int totalComments = 0;
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
        return instagramStatsHashMap;
    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setError((error));
        loginViewModel.firePropertyChanged();
    }
}
