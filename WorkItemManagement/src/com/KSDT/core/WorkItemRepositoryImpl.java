package com.KSDT.core;

import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkItemRepositoryImpl implements WorkItemRepository {
    private Map<String, Team> teams;
    private Map<String, Person> persons;
    private Map<String, WorkItem> workItems;
    private List<Board> boards;

    public WorkItemRepositoryImpl() {
        this.teams = new HashMap<>();
        this.workItems = new HashMap<>();
        this.persons = new HashMap<>();
        this.boards = new ArrayList<>();
    }

    @Override
    public Map<String, Team> getTeams() {
        return new HashMap<>(teams);
    }

    @Override
    public Map<String, WorkItem> getWorkItems() {
        return new HashMap<>(workItems);
    }

    @Override
    public Map<String, Person> getPersons() {
        return new HashMap<>(persons);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addTeam(String name, Team team) {
        this.teams.put(name, team);
    }

    @Override
    public void addPerson(String name, Person person) {
        this.persons.put(name, person);
    }

    @Override
    public void addBoard( Board board) {
        this.boards.add(board);
    }

    @Override
    public void addWorkItem(String name, WorkItem workItem) {
        this.workItems.put(name, workItem);
    }


}
