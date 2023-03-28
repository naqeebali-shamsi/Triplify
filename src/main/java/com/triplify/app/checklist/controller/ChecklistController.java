package com.triplify.app.checklist.controller;

import com.triplify.app.checklist.database.ChecklistQueryBuilder;
import com.triplify.app.checklist.model.Checklist;
import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.triplify.app.checklist.database.ChecklistDatabaseConstant.*;

@RestController
@RequestMapping(path = "api/v1/groups")
@CrossOrigin

public class ChecklistController implements IChecklistController {
    @PostMapping("/addchecklist")
    @Override
    public Map<String, Object> postChecklist(@RequestParam("group_id") long group_id,
                                             @RequestParam("checklist_name") String checklist_name,
                                             @RequestParam("checklisted") boolean checklisted)
    {
        Map<String,Object> response = new HashMap<>();
        Checklist checklist = new Checklist();
        checklist.setGroup_id(group_id);
        checklist.setChecklist_name(checklist_name);
        checklist.setChecklisted(checklisted);
        try {
            Connection connection =
                    DatabaseConnection.getInstance().getDatabaseConnection();
            ChecklistQueryBuilder checklistQueryBuilder = new ChecklistQueryBuilder();
            final int rowInserted =
                    checklistQueryBuilder.insertChecklistQuery(checklist, connection);
            response.put("SUCCESS",true);
            response.put("MESSAGE","Checklist is added successfully");
            if (connection != null) {
                connection.close();
            }
        }  catch (SQLException e) {
            response.put("SUCCESS",false);
            response.put("MESSAGE","Checklist is not added!!");
            throw new RuntimeException(e);
        } catch (DatabaseExceptionHandler e) {
            response.put("SUCCESS",false);
            response.put("MESSAGE","Checklist is not added!!");
            throw new RuntimeException(e);
        }
        return response;
    }

    @GetMapping("/showchecklist")
    @Override
    public List<Checklist> getChecklist(@RequestParam("group_id") long groupid) {

        List<Checklist> checklists = new ArrayList<>();
        Map<String, Object> responseObject = new HashMap<>();

        try{
            Connection connection =
                    DatabaseConnection.getInstance().getDatabaseConnection();
            ResultSet userChecklistResultSet =
                    connection.createStatement().executeQuery("select * from checklist where group_id = "
                            + groupid + ";");

            while (userChecklistResultSet.next()) {
                Checklist checklist = new Checklist();
                System.out.println("karan" + userChecklistResultSet.getLong("" + checklist_groupid));
                checklist.setGroup_id(userChecklistResultSet.getLong("" + checklist_groupid));
                checklist.setChecklist_name(userChecklistResultSet.getString("" + checklist_checklist));
                checklist.setChecklisted(userChecklistResultSet.getBoolean("" + checklist_checklisted));

                checklists.add(checklist);
            }

            responseObject.put("SUCCESS",true);
            responseObject.put("MESSAGE","Successfully checklists retrieved!!");
            responseObject.put("checkList",checklists);

        }  catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DatabaseExceptionHandler e) {
            throw new RuntimeException(e);
        }

        return checklists;
    }

    @PatchMapping("/addchecklist")
    @Override
    public Map<String, Object> patchChecklist(@RequestParam("group_id") long group_id,
                                             @RequestParam("checklist_name") String checklist_name,
                                             @RequestParam("checklisted") boolean checklisted)
    {
        Map<String,Object> response = new HashMap<>();
        Checklist checklist = new Checklist();
        checklist.setGroup_id(group_id);
        checklist.setChecklist_name(checklist_name);
        checklist.setChecklisted(checklisted);
        try {
            Connection connection =
                    DatabaseConnection.getInstance().getDatabaseConnection();
            ChecklistQueryBuilder checklistQueryBuilder = new ChecklistQueryBuilder();
            final int rowUpdated =
                    checklistQueryBuilder.updateChecklistQuery(checklist, connection);
            response.put("SUCCESS",true);
            response.put("MESSAGE","Checklist is added successfully");
            if (connection != null) {
                connection.close();
            }
        }  catch (SQLException e) {
            response.put("SUCCESS",false);
            response.put("MESSAGE","Checklist is not added!!");
            throw new RuntimeException(e);
        } catch (DatabaseExceptionHandler e) {
            response.put("SUCCESS",false);
            response.put("MESSAGE","Checklist is not added!!");
            throw new RuntimeException(e);
        }
        return response;
    }
}
