package com.KSDT.models.contracts;

import com.KSDT.models.enums.PriorityType;

public interface BasicItem extends WorkItem {

    Person getAssignee();

    PriorityType getPriority();

    void setAssignee(Person assignee);

    void changePriority(Person person, PriorityType newPriority);

}
