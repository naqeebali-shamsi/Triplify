package com.triplify.app.group.model;

import java.util.ArrayList;
import java.util.List;

public class GroupDetailsDBMock implements IGroupPersistence{

    @Override
    public StorageGroup load(GroupDetails groupDetails) {

        if(groupDetails!=null){
            groupDetails.setGroupName("G1");
            groupDetails.setTripStartDate("12-12-2022");
            groupDetails.setTripEndDate("12-14-2022");
            groupDetails.setDestination("G11");
            groupDetails.setGroupDescription("G11Description");
            groupDetails.setTripType("Public");
            return StorageGroup.SUCCESS;
        }else{
            return StorageGroup.STORAGE_FAILURE;
        }
    }

    @Override
    public StorageGroup makeGroupList(GroupDetails groupDetails) {
        if(groupDetails == null){
            return StorageGroup.STORAGE_FAILURE;
        }else{
            List<GroupDetails> groupDetailsList = new ArrayList<>();
            groupDetailsList.add(groupDetails);
            if(groupDetailsList.get(0).equals(groupDetails)){
                return StorageGroup.SUCCESS;
            }else{
                return StorageGroup.STORAGE_FAILURE;
            }
        }
    }
}
