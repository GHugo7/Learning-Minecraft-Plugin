package fr.mister.monpremierplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BonjourCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {

        if (args.length < 1 ) {
            sender.sendMessage("ftg sale pute ! :)");
            return true;
        }

        Player player = (Player) sender;
        String cible = args[0];

        sender.sendMessage("Tu as souhaité la bienvenue à " + cible);

        return true;
    }

}
