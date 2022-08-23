package shopaddon.ocshopaddon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import shopaddon.ocshopaddon.core.RegionHandler;
import shopaddon.ocshopaddon.model.shop.Shop;
import shopaddon.ocshopaddon.model.shop.ShopModel;

import java.util.ArrayList;

public class AdminCommand {

    private final ShopModel shopModel;

    public AdminCommand(ShopModel shopModel) {

        this.shopModel = shopModel;
    }

    public boolean commands(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Command can only be executed by a player.");
            return true;
        }

        Player admin = (Player) commandSender;

        if (strings.length < 2) {
            admin.sendMessage("You need a bit more on your command");
            return false;
        }

        if (strings[0].equalsIgnoreCase("remove")) {
            removeShop(admin, strings);
            return true;
        }
        else if (strings[0].equalsIgnoreCase("create")){
            createShop(admin, strings);
            return true;
        }
        else if (strings[0].equalsIgnoreCase("getShops")) {
            StringBuilder returnString = new StringBuilder("Current shops in use:");
            ArrayList<Shop> shopList = shopModel.getShopList();

            for (Shop index : shopList) {
                returnString.append("\n").append(index.getShopUID());
            }

            admin.sendMessage(returnString.toString());
            return true;
        }
        else if (strings[0].equalsIgnoreCase("expired")) {
            resetShops(admin);
            return true;
        }
        else if (strings[0].equalsIgnoreCase("setregion") && strings.length > 2) {
            setRegion(admin, strings);
            return true;
        }
        else
            return false;

    }

    private void resetShops(Player player) {
        shopModel.resetShops(player);
    }

    private void setRegion(Player player, String[] strings) {
        shopModel.setRegion(player, strings[1] ,RegionHandler.getRegion(strings[2]));
    }

    private void removeShop(Player player, String[] strings) {
        shopModel.removeShop(player, strings[1]);
    }

    private void createShop(Player player, String[] strings) {
        shopModel.createShop(player, strings[1]);
    }
}
