package com.KSDT.commands.addition;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.*;

import java.util.List;

import static com.KSDT.commands.CommandConstants.*;

public class AssignWorkItemCommand implements Command {

    private final static int EXPECTED_NUMBER_OF_ARGUMENTS=2;
    private final WorkItemRepository repository;

    private int workItemID;
    private String personName;

    public AssignWorkItemCommand(WorkItemRepository repository){
        this.repository=repository;
    }

    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);
        parseParameters(parameters);
        validateParameters();

        WorkItem item=repository.getWorkItems().get(workItemID);
        Person person=repository.getPersons().get(personName);

        person.addWorkItem(item);

        if(item.getWorkItemType().equals("Bug")){
            ((Bug) item).setAssignee(person);
        }
        else if(item.getWorkItemType().equals("Story")){
            ((Story) item).setAssignee(person);
        }
        return String.format(SUCCESSFULLY_ASSIGNED_ITEM_MESSAGE,item.getTitle(),personName);
    }

    private void validateInput(List<String> parameters) {
        if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
        }
    }

    private void validateParameters() {
        if (repository.getWorkItems().size() <= workItemID) {
            throw new IllegalArgumentException(String.format(INVALID_WORK_ITEM, workItemID));
        }

        if (!repository.getPersons().containsKey(personName)) {
            throw new IllegalArgumentException(String.format(INVALID_PERSON, personName));
        }

        if(repository.getWorkItems().get(workItemID).getWorkItemType().equalsIgnoreCase("Feedback")){
            throw new IllegalArgumentException(String.format(FEEDBACK_HAS_NO_ASSIGNEE_ERROR));
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
            workItemID = Integer.parseInt(parameters.get(0));
            personName = parameters.get(1);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
