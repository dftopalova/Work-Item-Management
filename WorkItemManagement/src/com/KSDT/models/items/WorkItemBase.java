package com.KSDT.models.items;

import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.StatusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class WorkItemBase implements WorkItem {
    private static final String TITLE_LENGTH_EXCEPTION = "Title must be between 10 and 50 symbols";
    private static final int TITLE_MAX_LENGTH = 50;
    private static final int TITLE_MIN_LENGTH = 10;
    private static final String DESCRIPTION_LENGTH_EXCEPTION = "Description must be between 10 and 500 symbols!";
    private static final int DESCRIPTION_LENGTH_MAX = 500;
    private static final int DESCRIPTION_LENGTH_MIN = 10;


    private String title;
    private StatusType status;
    private String description;
    private List<String> comments; // Pair<Person, String>
    private List<String> history;

    public  WorkItemBase(String title, StatusType status, String description) {
        setTitle(title);
        setStatus(status);
        setDescription(description);
        this.comments = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    private void setTitle(String title) {
        ValidationHelper.nullCheck(title);
        ValidationHelper.lengthCheck(title, TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, TITLE_LENGTH_EXCEPTION);
        this.title = title;
    }

    private void setStatus(StatusType status) {
        ValidationHelper.nullCheck(status);
        this.status = status;
    }

    private void setDescription(String description) {
        ValidationHelper.nullCheck(description);
        ValidationHelper.lengthCheck(description, DESCRIPTION_LENGTH_MIN, DESCRIPTION_LENGTH_MAX, DESCRIPTION_LENGTH_EXCEPTION);
        this.description = description;

    }

    @Override
    public void changeStatus(StatusType newStatus) {
        ValidationHelper.nullCheck(newStatus);
        history.add(HistoryHelper.collectChange(this.status, newStatus));
        setStatus(newStatus);
    }

    @Override
    public void addComment(String comment) {
        ValidationHelper.nullCheck(comment);
        comments.add(comment);
    }

    @Override
    public void addToHistory(String change) {
        ValidationHelper.nullCheck(change);
        history.add(change);
    }

    public abstract String getType();


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public StatusType getStatus() {
        return status;
    }

    @Override
    public List<String> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public String getDescription() {
        return description;
    }
}
