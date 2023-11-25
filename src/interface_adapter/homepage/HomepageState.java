package interface_adapter.homepage;

public class HomepageState {
    private String name; // Add name field

    private String username;
    private String bio; // Add bio field

    private String instagramToken;

    private String facebookToken;

    public HomepageState(HomepageState copy) {
        name = copy.name;
        username = copy.username;
        bio = copy.bio;
        instagramToken = copy.instagramToken;
        facebookToken = copy.facebookToken;
    }

    // Explicit default constructor
    public HomepageState(){}

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return this.bio;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setInstagramToken(String instagramToken){
        this.instagramToken = instagramToken;
    }

    public void setFacebookToken(String facebookToken){
        this.facebookToken = facebookToken;
    }

    public String getInstagramToken(){
        return this.instagramToken;
    }

    public String getFacebookToken(){
        return this.facebookToken;
    }

    @Override
    public String toString() {
        return "HomepageState{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
