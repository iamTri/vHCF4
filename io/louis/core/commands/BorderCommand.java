/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class BorderCommand implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   private io.louis.core.Core mainPlugin;
/*    */   private EOTWTask eotwTask;
/*    */   private int lastBorder;
/*    */   
/*    */   public BorderCommand(io.louis.core.Core mainPlugin)
/*    */   {
/* 16 */     this.mainPlugin = mainPlugin;
/* 17 */     this.lastBorder = 0;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/* 21 */     if (!s.hasPermission("core.border")) {
/* 22 */       s.sendMessage(ChatColor.RED + "No.");
/* 23 */       return true;
/*    */     }
/* 25 */     if (args.length != 1) {
/* 26 */       s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <start | stop>");
/* 27 */       return true;
/*    */     }
/* 29 */     if (args[0].equalsIgnoreCase("start")) {
/* 30 */       if (this.eotwTask != null) {
/* 31 */         s.sendMessage(ChatColor.RED + "EOTW event has already started!");
/*    */       }
/*    */       else {
/* 34 */         (this.eotwTask = new EOTWTask(this.lastBorder == 0 ? 4000 : this.lastBorder)).runTaskTimer(this.mainPlugin, 0L, 1200L);
/* 35 */         s.sendMessage(ChatColor.GREEN + "EOTW event has begun.");
/*    */       }
/*    */     }
/* 38 */     else if (args[0].equalsIgnoreCase("stop")) {
/* 39 */       if (this.eotwTask == null) {
/* 40 */         s.sendMessage(ChatColor.RED + "EOTW event has not started.");
/*    */       }
/*    */       else {
/* 43 */         this.lastBorder = this.eotwTask.getBorder();
/* 44 */         this.eotwTask.cancel();
/* 45 */         this.eotwTask = null;
/* 46 */         s.sendMessage(ChatColor.GREEN + "EOTW event successfully canceled.");
/*    */       }
/*    */     }
/* 49 */     return true;
/*    */   }
/*    */   
/*    */   private class EOTWTask extends org.bukkit.scheduler.BukkitRunnable
/*    */   {
/*    */     private int border;
/*    */     private int timer;
/*    */     
/*    */     public EOTWTask(int border) {
/* 58 */       this.timer = 5;
/* 59 */       this.border = border;
/*    */     }
/*    */     
/*    */     public void run() {
/* 63 */       if (this.timer > 0) {
/* 64 */         Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3Border shrinking in &d" + this.timer + " " + (this.timer == 1 ? "minute" : "minutes") + "&3 by &e200 blocks&3."));
/* 65 */         this.timer -= 1;
/*    */       }
/*    */       else {
/* 68 */         this.border -= 200;
/* 69 */         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb world set " + this.border + " 0 0");
/* 70 */         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb world_nether set " + this.border + " 0 0");
/* 71 */         Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3Border shrunk to &e" + this.border + " blocks&3."));
/* 72 */         if (this.border <= 400) {
/* 73 */           Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3End of the world border has finished shrinking."));
/* 74 */           cancel();
/* 75 */           BorderCommand.this.eotwTask = null;
/*    */         }
/*    */         else {
/* 78 */           this.timer = 5;
/* 79 */           Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3Border shrinking in &a" + this.timer + " " + (this.timer == 1 ? "minute" : "minutes") + "&3 by &e200 blocks&3."));
/* 80 */           this.timer -= 1;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */     public int getBorder() {
/* 86 */       return this.border;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\BorderCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */