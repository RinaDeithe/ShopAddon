package shopaddon.ocshopaddon.model.shop;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Shop {

    private ProtectedRegion region;
    private final String UID;
    private String shopName;
    private Player owner;
    private final ArrayList<String> members;

    public Shop(ProtectedRegion region) {
        this.UID = region.getId();
        members = new ArrayList<>();
        this.region = region;
    }

    public Shop(ProtectedRegion region, Player owner, String shopName) {
        this.UID = region.getId();
        members = new ArrayList<>(region.getMembers().getPlayers());
        this.region = region;
        this.owner = owner;
        this.shopName = shopName;
    }

    public void setShopName(String newName) {
        shopName = newName;
    }

    public String getShopUID() {
        return UID;
    }

    public Player getOwner() {
        return owner;
    }

    public String getShopName() {
        return shopName;
    }

    public String getRegionId() {
        return region.getId();
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setRegion(ProtectedRegion region) {
        this.region = region;
    }
}
