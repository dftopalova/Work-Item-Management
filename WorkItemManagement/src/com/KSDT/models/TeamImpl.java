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

    public void setName(String name) {
        ValidationHelper.nullCheck(name);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Person> getMembers() {
        return new HashMap<>(members);
    }

    @Override
    public Map<String, Board> getBoards() {
        return new HashMap<>(boards);
    }

    @Override
    public void addPerson(String name, Person person) {
        members.put(name, person);
    }

    @Override
    public void addBoard(String name, Board board) {
        this.boards.put(name, board);
    }
}
