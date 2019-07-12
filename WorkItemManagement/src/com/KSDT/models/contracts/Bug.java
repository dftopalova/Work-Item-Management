package com.KSDT.models.contracts;

import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;

import java.util.List;

public interface Bug extends BasicItem {

    List<String> getStepsToReproduce();

    SeverityType getSeverity();

    void changeSeverity(Person person, SeverityType newSeverity);

}
