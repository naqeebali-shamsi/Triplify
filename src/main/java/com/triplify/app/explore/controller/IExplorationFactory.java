package com.triplify.app.explore.controller;

import com.triplify.app.explore.model.Exploration;
import org.springframework.stereotype.Service;

@Service
public interface IExplorationFactory {
    Exploration makeExploration();
}
