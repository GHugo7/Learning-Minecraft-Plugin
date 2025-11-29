package  fr.mister.monpremierplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Seul un joueur est autorisé à éxecuté cette commande !");
            return true;
        }
        Player player = (Player) sender;

        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.sendMessage(ChatColor.GREEN + "✓ Tu as été soigné !");

        return true;
    }
}
