package com.jidokhants.mukyojeong.model;

import java.io.Serializable;

public class Feed implements Serializable {
    private Post post;
    private String key;

    public Feed() {
    }

    public Feed(Post post, String key) {
        this.post = post;
        this.key = key;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
