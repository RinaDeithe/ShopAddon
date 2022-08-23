package shopaddon.ocshopaddon.model.shop;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ShopModel {

    void createShop(Player player, String shopUID);
    void removeShop(Player player, String shopUID);
    Shop getShop(Player player, String shopUID);
    Shop getShop(Player player);

    void setRegion(Player player, String shopUID, ProtectedRegion region);
    void setOwner(String shopUID, Player player);
    void removeOwner(Player owner, String shopUID);

    ArrayList<Shop> getShopList();

    void setName(Player player, String currentUID, String newUID);

    void resetShops(Player player);
}
