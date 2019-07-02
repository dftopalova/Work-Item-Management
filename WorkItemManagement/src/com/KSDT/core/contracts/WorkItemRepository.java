package com.KSDT.core.contracts;

import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;

import java.util.List;
import java.util.Map;

public interface WorkItemRepository {

    Map<String, Team> getTeams();

    Map<String, WorkItem> getWorkItems();

    Map<String, Person> getPersons();

    List<Board> getBoards();
//    Map<String, Board> getBoards();

    void addTeam(String name, Team team);

    void addWorkItem(String name, WorkItem workItem);

    void addPerson(String name, Person person);

    void addBoard(Board board);
//    void addBoard(String name, Board board);


//
}
