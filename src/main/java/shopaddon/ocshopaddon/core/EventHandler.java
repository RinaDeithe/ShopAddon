package shopaddon.ocshopaddon.core;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import shopaddon.ocshopaddon.OcShopAddon;
import shopaddon.ocshopaddon.model.database.DatabaseCommunicator;
import shopaddon.ocshopaddon.model.shop.ShopModel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EventHandler implements Listener {

    private static volatile EventHandler instance;
    private final OcShopAddon plugin;
    private static final Lock lock = new ReentrantLock();
    private final ShopModel model;

    private EventHandler(OcShopAddon plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.model = ModelFactory.getInstance().getShopModel();
    }

    public static void init(OcShopAddon plugin) {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new EventHandler(plugin);
                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        DatabaseCommunicator.checkPlayer(event.getPlayer());
    }

}
