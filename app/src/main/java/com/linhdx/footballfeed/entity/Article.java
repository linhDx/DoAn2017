package com.linhdx.footballfeed.entity;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by shine on 30/04/2017.
 */

public class Article extends SugarRecord{
    String link, titlte, description,image, webName;
    Date pubDate;
    public Article() {
    }

    public Article(String link, String titlte, String description, String image, Date pubDate, String webName) {
        this.link = link;
        this.titlte = titlte;
        this.description = description;
        this.image = image;
        this.pubDate = pubDate;
        this.webName = webName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitlte() {
        return titlte;
    }

    public void setTitlte(String titlte) {
        this.titlte = titlte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }
}
