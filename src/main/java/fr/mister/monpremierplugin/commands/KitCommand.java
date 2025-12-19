package fr.mister.monpremierplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.HashMap;

public class KitCommand implements CommandExecutor {

    // HashMap pour stocker les cooldowns
    // Clé = "pseudo:kit", Valeur = timestamp (millisecondes)
    private HashMap<String, Long> cooldowns = new HashMap<>();

    // Durée du cooldown en secondes (5 minutes = 300 secondes)
    private static final long COOLDOWN_TIME = 300;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Seul un joueur est autorisé à éxecuter cette commande !");
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Usage: /kit <starter|builder|pvp|list>");
            return true;
        }

        String kitName = args[0].toLowerCase();


        switch (kitName) {
            case "starter":
                return giveStarterKit(p);

            case "builder":
                return giveBuilderKit(p);

            case "pvp":
                return givePvpKit(p);

            case "list":
                return kitList(p);

            default:
                p.sendMessage(ChatColor.RED + "KitName non valide ! Utilise /kit list");
                return true;
        }
    }

    private boolean checkCooldown(Player p, String kitName) {
        String key = p.getName() + ":" + kitName;

        if (cooldowns.containsKey(key)) {
            long lastUsed = cooldowns.get(key);
            long CurrentTime = System.currentTimeMillis();
            long timePassed = CurrentTime - lastUsed / 1000;

            if (timePassed > COOLDOWN_TIME) {
                long timeLeft = COOLDOWN_TIME - timePassed;

                long minutes = timeLeft / 60;
                long seconds = timeLeft % 60;

                p.sendMessage(ChatColor.RED + "Cooldown actif ! Attends encore " +
                        ChatColor.YELLOW + minutes + "m " + seconds + "s" +
                        ChatColor.YELLOW + " avant de réutiliser ce kit.");
                return false;
            }
        }
        cooldowns.put(key, System.currentTimeMillis());
        return true;
    }

    private static int CountEmptySlots(Inventory inv) {
        int count = 0;
        for (ItemStack item : inv.getStorageContents()) {
            if (item == null || item.getType() == Material.AIR) {
                count++;
            }
        }
        return count;
    }

    private boolean giveStarterKit(Player p) {

        if (!p.hasPermission("kits.starter")) {
            p.sendMessage(ChatColor.RED + "Tu n'as pas accès à cette commande !");
            return true;
        }

        if (!checkCooldown(p, "starter")) {
            return true;
        }

        if (CountEmptySlots(p.getInventory()) >= 3) {
            p.getInventory().addItem(
                    new ItemStack(Material.COOKED_BEEF, 32),
                    new ItemStack(Material.WOODEN_SWORD),
                    new ItemStack(Material.STONE_PICKAXE)
            );
            p.sendMessage(ChatColor.GREEN + "Tu viens de recevoir le kit Starter !");
        } else {
            p.sendMessage(ChatColor.RED + "Tu n'as pas assez de place dans l'inventaire !");
        }

        return true;
    }


    private boolean giveBuilderKit(Player p) {

        if (!p.hasPermission("kits.builder")) {
            p.sendMessage(ChatColor.RED + "Tu n'as pas accès à cette commande !");
            return true;
        }

        if (!checkCooldown(p, "builder")) {
            return true;
        }

        if (CountEmptySlots(p.getInventory()) >= 3) {
            p.getInventory().addItem(
                    new ItemStack(Material.OAK_PLANKS, 64),
                    new ItemStack(Material.COBBLESTONE, 32),
                    new ItemStack(Material.GLASS, 16)
            );
            p.sendMessage(ChatColor.GREEN + "Tu viens de recevoir le kit Builder !");
        } else {
            p.sendMessage(ChatColor.RED + "Tu n'as pas assez de place dans l'inventaire !");
        }

        return true;
    }

    private boolean givePvpKit(Player p) {

        ItemStack healingPotion = new ItemStack(Material.POTION, 5);
        PotionMeta meta = (PotionMeta) healingPotion.getItemMeta();

        meta.setBasePotionType(PotionType.HEALING);
        healingPotion.setItemMeta(meta);

        if (!p.hasPermission("kits.pvp")) {
            p.sendMessage(ChatColor.RED + "Tu n'as pas accès à cette commande !");
            return true;
        }

        if (!checkCooldown(p, "pvp")) {
            return true;
        }

        if (CountEmptySlots(p.getInventory()) >= 6) {
            p.getInventory().addItem(
                    new ItemStack(Material.IRON_SWORD),
                    new ItemStack(Material.IRON_HELMET),
                    new ItemStack(Material.IRON_CHESTPLATE),
                    new ItemStack(Material.IRON_LEGGINGS),
                    new ItemStack(Material.IRON_BOOTS),
                    healingPotion
            );
            p.sendMessage(ChatColor.GREEN + "Tu viens de recevoir le kit PvP !");
        } else {
            p.sendMessage(ChatColor.RED + "Tu n'as pas assez de place dans l'inventaire !");
        }

        return true;
    }

    private boolean kitList(Player p) {

        if (!p.hasPermission("kits.list")) {
            p.sendMessage(ChatColor.RED + "Tu n'as pas accès à cette commande !");
            return true;
        }

            p.sendMessage(ChatColor.GOLD + "========== KITS DISPONIBLES ==========");
            p.sendMessage(ChatColor.YELLOW + "• " + ChatColor.WHITE + "starter " + ChatColor.GRAY + "- Kit de démarrage");
            p.sendMessage(ChatColor.YELLOW + "• " + ChatColor.WHITE + "builder " + ChatColor.GRAY + "- Kit de construction");
            p.sendMessage(ChatColor.YELLOW + "• " + ChatColor.WHITE + "pvp " + ChatColor.GRAY + "- Kit de combat");
            p.sendMessage(ChatColor.GOLD + "======================================");
        return true;
    }
}