package com.KSDT.commands.addition;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.contracts.*;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class UnassignWorkItemCommand implements Command {
    private final static int EXPECTED_NUMBER_OF_ARGUMENTS=2;
    private final WorkItemRepository repository;

    private String workItemName;
    private String personName;

    public UnassignWorkItemCommand(WorkItemRepository repository){
        this.repository=repository;
    }

    @Override
    public String execute(List<String> parameters) {

        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();

        Person person=repository.getPersons().get(personName);
        Assignable itemToAdd = repository.getAssignableItems().values()
                .stream()
                .filter(item -> item.getTitle()
                        .equals(workItemName)).findFirst().orElse(null);

        if (itemToAdd == null) {
            throw new IllegalArgumentException(String.format(INVALID_ASSIGNABLE_WORK_ITEM, workItemName));
        }

        person.removeWorkItem(itemToAdd);
        itemToAdd.setAssignee(new PersonImpl());

        return String.format(SUCCESSFULLY_UNASSIGNED_ITEM_MESSAGE,itemToAdd.getTitle(),personName);
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

    private void parseParameters(List<String> parameters) {
        try {
            workItemName = parameters.get(0);
            personName = parameters.get(1);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
