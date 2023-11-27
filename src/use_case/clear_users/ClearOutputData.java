package use_case.clear_users;

// TODO Complete me

import java.util.List;

public class ClearOutputData {

    private List usernames;
    private boolean useCaseFailed;

    public ClearOutputData(List usernames, boolean useCaseFailed) {

        this.usernames = usernames;
        this.useCaseFailed = useCaseFailed;
    }

    public List getUsernames(){
        return usernames;
    }

}
