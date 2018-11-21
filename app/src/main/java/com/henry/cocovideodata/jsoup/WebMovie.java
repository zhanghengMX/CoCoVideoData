package com.henry.cocovideodata.jsoup;

import java.util.List;

/**
 * author: zhixin.lin
 * date: 2018/7/10.
 * description :
 */

public class WebMovie {

    private String title;
    private String poster;
    private String director;
    private String actor;
    private String updateTime;
    private boolean isSoap;
    private List<PlaySource> playSource;

    public boolean isSoap() {
        return isSoap;
    }

    public void setSoap(boolean soap) {
        isSoap = soap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<PlaySource> getPlaySource() {
        return playSource;
    }

    public void setPlaySource(List<PlaySource> playSource) {
        this.playSource = playSource;
    }

    @Override
    public String toString() {
        return "WebMovie{" +
                "title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", playSource=" + playSource +
                '}';
    }
}
