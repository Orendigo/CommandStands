
/**
 * Util Class
 */

package net.orendigo.commandstands.utility;

import java.text.DecimalFormat;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

/**
 * @author Orendigo
 */
public class Util {
    
    /*  This is pretty much a reimplementation of Skript's getTarget method, which
     *  I used in the first version of this plugin when it was wrriten in skript
     *  https://github.com/Njol/Skript/blob/master/src/main/java/ch/njol/skript/util/Utils.java#L109
     */ 
    public static <T extends Entity> T getTarget(final Player sender) {
        T target = null;
        double targetDistanceSquared = 0.0;
        final Vector l = sender.getEyeLocation().toVector();
        final Vector n = sender.getLocation().getDirection().normalize();
        final double cos45 = Math.cos(0.7853981633974483);
        for (final Entity other : sender.getWorld().getEntities()) {
            if (other != null) {
                if (other == sender) {
                    continue;
                }
                if (target != null && targetDistanceSquared <= other.getLocation().distanceSquared(sender.getLocation())) {
                    continue;
                }
                final Vector t = other.getLocation().add(0.0, 1.0, 0.0).toVector().subtract(l);
                if (n.clone().crossProduct(t).lengthSquared() >= 1.0 || t.normalize().dot(n) < cos45) {
                    continue;
                }
                target = (T) other;
                targetDistanceSquared = target.getLocation().distanceSquared(sender.getLocation());
            }
        }
        return target;
    }
    
    public static ArmorStand eventTargetFinder(final Player player) {
        final List<Entity> nearEntities = (List<Entity>)player.getNearbyEntities(3.0, 3.0, 3.0);
        for (final Entity nearEntity : nearEntities) {
            if (nearEntity.hasMetadata("PickUpPlayer")) {
                final List<MetadataValue> pickupID = (List<MetadataValue>)nearEntity.getMetadata("PickUpPlayer");
                if (player.getUniqueId().toString().equals(pickupID.get(0).asString())) {
                    return (ArmorStand)nearEntity;
                }
            }
        }
        return null;
    }
    
    public static boolean withinDistance(final Player sender, final ArmorStand target) {
        final Location l1 = sender.getLocation();
        final Location l2 = target.getLocation();
        return l1.distance(l2) < 3.0;
    }
    
    public static Double convertRadian(final Double d) {
        return d * 0.017453292519943295;
    }
    
    public static Double convertDegree(final Double r) {
        return r * 57.29577951308232;
    }
    
    public static void getRotationInfo(final Player sender, final String posePart, final EulerAngle standPose) {
        final DecimalFormat numberFormat = new DecimalFormat("#.000");
        sender.sendMessage(ChatColor.RED + posePart + ":" + ChatColor.BLUE + " X: " + ChatColor.DARK_AQUA + numberFormat.format(convertDegree(standPose.getX())) + ChatColor.BLUE + " Y: " + ChatColor.DARK_AQUA + numberFormat.format(convertDegree(standPose.getY())) + ChatColor.BLUE + " Z: " + ChatColor.DARK_AQUA + numberFormat.format(convertDegree(standPose.getZ())));
    }
    
    public static EulerAngle modifyStand(final Player player, final EulerAngle standPose, final Double d, final String axis, final Boolean add) {
        Double newVal = convertRadian(d);
        switch (axis) {
            case "x": {
                if (add) {
                    newVal += standPose.getX();
                }
                final EulerAngle newAngle = new EulerAngle((double)newVal, standPose.getY(), standPose.getZ());
                return newAngle;
            }
            case "y": {
                if (add) {
                    newVal += standPose.getY();
                }
                final EulerAngle newAngle = new EulerAngle(standPose.getX(), (double)newVal, standPose.getZ());
                return newAngle;
            }
            case "z": {
                if (add) {
                    newVal += standPose.getZ();
                }
                final EulerAngle newAngle = new EulerAngle(standPose.getX(), standPose.getY(), (double)newVal);
                return newAngle;
            }
            default:
                return standPose;
        }
    }
}
