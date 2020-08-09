
/**
 * WorldGuardManager Class
 * A lot of the structure of this project is taken from
 * RFChairs: https://github.com/RifleDLuffy/RFChairs
 */

package net.orendigo.commandstands.externalmanagers;

import net.orendigo.commandstands.CommandStands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * @author Orendigo
 */
public class WorldGuardManager {

    private final CommandStands plugin = CommandStands.getInstance();
    public WorldGuard worldGuard;
    public WorldGuardPlugin worldGuardPlugin;
    public RegionContainer container;
    public static Flag flag;
    
    public WorldGuardManager() {}
	
    public void setup() {
        worldGuard = WorldGuard.getInstance();
    }
    
    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        if (!(plugin instanceof WorldGuardPlugin)) return null;
        return (WorldGuardPlugin) plugin;
    }
    
    public Boolean canBuild(Player player, Location location) {
        final RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        final com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
        final World world = BukkitAdapter.adapt(location.getWorld());
        if (!WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(WorldGuardPlugin.inst().wrapPlayer(player), world))
            return query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(player), new StateFlag[] { Flags.BUILD });
        return true;
    }
    
}
