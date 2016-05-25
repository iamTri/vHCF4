/*    */ package io.louis.core.listeners;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import io.louis.core.utils.Cooldowns;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class GodAppleListener implements org.bukkit.event.Listener
/*    */ {
/*    */   private Core mainPlugin;
/* 19 */   Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("vHCF");
/*    */   
/* 21 */   public static HashMap<String, Boolean> cooldown = new HashMap();
/* 22 */   ItemStack enchantedApple = new ItemStack(Material.GOLDEN_APPLE, 1, (short)1);
/*    */   
/*    */   public GodAppleListener(Core mainPlugin) {
/* 25 */     this.mainPlugin = mainPlugin;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
/* 30 */     ItemStack inHand = e.getItem();
/* 31 */     if ((inHand.getType().equals(Material.GOLDEN_APPLE)) && (inHand.getDurability() == this.enchantedApple.getDurability()))
/*    */     {
/* 33 */       Player p = e.getPlayer();
/* 34 */       if (Cooldowns.isOnCooldown("gapple_cooldown", p))
/*    */       {
/* 36 */         e.setCancelled(true);
/* 37 */         e.getPlayer().updateInventory();
/* 38 */         p.sendMessage("");
/* 39 */         p.sendMessage(ChatColor.DARK_GREEN + "███" + ChatColor.BLACK + "█" + ChatColor.DARK_GREEN + "████");
/* 40 */         p.sendMessage(ChatColor.DARK_GREEN + "██" + ChatColor.GOLD + "████" + ChatColor.DARK_GREEN + "██ " + ChatColor.GOLD + "Super Golden Apple:");
/* 41 */         p.sendMessage(ChatColor.DARK_GREEN + "█" + ChatColor.GOLD + "██" + ChatColor.WHITE + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_GREEN + "█  " + ChatColor.GREEN + "Consumed");
/* 42 */         p.sendMessage(ChatColor.DARK_GREEN + "█" + ChatColor.GOLD + "█" + ChatColor.WHITE + "█" + ChatColor.GOLD + "████" + ChatColor.DARK_GREEN + "█ " + ChatColor.YELLOW + "Cooldown Remaining:");
/* 43 */         p.sendMessage(ChatColor.DARK_GREEN + "█" + ChatColor.GOLD + "██████" + ChatColor.DARK_GREEN + "█  " + ChatColor.BLUE + Cooldowns.getCooldownForPlayerInt("gapple_cooldown", p));
/* 44 */         p.sendMessage(ChatColor.DARK_GREEN + "█" + ChatColor.GOLD + "██████" + ChatColor.DARK_GREEN + "█");
/* 45 */         p.sendMessage(ChatColor.DARK_GREEN + "██" + ChatColor.GOLD + "████" + ChatColor.DARK_GREEN + "██ ");
/* 46 */         p.sendMessage("");
/* 47 */         p.sendMessage(ChatColor.GOLD + "Check your cooldown with /gapple");
/* 48 */         e.getPlayer().updateInventory();
/*    */ 
/*    */       }
/*    */       else
/*    */       {
/*    */ 
/* 54 */         Cooldowns.addCooldown("gapple_cooldown", p, Core.cfg4.getInt("Gapple.Cooldown"));
/* 55 */         p.sendMessage("");
/* 56 */         p.sendMessage(ChatColor.DARK_GREEN + "███" + ChatColor.BLACK + "█" + ChatColor.DARK_GREEN + "████");
/* 57 */         p.sendMessage(ChatColor.DARK_GREEN + "██" + ChatColor.GOLD + "████" + ChatColor.DARK_GREEN + "██ " + ChatColor.GOLD + "Super Golden Apple:");
/* 58 */         p.sendMessage(ChatColor.DARK_GREEN + "█" + ChatColor.GOLD + "██" + ChatColor.WHITE + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_GREEN + "█  " + ChatColor.GREEN + "Consumed");
/* 59 */         p.sendMessage(ChatColor.DARK_GREEN + "█" + ChatColor.GOLD + "█" + ChatColor.WHITE + "█" + ChatColor.GOLD + "████" + ChatColor.DARK_GREEN + "█ " + ChatColor.YELLOW + "Cooldown Remaining:");
/* 60 */         p.sendMessage(ChatColor.DARK_GREEN + "█" + ChatColor.GOLD + "██████" + ChatColor.DARK_GREEN + "█  " + ChatColor.BLUE + "" + Cooldowns.getCooldownForPlayerInt("gapple_cooldown", p));
/* 61 */         p.sendMessage(ChatColor.DARK_GREEN + "█" + ChatColor.GOLD + "██████" + ChatColor.DARK_GREEN + "█");
/* 62 */         p.sendMessage(ChatColor.DARK_GREEN + "██" + ChatColor.GOLD + "████" + ChatColor.DARK_GREEN + "██ ");
/* 63 */         p.sendMessage("");
/* 64 */         e.getPlayer().updateInventory();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\GodAppleListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */