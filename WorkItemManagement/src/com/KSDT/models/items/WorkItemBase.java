package com.KSDT.models.items;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;

import java.util.ArrayList;
import java.util.List;

public class WorkItemBase implements WorkItem {
    private static final String TITLE_LENGTH_EXCEPTION = "Title must be between 10 and 50 symbols";
    private static final int TITLE_MAX_LENGTH = 50;
    private static final int TITLE_MIN_LENGTH = 10;

//    TODO ID must be set in repository MAP - ID int key

    private String title;
    private StatusType status;
    private List<String> comments;
    private List<String> history;

    public WorkItemBase(String title, StatusType status, List<String> comments, List<String> history) {

        setTitle(title);
        setStatus(status);
        comments = new ArrayList<String>();
        history = new ArrayList<String>();

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
// TODO implement methods - change status etc

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public StatusType getStatus() {
        return null;
    }

    @Override
    public List<String> getComments() {
        return null;
    }

    @Override
    public List<String> getHistory() {
        return null;
    }
}
