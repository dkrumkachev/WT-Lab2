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
