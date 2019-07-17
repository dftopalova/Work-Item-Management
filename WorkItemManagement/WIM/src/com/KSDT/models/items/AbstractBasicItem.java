package com.KSDT.models.items;

import com.KSDT.models.PersonImpl;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.contracts.Assignable;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Priorityable;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.StatusType;

/*
BasicItem is an abstract base class for Bug and Story, which purpose is to combine
their common fields and methods (priority and assignee)
 */
public abstract class AbstractBasicItem extends WorkItemBase implements Priorityable, Assignable {
    Person assignee;
    PriorityType priority;

    protected AbstractBasicItem(String title, StatusType status, String description, PriorityType priority) {
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

        //TODO should return a copy
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
        person.addToPersonHistory(String.format("Person %s changed priority to work item %s.", person.getName(), this.getTitle()));
        setPriority(newPriority);
    }

    protected abstract String additionalInfo();
}
