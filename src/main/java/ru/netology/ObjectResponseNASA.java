package ru.netology;

public class ObjectResponseNASA {

    private String copyright;
    private String date;
    private String explanation;
    private String hdurl;
    private String mediaType;
    private String serviceVersion;
    private String title;
    private String url;

    public ObjectResponseNASA() {

    }

    public ObjectResponseNASA(String copyright, String url, String title, String serviceVersion, String mediaType, String hdurl, String explanation, String date) {
        this.copyright = copyright;
        this.url = url;
        this.title = title;
        this.serviceVersion = serviceVersion;
        this.mediaType = mediaType;
        this.hdurl = hdurl;
        this.explanation = explanation;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "NASA INFO  \n"+
                "title = " + title + '\n' +
                "copyright = " + copyright + '\n' +
                "url = " + url + '\n' +
                "*--------* \n";
    }
}
