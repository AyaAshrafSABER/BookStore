package Model;

import java.util.Map;
import java.util.Set;

public class shoppingCart {

    Map<String, Integer> cart;

    public void addToCart(String bookId, int quantity) {

        cart.put(bookId, quantity);

    }

    public void removeFromCart(String bookId) {
        if (cart.containsKey(bookId))
            cart.remove(bookId);
    }


}
