package personal.edu.news.entity;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
public class ViewPager_User {
    private String channelId;
    private String name;

    public ViewPager_User(String channelId, String name) {
        this.channelId = channelId;
        this.name = name;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
