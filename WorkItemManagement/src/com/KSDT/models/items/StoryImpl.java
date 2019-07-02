package com.KSDT.models.items;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Story;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;

public class StoryImpl extends WorkItemBase implements Story {

    private PriorityType priority;
    private SizeType size;
    private Person assignee;

    public StoryImpl(String title, StatusType status, String description, PriorityType priority, SizeType size, Person assignee) {
        super(title, status, description);
        setPriority(priority);
        setSize(size);
        setAssignee(assignee);
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
