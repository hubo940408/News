package personal.edu.news.entity;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class WebView_User {
    private String name;
    private String url;

    public WebView_User(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
