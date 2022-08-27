package shopaddon.ocshopaddon.model.shop;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ShopModel {
    Shop getShop(String shopUID);

    ArrayList<Shop> getShopList();

    void updateShop(Shop shop);

	ArrayList<Shop> getShopList(Player player);
}
