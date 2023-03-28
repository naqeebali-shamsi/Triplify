package com.triplify.app.group.database;

import com.triplify.app.group.model.GroupMembersDetails;

public interface IGroupMemberDetailsQueryBuilder {
    String groupMemberInsertQuery(GroupMembersDetails groupMembersDetails);
}
