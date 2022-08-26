package shopaddon.ocshopaddon.commands;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import shopaddon.ocshopaddon.core.RegionHandler;
import shopaddon.ocshopaddon.model.database.DataModel;
import shopaddon.ocshopaddon.model.shop.Shop;
import shopaddon.ocshopaddon.util.Feedback;

public class AdminCommand {

	private final DataModel dataModel;

	public AdminCommand(DataModel dataModel) {

		this.dataModel = dataModel;
	}

	public boolean removeOwner(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(strings.length == 1 && commandSender instanceof Player))
			return false;

		Player player = (Player) commandSender;

		return removeOwner(player, strings[0]);
	}

	public boolean setOwner(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(strings.length == 2 && commandSender instanceof Player))
			return false;

		Player player = (Player) commandSender;

		return setOwner(player, strings[0], Bukkit.getPlayer(strings[1]));

	}

	public boolean createShop(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(strings.length == 1 && commandSender instanceof Player))
			return false;

		Player player = (Player) commandSender;

		return createShop(player, strings[0]);

	}

	public boolean removeShop(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(strings.length == 1 && commandSender instanceof Player))
			return false;

		Player player = (Player) commandSender;

		return removeShop(player, strings[0]);

	}

	public boolean removeExpired(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(strings.length == 1 && commandSender instanceof Player))
			return false;

		Player player = (Player) commandSender;

		return removeExpired(player);
	}

	private boolean removeExpired(Player player) {

		Feedback.INSTANCE.shopReset(player);
		dataModel.resetShops();
		return true;
	}

	private boolean removeOwner(Player player, String shopUID) {

		Shop shop = dataModel.getShop(shopUID);

		if (shop == null)
			Feedback.INSTANCE.shopNotFound(player);
		else {
			shop.setOwnerUUID("server");

			dataModel.updateShop(shop);

			return true;
		}

		return false;
	}

	private boolean setOwner(Player player, String shopUID, Player newOwner) {

		Shop shop = dataModel.getShop(shopUID);

		if (shop == null)
			Feedback.INSTANCE.shopNotFound(player);
		else if (newOwner == null)
			Feedback.INSTANCE.playerNotFound(player);
		else {
			shop.setOwnerUUID(newOwner.getUniqueId().toString());

			dataModel.updateShop(shop);

			Feedback.INSTANCE.shopOwnerAdded(player);

			return true;
		}

		return false;

	}

	private boolean createShop(Player player, String shopUID) {

		ProtectedRegion region = RegionHandler.getRegion(shopUID);

		if (region == null) {
			Feedback.INSTANCE.regionNotFound(player);
			return false;
		}

		Shop shop = new Shop(region);

		if (!(dataModel.getShop(shopUID) == null)) {
			Feedback.INSTANCE.ShopAlreadyExists(player);
			return false;
		}

		dataModel.createShop(shop);
		Feedback.INSTANCE.shopCreated(player);

		return true;

	}

	private boolean removeShop(Player player, String shopUID) {

		if (dataModel.getShop(shopUID) == null) {
			Feedback.INSTANCE.shopNotFound(player);
			return false;
		}

		dataModel.removeShop(shopUID);
		Feedback.INSTANCE.shopRemoved(player);

		return true;

	}


}
