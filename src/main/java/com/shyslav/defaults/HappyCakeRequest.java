package com.shyslav.defaults;


import com.shyslav.utils.LazyGson;

/**
 * @author Shyshkin Vladyslav on 30.04.17.
 */
public class HappyCakeRequest {
    private final String url;
    private final String context;

    public HappyCakeRequest(String url) {
        this.url = url;
        this.context = null;
    }

    public HappyCakeRequest(String url, Object context) {
        this.url = url;
        if (context instanceof String) {
            this.context = (String) context;
        } else {
            this.context = LazyGson.toJson(context);
        }
    }

    public String getUrl() {
        return url;
    }

    public <T> T getObject(Class clazz) {
        return LazyGson.fromJson(context, clazz);
    }
}
