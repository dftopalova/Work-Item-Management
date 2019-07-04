package com.KSDT.core.factories;

import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;
import com.KSDT.models.items.FeedbackImpl;
import com.KSDT.models.items.StoryImpl;

public class WorkItemFactoryImpl implements WorkItemFactory {


    @Override
    public Team createTeam(String name) {
        return new TeamImpl(name);
    }

    @Override
    public Person createPerson(String name) {
        return new PersonImpl(name);
    }

    @Override
    public WorkItem createBug(String title, StatusType status, String description, String stepsToReproduce,PriorityType priority, SeverityType severity) {
        return new BugImpl(title, status, description, stepsToReproduce,priority, severity);
    }

    @Override
    public WorkItem createStory(String title, StatusType status, String description, PriorityType priority, SizeType size) {
        return new StoryImpl(title,status,description,priority,size);
    }

    @Override
    public WorkItem createFeedback(String title, StatusType status, String description, int rating) {
        return new FeedbackImpl(title,status,description,rating);
    }

    @Override
    public Board createBoard(String name) {
        return new BoardImpl(name);
    }


}
