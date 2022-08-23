package shopaddon.ocshopaddon.model.shop;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import shopaddon.ocshopaddon.model.database.DataModel;
import shopaddon.ocshopaddon.util.Feedback;

import java.util.ArrayList;

public class ShopPoolGuard implements ShopPool{

    private final ShopPoolExec executor;
    private final DataModel dataModel;

    public ShopPoolGuard(DataModel dataModel) {
        executor = new ShopPoolExec(dataModel);
        this.dataModel = dataModel;
    }

    @Override
    public void createShop(Player player, String shopUID) {
        ArrayList<Shop> list = dataModel.getShopList();

        for (Shop index : list) {
            if (index.getOwner().equals(player)) {
                Feedback.INSTANCE.ShopAlreadyExists(player);
                return;
            }
        }

        executor.createShop(player, shopUID);
        Feedback.INSTANCE.shopCreated(player);
    }

    @Override
    public void removeShop(Player player, String shopUID) {
        ArrayList<Shop> list = dataModel.getShopList();

        for (Shop index : list) {
            if (index.getOwner().equals(player)) {
                executor.removeShop(player, shopUID);
                return;
            }
        }

        Feedback.INSTANCE.shopNotFound(player);
    }

    @Override
    public Shop getShop(Player player, String shopUID) {
        ArrayList<Shop> list = dataModel.getShopList();

        for (Shop index : list) {
            if (index.getShopUID().equals(shopUID)) {
                return executor.getShop(player);
            }
        }
        return null;
    }

    @Override
    public Shop getShop(Player player) {
        ArrayList<Shop> list = dataModel.getShopList();

        for (Shop index : list) {
            if (index.getOwner().equals(player)) {
                return executor.getShop(player);
            }
        }
        return null;
    }

    @Override
    public void setOwner(Player player, String shopUID) {
        ArrayList<Shop> list = dataModel.getShopList();

        for (Shop index : list) {
            if (index.getShopUID().equals(shopUID)) {
                Feedback.INSTANCE.shopOwnerAdded(player);
                executor.setOwner(player, shopUID);
                return;
            }
        }

        Feedback.INSTANCE.shopNotFound(player);
    }

    @Override
    public void removeOwner(Player owner, String shopUID) {
        ArrayList<Shop> list = dataModel.getShopList();

        for (Shop index : list) {
            if (index.getShopUID().equals(shopUID)) {
                Feedback.INSTANCE.shopRemoved(owner);
                executor.removeOwner(owner, shopUID);
                return;
            }
        }

        Feedback.INSTANCE.shopNotFound(owner);
    }

    @Override
    public void setRegion(Player player, String shopUID, ProtectedRegion region) {
        ArrayList<Shop> list = dataModel.getShopList();

        for (Shop index : list) {
            if (index.getShopUID().equals(shopUID)) {
                Feedback.INSTANCE.shopRegionSet(player);
                executor.setRegion(player, shopUID, region);
                return;
            }
        }

        Feedback.INSTANCE.shopNotFound(player);

    }

    @Override
    public ArrayList<Shop> getShopList() {
        return executor.getShopList();
    }

    @Override
    public void setName(String currentUID, String newUID) {

    }
}