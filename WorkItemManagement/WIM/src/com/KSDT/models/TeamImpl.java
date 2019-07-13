package com.KSDT.models;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamImpl implements Team {

    private String name;
    private Map<String, Person> members;
    private Map<String, Board> boards;

    public TeamImpl(String name) {
        setName(name);
        this.members = new HashMap<>();
        this.boards = new HashMap<>();
    }

    public TeamImpl(Team team) {   //Copy constructor - defense / https://stackoverflow.com/questions/869033/how-do-i-copy-an-object-in-java /
        this.name = team.getName();
        this.members = team.getMembersList();
        this.boards = team.getBoardsList();
    }

    public void setName(String name) {
        ValidationHelper.emptyStringCheck(name);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Person> getMembersList() {
        return new HashMap<>(members);
    }

    @Override
    public Person getMember(String name) {
        return members.get(name);
    }

    @Override
    public Map<String, Board> getBoardsList() {
        return new HashMap<>(boards);
    }

    @Override
    public Board getBoard(String boardName) {
        return boards.get(boardName);
    }

    @Override
    public void addPerson(String name, Person person) {
        members.put(name, person);
    }

    @Override
    public void addBoard(String name, Board board) {
        this.boards.put(name, board);
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("Team %s`s info:" + System.lineSeparator(), getName()));

        members.entrySet().forEach(member ->
                strBuilder.append(member.getKey() + " " + member.getValue() + System.lineSeparator()));

        boards.entrySet().forEach(board ->
                strBuilder.append(board.getKey() + " " + board.getValue() + System.lineSeparator()));

        return strBuilder.toString();
    }
}
