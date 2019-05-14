package Model;

import java.sql.Date;
import java.util.Map;
import java.util.Set;


public class shoppingCartItem {


    private int bookId;
    private Date date;

    public shoppingCartItem(int bookId, int quantity, int price) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
        totalPrice = price * quantity;

    }

    public int getBookId() {
        return bookId;
    }

    public int getPrice() {
        return price;
    }

    private int price;
    private int quantity;
    private int totalPrice;

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Date getDate() {
        return date;
    }


}
