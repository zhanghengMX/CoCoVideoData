package com.henry.cocovideodata.jsoup;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Copyright (c) 2015. 北京视达科科技有限公司 All rights reserved.
 *
 * author: zhixin.lin
 *
 * date: 2018/6/14.
 *
 * description :
 */

public class PlaySource extends BmobObject implements Comparable<PlaySource>{

    public static final int TYPE_M3U8 = 1;//直接播放
    public static final int TYPE_WEB = 2;//网页打开
    public static final int TYPE_WEB_CONVERT = 3;//网页加前缀打开
    public static final int TYPE_BAIDU = 4;//百度网盘
    public static final int TYPE_DOWNLOAD = 5;//下载地址

    public PlaySource() {
        sourceList = new ArrayList<>();
    }

    /**
     * Id
     */
    private String doubanId;

    /**
     * 标题名字 : HC, TC等...
     */
    private String sourceName;
    /**
     * 集数
     */
    private int sourceIndex;

    /**
     * 播放源列表
     */
    private List<Item> sourceList;

    @Override
    public int compareTo(PlaySource o) {
        if (o.getSourceIndex() == this.getSourceIndex()) {
            return 0;
        }
        if (o.getSourceIndex() > this.getSourceIndex()) {
            return -1;
        }
        return 1;
    }

    public void setSourceList(List<Item> newList) {
        sourceList = newList;
    }


    /**
     * 真正的播放源
     */
    public static class Item {
        private int type;
        private String url;
        private String passwd;
        private int index;//标号

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;

            Item item = (Item) object;

            if (type != item.type) return false;
            return url != null ? url.equals(item.url) : item.url == null;

        }

        @Override
        public int hashCode() {
            int result = type;
            result = 31 * result + (url != null ? url.hashCode() : 0);
            return result;
        }
    }


    public String getDoubanId() {
        return doubanId;
    }

    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public int getSourceIndex() {
        return sourceIndex;
    }

    public void setSourceIndex(int sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public List<Item> getSourceList() {
        return sourceList;
    }

    public void addSource(Item item) {
        if (sourceList == null || sourceList.contains(item)) {
            return;
        }
        sourceList.add(item);
    }

    public void addSource(List<Item> slist) {
        sourceList.addAll(slist);
    }
}
