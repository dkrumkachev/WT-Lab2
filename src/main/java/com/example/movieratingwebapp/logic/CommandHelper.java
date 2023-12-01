package com.example.movieratingwebapp.logic;

import com.example.movieratingwebapp.logic.implementations.*;
import com.example.movieratingwebapp.logic.interfaces.ICommand;

import java.util.HashMap;
import java.util.Map;

public final class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();

    private final Map<CommandName, ICommand> commands = new HashMap<>();

    public CommandHelper() {
        commands.put(CommandName.UNKNOWN_COMMAND, new UnknownCommand());
        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTER, new RegistrationCommand());
        commands.put(CommandName.GET_MOVIES, new GetMoviesCommand());
        commands.put(CommandName.GET_MOVIE, new GetMovieCommand());
        commands.put(CommandName.ADD_REVIEW, new AddReviewCommand());
        commands.put(CommandName.GET_USER, new GetUserCommand());
        commands.put(CommandName.ADD_GENRE, new AddGenreCommand());
        commands.put(CommandName.ADD_MOVIE, new AddMovieCommand());
        commands.put(CommandName.GET_USERS, new GetUsersCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.DELETE_REVIEW, new DeleteReviewCommand());

    }

    public static CommandHelper getInstance() {
        return instance; }

    public ICommand getCommand(String commandName) {
        if (commandName == null) {
            return commands.get(CommandName.UNKNOWN_COMMAND);
        }
        try {
            CommandName name = CommandName.valueOf(commandName.toUpperCase());
            return commands.get(name);
        } catch (IllegalArgumentException e) {
            return commands.get(CommandName.UNKNOWN_COMMAND);
        }
    }

}
