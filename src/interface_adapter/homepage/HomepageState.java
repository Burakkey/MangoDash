package interface_adapter.homepage;

public class HomepageState {
    private String username = "";
    private String name = ""; // Add name field
    private String bio = ""; // Add bio field

    public HomepageState(HomepageState copy) {
        username = copy.username;
        name = copy.name;
        bio = copy.bio;
    }

    // Explicit default constructor
    public HomepageState() {
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
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

    @Override
    public String toString() {
        return "HomepageState{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
