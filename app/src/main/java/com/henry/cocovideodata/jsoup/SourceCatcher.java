package com.henry.cocovideodata.jsoup;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * author: zhixin.lin
 *
 * date: 2018/7/10.
 *
 * description :
 */

public class SourceCatcher {

    private static final String TAG = "SourceCatcher";

    public static final String OK_SEARCH_URL = "https://okzyw.com/index.php?m=vod-search";
    public static final String OK_BASE_URL = "https://okzyw.com/";

    public static final String ZUIDA_SEARCH_URL = "http://zuidazy.net/index.php?m=vod-search";
    public static final String ZUIDA_BASE_URL = "http://zuidazy.net/";

    public static final String O1ZY_SEARCH_URL = "http://www.01zy.com/index.php?m=vod-search";
    public static final String O1_BASE_URL = "http://www.01zy.com/";

    public static final String KUYU_SEARCH_URL = "http://www.dyw80s.com/index.php?m=vod-search";
    public static final String KUYU_BASE_URL = "http://www.dyw80s.com/";

    public static final String YUN1717_SEARCH_URL = "http://zy.itono.cn/index.php?m=vod-search";
    public static final String YUN1717_BASE_URL = "http://zy.itono.cn/";

    public static final String PREFIX_QQ = "http://v.qq.com";
    public static final String PREFIX_MGTV = "http://www.mgtv.com";
    public static final String PREFIX_YOUKU = "http://v.youku.com";
    public static final String PREFIX_IQIYI = "http://www.iqiyi.com";

    public static final String PREFIX_QQS = "https://v.qq.com";
    public static final String PREFIX_MGTVS = "https://www.mgtv.com";
    public static final String PREFIX_YOUKUS = "https://v.youku.com";
    public static final String PREFIX_IQIYIS = "https://www.iqiyi.com";

