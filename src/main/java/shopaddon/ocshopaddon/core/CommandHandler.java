package shopaddon.ocshopaddon.core;

import net.milkbowl.vault.economy.Economy;
import shopaddon.ocshopaddon.OcShopAddon;
import shopaddon.ocshopaddon.commands.AdminCommand;
import shopaddon.ocshopaddon.commands.UserCommand;

public class CommandHandler {

    private static final CommandHandler instance;

    static {
        instance = new CommandHandler();
    }

    private CommandHandler() {}

    public static void init(OcShopAddon plugin, Economy econ) {

        AdminCommand adminCommand = new AdminCommand(ModelFactory.getInstance().getDataModel());
        UserCommand userCommand = new UserCommand(ModelFactory.getInstance().getShopModel(), econ);

        //ADMIN COMMANDS
        plugin.getCommand("createShop").setExecutor(adminCommand::createShop);
        plugin.getCommand("removeShop").setExecutor(adminCommand::removeShop);
        plugin.getCommand("removeExpired").setExecutor(adminCommand::removeExpired);
        plugin.getCommand("setOwner").setExecutor(adminCommand::setOwner);
        plugin.getCommand("removeOwner").setExecutor(adminCommand::removeOwner);

        //USER COMMANDS
        plugin.getCommand("addMember").setExecutor(userCommand::addMember);
        plugin.getCommand("viewShops").setExecutor(userCommand::view);
        plugin.getCommand("buyShop").setExecutor(userCommand::buy);
        plugin.getCommand("sellShop").setExecutor(userCommand::sell);
        plugin.getCommand("setShopNick").setExecutor(userCommand::setNick);

    }

}
