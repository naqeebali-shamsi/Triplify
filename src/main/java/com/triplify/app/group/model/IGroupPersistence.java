package com.triplify.app.group.model;

public interface IGroupPersistence {
    enum StorageGroup{
        STORAGE_FAILURE,
        SUCCESS
    }

    StorageGroup load(GroupDetails groupDetails);

    StorageGroup makeGroupList(GroupDetails groupDetails);
}
