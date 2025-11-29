package fr.mister.monpremierplugin;

import fr.mister.monpremierplugin.commands.BonjourCommand;
import fr.mister.monpremierplugin.commands.HealCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MonPremierPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("=========================================");
        getLogger().info("Mon Premier plugin démarre ! \uD83D\uDE80");
        getLogger().info("=========================================");
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("bonjour").setExecutor(new BonjourCommand());
    }
    @Override
    public void onDisable() {
        getLogger().info("Mon plugin s'arrête !");
    }
}
