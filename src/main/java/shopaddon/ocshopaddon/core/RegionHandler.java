package shopaddon.ocshopaddon.core;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class RegionHandler {

    private static World world = Bukkit.getWorld("world");
    private static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    private static RegionManager regionManager = container.get(BukkitAdapter.adapt(world));


    public static ProtectedRegion getRegion(String ID) {
        return regionManager.getRegion(ID);
    }
}
