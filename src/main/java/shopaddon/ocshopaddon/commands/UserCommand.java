package shopaddon.ocshopaddon.commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import shopaddon.ocshopaddon.model.shop.Shop;
import shopaddon.ocshopaddon.model.shop.ShopModel;
import shopaddon.ocshopaddon.util.Feedback;

import java.util.ArrayList;

public class UserCommand {

    private final ShopModel model;
    private final Economy econ;
    private final double price = 500.00;

    public UserCommand(ShopModel model, Economy econ) {
        this.model = model;
        this.econ = econ;
    }

    public boolean shop(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player;

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players allowed");
            return false;
        } else
            player = (Player) commandSender;

        if (strings.length < 2)
            return false;
        else if (strings[1].equals("view")) {
            getShops(player);
            return true;
        }

        if (strings.length < 3)
            return false;
        else if (strings[1].equals("buy")) {
            buy(player, strings);
            return true;
        } else if (strings[1].equals("sell")) {
            sell(player, strings);
            return true;
        }

        if (strings.length < 4)
            return false;
        else if (strings[1].equals("rename")) {
            setName(player, strings);
            return true;
        }
        return false;
    }

    public void buy(Player player, String[] strings) {
        if (econ.getBalance(player) > price) {
            econ.withdrawPlayer(player, price);
            model.setOwner(strings[2], player);
        } else
            Feedback.INSTANCE.notEnoughCash(player);
    }

    public void sell(Player player, String[] strings) {
        model.removeOwner(player, strings[2]);
        econ.depositPlayer(player, (price * 3/4));
    }

    public void setName(Player player, String[] strings) {
        model.setName(player, strings[2], strings[3]);
    }

    public void getShops(Player player) {
        StringBuilder returnString = new StringBuilder("Current shops in use:");
        ArrayList<Shop> shopList = model.getShopList();

        for (Shop index : shopList) {
            returnString.append("\n").append(index.getShopUID());
        }

        player.sendMessage(returnString.toString());
    }


}
