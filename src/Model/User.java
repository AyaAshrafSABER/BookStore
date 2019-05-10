package Model;

import Controller.DbConnect;
import net.sf.resultsetmapper.MapToData;
import net.sf.resultsetmapper.ReflectionResultSetMapper;
import net.sf.resultsetmapper.ResultSetMapper;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class User {

    private static User user;
    @MapToData(columnPrefix = "userName")
    private String userName;
    @MapToData(columnPrefix = "password")
    private String password;
    @MapToData(columnPrefix = "firstName")
    private String firstName;
    @MapToData(columnPrefix = "lastName")
    private String lastName;
    @MapToData(columnPrefix = "email")
    private String email;
    @MapToData(columnPrefix = "phoneNumber")
    private String phoneNumber;
    private List<String> shippingAddress;
    private boolean privilege;
    private shoppingCart shoppingCart;

    public Model.shoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public static User getUser() {
        return user;
    }

    public List<String> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(List<String> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public User loginUser(String userName, String password) {
        DbConnect connect = DbConnect.getInstance();
        String query = "{CALL authenticateUser(?,?)}";
        try {
            CallableStatement statement = connect.getConnection().prepareCall(query);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            if (set != null) {
                return null;
            } else {
                ResultSetMapper<User> resultSetMapper = new ReflectionResultSetMapper<User>(User.class);
                query = "{CALL logIn(?)}";
                statement = connect.getConnection().prepareCall(query);
                statement.setString(1, userName);
                User user = resultSetMapper.mapRow(statement.executeQuery());

                ResultSet set1 = connect.getData("Select shippingAddress from Shippingaddress where Shippingaddress.userName=" + userName + ";");
                while (set1.next()) {
                    shippingAddress.add(set1.getString(1));
                }
                this.user = user;
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPrivilege() {
        return privilege;
    }
}
