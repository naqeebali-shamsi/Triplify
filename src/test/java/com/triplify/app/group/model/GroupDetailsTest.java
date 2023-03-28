package com.triplify.app.group.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class GroupDetailsTest {

    @Test
    @DisplayName("Testing group details!!")
    void nullCreateAllGroupDetailsList() {
        IGroupPersistence iGroupPersistence = new GroupDetailsDBMock();
        IGroupPersistence.StorageGroup result = iGroupPersistence.makeGroupList(new GroupDetails());
        Assertions.assertEquals(IGroupPersistence.StorageGroup.SUCCESS,result);
    }

    @Test
    @DisplayName("Testing group details!!")
    void CreateAllGroupDetailsList() {
        List<GroupDetails> groupDetailsList = new ArrayList<>();
        GroupDetails groupDetails = new GroupDetails();
        groupDetails.setGroupName("G1");
        groupDetails.setTripStartDate("12-12-2022");
        groupDetails.setTripEndDate("12-14-2022");
        groupDetails.setDestination("G11");
        groupDetails.setGroupDescription("G11Description");
        groupDetails.setTripType("Public");
        groupDetailsList.add(groupDetails);
        IGroupPersistence iGroupPersistence = new GroupDetailsDBMock();
        IGroupPersistence.StorageGroup result = iGroupPersistence.makeGroupList(groupDetails);
        Assertions.assertEquals(IGroupPersistence.StorageGroup.SUCCESS,result);
    }

    @Test
    @DisplayName("Testing empty group creation response!!")
    void emptyCreateGroupResponse() {
        try{
            GroupDetails groupDetails = new GroupDetails();
            IGroupPersistence iGroupPersistence = new GroupDetailsDBMock();
            IGroupPersistence.StorageGroup result = iGroupPersistence.load(groupDetails);
            Assertions.assertEquals(IGroupPersistence.StorageGroup.SUCCESS, result);
        }catch (Exception e){
            Assertions.fail("Save failed, threw exception:\" + e.getMessage()");
        }
    }

    @Test
    @DisplayName("Testing actual create group response!!")
    void createGroupResponse() {
        GroupDetails groupDetails = new GroupDetails();
        GroupDetailsDBMock groupDetailsDBMock = new GroupDetailsDBMock();
        IGroupPersistence.StorageGroup result = groupDetailsDBMock.load(groupDetails);
        Assertions.assertEquals(IGroupPersistence.StorageGroup.SUCCESS, result);
        Assertions.assertEquals("G1", groupDetails.getGroupName());
        Assertions.assertEquals("12-12-2022", groupDetails.getTripStartDate());
        Assertions.assertEquals("12-14-2022", groupDetails.getTripEndDate());
        Assertions.assertEquals("G11", groupDetails.getDestination());
        Assertions.assertEquals("G11Description", groupDetails.getGroupDescription());
        Assertions.assertEquals("Public", groupDetails.getTripType());
    }
}