package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Team;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.KSDT.commands.CommandConstants.*;

public class ShowAllTeamsCommand implements Command {

    private final WorkItemRepository repository;

    private Map<String, Team> teams;

    public ShowAllTeamsCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {

        this.teams = repository.getTeams();

        if (teams.isEmpty()) {
            return NO_CREATED_TEAMS_MESSAGE;
        }

        Set<String> teamsToStringList = teams.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("List of names of all teams:" + System.lineSeparator());
        teamsToStringList.forEach(team -> stringBuilder.append(team + " "));
        return stringBuilder.toString();
    }
}
