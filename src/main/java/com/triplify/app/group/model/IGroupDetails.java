package com.triplify.app.group.model;

import com.triplify.app.database.DatabaseExceptionHandler;

import java.util.List;

public interface IGroupDetails {
    List<GroupDetails> createAllGroupDetailsList() throws DatabaseExceptionHandler;
}
