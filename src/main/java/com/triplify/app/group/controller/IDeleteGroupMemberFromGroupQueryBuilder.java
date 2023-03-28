package com.triplify.app.group.controller;

public interface IDeleteGroupMemberFromGroupQueryBuilder {
    String deleteMember(String username, Long group_id);
}
