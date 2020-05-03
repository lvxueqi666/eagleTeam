package com.example.qiqi.xianwan.bean;

import java.util.ArrayList;
import java.util.List;

public class MyBean {
    private String header;
    private String footer;
    private List<ItemBean> list = new ArrayList<>();
    private List<Diandi> diandi = new ArrayList<>();
    private List<String> urls = new ArrayList<>();

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<Diandi> getDiandi() {
        return diandi;
    }

    public void setDiandi(List<Diandi> diandi) {
        this.diandi = diandi;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public List<ItemBean> getList() {
        return list;
    }

    public void setList(List<ItemBean> list) {
        this.list = list;
    }

    public static class ItemBean {
        private String content;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
