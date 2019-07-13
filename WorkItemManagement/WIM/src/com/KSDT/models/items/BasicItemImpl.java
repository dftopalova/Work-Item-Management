package com.KSDT.models.items;

import com.KSDT.models.PersonImpl;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.contracts.BasicItem;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.StatusType;

/*
BasicItem is an abstract base class for Bug and Story, which purpose is to combine
their common fields and methods (priority and assignee)
 */
public abstract class BasicItemImpl extends WorkItemBase implements BasicItem {

    Person assignee;
    PriorityType priority;

    protected BasicItemImpl(String title, StatusType status, String description,PriorityType priority) {
        super(title, status, description);
        setPriority(priority);
        setAssignee(new PersonImpl());
    }

    private void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    @Override
    public void setAssignee(Person assignee) {
        this.assignee = assignee;
    }

    @Override
    public Person getAssignee() {
        return assignee;
    }

    @Override
    public PriorityType getPriority() {
        return priority;
    }

    @Override
    public void changePriority(Person person, PriorityType newPriority) {
        String change = HistoryHelper.collectChange(getPriority(), newPriority);

        addToHistory(person, change );
        person.addToPersonHistory(change);
        setPriority(newPriority);
    }
}
