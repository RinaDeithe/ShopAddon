package shopaddon.ocshopaddon.model.shop;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import shopaddon.ocshopaddon.model.database.DataModel;

import java.util.ArrayList;

public class ShopManager implements ShopModel{

    private final ShopPool pool;

    public ShopManager(DataModel dataModel) {

        pool = new ShopPoolGuard(dataModel);

    }

    @Override
    public void createShop(Player player, String shopUID) {
        pool.createShop(player,shopUID);
    }

    @Override
    public void removeShop(Player player, String shopUID) {
        pool.removeShop(player, shopUID);

    }

    @Override
    public Shop getShop(Player player, String shopUID) {
        return pool.getShop(player, shopUID);
    }

    @Override
    public Shop getShop(Player player) {
        return pool.getShop(player);
    }

    @Override
    public void setRegion(Player player, String shopUID, ProtectedRegion region) {
        pool.setRegion(player, shopUID, region);
    }

    @Override
    public void setOwner(String shopUID, Player player) {
        pool.setOwner(player, shopUID);
    }

    @Override
    public void removeOwner(Player owner, String shopUID) {
        pool.removeOwner(owner, shopUID);
    }

    @Override
    public ArrayList<Shop> getShopList() {
        return pool.getShopList();
    }

    @Override
    public void setName(Player player, String currentID, String newID) {
        pool.setName(player, currentID, newID);
    }

    @Override
    public void resetShops(Player player) {
        pool.resetShops(player);
    }
}
