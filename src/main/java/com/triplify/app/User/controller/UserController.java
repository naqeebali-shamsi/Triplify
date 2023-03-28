package com.triplify.app.User.controller;

import com.triplify.app.User.database.UpdateUserTableQueryBuilder;
import com.triplify.app.User.database.UserDetailsQueryBuilder;
import com.triplify.app.User.database.UserRegistrationQueryBuilder;
import com.triplify.app.User.model.UserTable;
import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.triplify.app.User.database.UserDatabaseConstant.*;

@RestController
@CrossOrigin
public class UserController {

    UserRegistrationQueryBuilder userRegistrationQueryBuild = new UserRegistrationQueryBuilder();

    private UserTable createUserTable() {
        IUserCreationFactory iGroupCreationFactory = UserFactory.factorySingleton();
        return iGroupCreationFactory.makeUserTable();
    }

    @PostMapping("updateProfile")
    public Map<String, Object> updateProfile(@RequestParam("username") String username,
                                             @RequestParam("firstname") String firstname,
                                             @RequestParam("lastname") String lastname,
                                             @RequestParam("emailAddress") String emailAddress,
                                             @RequestParam("avatar") MultipartFile imageFile) throws DatabaseExceptionHandler, SQLException, IOException {
        UserTable userTable = createUserTable();
        List<UserTable> userTableList = getAllUsers();
        Map<String, Object> resultResponse = new HashMap<>();

        for(int i = 0 ; i < userTableList.size() ; i++){
            if(userTableList.get(i).getUsername().equalsIgnoreCase(username)){
                userTable.setId(userTableList.get(i).getId());
                userTable.setFirstname(firstname);
                userTable.setLastname(lastname);
                userTable.setEmailAddress(emailAddress);
                userTable.setPassword(userTableList.get(i).getPassword());
                userTable.setDob(userTableList.get(i).getDob());
                userTable.setProfPic(imageFile.getBytes());
                userTable.setLoggedIn(true);
                userTable.setUsername(username);
            }
        }

        Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();
        UpdateUserTableQueryBuilder updateUserTableQueryBuilder = new UpdateUserTableQueryBuilder();

        final int rowUpdated = updateUserTableQueryBuilder.updateQuery(userTable, connection);

        if( rowUpdated > 0){
            resultResponse.put("SUCCESS",true);
            resultResponse.put("MESSAGE","SUCCESSFULLY Updated Profile");
            return resultResponse;
        } else {
            resultResponse.put("SUCCESS",false);
            resultResponse.put("MESSAGE","Not updated properly!!");
            return resultResponse;
        }
    }

    @PostMapping("users/login")
    public Map<String, Object> loginUser(@RequestParam("emailAddress") String emailAddress,
                            @RequestParam("password") String password) throws DatabaseExceptionHandler {
        List<UserTable> listOfUsers = getAllUsers();
        Map<String, Object> response = new HashMap<>();
        for(UserTable user : listOfUsers){
            if(user.getEmailAddress().equalsIgnoreCase(emailAddress) &&
                user.getPassword().equalsIgnoreCase(password)){
                user.setLoggedIn(true);
                response.put("SUCCESS", true);
                response.put("USERNAME", user.getUsername());
                response.put("MESSAGE", "Login successful");
            }
            else{
                response.put("SUCCESS", false);
                response.put("MESSAGE", "Incorrect username or password");
            }
        }
        return response;
    }

    @PostMapping("/usersDetails")
    public Map<String, Object> getUserDetails(@RequestParam("username") String username) throws DatabaseExceptionHandler {
        Map<String, Object> response = new HashMap<>();
        List<UserTable> userTableList = new ArrayList<>();

        UserTable userTable = createUserTable();

        Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();
        try{
            UserDetailsQueryBuilder userDetailsQueryBuilder = new UserDetailsQueryBuilder();
            final String selectUserDetailsQuery = userDetailsQueryBuilder.selectQuery() + "\"" + username + "\"";
            ResultSet resultSet = connection.createStatement().executeQuery(selectUserDetailsQuery);

            while (resultSet.next()){
                userTable.setId(resultSet.getLong(""+user_table_id));
                userTable.setFirstname(resultSet.getString(""+user_table_first_name));
                userTable.setLastname(resultSet.getString(""+user_table_last_name));
                userTable.setEmailAddress(resultSet.getString(""+user_table_email_address));
                if(resultSet.getBytes(""+user_image) != null){
                    userTable.setProfPic(resultSet.getBytes(""+user_image));
                }else{
                    userTable.setProfPic(new byte[]{});
                }

                userTableList.add(userTable);
            }

            response.put("SUCCESS", true);
            response.put("MESSAGE", "Successfully user details sent");
            response.put("UserDetails",userTableList);

        }catch (Exception e){
            System.out.println(e.getMessage());
            response.put("SUCCESS", false);
            response.put("MESSAGE", "User details not there!!");
        }

        return response;
    }

    @GetMapping("/users")
    public List<UserTable> getAllUsers() throws DatabaseExceptionHandler {
        Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();
        List<UserTable> listOfUserTables = new ArrayList<>();

        try {
            System.out.println(connection.getCatalog());

            ResultSet resultSet =
                    connection.createStatement().executeQuery("select * from user_table");

            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String emailAddress = resultSet.getString("email_address");
                String password = resultSet.getString("password");
                boolean isLoggedIn = resultSet.getBoolean("is_logged_in");
                Blob image = resultSet.getBlob("image");

                UserTable userTable = new UserTable(id,username,firstname,lastname,emailAddress,password,isLoggedIn, image);
                listOfUserTables.add(userTable);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfUserTables;
    }

    @PostMapping(value = "/users/register", consumes = {"multipart/form-data"})
    public Map<String, Object> register( @RequestParam("username") String username,
                            @RequestParam("first_name") String firstname,
                            @RequestParam("last_name") String lastname,
                            @RequestParam("email") String emailAddress,
                            @RequestParam("password") String password,
                            @RequestParam("dob") String dob,
                            @RequestParam("avatar") MultipartFile imageFile) throws DatabaseExceptionHandler{

        UserTable userTable = new UserTable();
        userTable.setFirstname(firstname);
        userTable.setLastname(lastname);
        userTable.setEmailAddress(emailAddress);
        userTable.setPassword(password);
        userTable.setUsername(username);
        userTable.setLoggedIn(false);
        userTable.setUsername(username);
        userTable.setDob(dob);
        byte[] img = new byte[0];
        try {
            img = imageFile.getBytes();
            userTable.setProfPic(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<UserTable> listOfUsers = getAllUsers();
        Map<String, Object> response = new HashMap<>();
        for(UserTable user : listOfUsers){
            if(user.getUsername().equals(userTable.getUsername())){
                response.put("MESSAGE", "User already exists!");
                response.put("REDIRECT", true);
                response.put("USERNAME", username);
                response.put("SUCCESS", false);
            }
        }

        try(final Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();
            final Statement statement = connection.createStatement()){

            final int rowInserted = userRegistrationQueryBuild.insertQuery(userTable, connection);
            if(rowInserted > 0){
                System.out.println("User record inserted into DB");
            }
            response.put("SUCCESS", true);
            response.put("MESSAGE", "registration successful");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            response.put("SUCCESS", false);
            response.put("MESSAGE", "Registration failed");
        }
        return response;
    }
}
