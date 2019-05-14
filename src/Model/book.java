package Model;


import net.sf.resultsetmapper.MapToData;

public class book {


    @MapToData(columnPrefix = "ISBN")
    private int bookId;

    @MapToData(columnPrefix = "title")
    private String title;

    @MapToData(columnPrefix = "publisherName")
    private String pName;

    @MapToData(columnPrefix = "publicationYear")
    private String pYear;

    @MapToData(columnPrefix = "price")
    private int price;

    @MapToData(columnPrefix = "category")
    private String category;

    private int quantity;
    private int threshold;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }


    public int getIsbn() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpYear() {
        return pYear;
    }

    public void setpYear(String pYear) {
        this.pYear = pYear;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
