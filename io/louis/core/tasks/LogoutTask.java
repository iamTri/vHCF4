/*    */ package io.louis.core.tasks;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class LogoutTask extends org.bukkit.scheduler.BukkitRunnable
/*    */ {
/*    */   private Player p;
/*    */   private Core mainPlugin;
/*    */   private int i;
/*    */   private final String SCORE;
/*    */   
/*    */   public LogoutTask(Player p, Core mainPlugin)
/*    */   {
/* 16 */     this.SCORE = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.LogOut"));
/* 17 */     this.p = p;
/* 18 */     this.mainPlugin = mainPlugin;
/* 19 */     this.i = Core.cfg3.getInt("Timers.LogOut");
/* 20 */     mainPlugin.getCooldownManager().tryCooldown(p, this.SCORE, this.i * 1000, false, true, true);
/* 21 */     p.sendMessage(ChatColor.GRAY + "Please wait " + ChatColor.GREEN + this.i + ChatColor.GRAY + " seconds before disconnecting safely.");
/*    */   }
/*    */   
/*    */   public synchronized void cancel() throws IllegalStateException {
/* 25 */     this.mainPlugin.getCooldownManager().removeCooldown(this.p, this.SCORE);
/* 26 */     super.cancel();
/*    */   }
/*    */   
/*    */   public void run() {
/* 30 */     if (this.i > 1) {
/* 31 */       this.i -= 1;
/*    */     }
/*    */     else {
/* 34 */       if (!this.mainPlugin.getLogoutCommand().getExemptUUIDs().contains(this.p.getUniqueId())) {
/* 35 */         this.mainPlugin.getLogoutCommand().getExemptUUIDs().add(this.p.getUniqueId());
/*    */       }
/* 37 */       this.p.kickPlayer(ChatColor.GREEN + "You have safely logged out.");
/* 38 */       this.mainPlugin.getLogoutCommand().getLogoutTasks().remove(this.p.getUniqueId());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\tasks\LogoutTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */