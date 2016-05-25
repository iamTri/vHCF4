/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.tasks.LogoutTask;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public class LogoutCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*    */ {
/*    */   private io.louis.core.Core mainPlugin;
/*    */   private java.util.Set<UUID> exemptUUIDs;
/*    */   private Map<UUID, LogoutTask> bukkitTasks;
/*    */   
/*    */   public LogoutCommand(io.louis.core.Core mainPlugin)
/*    */   {
/* 20 */     this.mainPlugin = mainPlugin;
/* 21 */     this.exemptUUIDs = new java.util.HashSet();
/* 22 */     this.bukkitTasks = new java.util.HashMap();
/*    */   }
/*    */   
/*    */   public java.util.Set<UUID> getExemptUUIDs() {
/* 26 */     return this.exemptUUIDs;
/*    */   }
/*    */   
/*    */   public Map<UUID, LogoutTask> getLogoutTasks() {
/* 30 */     return this.bukkitTasks;
/*    */   }
/*    */   
/*    */   public boolean onCommand(org.bukkit.command.CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/* 34 */     if (!(s instanceof Player)) {
/* 35 */       s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/* 36 */       return true;
/*    */     }
/* 38 */     Player p = (Player)s;
/* 39 */     if (this.bukkitTasks.containsKey(p.getUniqueId())) {
/* 40 */       p.sendMessage(ChatColor.RED + "You are already scheduled to logout soon!");
/* 41 */       return true;
/*    */     }
/* 43 */     LogoutTask logoutTask = new LogoutTask(p, this.mainPlugin);
/* 44 */     this.bukkitTasks.put(p.getUniqueId(), logoutTask);
/* 45 */     logoutTask.runTaskTimer(this.mainPlugin, 20L, 20L);
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler
/*    */   public void onQuit(PlayerQuitEvent e) {
/* 51 */     if (this.bukkitTasks.containsKey(e.getPlayer().getUniqueId())) {
/* 52 */       ((LogoutTask)this.bukkitTasks.remove(e.getPlayer().getUniqueId())).cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler
/*    */   public void onMove(PlayerMoveEvent e) {
/* 58 */     if (((e.getTo().getBlockX() != e.getFrom().getBlockX()) || (e.getTo().getBlockY() != e.getFrom().getBlockY()) || (e.getTo().getBlockZ() != e.getFrom().getBlockZ())) && (this.bukkitTasks.containsKey(e.getPlayer().getUniqueId()))) {
/* 59 */       ((LogoutTask)this.bukkitTasks.remove(e.getPlayer().getUniqueId())).cancel();
/* 60 */       e.getPlayer().sendMessage(ChatColor.RED + "You moved, disconnect cancelled.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\LogoutCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */