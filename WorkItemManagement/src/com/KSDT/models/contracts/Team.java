package com.KSDT.models.contracts;

import java.util.Map;

public interface Team {
    String getName();

    Map<String, Person> getMembersList();

    Person getMember(String name);

    Map<String, Board> getBoardsList();

    Board getBoard(String boardName);

    void addPerson(String name, Person person);

    void addBoard(String name, Board board);

}
