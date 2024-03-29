package com.KSDT.models;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardImpl implements Board {
    private static final String BOARD_NAME_LENGTH_EXCEPTION = "Board name must be between 5 and 10 symbols";
    private static final int BOARD_NAME_MAX_LENGTH = 10;
    private static final int BOARD_NAME_MIN_LENGTH = 5;

    private String name;
    private Team owner;
    private Map<String, WorkItem> workItems;
    private List<String> history;

    public BoardImpl(String name, Team owner) {
        setName(name);
        setOwner(owner);
        this.workItems = new HashMap<>();
        this.history = new ArrayList<>();
    }

    private void setOwner(Team owner) {
        this.owner = owner;
    }

    private void setName(String name) {
        ValidationHelper.emptyStringCheck(name);
        ValidationHelper.lengthCheck(name, BOARD_NAME_MIN_LENGTH, BOARD_NAME_MAX_LENGTH, BOARD_NAME_LENGTH_EXCEPTION);
        this.name = name;
    }

    @Override
    public void addWorkItem(String name, WorkItem workItem) {
        ValidationHelper.emptyStringCheck(name);
        workItems.put(name, workItem);
    }

    @Override
    public void addToHistory(String change) {
        history.add(change);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, WorkItem> getWorkItemsList() {
        return new HashMap<>(workItems);
    }

    @Override
    public WorkItem getWorkItem(String name) {
        return workItems.get(name);
    }

    @Override
    public Team getTeamOwner() {
        return new TeamImpl(owner);
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("Board %s info:" +
                        System.lineSeparator() +
                        "Team Owner: %s" +
                        System.lineSeparator(),
                getName(), getTeamOwner().getName()));

        strBuilder.append("Work items in board: " + System.lineSeparator());
        workItems.keySet().forEach(entry -> strBuilder.append(entry + " "));
        strBuilder.append(System.lineSeparator());

        strBuilder.append("History:" + System.lineSeparator());
        history.stream().forEach(statement -> strBuilder.append(statement).append(System.lineSeparator()));

        return strBuilder.toString();
    }
}
