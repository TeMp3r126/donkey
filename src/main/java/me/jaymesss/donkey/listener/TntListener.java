package me.jaymesss.donkey.listener;

import me.jaymesss.donkey.Donkey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TntListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (Donkey.getInstance().getConfig().get("tnt").equals("disabled")) {
            event.setCancelled(true);

        }
    }
}
