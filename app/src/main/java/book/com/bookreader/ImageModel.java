package book.com.bookreader;

import com.google.gson.annotations.SerializedName;

public class ImageModel {
    @SerializedName(value = "url_q", alternate = "url_o")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
