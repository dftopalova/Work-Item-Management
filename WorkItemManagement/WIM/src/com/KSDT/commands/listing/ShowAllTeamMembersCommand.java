package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import java.util.List;
import java.util.Set;

import static com.KSDT.commands.CommandConstants.*;

public class ShowAllTeamMembersCommand implements Command {

    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private WorkItemRepository repository;

    private String teamName;

    public ShowAllTeamMembersCommand(WorkItemRepository repository){
        this.repository=repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        teamName=parameters.get(0);
        validateParameters();

        Set<String> membersToPrint=repository.getTeams().get(teamName).getMembersList().keySet();
        if(membersToPrint.isEmpty()){
            return String.format(NO_MEMBERS_IN_TEAM_ERROR,teamName);
        }
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("All Members in team %s:",teamName)).append(System.lineSeparator());
        membersToPrint.forEach(member-> strBuilder.append(member + System.lineSeparator()));
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
