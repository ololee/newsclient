package cn.ololee.newsclient.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class NewsItem implements Parcelable {
    private String id;
    private String title;//标题
    private String time;//时间
    private String source;//来源
    private String category;//分类
    private String picture;//图片
    private String content;//内容
    private String url;//url
    private String weburl;//电脑端url
    private String pictureurl;

    protected NewsItem(Parcel in) {
        id = in.readString();
        title = in.readString();
        time = in.readString();
        source = in.readString();
        category = in.readString();
        picture = in.readString();
        content = in.readString();
        url = in.readString();
        weburl = in.readString();
        pictureurl = in.readString();
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", source='" + source + '\'' +
                ", category='" + category + '\'' +
                ", picture='" + picture + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", weburl='" + weburl + '\'' +
                ", pictureurl='" + pictureurl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(source);
        dest.writeString(category);
        dest.writeString(picture);
        dest.writeString(content);
        dest.writeString(url);
        dest.writeString(weburl);
        dest.writeString(pictureurl);
    }


}
