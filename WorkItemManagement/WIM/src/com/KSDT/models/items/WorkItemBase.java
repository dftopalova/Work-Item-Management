package com.KSDT.models.items;

import com.KSDT.models.BoardImpl;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.Pair;
import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkItemBase implements WorkItem {
    private static final String TITLE_LENGTH_EXCEPTION = "Title must be between 10 and 50 symbols";
    private static final int TITLE_MAX_LENGTH = 50;
    private static final int TITLE_MIN_LENGTH = 10;
    private static final String DESCRIPTION_LENGTH_EXCEPTION = "Description must be between 10 and 500 symbols!";
    private static final int DESCRIPTION_LENGTH_MAX = 500;
    private static final int DESCRIPTION_LENGTH_MIN = 10;


    private String title;
    private StatusType status;
    private String description;
    private List<Pair<Person, String>> commentPairs;
    private List<Pair<Person, String>> historyPairs;

    public WorkItemBase(String title, StatusType status, String description) {
        setTitle(title);
        setStatus(status);
        setDescription(description);
        this.commentPairs = new ArrayList<>();
        this.historyPairs = new ArrayList<>();
    }

    private void setTitle(String title) {
        ValidationHelper.emptyStringCheck(title);
        ValidationHelper.lengthCheck(title, TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, TITLE_LENGTH_EXCEPTION);
        this.title = title;
    }

    private void setStatus(StatusType status) {
        ValidationHelper.statusTypeCheck(status, this.getWorkItemType());
        this.status = status;
    }

    private void setDescription(String description) {
        ValidationHelper.emptyStringCheck(description);
        ValidationHelper.lengthCheck(description, DESCRIPTION_LENGTH_MIN, DESCRIPTION_LENGTH_MAX, DESCRIPTION_LENGTH_EXCEPTION);
        this.description = description;

    }

    @Override
    public void changeStatus(Person person, StatusType newStatus) {
        historyPairs.add(Pair.create(person, HistoryHelper.collectChange(this.status, newStatus)));
        person.addToPersonHistory(String.format("Person %s changed status to work item %s.", person.getName(), this.getTitle()));
        setStatus(newStatus);
    }

    @Override
    public void addComment(Person person, String comment) {
        ValidationHelper.emptyStringCheck(comment);
        commentPairs.add(Pair.create(person, comment));
        person.addToPersonHistory(String.format("Person %s added a comment to work item %s.", person.getName(), this.getTitle()));
    }

    @Override
    public void addToHistory(Person person, String change) {
        ValidationHelper.emptyStringCheck(change);
        historyPairs.add(Pair.create(person, change));
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public StatusType getStatus() {
        return status;
    }

    @Override
    public List<Pair<Person, String>> getComments() {
        return new ArrayList<>(commentPairs);
    }

    @Override
    public List<Pair<Person, String>> getHistoryPairs() {
        return new ArrayList<>(historyPairs);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getWorkItemType() {
        return this.getClass().getSimpleName().replace("Impl", "");
    }

    private String getOnlyComments() {
        StringBuilder stringBuilder = new StringBuilder();
        getComments().forEach(personStringPair -> stringBuilder.append(personStringPair.getSecond() + System.lineSeparator()));
        return stringBuilder.toString();
    }

    private String getOnlyHistory() {
        StringBuilder stringBuilder = new StringBuilder();
        getHistoryPairs().forEach(personStringPair -> stringBuilder.append(personStringPair.getSecond() + System.lineSeparator()));
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format(
                "%s info:" +
                        System.lineSeparator() +
                        "Title: %s" +
                        System.lineSeparator() +
                        "Status: %s" +
                        System.lineSeparator() +
                        "Description: %s" +
                        System.lineSeparator() +
                        "Comments: %s" +
                        System.lineSeparator() +
                        "History: %s" +
                        System.lineSeparator() +
                        "%s",
                getWorkItemType(), getTitle(), getStatus(), getDescription(), getOnlyComments(), getOnlyHistory(), additionalInfo()));
        return strBuilder.toString();
    }

    protected abstract String additionalInfo();
}
