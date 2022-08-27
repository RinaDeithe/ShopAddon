package shopaddon.ocshopaddon.commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import shopaddon.ocshopaddon.model.shop.Shop;
import shopaddon.ocshopaddon.model.shop.ShopModel;
import shopaddon.ocshopaddon.util.Feedback;

import java.util.ArrayList;
import java.util.UUID;

public class UserCommand {

    private final ShopModel model;
    private final Economy econ;
    private final double price = 500.00;

    public UserCommand(ShopModel model, Economy econ) {
        this.model = model;
        this.econ = econ;
    }

    //Command inits

    public boolean addMember(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;

        if (!(commandSender instanceof Player && strings.length == 2)) {
            return false;
        } else
            player = (Player) commandSender;

        return addMember(player, strings[0], Bukkit.getPlayer(strings[1]));
    }

    public boolean removeMember(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;

        if (!(commandSender instanceof Player && strings.length == 2)) {
            return false;
        } else
            player = (Player) commandSender;

        return removeMember(player, strings[0], Bukkit.getPlayer(strings[1]));
    }

    public boolean buy(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;

        if (!(commandSender instanceof Player && strings.length == 1)) {
            return false;
        } else
            player = (Player) commandSender;

        return buyShop(player, strings[0]);
    }

    public boolean sell(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;

        if (!(commandSender instanceof Player && strings.length == 1)) {
            return false;
        } else
            player = (Player) commandSender;

        return sellShop(player, strings[0]);
    }

    public boolean setNick(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;

        if (!(commandSender instanceof Player && strings.length == 2)) {
            return false;
        } else
            player = (Player) commandSender;

        return setNick(player, strings[0], strings[1]);
    }

    public boolean view(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players allowed");
            return false;
        } else
            player = (Player) commandSender;

        player.sendMessage(view());
        return true;
    }

    public boolean viewOwn(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players allowed");
            return false;
        } else
            player = (Player) commandSender;

        player.sendMessage(viewOwn(player));
        return true;
    }

    public boolean viewShop(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;

        if (!(commandSender instanceof Player && strings.length == 1)) {
            return false;
        } else
            player = (Player) commandSender;

        return viewShop(player, strings[0]);
    }

    //Command Conds

    private boolean sellShop(Player player, String shopUID) {
        Shop shop = model.getShop(shopUID);

        if (shop == null) {
            Feedback.INSTANCE.shopNotFound(player);
        } else if (!(shop.getOwnerUUID().equals(player.getUniqueId().toString()))) {
            Feedback.INSTANCE.notOwnerOfShop(player);
        } else {
            shop.setOwnerUUID("server");
            model.updateShop(shop);
            econ.depositPlayer(player, (price * 3 / 4));
            Feedback.INSTANCE.shopSold(player);
            return true;
        }

        return false;
    }

    private boolean buyShop(Player player, String shopUID) {

        Shop shop = model.getShop(shopUID);

        if (shop == null) {
            Feedback.INSTANCE.shopNotFound(player);
        } else if (!(econ.getBalance(player) >= price)) {
            Feedback.INSTANCE.notEnoughCash(player);
        } else if (!(shop.getOwnerUUID().equalsIgnoreCase("server"))) {
            Feedback.INSTANCE.shopBelongsToOthers(player);
        } else {
            econ.withdrawPlayer(player, price);
            shop.setOwnerUUID(player.getUniqueId().toString());
            model.updateShop(shop);
            Feedback.INSTANCE.shopBought(player, shopUID);
            return true;
        }
        return false;
    }

    private String view() {
        StringBuilder returnString = new StringBuilder("Current shops in use:");
        ArrayList<Shop> shopList = model.getShopList();

        for (Shop index : shopList) {
            returnString.append("\n").append(index.getShopUID());
        }

        return returnString.toString();
    }

    private boolean setNick(Player player, String shopUID, String shopNick) {
        Shop shop = model.getShop(shopUID);

        if (shop == null) {
            Feedback.INSTANCE.shopNotFound(player);
        } else if (!(shop.getOwnerUUID().equals(player.getUniqueId().toString()))) {
            Feedback.INSTANCE.notOwnerOfShop(player);
        } else {
            shop.setShopNick(shopNick);
            Feedback.INSTANCE.shopRenamed(player, shopNick);
            return true;
        }

        return false;
    }

    private boolean addMember(Player player, String shopUID, Player member) {
        Shop shop = model.getShop(shopUID);

        if (shop == null) {
            Feedback.INSTANCE.shopNotFound(player);
        } else if (!(shop.getOwnerUUID().equals(player.getUniqueId().toString()))) {
            Feedback.INSTANCE.notOwnerOfShop(player);
        } else if (member == null) {
            Feedback.INSTANCE.playerNotFound(player);
        } else if (shop.getMemberUUIDList().contains(member.getUniqueId().toString())) {
            Feedback.INSTANCE.memberAlreadyAdded(player);
        } else {
            shop.addMember(member);

            model.updateShop(shop);
            Feedback.INSTANCE.memberAdded(player);
            return true;
        }
        return false;
    }

    private boolean removeMember(Player player, String shopUID, Player member) {
        Shop shop = model.getShop(shopUID);

        if (shop == null) {
            Feedback.INSTANCE.shopNotFound(player);
        } else if (!(shop.getOwnerUUID().equals(player.getUniqueId().toString()))) {
            Feedback.INSTANCE.notOwnerOfShop(player);
        } else if (member == null) {
            Feedback.INSTANCE.playerNotFound(player);
        } else if (!shop.getMemberUUIDList().contains(member.getUniqueId().toString())) {
            Feedback.INSTANCE.memberNotPartOfShop(player);
        } else {
            shop.removeMember(member);

            model.updateShop(shop);
            Feedback.INSTANCE.memberRemoved(player);
            return true;
        }
        return false;

    }

    private boolean viewShop(Player player, String shopUID) {

        Shop shop = model.getShop(shopUID);

        if (shop == null) {
            Feedback.INSTANCE.shopNotFound(player);
            return false;
        } else {
            StringBuilder returnString = new StringBuilder("Shop §6" + shopUID + "§f:");

            returnString.append("\n").append("Shop nick: ").append(shop.getShopNick());
            returnString.append("\n").append("Owner: ").append(Bukkit.getOfflinePlayer(UUID.fromString(shop.getOwnerUUID())));
            returnString.append("\n").append("Members: ");

            for (String index : shop.getMemberUUIDList()) {
                returnString.append("\n    ").append(Bukkit.getOfflinePlayer(UUID.fromString(index)));
            }

            player.sendMessage(returnString.toString());

            return true;
        }
    }

    private String viewOwn(Player player) {

        StringBuilder returnString = new StringBuilder("You shops:");

        for (Shop index : model.getShopList(player)) {
            returnString.append("\n").append(index.getShopNick());
        }


        return returnString.toString();

    }

}
