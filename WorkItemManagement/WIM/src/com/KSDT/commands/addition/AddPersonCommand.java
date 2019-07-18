package com.KSDT.commands.addition;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class AddPersonCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String personNameToBeAdded;
    private String teamToAddName;

    public AddPersonCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String  execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();


        Team team = repository.getTeams().get(teamToAddName);
        Person person = repository.getPersons().get(personNameToBeAdded);
        team.addPerson(personNameToBeAdded, person);

        return String.format(PERSON_ADDED_TO_TEAM, personNameToBeAdded, teamToAddName);
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getTeams().containsKey(teamToAddName)) {
            throw new IllegalArgumentException(String.format(INVALID_TEAM, teamToAddName));
        }
        if (!repository.getPersons().containsKey(personNameToBeAdded)) {
            throw new IllegalArgumentException(String.format(INVALID_PERSON, personNameToBeAdded));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {

            personNameToBeAdded = parameters.get(0);
            teamToAddName = parameters.get(1);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
