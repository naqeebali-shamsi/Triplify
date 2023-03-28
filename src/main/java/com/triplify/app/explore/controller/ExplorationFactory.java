package com.triplify.app.explore.controller;

import com.triplify.app.explore.model.Exploration;

public class ExplorationFactory implements IExplorationFactory{

    private static ExplorationFactory explorationFactory = null;

    public static IExplorationFactory factorySingleton(){
        if (explorationFactory == null){
            explorationFactory = new ExplorationFactory();
        }
        return explorationFactory;
    }

    @Override
    public Exploration makeExploration() {
        return new Exploration();
    }
}
