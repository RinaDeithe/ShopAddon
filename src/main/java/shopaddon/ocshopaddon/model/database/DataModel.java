package shopaddon.ocshopaddon.model.database;

import shopaddon.ocshopaddon.model.shop.Shop;

import java.util.ArrayList;

public interface DataModel {
    void createShop(Shop shop);
    Shop getShop(String shopUID);
    ArrayList<Shop> getShopList();

    void removeShop(String shopUID);

    void updateShop(Shop shop);

    void resetShops();
}
