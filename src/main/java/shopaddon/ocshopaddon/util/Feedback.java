package shopaddon.ocshopaddon.util;

import org.bukkit.entity.Player;

public enum Feedback {
    INSTANCE;

    private String PREFIX = "[OC] ";

    public void shopBought(Player player) {
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

    public void shopRegionSet(Player player) {
        player.sendMessage(PREFIX + "Added new region to shop.");
    }

    public void notEnoughCash(Player player) {
    }
}
