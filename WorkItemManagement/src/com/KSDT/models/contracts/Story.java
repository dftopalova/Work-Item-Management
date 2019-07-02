package com.KSDT.models.contracts;

import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;

public interface Story extends WorkItem {
    PriorityType getPriority();

    SizeType getSize();

    Person getAssignee();
}
