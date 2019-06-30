package com.KSDT.models;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.List;

public class BoardImpl implements Board {
    private static final String BOARD_NAME_LENGTH_EXCEPTION = "Board name must be between 5 and 10 symbols";
    private static final int BOARD_NAME_MAX_LENGTH = 10;
    private static final int BOARD_NAME_MIN_LENGTH = 5;

    private String name;
    private List<WorkItem> workItems;
    private List<String> history;

    public BoardImpl(String name) {
        setName(name);
        this.workItems = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    public void setName(String name) {
        ValidationHelper.lengthCheck(name, BOARD_NAME_MIN_LENGTH, BOARD_NAME_MAX_LENGTH, BOARD_NAME_LENGTH_EXCEPTION);
        ValidationHelper.nullCheck(name);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<WorkItem> getWorkItem() {
        return new ArrayList<>(workItems);
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
}
