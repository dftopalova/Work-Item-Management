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
        this.priority = priority;
    }

    private void setSize(SizeType size) {
        this.size = size;
    }

    private void setAssignee(Person assignee) {
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
    public void changePriority(Person person, PriorityType newPriority) {
        String change = HistoryHelper.collectChange(getPriority(), newPriority);
        addToHistory(person, change);
        person.addToPersonHistory(change);
        setPriority(newPriority);
    }

    @Override
    public void changeSize(Person person, SizeType newSize) {
        String change = HistoryHelper.collectChange(getSize(), newSize);

        addToHistory(person, change);
        person.addToPersonHistory(change);
        setSize(newSize);
    }

    @Override
    public String additionalInfo() {
        StringBuilder strBuilder =new StringBuilder();
        strBuilder.append(String.format(
                "Priority: %s" +
                        System.lineSeparator() +
                        "Size: %s" +
                        System.lineSeparator() +
                        "Assignee: %s" +
                        System.lineSeparator() +
                        "=*=*=*=*="+System.lineSeparator()
                ,getPriority(), getSize(), getAssignee()));
        return strBuilder.toString();
    }
}
