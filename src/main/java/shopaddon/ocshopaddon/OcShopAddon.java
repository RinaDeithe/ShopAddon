package shopaddon.ocshopaddon;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import shopaddon.ocshopaddon.core.CommandHandler;
import shopaddon.ocshopaddon.core.ConnectionHandler;
import shopaddon.ocshopaddon.core.EventHandler;
import shopaddon.ocshopaddon.model.database.DatabaseCommunicator;

import java.util.logging.Logger;

public final class OcShopAddon extends JavaPlugin implements Listener {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private ConnectionHandler connectionHandler;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        ConnectionHandler.adress = this.getConfig().getString("postgresql.adress");
        ConnectionHandler.port = this.getConfig().getString("postgresql.port");
        ConnectionHandler.user = this.getConfig().getString("postgresql.user");
        ConnectionHandler.pass = this.getConfig().getString("postgresql.password");
        ConnectionHandler.dataBase = this.getConfig().getString("postgresql.database");

        connectionHandler = ConnectionHandler.getInstance();

        EventHandler.init(this);

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        CommandHandler.init(this, econ);
        DatabaseCommunicator.addInactive();

    }

    @Override
    public void onDisable() {
        connectionHandler.closeConnection();
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
