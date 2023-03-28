package com.triplify.app.explore.controller;

import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.explore.exception.ExplorationException;
import com.triplify.app.explore.model.Exploration;
import com.triplify.app.group.model.GroupDetails;
import com.triplify.app.group.model.GroupMembersDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ExplorationController implements IExplorationController {
    private final Exploration exploration;

    public ExplorationController(){
        exploration = createExploration();
    }

    public ExplorationController(Exploration exploration){
        this.exploration = exploration;
    }

    /**
     *      Abstract Factory Design pattern to create Exploration Object
     * */
    private Exploration createExploration() {
        IExplorationFactory iExplorationFactory =
                ExplorationFactory.factorySingleton();
        Exploration exploration = iExplorationFactory.makeExploration();
        return exploration;
    }

    @PostMapping("/search")
    @Override
    public List<Exploration> searchGroups(@RequestParam("location") String location)
            throws ExplorationException, DatabaseExceptionHandler {

        List<GroupDetails> groupDetailsList;
        List<GroupMembersDetails> groupMembersDetailsList;
        List<Exploration> explorationList;

        if(!exploration.validateSearchLocationString(location)){
            return null;
        }else{
            groupDetailsList = exploration.getGroupDetailsList();
            groupMembersDetailsList = exploration.getGroupMembersDetailsList();
            explorationList = calculateGroupsByLocation(groupDetailsList,groupMembersDetailsList,location);

            for(int i = 0 ; i < explorationList.size() ; i++){
                exploration.insertQueryInExplorationTable(explorationList.get(i));
            }
        }
        return explorationList;
    }

    private List<Exploration> calculateGroupsByLocation(List<GroupDetails> groupDetailsList,
                                                        List<GroupMembersDetails> groupMembersDetailsList,
                                                        String location) {
        int countMembersInGroup = 0;
        List<Exploration> explorationList = new ArrayList<>();

        for(int i = 0 ; i < groupDetailsList.size() ; i++){

            Exploration exploration = createExploration();

            if(groupDetailsList.get(i).getDestination().equalsIgnoreCase(location)){
                exploration.setGroupName(groupDetailsList.get(i).getGroupName());
                exploration.setPlaceDescription(groupDetailsList.get(i).getGroupDescription());

                countMembersInGroup = 0;
                for(int j = 0 ; j < groupMembersDetailsList.size() ; j++){
                    if(groupMembersDetailsList.get(j).getGroupName()
                            .equalsIgnoreCase(groupDetailsList.get(i).getGroupName())){
                        countMembersInGroup++;
                    }
                }

                exploration.setNumberOfMembers(countMembersInGroup);
                exploration.setGroupId(groupDetailsList.get(i).getId());
                explorationList.add(exploration);
            }
        }
        return explorationList;
    }
}
