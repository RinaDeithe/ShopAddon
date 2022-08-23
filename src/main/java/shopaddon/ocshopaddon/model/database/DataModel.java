package shopaddon.ocshopaddon.model.database;

import org.bukkit.entity.Player;
import shopaddon.ocshopaddon.model.shop.Shop;

import java.util.ArrayList;

public interface DataModel {
    void createShop(Shop shop);
    Shop getShop(String shopUID);

    void resetInactive(Player player);
    void addInactive();
    ArrayList<Shop> getShopList();

    void removeShop(String shopUID);

    void updateShop(Shop shop);
}
