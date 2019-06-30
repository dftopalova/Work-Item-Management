package com.KSDT.models.contracts;

import com.KSDT.models.enums.SeverityType;

import java.util.List;

public interface Bug {
    List<String> getStepsToReproduce();

    SeverityType getSeverity();

    Person getAssignee();

}
