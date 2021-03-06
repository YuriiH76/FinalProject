package com.epam.web.command;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
//    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
//        commands.put("logout", new LogoutCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("charge", new ChargeCommand());
        commands.put("userlist", new UserListCommand());
        commands.put("addUser", new AddUserCommand());
        commands.put("updateUser", new UpdateUserCommand());
        commands.put("updateUserAccount", new UpdateUserAccountCommand());
//        commands.put("viewSettings", new ViewSettingsCommand());
//        commands.put("updateSettings", new UpdateSettingsCommand());
//
//        // client commands
//        commands.put("listMenu", new ListMenuCommand());
//
//        // admin commands
//        commands.put("listOrders", new ListOrdersCommand());

//        log.debug("Command container was successfully initialized");
//        log.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName
     *            Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
//            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}