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
    private String name;
    private List<String> history;
    private List<WorkItem> workItems;


    public PersonImpl(String name) {
        setName(name);
        this.history = new ArrayList<>();
        this.workItems = new ArrayList<>();
    }

    public void setName(String name) {
        ValidationHelper.nullCheck(name);
        ValidationHelper.lengthCheck(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH, NAME_LENGTH_EXCEPTION);
        this.name = name;
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
    public String toString(){
        StringBuilder strBuilder=new StringBuilder();
        strBuilder.append(String.format("Person %s`s info:\n",getName()));

        workItems.stream().forEach(item->strBuilder.append(item).append("\n"));
        history.stream().forEach(statement->strBuilder.append(statement).append("\n"));

        return strBuilder.toString();
    }
}
