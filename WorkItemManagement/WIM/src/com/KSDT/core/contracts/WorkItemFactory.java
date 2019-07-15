package com.KSDT.core.contracts;


import com.KSDT.models.contracts.*;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;

public interface WorkItemFactory {

    Team createTeam(String name);

    Person createPerson(String name);

    Bug createBug(String title, StatusType status, String description, String stepsToReproduce, PriorityType priority, SeverityType severity);

    Story createStory(String title, StatusType status, String description, PriorityType priority, SizeType size);

    Feedback createFeedback(String title, StatusType status, String description, int rating);

    Board createBoard(String name, Team ownerTeam);



}
