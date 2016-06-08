
package com.rayzr522.spamclicker;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SpamClicker extends JavaPlugin implements Listener {

	public void onEnable() {

		getServer().getPluginManager().registerEvents(this, this);

		getLogger().info("SpamClicker v" + getDescription().getVersion() + " loaded. Click click click click click click :)");

	}

	public void onDisable() {

		getLogger().info("SpamClicker v" + getDescription().getVersion() + " unloaded. Click, *wait*, click, *wait*, click, *rage*");

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogIn(PlayerLoginEvent e) {

		Player p = e.getPlayer();
		p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(1024);
		p.saveData();

	}

}
