package book.com.bookreader;

import com.google.gson.annotations.SerializedName;

public class ResponseDetails {

    @SerializedName("photos")
    private Photo photos;
    @SerializedName("stat")
    private String stat;

    public Photo getPhotos() {
        return photos;
    }

    public void setPhotos(Photo photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
