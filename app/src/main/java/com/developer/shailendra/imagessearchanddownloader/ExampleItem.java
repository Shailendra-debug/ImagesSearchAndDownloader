package com.developer.shailendra.imagessearchanddownloader;


public class ExampleItem {
    private String mCreator;
    private String mImageUrl;
    private String webformatURL;
    private String pageURL;

    public ExampleItem(String imageUrl, String creator , String WebformatURL, String PageURL) {
        mImageUrl = imageUrl;
        mCreator = creator;
        webformatURL = WebformatURL;
        pageURL = PageURL;
    }
    public String getCreator() {
        return mCreator;
    }
    public String getImageUrl() {
        return mImageUrl;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public String getPageURL() {
        return pageURL;
    }
}
