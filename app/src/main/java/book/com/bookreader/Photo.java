package book.com.bookreader;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {

    @SerializedName("photo")
    private List<ImageModel> photo;
    @SerializedName("page")
    private int page;
    @SerializedName("pages")
    private int pages;
    @SerializedName("perpage")
    private int perpage;
    @SerializedName("total")
    private int total;

    public List<ImageModel> getPhoto() {
        return photo;
    }

    public void setPhoto(List<ImageModel> photo) {
        this.photo = photo;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getTotalItem() {
        return total;
    }

    public void setTotalItem(int totalItem) {
        this.total = totalItem;
    }
}
