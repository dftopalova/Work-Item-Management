package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Person;

import java.util.*;
import static com.KSDT.commands.CommandConstants.NO_REGISTERED_PERSONS_MESSAGE;

public class ShowAllPeopleCommand implements Command {

    private final WorkItemRepository repository;

    private Map<String, Person> persons;

    public ShowAllPeopleCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {

        this.persons = repository.getPersons();

        if (persons.isEmpty()) {
            return NO_REGISTERED_PERSONS_MESSAGE;
        }

        Set<String> personsToStringList = persons.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("List of names of all people:" + System.lineSeparator());
        personsToStringList.forEach(person -> stringBuilder.append(person + " "));
        return stringBuilder.toString();
    }
}
