package com.triplify.app.group.controller;

import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.group.model.GroupMembersDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class GroupAddMemberController {

    private GroupMembersDetails createGroupMemberDetails() {
        IGroupCreationFactory iGroupCreationFactory = GroupFactory.factorySingleton();
        return iGroupCreationFactory.makeGroupMemberDetails();
    }

    @GetMapping("/groups/allMembers")
    public List<GroupMembersDetails> getAllMembers() throws DatabaseExceptionHandler{
        GroupMembersDetails groupMembersDetails = createGroupMemberDetails();
        List<GroupMembersDetails> groupMembersDetailsList = groupMembersDetails.getAllMembersForGroup(groupMembersDetails);
        return groupMembersDetailsList;
    }

    @PostMapping("group/addMember")
    public Map<String, Object> addGroupMember(@RequestParam("username") String username) throws DatabaseExceptionHandler {
        GroupMembersDetails groupMembersDetails = createGroupMemberDetails();
        Map<String,Object> responseForAddMember = groupMembersDetails.addMemberInGroup(username);
        return responseForAddMember;
    }

}
