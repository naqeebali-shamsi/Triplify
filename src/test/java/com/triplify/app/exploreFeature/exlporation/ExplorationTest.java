package com.triplify.app.exploreFeature.exlporation;

import com.triplify.app.group.controller.GroupAddMemberController;
import com.triplify.app.group.controller.GroupController;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.group.model.GroupDetails;
import com.triplify.app.group.model.GroupMembersDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

@DisplayName("Exploration class methods Testing()!!")
public class ExplorationTest {

    @Test
    @DisplayName("Valid Search String Testing()")
    public void validateSearchLocationStringTest(){
        String searchLocation = "Australia";
        final boolean expectedAnswer = true;
        final boolean isSearchLocationValid =
                (searchLocation != null) && (!searchLocation.trim().isEmpty());
        Assertions.assertEquals(expectedAnswer,isSearchLocationValid);
    }

    @Test
    @DisplayName("Is Null or Empty Search String Testing()")
    public void isNullOrEmptyValidateSearchLocationStringTest(){
        String searchLocation = "";
        final boolean expectedAnswer = false;
        final boolean isSearchLocationValid =
                (searchLocation != null) && (!searchLocation.trim().isEmpty());
        Assertions.assertEquals(expectedAnswer,isSearchLocationValid);
    }

    @Test
    @DisplayName("Getting All group Details Testing()")
    public void getGroupDetailsTest(){
        List<GroupDetails> groupDetailsList = new ArrayList<>();
        try {
            groupDetailsList = new GroupController().getAllGroupDetails();
        } catch (DatabaseExceptionHandler e) {
            throw new RuntimeException(e);
        }
        if(groupDetailsList.size() > 0){
            Assertions.assertTrue(true,"Yes group Details is there!!");
        }else{
            Assertions.assertFalse(false,"No group Details is not there!!");
        }
    }

    @Test
    @DisplayName("Getting All group Details Testing()")
    public void getGroupMembersDetailsListTest(){
        List<GroupMembersDetails> groupMembersDetailsList = new ArrayList<>();
        try {
            groupMembersDetailsList = new GroupAddMemberController().getAllMembers();
        } catch (DatabaseExceptionHandler e) {
            throw new RuntimeException(e);
        }
        if(groupMembersDetailsList.size() > 0){
            Assertions.assertTrue(true,"Yes group Details is there!!");
        }else{
            Assertions.assertFalse(false,"No group Details is not there!!");
        }
    }

}
