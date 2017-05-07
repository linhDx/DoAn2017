package com.linhdx.footballfeed.AppObjectNetWork.RssObjectNetWork;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Element;
/**
 * Created by shine on 18/04/2017.
 */
@Root(name = "item", strict = false)
public class Article_BDC {
    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;

    @Element(name = "description")
    private String description;

    @Element(name = "image")
    private String image;

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

    public String getImage() {
        return image;
    }

    public String getPubDate() {
        return pubDate;
    }
}
