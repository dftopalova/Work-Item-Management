package com.KSDT.models.items;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Story;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;

public class StoryImpl extends WorkItemBase implements Story {
    private static final String DESCRIPTION_LENGTH_EXCEPTION = "Description must be between 10 and 500 symbols!";
    private static final int DESCRIPTION_LENGTH_MAX = 500;
    private static final int DESCRIPTION_LENGTH_MIN = 10;

    private String description;
    private PriorityType priority;
    private SizeType size;
    private Person assignee;

    public StoryImpl(String title, StatusType status, String description, PriorityType priority, SizeType size, Person assignee) {
        super(title, status);
        setDescription(description);
        setPriority(priority);
        setSize(size);
        setAssignee(assignee);
    }

    private void setDescription(String description) {
        ValidationHelper.nullCheck(description);
        ValidationHelper.lengthCheck(description, DESCRIPTION_LENGTH_MIN, DESCRIPTION_LENGTH_MAX, DESCRIPTION_LENGTH_EXCEPTION);
        this.description = description;
    }

    private void setPriority(PriorityType priority) {
        ValidationHelper.nullCheck(priority);
        this.priority = priority;
    }

    private void setSize(SizeType size) {
        ValidationHelper.nullCheck(size);
        this.size = size;
    }

    private void setAssignee(Person assignee) {
        ValidationHelper.nullCheck(assignee);
        this.assignee = assignee;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public PriorityType getPriority() {
        return null;
    }

    @Override
    public SizeType getSize() {
        return null;
    }

    @Override
    public Person getAssignee() {
        return null;
    }
}
