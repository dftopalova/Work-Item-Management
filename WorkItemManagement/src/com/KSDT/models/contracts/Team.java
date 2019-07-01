package com.KSDT.models.contracts;

import java.util.Map;

public interface Team {
    String getName();

    Map<String, Person> getMembers();

    Map<String, Board> getBoards();

    void addPerson(String name, Person person);

}
