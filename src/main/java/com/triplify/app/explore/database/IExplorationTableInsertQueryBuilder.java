package com.triplify.app.explore.database;

import com.triplify.app.explore.model.Exploration;

public interface IExplorationTableInsertQueryBuilder {
    String explorationTableInsertQuery(Exploration exploration);
}
