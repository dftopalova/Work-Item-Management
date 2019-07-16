package com.KSDT.models.items;

import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Story;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;

public class StoryImpl extends AbstractBasicItem implements Story {

    private SizeType size;

    public StoryImpl(String title, StatusType status, String description, PriorityType priority, SizeType size) {
        super(title, status, description,priority);
        setSize(size);
    }

    private void setSize(SizeType size) {
        this.size = size;
    }

    @Override
    public SizeType getSize() {
        return size;
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
                ,getPriority(), getSize(), getAssignee().getName()));
        return strBuilder.toString();
    }
}
