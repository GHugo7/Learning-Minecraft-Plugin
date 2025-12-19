package fr.mister.monpremierplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Seul un joueur est autorisé à éxecuté cette commande !");
            return true;
        }

        Player p = (Player) sender;

        switch (msg.toLowerCase()) {
            case "tp":
            case "teleport":
                return handleTeleport(p, args);

            case "tph":
            case "teleporthere":
                return handleTeleportHere(p, args);

            case "tpall":
            case "teleportall":
                return handleTeleportAll(p, args);

            default:
                return false;
        }
    }

    private boolean handleTeleport(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Usage: /tp <joueur>");
            return false;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            p.sendMessage(ChatColor.RED + "Usage: /tp <joueur>");
            return false;
        }

        p.teleport(target.getLocation());

        p.sendMessage(ChatColor.GREEN + "✅ Téléporté à " + target.getName() + " !");
        target.sendMessage(ChatColor.YELLOW + p.getName() + " s'est téléporté à toi.");
        return true;
    }

    private boolean handleTeleportHere(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Usage: /tp <joueur>");
            return false;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            p.sendMessage(ChatColor.RED + "Usage: /tp <joueur>");
            return false;
        }

        target.teleport(p.getLocation());

        p.sendMessage(ChatColor.GREEN + "✅ Tu as Téléporté " + target.getName() + " vers toi !");
        target.sendMessage(ChatColor.YELLOW + p.getName() + " t'a téléporté vers lui.");
        return true;
    }

    private boolean handleTeleportAll(Player p, String[] args) {
        if (args.length == 0) {
            return  false;
        }
        return true;
    }
}
