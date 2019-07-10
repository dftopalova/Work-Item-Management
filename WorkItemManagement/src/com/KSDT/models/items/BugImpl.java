package com.KSDT.models.items;

import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.Pair;
import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.enums.PriorityType;
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
    private PriorityType priority;

    public BugImpl(String title, StatusType status,String description, String stepsToReproduce, PriorityType priority, SeverityType severity) {
        super(title, status, description);
        setStepsToReproduce(stepsToReproduce);
        setPriority(priority);
        setSeverity(severity);
    }

    private void setStepsToReproduce(String stepsToReproduce) {
        ValidationHelper.emptyStringCheck(stepsToReproduce);
        String[] stepsList = stepsToReproduce.trim().split("/");

        this.stepsToReproduce = Arrays.asList(stepsList);
//        this.stepsToReproduce = Collections.singletonList(stepsToReproduce);
    }

    private void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    public void setAssignee(Person assignee) {
        this.assignee = assignee;
    }

    public void setPriority(PriorityType priority) {
        this.priority = priority;
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
    public PriorityType getPriority() {
        return priority;
    }

    @Override
    public void changePriority(Person person, PriorityType newPriority) {
        String change = HistoryHelper.collectChange(getPriority(), newPriority);

        addToHistory(person, change );
        person.addToPersonHistory(change);
        setPriority(newPriority);
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
        StringBuilder strBuilder =new StringBuilder();
        strBuilder.append(String.format(
                "Steps to reproduce: %s" +
                        System.lineSeparator() +
                "Severity: %s" +
                        System.lineSeparator() +
                "Priority: %s" +
                        System.lineSeparator() +
                "Assignee: %s" +
                        System.lineSeparator() +
                        "=*=*=*=*="+
                System.lineSeparator()
                , getStepsToReproduce(),getSeverity(),getPriority(),getAssignee()));
        return strBuilder.toString();
    }
}