    protected String mSearchUrl;
    protected String mBaseUrl;
    protected OkHttpClient mHttpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS).build();

    public SourceCatcher(String mSearchUrl, String mBaseUrl) {
        this.mSearchUrl = mSearchUrl;
        this.mBaseUrl = mBaseUrl;
    }

    public List<WebMovie> start(String keyWord) throws Exception {
        return getMovieList(getDetailUrl(keyWord));
    }

    private List<DetailUrl> getDetailUrl(String keyWord) throws IOException {

        FormBody formBody = new FormBody.Builder()
                .add("wd", keyWord)
                .add("submit", "search")
                .build();

        Request request = new Request.Builder()
                .url(mSearchUrl)
                .post(formBody)
                .build();

        Response response = mHttpClient.newCall(request).execute();

        String result = response.body().string();

        Document document = Jsoup.parse(result);
        Elements elements = document.getElementsByClass("xing_vb4");
        List<DetailUrl> detailList = new ArrayList<>();
        Element li = null;
        Elements updateTimes = null;
        for (Element e : elements) {
            Element es = e.selectFirst("a[href]");
            if (es == null) {
                continue;
            }
            String url = es.attr("href");
            if (TextUtils.isEmpty(url)) {
                continue;
            }
            int index = url.indexOf("?");
            if (index == -1 || !url.endsWith("html")) {
                continue;
            }
            String title = es.text();
            String updateTime = null;
            li = e.parent();
            if (li != null) {
                updateTimes = li.getElementsByClass("xing_vb6");
                if (updateTimes != null && !updateTimes.isEmpty()) {
                    updateTime = updateTimes.get(0).text();
                }
            }
            url = url.substring(index);
            Log.i(TAG, "text = " + es.text() + ",url = " + url + ", title = " + title + ", updateTime" +
                    " = " + updateTime);
            detailList.add(new DetailUrl().setTime(updateTime).setTitle(title).setUrl(url));
        }

        return detailList;
    }

    private List<WebMovie> getMovieList(List<DetailUrl> detailUrlList) throws
            IOException {

        List<WebMovie> movieList = new ArrayList<>();
        for (DetailUrl url : detailUrlList) {
            String detailHtml = get(url.getUrl());
            WebMovie webMovie = getMovieInfo(detailHtml);
            if (webMovie == null) {
                continue;
            }
            webMovie.setTitle(url.getTitle());
            webMovie.setUpdateTime(url.getTime());
            Pair<List<PlaySource>, Boolean> pair = getPlaySource(detailHtml);
            if (pair == null || pair.getFirst() == null || pair.getFirst().isEmpty()) {
                continue;
            }
            for (PlaySource source : pair.getFirst()) {
                source.setSourceName(url.getTitle());
            }
            webMovie.setPlaySource(pair.getFirst());
            webMovie.setSoap(pair.getSecond());
            movieList.add(webMovie);
        }
        return movieList;
    }

    private Pair<List<PlaySource>, Boolean> getPlaySource(String detailHtml) {
        Document doc = Jsoup.parse(detailHtml);
        Elements sourceElmts = doc.getElementsByAttributeValue("name", "copy_sel");
        if (sourceElmts == null || sourceElmts.isEmpty()) {
            return null;
        }
        boolean isSoap = false;
        List<PlaySource> playSourceList = new ArrayList<>();
        PlaySource source = null;

        boolean hasCheckKind = false;
        for (Element urlElmt : sourceElmts) {
            String url = urlElmt.parent().text();
//            String url = urlElmt.attr("value");
            if (TextUtils.isEmpty(url)) {
                continue;
            }

            String[] arr = url.split("\\$");
            if (arr == null || arr.length < 2) {
                continue;
            }

            if (!hasCheckKind) {
                if (arr[0].startsWith("第") || arr[0].endsWith("集") || MuggulUtil.isNumeric(arr[0])) {
                    isSoap = true;
                }
                hasCheckKind = true;
            }

            if (isSoap) {
                if (arr[0].contains("第") || arr[0].contains("集")) {
                    arr[0] = arr[0].replace("第","");
                    arr[0] = arr[0].replace("集","");
                }
                int index = MuggulUtil.tryParserInt(arr[0], -1);
                if (index == -1) {
                    continue;
                }
                source = getPlaySourceByIndex(playSourceList, index);
                if (source == null) {
                    source = new PlaySource();
                    source.setSourceIndex(index);
                    source.addSource(getSourceItem(arr[1]));
                    playSourceList.add(source);
                } else {
                    source.addSource(getSourceItem(arr[1]));
                }
            } else {
                if (source == null) {
                    source = new PlaySource();
                }
                source.addSource(getSourceItem(arr[1]));
            }
        }
        if (!isSoap) {
            playSourceList.add(source);
        }
        Pair<List<PlaySource>, Boolean> pair = new Pair<>();
        pair.setFirst(playSourceList);
        pair.setSecond(isSoap);
        return pair;
    }

    private PlaySource.Item getSourceItem(String url) {

        PlaySource.Item item = new PlaySource.Item();
        if (url.endsWith(".m3u8")) {
            item.setUrl(url);
            item.setType(PlaySource.TYPE_M3U8);
        } else if (url.endsWith(".mp4") || url.endsWith(".avi")
                || url.endsWith(".mkv")) {
            item.setUrl(url);
            item.setType(PlaySource.TYPE_DOWNLOAD);
        } else {
            item.setUrl(url);
            item.setType(PlaySource.TYPE_WEB);
            if (YUN1717_BASE_URL.equals(mBaseUrl) && (url.startsWith(SourceCatcher.PREFIX_IQIYI)
                    || url.startsWith(SourceCatcher.PREFIX_MGTV)
                    || url.startsWith(SourceCatcher.PREFIX_QQ)
                    || url.startsWith(SourceCatcher.PREFIX_YOUKU)
                    || url.startsWith(SourceCatcher.PREFIX_IQIYIS)
                    || url.startsWith(SourceCatcher.PREFIX_MGTVS)
                    || url.startsWith(SourceCatcher.PREFIX_QQS)
                    || url.startsWith(SourceCatcher.PREFIX_YOUKUS))) {
                item.setType(PlaySource.TYPE_WEB_CONVERT);
            }
        }
        return item;
    }

    private PlaySource getPlaySourceByIndex(List<PlaySource> playSourceList, int index) {
        PlaySource source = null;
        for (PlaySource playSource : playSourceList) {
            if (index == playSource.getSourceIndex()) {
                source = playSource;
                break;
            }
        }
        return source;
    }


    private WebMovie getMovieInfo(String detailHtml) {
        Document doc = Jsoup.parse(detailHtml);
        Elements imgElements = doc.getElementsByClass("vodImg");
        if (imgElements == null || imgElements.isEmpty()) {
            return null;
        }
        Element imgElmt = imgElements.get(0);
        if (imgElmt == null) {
            return null;
        }
        Elements imgElmts = imgElmt.getElementsByClass("lazy");
        if (imgElmts == null || imgElmts.isEmpty()) {
            return null;
        }
        String poster = imgElmts.get(0).attr("src");

        Elements infoElements = doc.getElementsByClass("vodinfobox").select("li");
        if (infoElements == null || infoElements.isEmpty()) {
            return null;
        }

        String director = "";
        String actor = "";
        for (Element li : infoElements) {
            if (li == null || li.text() == null) {
                continue;
            }
            if (li.text().contains("导演")) {
                director = li.select("span").get(0).text();
            }
            if (li.text().contains("主演")) {
                actor = li.select("span").get(0).text();
            }
        }
        Log.i(TAG, "getMovieInfo: " + director);
        Log.i(TAG, "getMovieInfo: " + actor);
        WebMovie movie = new WebMovie();
        movie.setActor(actor);
        movie.setDirector(director);
        movie.setPoster(poster);
        return movie;
    }

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(mBaseUrl + url).get().build();
        return mHttpClient.newCall(request).execute().body().string();
    }
}
