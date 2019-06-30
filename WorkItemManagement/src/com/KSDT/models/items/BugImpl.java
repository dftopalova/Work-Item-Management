package com.KSDT.models.items;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends WorkItemBase implements Bug {
    private List<String> stepsToReproduce;
    private SeverityType severity;
    private Person assignee;

    public BugImpl(String title, StatusType status, List<String> stepsToReproduce, SeverityType severity, Person assignee) {
        super(title, status);
        setStepsToReproduce(stepsToReproduce);
        setAssignee(assignee);
        setSeverity(severity);
    }

    private void setStepsToReproduce(List<String> stepsToReproduce) {
        ValidationHelper.nullCheck(stepsToReproduce);
//        TODO fix LIST logic
        this.stepsToReproduce = stepsToReproduce;
    }

    private void setSeverity(SeverityType severity) {
        ValidationHelper.nullCheck(severity);
        this.severity = severity;
    }

    private void setAssignee(Person assignee) {
        ValidationHelper.nullCheck(assignee);
        this.assignee = assignee;
    }

    @Override
    public List<String> getStepsToReproduce() {
        return new ArrayList<String>(stepsToReproduce);
    }

    @Override
    public SeverityType getSeverity() {
        return severity;
    }

    @Override
    public Person getAssignee() {
        return assignee;
    }
}
