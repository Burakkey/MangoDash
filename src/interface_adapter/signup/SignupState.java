package interface_adapter.signup;

public class SignupState {
    private String name = "";
    private String username = "";
    private String error = null;
    private String password = "";
    private String passwordError = null;
    private String repeatPassword = "";
    private String repeatPasswordError = null;

    public SignupState(SignupState copy) {
        name = copy.name;
        username = copy.username;
        error = copy.error;
        password = copy.password;
        passwordError = copy.passwordError;
        repeatPassword = copy.repeatPassword;
        repeatPasswordError = copy.repeatPasswordError;
    }

    public SignupState() {
    }

    public String getName(){
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getError() {
        return error;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setError(String usernameError) {
        this.error = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    @Override
    public String toString() {
        return "SignupState{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                '}';
    }
}
