package net.terrocidepvp.commandforall;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandForAll extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getCommand("pexecute").setExecutor(this);
        getLogger().info("Successfully enabled! Remember to use the #player placeholder through the /pexecute command.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Perm check.
        if (!sender.hasPermission("commandforall.use")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }
        // Length check.
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You need to enter '/pexecute {command with #player placeholder}'!");
            return true;
        }
        // Grab string from command.
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String str = StringUtils.removeEnd(sb.toString().trim(), " ");
        // Check for placeholder.
        if (!str.contains("#player")) {
            sender.sendMessage(ChatColor.RED + "Ypu need to use the #player placeholder in your command!");
            return true;
        }
        // Dispatch command for all players.
        for (Player player : getServer().getOnlinePlayers()) {
            getServer().dispatchCommand(getServer().getConsoleSender(), str.replace("#player", player.getName()));
        }
        sender.sendMessage(ChatColor.DARK_GREEN + "Dispatched command for all players: " + ChatColor.GREEN + str);
        return true;
    }

}
