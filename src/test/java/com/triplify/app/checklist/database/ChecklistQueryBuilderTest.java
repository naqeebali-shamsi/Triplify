package com.triplify.app.checklist.database;

import com.triplify.app.checklist.model.Checklist;
import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import java.util.Random;
@DisplayName("group checklist Testing!!")
public class ChecklistQueryBuilderTest {
    @Test
    @DisplayName("checklist query testing!!")
    public void insertChecklistQuery() {
        Random rand = new Random();
        int upperbound = 100;

        final long groupid = 4;
        final String checklist_name = "umbrella";
        final boolean checklisted = false;
        final long checklist_id = rand.nextLong(upperbound);

        Checklist checklist = new Checklist();
        checklist.setGroup_id(groupid);
        checklist.setChecklist_name(checklist_name);
        checklist.setChecklisted(checklisted);
        checklist.setchecklist_id(checklist_id);

        ChecklistQueryBuilder checklistQueryBuilder = new ChecklistQueryBuilder();

        try {
            Connection connection =
                    DatabaseConnection.getInstance().getDatabaseConnection();
            final String expectedInsertResult = String.valueOf(checklistQueryBuilder.insertChecklistQuery(checklist, connection));
            final String actualInsertResult = "1";

            System.out.println(expectedInsertResult);
            System.out.println(actualInsertResult);
            Assertions.assertEquals(expectedInsertResult,actualInsertResult,"Incorrect Insert Query Has been generated!!");
        } catch(DatabaseExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }
}
