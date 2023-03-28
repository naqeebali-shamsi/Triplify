package com.triplify.app.explore.controller;

import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.explore.exception.ExplorationException;
import com.triplify.app.explore.model.Exploration;

import java.sql.SQLException;
import java.util.List;

public interface IExplorationController {

    List<Exploration> searchGroups(final String location) throws ExplorationException, SQLException, DatabaseExceptionHandler;
}
