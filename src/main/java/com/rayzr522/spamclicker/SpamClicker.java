
package com.rayzr522.spamclicker;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Strings;

import net.md_5.bungee.api.ChatColor;

public class SpamClicker extends JavaPlugin implements Listener, CommandExecutor {

    private static final String HORIZONTAL_BAR = ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + Strings.repeat("-", 50);

    private boolean             ENABLED        = true;
    private FileConfiguration   config;

    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);

        loadConfig();

        getCommand("SpamClicker").setExecutor(this);

        getLogger().info("SpamClicker v" + getDescription().getVersion() + " loaded. Click click click click click click :)");

    }

    public void loadConfig() {

        File configFile = getFile("config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", true);
        }

        reloadConfig();
        config = getConfig();

        ENABLED = config.getBoolean("enabled");

    }

    public void onDisable() {

        getLogger().info("SpamClicker v" + getDescription().getVersion() + " unloaded. Click, *wait*, click, *wait*, click, *rage*");

    }

    public File getFile(String path) {

        return new File(getDataFolder() + File.separator + path);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogIn(PlayerJoinEvent e) {

        apply(e.getPlayer());

    }

    public void apply(Player p) {

        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(ENABLED ? 1024 : 4);
        p.saveData();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getLabel().equals("spamclicker")) {

            return true;

        }

        if (args.length < 1) {

            showHelp(sender);

        } else {

            if (args[0].equalsIgnoreCase("reload")) {

                boolean oldEnabled = ENABLED;

                loadConfig();

                if (oldEnabled != ENABLED) {

                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {

                        apply(p);

                    }

                }

                sender.sendMessage(ChatColor.GREEN + "SpamClicker" + ChatColor.AQUA + " config reloaded");

            } else if (args[0].equals("version")) {

                sender.sendMessage(ChatColor.AQUA + "You're running " + ChatColor.GREEN + "v" + getDescription().getVersion() + ChatColor.AQUA + " of " + ChatColor.GREEN + "SpamClicker");

            } else {

                sender.sendMessage(ChatColor.RED + "Unknown command '" + args[0] + "'");
                showHelp(sender);

            }

        }

        return true;

    }

    public void showHelp(CommandSender sender) {

        sender.sendMessage(HORIZONTAL_BAR);

        sender.sendMessage(ChatColor.GREEN + "/SpamClicker reload: " + ChatColor.GRAY + "Reloads the config");
        sender.sendMessage(ChatColor.GREEN + "/SpamClicker version: " + ChatColor.GRAY + "Shows the version of SpamClicker");

        sender.sendMessage(HORIZONTAL_BAR);

    }

}
