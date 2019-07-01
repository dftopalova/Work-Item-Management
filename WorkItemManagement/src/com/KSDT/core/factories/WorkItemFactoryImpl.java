package com.KSDT.core.factories;

import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;

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
    public WorkItem createBug(String title, StatusType status, String stepsToReproduce, SeverityType severity) {
        return new BugImpl(title, status, stepsToReproduce, severity);
    }


}
