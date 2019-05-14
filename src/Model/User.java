package Model;

import Controller.DbConnect;
import net.sf.resultsetmapper.MapToData;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private static User user;

    private static int userId;
    private static List<String> shippingAddress;
    private static boolean privilege;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private static List<shoppingCartItem> shoppingCart;
    private String phoneNum;

    public static int getUserId() {
        return userId;
    }

    public static User getUser() {

        return user;
    }

    public static User loginUser(String userName, String password) throws SQLException {
        DbConnect connect = DbConnect.getInstance();
        String query = "{CALL authenticateUser(?,?)}";

        CallableStatement statement = connect.getConnection().prepareCall(query);
        statement.setString(1, userName);
        statement.setString(2, password);
        ResultSet set = statement.executeQuery();
        if (!set.next()) {
            return null;
        } else {
            userId = set.getInt(1);
            privilege = set.getBoolean(2);
            query = "{CALL logIn(?)}";
            statement = connect.getConnection().prepareCall(query);
            statement.setInt(1, userId);
            ResultSet set1 = statement.executeQuery();
            User user1 = new User();
            while (set1.next()) {
                user1.setUserName(set1.getString(1));
                user1.setPassword(set1.getString(2));
                user1.setFirstName(set1.getString(3));
                user1.setLastName(set1.getString(4));
                user1.setEmail(set1.getString(5));
                user1.setPhoneNumber(set1.getString(6));
            }

            statement = connect.getConnection().prepareCall("{CALL getShippingAddress(?)}");
            statement.setInt(1, userId);
            ResultSet set2 = statement.executeQuery();
            shippingAddress = new ArrayList<>();
            while (set2.next()) {
                shippingAddress.add(set2.getString(1));
            }
            shoppingCart = new ArrayList<>();
            user = user1;
            return user;
        }


    }

    public List<shoppingCartItem> getShoppingCart() {
        return shoppingCart;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(List<String> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getTotalPriceCart() {
        int sum = 0;
        for (shoppingCartItem item : shoppingCart) {
            sum += item.getTotalPrice();
        }
        return sum;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNum;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNum = phoneNumber;
    }

    public boolean isPrivilege() {
        return privilege;
    }

    public void emptyShoppingCart() {

        shoppingCart = new ArrayList<>();

    }

    public void logOut() {

        user = null;
    }
}
