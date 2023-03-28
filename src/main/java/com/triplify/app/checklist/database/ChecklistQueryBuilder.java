package com.triplify.app.checklist.database;

import com.triplify.app.checklist.model.Checklist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.triplify.app.checklist.database.ChecklistDatabaseConstant.*;

public class ChecklistQueryBuilder implements IChecklistQueryBuilder{
    @Override
    public int insertChecklistQuery(final Checklist checklist, Connection connection){
        String query = "INSERT INTO "+ checklist_table + "(" +
                checklist_groupid + ", " +
                checklist_checklist + ", " +
                checklist_checklisted + ") " +
                "VALUES (?,?,?);";
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setLong(1, checklist.getGroup_id());
            pstmt.setString(2, checklist.getChecklist_name());
            pstmt.setBoolean(3, checklist.isChecklisted());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateChecklistQuery(final Checklist checklist, Connection connection){

        String query = "update "+ checklist_table + " set " +
                checklist_checklisted + " = ?" + " where " +
                checklist_checklist + " = ?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setBoolean(1, checklist.isChecklisted());
            pstmt.setString(2, checklist.getChecklist_name());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
