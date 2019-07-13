package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Person;

import java.util.Collection;
import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class ShowTeamActivityCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private WorkItemRepository repository;

    private String teamName;

    public ShowTeamActivityCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        teamName = parameters.get(0);
        validateParameters();

        Collection<Person> members = repository.getTeams().get(teamName).getMembersList().values();

        if (members.isEmpty()) {
            return String.format(NO_MEMBERS_IN_TEAM_ERROR, teamName);
        }

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("Team %s`s activity:" + System.lineSeparator(), teamName));
        members.forEach(member -> strBuilder.append(member.getHistory()).append(System.lineSeparator()));

        return strBuilder.toString();
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(INVALID_TEAM, teamName));
        }
    }
}
