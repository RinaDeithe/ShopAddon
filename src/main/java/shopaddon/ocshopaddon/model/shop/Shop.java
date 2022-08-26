package shopaddon.ocshopaddon.model.shop;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Shop {

    private ProtectedRegion region;
    private String UID;
    private String shopName;
    private String ownerUUID;
    private final ArrayList<String> members;

    public Shop(ProtectedRegion region) {
        this.UID = region.getId();
        members = new ArrayList<>();
        ownerUUID = "server";
        this.region = region;
        addMembers();
    }

    public Shop(ProtectedRegion region, String ownerUUID, String shopName) {
        this.UID = region.getId();
        members = new ArrayList<>(region.getMembers().getPlayers());
        this.region = region;
        this.ownerUUID = ownerUUID;
        this.shopName = shopName;
        addMembers();
    }

    private void addMembers() {
        DefaultDomain regionMembers = region.getMembers();
        for (String index : members) {
            regionMembers.addPlayer(UUID.fromString(index));
        }
        if (!ownerUUID.equalsIgnoreCase("server"))
            regionMembers.addPlayer(UUID.fromString(ownerUUID));
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
        this.UID = region.getId();
    }
}
