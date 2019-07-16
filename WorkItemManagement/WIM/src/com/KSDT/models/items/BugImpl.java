package com.KSDT.models.items;

import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BugImpl extends AbstractBasicItem implements Bug {

    private List<String> stepsToReproduce;
    private SeverityType severity;

    public BugImpl(String title, StatusType status, String description, String stepsToReproduce, PriorityType priority, SeverityType severity) {
        super(title, status, description, priority);
        setStepsToReproduce(stepsToReproduce);
        setSeverity(severity);
    }

    private void setStepsToReproduce(String stepsToReproduce) {
        ValidationHelper.emptyStringCheck(stepsToReproduce);
        String[] stepsList = stepsToReproduce.trim().split("/");

        this.stepsToReproduce = Arrays.asList(stepsList);
    }

    private void setSeverity(SeverityType severity) {
        this.severity = severity;
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
    public void changeSeverity(Person person, SeverityType newSeverity) {
        String change = HistoryHelper.collectChange(getSeverity(), newSeverity);

        addToHistory(person, change);
        person.addToPersonHistory(change);
        setSeverity(newSeverity);
    }

    @Override
    public String additionalInfo() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format(
                "Steps to reproduce: %s" +
                        System.lineSeparator() +
                        "Severity: %s" +
                        System.lineSeparator() +
                        "Priority: %s" +
                        System.lineSeparator() +
                        "Assignee: %s" +
                        System.lineSeparator() +
                        "=*=*=*=*=" +
                        System.lineSeparator()
                , getStepsToReproduce(), getSeverity(), getPriority(), getAssignee().getName()));
        return strBuilder.toString();
    }


}
