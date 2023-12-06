package use_case.change_api_data;

public class ChangeAPIDataInput {
    String APIToken;
    String username;
    String name;
    public ChangeAPIDataInput(String APIToken, String username, String name){
        this.APIToken = APIToken;
        this.username = username;
        this.name = name;
    }

    public String getAPIToken() {
        return APIToken;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
