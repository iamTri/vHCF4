/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class PvPProtectionCommand implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   private io.louis.core.Core mainPlugin;
/*    */   
/*    */   public PvPProtectionCommand(io.louis.core.Core mainPlugin)
/*    */   {
/* 12 */     this.mainPlugin = mainPlugin;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/* 16 */     if (!s.hasPermission("core.pvpprotection")) {
/* 17 */       s.sendMessage(ChatColor.RED + "No.");
/* 18 */       return true;
/*    */     }
/* 20 */     if (args.length != 1) {
/* 21 */       s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <start|stop>");
/* 22 */       return true;
/*    */     }
/* 24 */     if (args[0].equalsIgnoreCase("start")) {
/* 25 */       this.mainPlugin.getPvpTimerCommand().setEnabled(false);
/* 26 */       s.sendMessage(ChatColor.GREEN + "PvP protection is now disabled.");
/*    */     }
/* 28 */     else if (args[0].equalsIgnoreCase("stop")) {
/* 29 */       this.mainPlugin.getPvpTimerCommand().setEnabled(true);
/* 30 */       s.sendMessage(ChatColor.GREEN + "PvP protection is now re-enabled.");
/*    */     }
/*    */     else {
/* 33 */       s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <start|stop>");
/*    */     }
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\PvPProtectionCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */