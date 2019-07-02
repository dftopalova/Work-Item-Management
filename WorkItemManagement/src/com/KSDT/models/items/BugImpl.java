package com.KSDT.models.items;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BugImpl extends WorkItemBase implements Bug {
    private List<String> stepsToReproduce;
    private SeverityType severity;
    private Person assignee;

    public BugImpl(String title, StatusType status,String description, String stepsToReproduce, SeverityType severity) {
        super(title, status, description);
        setStepsToReproduce(stepsToReproduce);
        setSeverity(severity);
    }

    private void setStepsToReproduce(String stepsToReproduce) {
        ValidationHelper.nullCheck(stepsToReproduce);
        String[] stepsList = stepsToReproduce.trim().split("/");

        this.stepsToReproduce = Collections.singletonList(stepsToReproduce);
        //TODO CHECK singleton
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
        return new ArrayList<>(stepsToReproduce);
    }

    @Override
    public SeverityType getSeverity() {
        return severity;
    }

    @Override
    public Person getAssignee() {
        return assignee;
    }

    @Override
    public String getType() {
        return "BUG_";
    }
}
