package com.KSDT.core.contracts;

import com.KSDT.models.contracts.*;

import java.util.List;
import java.util.Map;

public interface WorkItemRepository {

    Map<Integer, WorkItem> getAllItems();

    Map<String, Team> getTeams();

//    List<WorkItem> getWorkItems();

    Map<Integer, Assignable> getAssignableItems();

    Map<Integer, Priorityable> getPrioritizableItems();

    Map<Integer, Bug> getBugMap();

    Map<Integer, Story> getStoryMap();

    Map<Integer, Feedback> getFeedbackMap();

    Map<String, Person> getPersons();

    List<Board> getBoardsList();

    void addTeam(String name, Team team);

//    void addWorkItem(WorkItem workItem);

    void addPerson(String name, Person person);

    void addBoard(Board board);

    void addBug(Bug bug);

    void addStory(Story story);

    void addFeedback(Feedback feedback);

    <K, V> K getWorkItemID(Map<K, V> map, V value);
}
