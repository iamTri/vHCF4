/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class DeathBanCommand implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   private Core mainPlugin;
/*    */   private boolean eotwDeathBans;
/*    */   private io.louis.core.LanguageFile lf;
/*    */   
/*    */   public DeathBanCommand(Core mainPlugin)
/*    */   {
/* 14 */     this.mainPlugin = mainPlugin;
/* 15 */     this.eotwDeathBans = false;
/* 16 */     this.lf = Core.getInstance().getLanguageFile();
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/* 20 */     if (!s.hasPermission("core.deathban")) {
/* 21 */       s.sendMessage(org.bukkit.ChatColor.RED + "No.");
/* 22 */       return true;
/*    */     }
/* 24 */     if (args.length != 1) {
/* 25 */       s.sendMessage(org.bukkit.ChatColor.RED + "Correct Usage: /" + c.getName() + " <start | stop>");
/* 26 */       return true;
/*    */     }
/* 28 */     if (args[0].equalsIgnoreCase("start")) {
/* 29 */       if (isEotwDeathBans()) {
/* 30 */         s.sendMessage(this.lf.getString("EOTW_DEATHBANS.ALREADY_ENABLED"));
/*    */       }
/*    */       else
/*    */       {
/* 34 */         setEotwDeathBans(true);
/* 35 */         this.mainPlugin.getDeathBanListener().reset();
/* 36 */         s.sendMessage(org.bukkit.ChatColor.GREEN + "EOTW DeathBans are now active.");
/*    */       }
/*    */       
/*    */     }
/* 40 */     else if (args[0].equalsIgnoreCase("stop")) {
/* 41 */       if (!isEotwDeathBans()) {
/* 42 */         s.sendMessage(org.bukkit.ChatColor.RED + "EOTW DeathBans are already disabled.");
/*    */       }
/*    */       else
/*    */       {
/* 46 */         setEotwDeathBans(false);
/* 47 */         s.sendMessage(org.bukkit.ChatColor.GREEN + "EOTW DeathBans are no longer active.");
/*    */       }
/*    */     }
/*    */     
/* 51 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isEotwDeathBans() {
/* 55 */     return this.eotwDeathBans;
/*    */   }
/*    */   
/*    */   public void setEotwDeathBans(boolean eotwDeathBans) {
/* 59 */     this.eotwDeathBans = eotwDeathBans;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\DeathBanCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */