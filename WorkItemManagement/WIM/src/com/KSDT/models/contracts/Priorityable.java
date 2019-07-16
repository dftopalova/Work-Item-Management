package com.KSDT.models.contracts;

import com.KSDT.models.enums.PriorityType;

public interface Priorityable extends WorkItem {

    PriorityType getPriority();

    void changePriority(Person person, PriorityType newPriority);

}
