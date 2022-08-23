package shopaddon.ocshopaddon.model.shop;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import shopaddon.ocshopaddon.core.RegionHandler;
import shopaddon.ocshopaddon.model.database.DataModel;

import java.util.ArrayList;

public class ShopPoolExec implements ShopPool{

    DataModel dataModel;

    public ShopPoolExec(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @Override
    public void createShop(Player player, String shop) {
        dataModel.createShop(
                new Shop(
                        RegionHandler.getRegion(shop)
                )
        );
    }

    @Override
    public void removeShop(Player player, String shopUID) {
        dataModel.removeShop(shopUID);
    }

    @Override
    public Shop getShop(Player player, String shopUID) {
        return dataModel.getShop(shopUID);
    }

    @Override
    public Shop getShop(Player player) {
        for (Shop index : dataModel.getShopList()) {
            if (index.getOwnerUUID().equals(player.getUniqueId().toString()))
                return index;
        }
        return null;
    }

    @Override
    public void setOwner(Player player, String shopUID) {
        Shop shop = dataModel.getShop(shopUID);
        shop.setOwnerUUID(player.getUniqueId().toString());

        dataModel.updateShop(shop);
    }

    @Override
    public void removeOwner(Player owner, String shopUID) {
        Shop shop = dataModel.getShop(shopUID);
        shop.setOwnerUUID("server");

        dataModel.updateShop(shop);
    }

    @Override
    public void setRegion(Player player, String shopUID, ProtectedRegion region) {
        Shop shop = dataModel.getShop(shopUID);
        shop.setRegion(region);

        dataModel.updateShop(shop);
    }

    @Override
    public ArrayList<Shop> getShopList() {
        return dataModel.getShopList();
    }

    @Override
    public void setName(Player player, String currentID, String newID) {
        Shop shop = dataModel.getShop(currentID);
        shop.setShopName(newID);

        dataModel.updateShop(shop);
    }

    @Override
    public void resetShops(Player player) {
        dataModel.resetShops();
    }
}
