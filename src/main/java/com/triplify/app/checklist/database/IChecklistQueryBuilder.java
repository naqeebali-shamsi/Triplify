package com.triplify.app.checklist.database;

import com.triplify.app.checklist.model.Checklist;

import java.sql.Connection;

public interface IChecklistQueryBuilder {
    int insertChecklistQuery(final Checklist checklist, Connection connection);
    int updateChecklistQuery(final Checklist checklist, Connection connection);
}
