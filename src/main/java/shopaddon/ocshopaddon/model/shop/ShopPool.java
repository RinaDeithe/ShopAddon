package shopaddon.ocshopaddon.model.shop;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ShopPool {
    void createShop(Player player, String shopUID);
    void removeShop(Player player, String shopUID);
    Shop getShop(Player player, String shopUID);
    Shop getShop(Player player);
    void setOwner(Player player, String shopUID);
    void removeOwner(Player owner, String shopUID);
    void setRegion(Player player, String shopUID, ProtectedRegion region);
    ArrayList<Shop> getShopList();

    void setName(Player player, String currentID, String newID);
}
