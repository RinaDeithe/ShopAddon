package shopaddon.ocshopaddon.model.shop;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Shop {

    private ProtectedRegion region;
    private String UID;
    private String shopNick;
    private String ownerUUID;
    private final ArrayList<String> memberUUIDList;

    public Shop(ProtectedRegion region) {
        this.UID = region.getId();
        memberUUIDList = new ArrayList<>();
        ownerUUID = "server";
        this.region = region;
        updateRegion();
    }

    public Shop(ProtectedRegion region, String ownerUUID, String shopName) {
        this.UID = region.getId();
        memberUUIDList = new ArrayList<>(region.getMembers().getPlayers());
        this.region = region;
        this.ownerUUID = ownerUUID;
        this.shopNick = shopName;
        updateRegion();
    }

    private void updateRegion() {
        DefaultDomain regionMembers = region.getMembers();
        for (String index : memberUUIDList) {
            regionMembers.addPlayer(UUID.fromString(index));
        }
        if (!ownerUUID.equalsIgnoreCase("server"))
            regionMembers.addPlayer(UUID.fromString(ownerUUID));
    }

    public void addMember(Player newMember) {
        memberUUIDList.add(newMember.getUniqueId().toString());
        updateRegion();
    }

    public void setShopNick(String newName) {
        shopNick = newName;
    }

    public String getShopUID() {
        return UID;
    }

    public String getOwnerUUID() {
        return ownerUUID;
    }

    public String getShopNick() {
        return shopNick;
    }

    public void setOwnerUUID(String ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public void setRegion(ProtectedRegion region) {
        this.region = region;
        this.UID = region.getId();
    }

    public ArrayList<String> getMemberUUIDList() {
        return memberUUIDList;
    }

	public void removeMember(Player member) {
        memberUUIDList.remove(member.getUniqueId().toString());
        updateRegion();
	}
}
