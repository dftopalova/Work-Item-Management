package com.KSDT.core;

import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkItemRepositoryImpl implements WorkItemRepository {
    private Map<String, Team> teams;
    private Map<String, Person> persons;
//    private List<WorkItem> workItems;
    private Map<Integer, Bug> bugMap;
    private Map<Integer, Feedback> feedbackMap;
    private Map<Integer, Story> storyMap;
    private List<Board> boards;
    private int workItemID = 0;

    public WorkItemRepositoryImpl() {
        this.teams = new HashMap<>();
//        this.workItems = new ArrayList<>();
        this.bugMap = new HashMap<>();
        this.storyMap = new HashMap<>();
        this.feedbackMap = new HashMap<>();
        this.persons = new HashMap<>();
        this.boards = new ArrayList<>();
    }

    @Override
    public Map<String, Team> getTeams() {
        return new HashMap<>(teams);
    }

    @Override
    public Map<Integer, Bug> getBugMap() {
        return new HashMap<>(bugMap);
    }

    @Override
    public Map<Integer, Story> getStoryMap() {
        return new HashMap<>(storyMap);
    }

    @Override
    public Map<Integer, Feedback> getFeedbackMap() {
        return new HashMap<>(feedbackMap);
    }

//    @Override
//    public List<WorkItem> getWorkItems() {
//        return new ArrayList<>(workItems);
//    }

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
        List<String> assignable = new ArrayList<>();
        assignable = bugMap.toString.

        this.persons.put(name, person);
    }

    @Override
    public void addBoard( Board board) {
        this.boards.add(board);
    }

    @Override
    public void addBug(Bug bug) {
        this.bugMap.put(getCurrentID(), bug);
        incrementIDCounter();
    }

    @Override
    public void addStory(Story story) {
        this.storyMap.put(getCurrentID(), story);
        incrementIDCounter();
    }

    @Override
    public void addFeedback(Feedback feedback) {
        this.feedbackMap.put(getCurrentID(), feedback);
        incrementIDCounter();
    }

    private void incrementIDCounter() {
        workItemID++;
    }

    private int getCurrentID() {
        return workItemID;
    }

    public <K, V> K getWorkItemID(Map<K, V> map, V value) {
        return map.entrySet().stream()
                .filter(item -> item
                .getValue().equals(value))
                .map(Map.Entry::getKey)
                .findFirst().get();

    }


//    @Override
//    public void addWorkItem(WorkItem workItem) {
//        this.workItems.add(workItem);
//    }

}
