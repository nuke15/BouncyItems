package net.maxmushroom.bouncyitems;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class BouncyItems extends JavaPlugin {

    private final int BOUNCE_INTERVAL = 20 * 2;
    private final float VELOCITY = 0.5f;
    private BukkitTask bounceTask;

    @Override
    public void onEnable() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        bounceTask = scheduler.runTaskTimer(this, () -> {
            // iterate through all worlds
            for (World world : this.getServer().getWorlds()) {
                // iterate through each item entity
                for (Item item : world.getEntitiesByClass(Item.class)) {
                    // if an item is greater than a minimum distance from a player,
                    // and within that player's render distance,
                    // give the item an upward velocity
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        double distance = player.getLocation().distance(item.getLocation());
                        if (distance > 3 && distance < 20) {
                            item.setVelocity(item.getVelocity().add(new Vector(0, VELOCITY, 0)));
                        }
                    }

                }
            }
        }, 0, BOUNCE_INTERVAL);
    }

    @Override
    public void onDisable() {
        bounceTask.cancel();
    }

}
