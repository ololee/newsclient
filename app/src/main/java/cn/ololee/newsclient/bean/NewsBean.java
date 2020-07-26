package cn.ololee.newsclient.bean;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
* Gson Object
* Newsbean
* Vector
* */
public class NewsBean{
    private int status;
    private String msg;
    private CopyOnWriteArrayList<NewsItem> newsItemList;

    public NewsBean() {
    }

    public NewsBean(int status, String msg, CopyOnWriteArrayList<NewsItem> newsItemList) {
        this.status = status;
        this.msg = msg;
        this.newsItemList = newsItemList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewsItem> getNewsItemList() {
        return newsItemList;
    }

    public void setNewsItemList(CopyOnWriteArrayList<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", newsItemList=" + newsItemList +
                '}';
    }
}
