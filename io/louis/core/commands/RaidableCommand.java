/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class RaidableCommand implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   private final String[] FLAGS;
/*    */   
/*    */   public RaidableCommand()
/*    */   {
/* 11 */     this.FLAGS = new String[] { "ownedAreaDenyBuild", "ownedAreaProtectMaterials", "ownedAreaDenyUseage", "territoryDenyBuild", "territoryDenyBuildWhenOffline", "territoryDenyUseage", "territoryEnemyDenyBuild", "territoryEnemyDenyBuildWhenOffline", "territoryEnemyDenyUseage", "territoryEnemyProtectMaterials", "territoryAllyDenyBuild", "territoryAllyProtectMaterials", "territoryAllyDenyUseage", "territoryAllyDenyBuildWhenOffline" };
/*    */   }
/*    */   
/*    */   public void trigger(boolean start) {
/* 15 */     for (String flag : this.FLAGS) {
/* 16 */       org.bukkit.Bukkit.dispatchCommand(org.bukkit.Bukkit.getConsoleSender(), "f config " + flag + " " + (!start));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/* 21 */     if (!s.hasPermission("core.raidable")) {
/* 22 */       s.sendMessage(org.bukkit.ChatColor.RED + "No.");
/* 23 */       return true;
/*    */     }
/* 25 */     if (args.length != 1) {
/* 26 */       s.sendMessage(org.bukkit.ChatColor.RED + "/" + c.getName() + " <start|stop>");
/* 27 */       return true;
/*    */     }
/* 29 */     if (args[0].equalsIgnoreCase("start")) {
/* 30 */       trigger(true);
/* 31 */       s.sendMessage(org.bukkit.ChatColor.GREEN + "Starting raidable event.");
/*    */     }
/* 33 */     else if (args[0].equalsIgnoreCase("stop")) {
/* 34 */       trigger(false);
/* 35 */       s.sendMessage(org.bukkit.ChatColor.RED + "Stopping raidable event.");
/*    */     }
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\RaidableCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */