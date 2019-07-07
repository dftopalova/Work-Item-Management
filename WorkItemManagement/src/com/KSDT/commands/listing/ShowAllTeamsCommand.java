package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.KSDT.commands.CommandConstants.*;

public class ShowAllTeamsCommand implements Command {

    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private Map<String, Team> teams;

    public ShowAllTeamsCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {

        this.teams = repository.getTeams();

        if (teams.isEmpty()) {
            return NO_CREATED_TEAMS_MESSAGE;
        }

        List<String> teamsToStringList = teamsToString();

        return String.join(JOIN_DELIMITER + System.lineSeparator(), teamsToStringList).trim();
    }

    private List<String> teamsToString() {

        List<String> stringifiedTeams = new ArrayList<>();
        for (Team team : teams.values()) {
            stringifiedTeams.add(team.toString());
        }
        //persons.forEach((s, person) -> stringifiedPersons.add(person.toString()));
        return stringifiedTeams;
    }

}
