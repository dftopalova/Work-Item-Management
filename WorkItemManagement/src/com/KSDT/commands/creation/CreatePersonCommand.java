package com.KSDT.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Person;

import java.util.List;

import static com.KSDT.commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;
import static com.KSDT.commands.CommandConstants.PERSON_CREATED;

public class CreatePersonCommand implements Command {
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private String name;

    public CreatePersonCommand(WorkItemRepository repository, WorkItemFactory factory) {
    this.repository = repository;
    this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);

        Person person = factory.createPerson(name);
        repository.addPerson(name, person);
        return String.format(PERSON_CREATED, person.getName());  // repository.getPersons().size()-1
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            name = parameters.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException("");
//            TODO EXCEPTION
        }
    }

}
