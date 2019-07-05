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
        ValidationHelper.nullCheck(stepsToReproduce);
        String[] stepsList = stepsToReproduce.trim().split("/");

        this.stepsToReproduce = Arrays.asList(stepsList);
//        this.stepsToReproduce = Collections.singletonList(stepsToReproduce);
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

    public void setPriority(PriorityType priority) {
        ValidationHelper.nullCheck(priority);
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
    public void changePriority(PriorityType newPriority) {
        ValidationHelper.nullCheck(newPriority);
        addToHistory(HistoryHelper.collectChange(getPriority(), newPriority));
        setPriority(newPriority);
    }

    @Override
    public void changeSeverity(SeverityType newSeverity) {
        ValidationHelper.nullCheck(newSeverity);
        addToHistory(HistoryHelper.collectChange(getSeverity(), newSeverity));
        setSeverity(newSeverity);
    }

    @Override
    public String getType() {
        return "BUG_";
    }

    @Override
    public String additionalInfo() {
        StringBuilder strBuilder =new StringBuilder();
        strBuilder.append(String.format(
                "Steps to reproduce: %s" +
                "Severity: %s" +
                "Priority: %s" +
                "Assignee: %s\n" +
                        "=*=*=*=*=\n", getStepsToReproduce(),getSeverity(),getPriority(),getAssignee()));
        return strBuilder.toString();
    }
}
