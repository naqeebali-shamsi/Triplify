package com.triplify.app.location.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class LocationServer {
    @PostMapping("/location")
    public List<Map<String, Object>> getLocation(){
        Map<String, Object> response = new HashMap<>();
        response.put("latitude","44.651070");
        response.put("longitude","-63.582687");
        response.put("username", "Naqeeb"); //TODO: Add usernames dynamically w.r.t group id
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(response);
        return list;
    }
}
