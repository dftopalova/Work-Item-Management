package com.KSDT.core;

import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;

import java.util.HashMap;
import java.util.Map;

public class WorkItemRepositoryImpl implements WorkItemRepository {
    private Map<String, Team> teams;
    private Map<String, Person> persons;
    private Map<String, WorkItem> workItems;

    public WorkItemRepositoryImpl() {
        this.teams = new HashMap<>();
        this.workItems = new HashMap<>();
        this.persons = new HashMap<>();
    }

//    TODO type methods connected with persons - add remove etc

    @Override
    public Map<String, Team> getTeams() {
        return new HashMap<>(teams);
    }

    @Override
    public Map<String, WorkItem> getWorkItems() {
        return new HashMap<>(workItems);
    }

    @Override
    public void addTeam(String name, Team team) {
        this.teams.put(name, team);
    }

    @Override
    public void addWorkItem(String name, WorkItem workItem) {
        this.workItems.put(name, workItem);
    }
}
