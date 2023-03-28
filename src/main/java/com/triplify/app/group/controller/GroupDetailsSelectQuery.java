package com.triplify.app.group.controller;

import static com.triplify.app.group.database.GroupDetailsDatabaseConstant.GROUP_DETAILS_TABLE;

public class GroupDetailsSelectQuery implements IGroupDetailsSelectQuery {
    @Override
    public String selectQueryForGroup() {
        return "SELECT * from " + GROUP_DETAILS_TABLE;
    }
}
