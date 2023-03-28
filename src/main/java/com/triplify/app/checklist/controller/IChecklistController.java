package com.triplify.app.checklist.controller;

import com.triplify.app.checklist.model.Checklist;

import java.util.List;
import java.util.Map;

public interface IChecklistController {
    Map<String, Object> postChecklist(long group_id, String checklist_name, boolean checklisted);
    List<Checklist> getChecklist(long groupid);

    Map<String, Object> patchChecklist(long group_id, String checklist_name, boolean checklisted);
}
