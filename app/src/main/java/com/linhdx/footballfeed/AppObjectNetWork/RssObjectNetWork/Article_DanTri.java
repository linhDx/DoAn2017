package com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by shine on 30/04/2017.
 */
@Root(name = "item", strict = false)
public class Article_DanTri {
    @Element(name = "title")
    private String title;

    @Element(name = "description")
    private String description;

    @Element(name = "link")
    private String link;

    @Element(name = "pubDate")
    private String pubDate;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }
}
