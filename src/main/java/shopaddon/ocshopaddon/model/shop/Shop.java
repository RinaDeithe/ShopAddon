package shopaddon.ocshopaddon.model.shop;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Shop {

    private ProtectedRegion region;
    private final String UID;
    private String shopName;
    private String ownerUUID;
    private final ArrayList<String> members;

    public Shop(ProtectedRegion region) {
        this.UID = region.getId();
        members = new ArrayList<>();
        ownerUUID = "server";
        this.region = region;
    }

    public Shop(ProtectedRegion region, String ownerUUID, String shopName) {
        this.UID = region.getId();
        members = new ArrayList<>(region.getMembers().getPlayers());
        this.region = region;
        this.ownerUUID = ownerUUID;
        this.shopName = shopName;
    }

    public void setShopName(String newName) {
        shopName = newName;
    }

    public String getShopUID() {
        return UID;
    }

    public String getOwnerUUID() {
        return ownerUUID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setOwnerUUID(String ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public void setRegion(ProtectedRegion region) {
        this.region = region;
    }
}
