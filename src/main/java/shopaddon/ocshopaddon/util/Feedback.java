package shopaddon.ocshopaddon.util;

import org.bukkit.entity.Player;

public enum Feedback {
    INSTANCE;

    private final String PREFIX = "§6[§eOC§6]§7 ";

    public void shopBought(Player player, String string) {
        player.sendMessage(PREFIX + "Successfully bought a shop");
    }

    public void shopSold(Player player) {
        player.sendMessage(PREFIX + "Successfully sold a shop");
    }

    public void shopCreated(Player player) {
        player.sendMessage(PREFIX + "Shop created");
    }

    public void shopRemoved(Player player) {
        player.sendMessage(PREFIX + "Shop removed");
    }

    public void shopNotFound(Player player) {
        player.sendMessage(PREFIX + "Shop not found.");
    }

    public void ShopAlreadyExists(Player player) {
        player.sendMessage(PREFIX + "A shop by that name already exists.");
    }

    public void shopOwnerAdded(Player player) {
        player.sendMessage(PREFIX + "Added new owner.");
    }

    public void notEnoughCash(Player player) {
        player.sendMessage(PREFIX + "Get back when you have more cash.");
    }

    public void shopRenamed(Player player, String newID) {
        player.sendMessage(PREFIX + "Renamed shop to " + newID + ".");
    }

    public void memberAdded(Player player) {
        player.sendMessage(PREFIX + "Member added.");
    }

    public void notOwnerOfShop(Player player) {
        player.sendMessage(PREFIX + "You don't own this shop.");
    }

    public void memberAlreadyAdded(Player player) {
        player.sendMessage(PREFIX + "Member already added.");
    }

    public void playerNotFound(Player player) {
        player.sendMessage(PREFIX + "Player not found.");
    }

    public void shopBelongsToOthers(Player player) {
        player.sendMessage(PREFIX + "Shop belongs to someone else.");
    }

	public void shopReset(Player player) {
        player.sendMessage(PREFIX + "Resetting expired shops.\nYou might want to empty them.");
	}

    public void regionNotFound(Player player) {
        player.sendMessage(PREFIX + "Region not found.");
    }
}
