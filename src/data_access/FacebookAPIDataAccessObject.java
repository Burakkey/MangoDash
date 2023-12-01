package data_access;

public class FacebookAPIDataAccessObject implements FacebookAPIDataAccessInterface {
    private String apiKey;
    private FacebookStats facebookStats;

    public FacebookAPIDataAccessObject(String apiKey, FacebookStats facebookStats) {
        this.apiKey = apiKey;
        this.facebookStats = facebookStats;
    }

    @Override
    public void fetchData() throws MalformedURLException {

    }

    @Override
    public void setApi(String apiKey) {
        this.apiKey = apiKey
    }

    @Override
    public FacebookStats getFacebookStats() {
        return facebookStats
    }
}
