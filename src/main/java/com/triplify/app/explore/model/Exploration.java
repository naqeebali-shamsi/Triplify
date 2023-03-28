package com.triplify.app.explore.model;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.explore.database.ExplorationTableInsertQueryBuilder;
import com.triplify.app.explore.exception.ExplorationException;
import com.triplify.app.group.controller.GroupAddMemberController;
import com.triplify.app.group.controller.GroupController;
import com.triplify.app.group.model.GroupDetails;
import com.triplify.app.group.model.GroupMembersDetails;

import javax.persistence.Entity;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Entity
public class Exploration {
    private Long explore_id;
    private String groupName;
    private String placeDescription;
    private int numberOfMembers;
    private String groupType;
    private Long groupId;

    public Exploration(){

    }

    public Exploration(String groupName, String placeDescription, int numberOfMembers, String groupType, Long groupId) {
        this.groupName = groupName;
        this.placeDescription = placeDescription;
        this.numberOfMembers = numberOfMembers;
        this.groupType = groupType;
        this.groupId = groupId;
    }

    public Exploration(Long explore_id, String groupName, String placeDescription, int numberOfMembers, String groupType, Long groupId) {
        this.explore_id = explore_id;
        this.groupName = groupName;
        this.placeDescription = placeDescription;
        this.numberOfMembers = numberOfMembers;
        this.groupType = groupType;
        this.groupId = groupId;
    }

    public Long getExplore_id() {
        return explore_id;
    }

    public void setExplore_id(Long explore_id) {
        this.explore_id = explore_id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Exploration{" +
                "explore_id=" + explore_id +
                ", groupName='" + groupName + '\'' +
                ", placeDescription='" + placeDescription + '\'' +
                ", numberOfMembers=" + numberOfMembers +
                ", groupType='" + groupType + '\'' +
                ", groupId=" + groupId +
                '}';
    }

    public boolean validateSearchLocationString(final String location)
        throws ExplorationException {
        final boolean isStringValid = (location != null) &&
                (!location.trim().isEmpty());
        if(!isStringValid){
            throw new ExplorationException("Invalid location was searched there!!");
        }else{
            return true;
        }
    }

    public List<GroupDetails> getGroupDetailsList() throws DatabaseExceptionHandler {
        return new GroupController().getAllGroupDetails();
    }

    public List<GroupMembersDetails> getGroupMembersDetailsList() throws DatabaseExceptionHandler {
        return new GroupAddMemberController().getAllMembers();
    }

    public void insertQueryInExplorationTable(Exploration exploration) throws DatabaseExceptionHandler {
        try (final Connection connection = makeDBConnection();
             final Statement statement = connection.createStatement()){

            ExplorationTableInsertQueryBuilder explorationTableInsertQueryBuilder
                    = new ExplorationTableInsertQueryBuilder();

            final String insertExplorationTableQuery =
                    explorationTableInsertQueryBuilder.explorationTableInsertQuery(exploration);

            final int rowInserted =
                    statement.executeUpdate(insertExplorationTableQuery, Statement.RETURN_GENERATED_KEYS);

            if (rowInserted > 0) {
                System.out.println("Successfully retrieved the groups going for this trip");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection makeDBConnection() throws DatabaseExceptionHandler {
        return DatabaseConnection.getInstance().getDatabaseConnection();
    }
}
