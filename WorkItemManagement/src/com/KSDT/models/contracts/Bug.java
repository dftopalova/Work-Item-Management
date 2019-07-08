package com.KSDT.models.contracts;

import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;

import java.util.List;

public interface Bug extends WorkItem {
    List<String> getStepsToReproduce();

    SeverityType getSeverity();

    Person getAssignee();

    PriorityType getPriority();

    void changePriority(Person person, PriorityType newPriority);

    void changeSeverity(Person person, SeverityType newSeverity);

}
