package com.KSDT.models.contracts;

public interface Assignable extends WorkItem {
    Person getAssignee();

    void setAssignee(Person assignee);

}
