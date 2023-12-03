package interface_adapter.homepage;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.InstagramDataGetter;
import use_case.change_user_data.ChangeDataOutput;
import use_case.change_user_data.ChangeDataOutputBoundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomepagePresenter implements ChangeDataOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final HomepageViewModel homepageViewModel;
    private ViewManagerModel viewManagerModel;

    public HomepagePresenter(LoginViewModel loginViewModel, HomepageViewModel homepageViewModel,
                             ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.homepageViewModel = homepageViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareFailView(String s) {
        // On success, switch to the logged in view.
    }

    @Override
    public void prepareSuccessView(ChangeDataOutput changeDataOutput) {
        // On success, switch to the logged in view.
        HomepageState homepageState = homepageViewModel.getState();
        homepageState.setName(changeDataOutput.getName());
        homepageState.setUsername(changeDataOutput.getUsername());
        homepageState.setBio(changeDataOutput.getBio());
        this.homepageViewModel.setState(homepageState);
        this.viewManagerModel.setActiveView(homepageViewModel.getViewName());
        this.homepageViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareAPIView(ChangeDataOutput changeDataOutput) {
        HashMap<String, Object> instagramStatsHashMap = makeInstagramStatsHashmap(changeDataOutput);
        HomepageState homepageState = homepageViewModel.getState();
        homepageState.setInstagramKeyError((Boolean) changeDataOutput.getInstagramData().get("keyError"));
        homepageState.setFacebookKeyError((Boolean) changeDataOutput.getFacebookData().get("keyError"));
        homepageState.setInstagramStatsHashMap(instagramStatsHashMap);
        this.homepageViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();
    }

    public static HashMap<String, Object> makeInstagramStatsHashmap(InstagramDataGetter changeDataOutput) {
        JSONArray arrayFollowers = (JSONArray) changeDataOutput.getInstagramData().get("followers");
        JSONArray arrayPosts = (JSONArray) changeDataOutput.getInstagramData().get("posts");
        JSONArray arrayUsername = (JSONArray) changeDataOutput.getInstagramData().get("username");
        System.out.println(changeDataOutput.getInstagramData());

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


    public void prepareFailAPIView(ChangeDataOutput changeDataOutput){
        HomepageState homepageState = new HomepageState();
        homepageState.setName(changeDataOutput.getName());
        homepageState.setUsername(changeDataOutput.getUsername());
        homepageState.setBio(changeDataOutput.getBio());
        this.homepageViewModel.setState(homepageState);
    }
}
