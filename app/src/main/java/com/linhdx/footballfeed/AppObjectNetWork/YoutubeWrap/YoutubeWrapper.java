package com.linhdx.footballfeed.AppObjectNetWork.YoutubeWrap;

/**
 * Created by shine on 06/05/2017.
 */

public class YoutubeWrapper {
    public String nextPageToken;
    public Items[] items;

    public class Items {
        public Snippet snippet;
        public Id id;

        public class Id {
            public String videoId;
        }

        public class Snippet {
            public String publishedAt;
            public String title;
            public String description;
            public Thumbnails thumbnails;
            public String channelTitle;


            public class Thumbnails {
                public Thumbnai medium;

                public class Thumbnai {
                    public String url;
                }
            }
        }
    }
}
