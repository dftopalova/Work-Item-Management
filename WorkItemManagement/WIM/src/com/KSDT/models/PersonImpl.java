package com.KSDT.models;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonImpl implements Person {
    private static final String NAME_LENGTH_EXCEPTION = "Name must be between 5 and 15 symbols";
    private static final int NAME_MAX_LENGTH = 15;
    private static final int NAME_MIN_LENGTH = 5;
    private static final String ITEM_ALREADY_ASSIGNED = "! %s already has been assigned to %s.";
    private static final String ITEM_NEVER_ASSIGNED = "! %s has never been assigned to %s.";
    private String name;
    private List<String> history;
    private List<WorkItem> workItems;


    public PersonImpl(String name) {
        setName(name);
        this.history = new ArrayList<>();
        this.workItems = new ArrayList<>();
    }

    //emptyPerson - for UnassignWorkItem Command
    public PersonImpl() {
        this.name="NO_NAME";
        this.history = new ArrayList<>();
        this.workItems = new ArrayList<>();
    }


    public void setName(String name) {
        ValidationHelper.emptyStringCheck(name);
        ValidationHelper.lengthCheck(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH, NAME_LENGTH_EXCEPTION);
        this.name = name;
    }

    public void addWorkItem(WorkItem item){
        if (workItems.contains(item)) {
            throw new IllegalArgumentException(String.format(ITEM_ALREADY_ASSIGNED, item.getTitle(), name));
        }
        workItems.add(item);
    }

    public void removeWorkItem(WorkItem item){
        if (!workItems.contains(item)) {
            throw new IllegalArgumentException(String.format(ITEM_NEVER_ASSIGNED, item.getTitle(), name));
        }
        workItems.remove(item);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public List<WorkItem> getWorkItems() {
        return new ArrayList<>(workItems);
    }

    @Override
    public void addToPersonHistory(String change) {
        history.add(change);
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("Person %s`s info:" + System.lineSeparator(), getName()));

        workItems.stream().forEach(item -> strBuilder.append(item).append(System.lineSeparator()));
        history.stream().forEach(statement -> strBuilder.append(statement).append(System.lineSeparator()));

        return strBuilder.toString();
    }
}
