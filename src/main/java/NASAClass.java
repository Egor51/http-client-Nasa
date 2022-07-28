import com.fasterxml.jackson.annotation.JsonProperty;

public record NASAClass(String copyright, String date, String explanation, String hdurl, String mediaType,
                        String serviceVersion, String title, String url) {
    public NASAClass(@JsonProperty("copyright") String copyright, @JsonProperty("date") String date, @JsonProperty("explanation") String explanation,
                     @JsonProperty("hdurl") String hdurl, @JsonProperty("media_type") String mediaType, @JsonProperty("service_version") String serviceVersion,
                     @JsonProperty("title") String title, @JsonProperty("url") String url) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.mediaType = mediaType;
        this.serviceVersion = serviceVersion;
        this.title = title;
        this.url = url;
    }

    @Override
    public String toString() {
        return "NASAR{" +
                "copyright='" + copyright + '\'' +
                ", date='" + date + '\'' +
                ", explanation='" + explanation + '\'' +
                ", hdurl='" + hdurl + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", serviceVersion='" + serviceVersion + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
