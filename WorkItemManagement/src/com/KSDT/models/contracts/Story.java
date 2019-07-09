package com.KSDT.models.contracts;

import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;

public interface Story  {
    PriorityType getPriority();

    SizeType getSize();

    Person getAssignee();

    void changePriority(Person person, PriorityType newPriority);

    void changeSize(Person person, SizeType newSize);

    void setAssignee(Person assignee);
}
