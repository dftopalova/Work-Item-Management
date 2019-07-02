package com.KSDT.models;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Board;
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
//    TODO CHANGE List<WorkItem> to Map<String, WorkItem> !!
    private Map<String, WorkItem> workItems;
    private List<String> history;

    public BoardImpl(String name) {
        setName(name);
        this.workItems = new HashMap<>();
        this.history = new ArrayList<>();
    }

    public void setName(String name) {
        ValidationHelper.lengthCheck(name, BOARD_NAME_MIN_LENGTH, BOARD_NAME_MAX_LENGTH, BOARD_NAME_LENGTH_EXCEPTION);
        ValidationHelper.nullCheck(name);
        this.name = name;
    }

    @Override
    public void addWorkItem(String name, WorkItem workItem) {
        ValidationHelper.nullCheck(name);
        ValidationHelper.nullCheck(workItem);
        workItems.put(name, workItem);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, WorkItem> getWorkItem() {
        return new HashMap<>(workItems);
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
}
