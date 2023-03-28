package com.triplify.app.group.controller;

import com.triplify.app.group.model.GroupDetails;

public interface IGroupInsertQueryBuilder {
    String insertGroupQuery(GroupDetails groupDetails);
}
