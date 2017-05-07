package com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by shine on 18/04/2017.
 */
@Root(name = "rss", strict = false)
public class RssFeed_BDC {
    @Element(name="title")
    @Path("channel")
    private String channelTitle;
    @ElementList(name="item", inline=true)
    @Path("channel")
    private List<Article_BDC> articleBDCList;

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public List<Article_BDC> getArticleBDCList() {
        return articleBDCList;
    }

    public void setArticleBDCList(List<Article_BDC> articleBDCList) {
        this.articleBDCList = articleBDCList;
    }
}
