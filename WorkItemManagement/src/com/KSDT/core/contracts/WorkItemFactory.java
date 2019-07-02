package com.KSDT.core.contracts;


import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;

public interface WorkItemFactory {

//    TODO Change code down below

    Team createTeam(String name);

    Person createPerson(String name);

    WorkItem createBug(String title, StatusType status, String stepsToReproduce, SeverityType severity);

    Board createBoard(String name);
//
//    Table createTable(String model, String materialType, double price, double height, double length, double width);
//
//    Chair createChair(String type, String model, String material, double price, double height, int numberOfLegs);

}
