package com.KSDT.models.items;

import com.KSDT.models.common.HistoryHelper;
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

    public StoryImpl(String title, StatusType status, String description, PriorityType priority, SizeType size) {
        super(title, status, description);
        setPriority(priority);
        setSize(size);
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
    public String getType() {
        return "STORY_";
    }

    @Override
    public PriorityType getPriority() {
        return priority;
    }

    @Override
    public SizeType getSize() {
        return size;
    }

    @Override
    public Person getAssignee() {
        return assignee;
    }

    @Override
    public void changePriority(PriorityType newPriority) {
        ValidationHelper.nullCheck(newPriority);
        addToHistory(HistoryHelper.collectChange(getPriority(), newPriority));
        setPriority(newPriority);
    }

    @Override
    public void changeSize(SizeType newSize) {
        ValidationHelper.nullCheck(newSize);
        addToHistory(HistoryHelper.collectChange(getSize(), newSize));
        setSize(newSize);
    }

    @Override
    public String additionalInfo() {
        StringBuilder strBuilder =new StringBuilder();
        strBuilder.append(String.format(
                "Priority: %s" +
                        "Size: %s" +
                        "Assignee: %s\n" +
                        "=*=*=*=*=\n",getPriority(), getSize(), getAssignee()));
        return strBuilder.toString();
    }
}
