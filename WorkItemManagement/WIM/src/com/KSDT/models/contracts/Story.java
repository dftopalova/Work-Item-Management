package com.KSDT.models.contracts;

import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;

public interface Story extends Priorityable, Assignable {

    SizeType getSize();

    void changeSize(Person person, SizeType newSize);

}
