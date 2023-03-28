package com.triplify.app.location.controller;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.utils.Seeker;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.triplify.app.location.database.LocationDatabaseConstant.*;

@RestController
@CrossOrigin
public class LocationController {
    @PatchMapping("/save/location")
    public Map<String, Boolean> saveUserLocation (@RequestParam("username") String username,
                                                  @RequestParam("latitude") double latitude,
                                                  @RequestParam("longitude") double longitude) {

        Map<String, Boolean> response = new HashMap<>();
        String query = "INSERT INTO " + USER_LOCATION_TABLE +
                " (" + USER_LOCATION_USERNAME + ","
                + USER_LOCATION_LATITUDE + ","
                + USER_LOCATION_LONGITUDE + ")"
                + " VALUES (?,?,?)"
                + " ON DUPLICATE KEY UPDATE "
                + USER_LOCATION_USERNAME + "=?";

        try{
            Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setDouble(2,latitude);
            preparedStatement.setDouble(3,longitude);
            preparedStatement.setString(4,username);
            preparedStatement.execute();
            response.put("SUCCESS", true);
        } catch (DatabaseExceptionHandler e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @GetMapping("/fetch/location/{group_id}")
    public List<Map<String, Object>> fetchGroupLocation(@PathVariable("group_id") Long group_id,
                                                        @RequestParam("username") String username){
        Seeker seeker = new Seeker();
        List<Map<String, Object>> allMembers = seeker.findUserLocation(seeker.findMembersInGroup(group_id));
        List<Map<String, Object>> filteredMembers = new ArrayList<>();

        for(Map<String, Object> member: allMembers){
            if(!member.get("username").equals(username)){
                filteredMembers.add(member);
            }
        }
        return filteredMembers;
    }
}
