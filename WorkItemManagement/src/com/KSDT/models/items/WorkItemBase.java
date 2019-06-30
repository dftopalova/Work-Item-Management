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
    private List<String> comments; //possibly Map because it needs author to every comment
    private List<String> history;

    public WorkItemBase(String title, StatusType status) {
        setTitle(title);
        setStatus(status);
        this.comments = new ArrayList<String>();
        this.history = new ArrayList<String>();
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
        return title;
    }

    @Override
    public StatusType getStatus() {
        return status;
    }

    @Override
    public List<String> getComments() {
        return new ArrayList<String>(comments);
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<String>(history);
    }
}
