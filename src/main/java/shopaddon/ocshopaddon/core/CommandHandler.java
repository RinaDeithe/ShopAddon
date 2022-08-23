package shopaddon.ocshopaddon.core;

import net.milkbowl.vault.economy.Economy;
import shopaddon.ocshopaddon.OcShopAddon;
import shopaddon.ocshopaddon.commands.AdminCommand;
import shopaddon.ocshopaddon.commands.UserCommand;

public class CommandHandler {

    private static final CommandHandler instance;
    private static AdminCommand adminCommand;
    private static UserCommand userCommand;

    static {
        instance = new CommandHandler();
    }

    private CommandHandler() {}

    public static void init(OcShopAddon plugin, Economy econ) {

        adminCommand = new AdminCommand(ModelFactory.getInstance().getShopModel());
        userCommand = new UserCommand(ModelFactory.getInstance().getShopModel(), econ);

        plugin.getCommand("admin").setExecutor(adminCommand::commands);

        plugin.getCommand("shop").setExecutor(userCommand::shop);

    }

}
