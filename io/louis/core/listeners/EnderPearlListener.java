/*    */ package io.louis.core.listeners;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ 
/*    */ public class EnderPearlListener implements org.bukkit.event.Listener
/*    */ {
/*    */   private Core mainPlugin;
/*    */   
/*    */   public EnderPearlListener(Core mainPlugin)
/*    */   {
/* 14 */     this.mainPlugin = mainPlugin;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler(priority=org.bukkit.event.EventPriority.HIGH)
/*    */   public void onProjectileLaunch(PlayerInteractEvent e) {
/* 19 */     if ((e.hasItem()) && (e.getAction().name().contains("RIGHT"))) {
/* 20 */       org.bukkit.inventory.ItemStack itemStack = e.getItem();
/* 21 */       if (itemStack.getType() == org.bukkit.Material.ENDER_PEARL) {
/* 22 */         if (this.mainPlugin.getPvpTimerCommand().isProtected(e.getPlayer())) {
/* 23 */           e.setCancelled(true);
/* 24 */           e.getPlayer().updateInventory();
/* 25 */           e.getPlayer().sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', "&cYou cannot throw enderpearls while under pvp protection."));
/*    */         }
/* 27 */         else if (!this.mainPlugin.getCooldownManager().tryCooldown(e.getPlayer(), org.bukkit.ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.EnderPearl")), Core.cfg3
/* 28 */           .getInt("Timers.EnderPearl") * 1100, true, false, true)) {
/* 29 */           e.setCancelled(true);
/* 30 */           e.getPlayer().updateInventory();
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\EnderPearlListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */