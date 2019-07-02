package com.KSDT.commands;

public class CommandConstants {
//    TODO List all messages as constants

    //Error messages
    public static final String INVALID_NUMBER_OF_ARGUMENTS = "! Invalid number of arguments. Expected: %d, Received: %d";
    public static final String INVALID_TEAM = "! Team with ID %s doesn't exist.";
    public static final String INVALID_PERSON = "! Person with ID %s doesn't exist.";
    public static final String INVALID_BOARD = "! Board with ID %s doesn't exist.";
    public static final String BOARD_ALREADY_EXISTS = "! Board with ID %s in team %s already exists.";
    // TODO BUG, STORY AND FEEDBACK ERRORS

    //Success messages
    public static final String TEAM_CREATED = "Team with ID %s was created.";
    public static final String PERSON_CREATED = "Person with ID %s was created.";
    public static final String PERSON_ADDED_TO_TEAM = "Person with ID %s was added to %s team.";
    public static final String BOARD_ADDED_TO_TEAM = "Board with ID %s was added to %s team.";
    public static final String BUG_ADDED_TO_BOARD = "Bug with ID %s was added to %s board.";
    public static final String STORY_ADDED_TO_BOARD = "Story with ID %s was added to %s board.";


}
