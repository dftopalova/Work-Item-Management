package com.KSDT.core.contracts;

import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;

import java.util.List;
import java.util.Map;

public interface WorkItemRepository {

    Map<String, Team> getTeams();

    List<WorkItem> getWorkItems();

    Map<String, Person> getPersons();

    List<Board> getBoards();

    void addTeam(String name, Team team);

    void addWorkItem(WorkItem workItem);

    void addPerson(String name, Person person);

    void addBoard(Board board);


}
