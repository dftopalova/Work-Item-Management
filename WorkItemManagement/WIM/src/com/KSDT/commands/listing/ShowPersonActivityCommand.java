package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;

import java.util.List;

import static com.KSDT.commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;
import static com.KSDT.commands.CommandConstants.INVALID_PERSON;

public class ShowPersonActivityCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private WorkItemRepository repository;

    private String personName;

    public ShowPersonActivityCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        personName = parameters.get(0);
        validateParameters();

        List<String> historyToShow = repository.getPersons().get(personName).getHistory();

        if (historyToShow.isEmpty()) {
            return String.format("Person %s has no activity.", personName);
        }

        return String.format("Person %s`s activity: "
                + System.lineSeparator()
                + historyToShow,
                personName);
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (!repository.getPersons().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(INVALID_PERSON, personName));
        }
    }
}
