/*    */ package io.louis.core.listeners;
/*    */ 
/*    */ import java.util.Set;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*    */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ public class AntiBotListener implements org.bukkit.event.Listener
/*    */ {
/*    */   private Set<String> playerSet;
/*    */   
/*    */   public AntiBotListener()
/*    */   {
/* 17 */     this.playerSet = new java.util.HashSet();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onJoin(PlayerJoinEvent e) {
/* 22 */     if (e.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
/* 23 */       com.sk89q.worldguard.protection.managers.RegionManager regionManager = com.sk89q.worldguard.bukkit.WGBukkit.getRegionManager(e.getPlayer().getWorld());
/* 24 */       com.sk89q.worldguard.protection.ApplicableRegionSet regionSet = regionManager.getApplicableRegions(e.getPlayer().getLocation());
/* 25 */       for (com.sk89q.worldguard.protection.regions.ProtectedRegion region : regionSet) {
/* 26 */         if (region.getId().equalsIgnoreCase("spawn")) {
/* 27 */           this.playerSet.add(e.getPlayer().getName());
/* 28 */           break;
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {
/* 36 */     this.playerSet.remove(e.getPlayer().getName());
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onKick(org.bukkit.event.player.PlayerKickEvent e) {
/* 41 */     this.playerSet.remove(e.getPlayer().getName());
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onChat(AsyncPlayerChatEvent e) {
/* 46 */     if (this.playerSet.contains(e.getPlayer().getName())) {
/* 47 */       e.setCancelled(true);
/* 48 */       e.getPlayer().sendMessage(org.bukkit.ChatColor.RED + "You cannot speak or type commands until you move.");
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onCommandProcess(PlayerCommandPreprocessEvent e) {
/* 54 */     if (this.playerSet.contains(e.getPlayer().getName())) {
/* 55 */       e.setCancelled(true);
/* 56 */       e.getPlayer().sendMessage(org.bukkit.ChatColor.RED + "You cannot speak or type commands until you move.");
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onMove(PlayerMoveEvent e) {
/* 62 */     if ((e.getFrom().getX() != e.getTo().getX()) || (e.getFrom().getZ() != e.getTo().getZ())) {
/* 63 */       this.playerSet.remove(e.getPlayer().getName());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\AntiBotListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */