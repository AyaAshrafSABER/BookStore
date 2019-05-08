package View;

public class BookView {
    private static BookView ourInstance = new BookView();

    public static BookView getInstance() {
        return ourInstance;
    }

    private BookView() {
    }
}
