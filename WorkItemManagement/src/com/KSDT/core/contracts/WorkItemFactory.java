package com.KSDT.core.contracts;


import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;

public interface WorkItemFactory {

//    TODO Change code down below

    Team createTeam(String name);

    Person createPerson(String name);

    WorkItem createBug(String title, StatusType status, String description, String stepsToReproduce, SeverityType severity);

    WorkItem createStory(String title, StatusType status, String description, PriorityType priority, SizeType size, Person assignee);

    WorkItem createFeedback(String title, StatusType status, String description, int rating);

    Board createBoard(String name);




}
