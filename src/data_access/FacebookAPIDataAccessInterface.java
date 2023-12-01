package data_access;

public interface FacebookAPIDataAccessInterface {
    void fetchData();
    void setApi(String apiKey);
    FacebookStats getFacebookStats();
}