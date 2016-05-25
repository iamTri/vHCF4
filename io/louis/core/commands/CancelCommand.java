/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import io.louis.core.listeners.PortalListener;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CancelCommand implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   private Core mainPlugin;
/*    */   
/*    */   public CancelCommand(Core mainPlugin)
/*    */   {
/* 16 */     this.mainPlugin = mainPlugin;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/* 20 */     if (!(s instanceof Player)) {
/* 21 */       s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/* 22 */       return true;
/*    */     }
/* 24 */     Player p = (Player)s;
/* 25 */     if (this.mainPlugin.getPortalListener().getCountdownMap().containsKey(p)) {
/* 26 */       p.sendMessage(ChatColor.GREEN + "Successfully cancelled re-location.");
/* 27 */       this.mainPlugin.getPortalListener().getCountdownMap().remove(p);
/*    */     }
/*    */     else {
/* 30 */       p.sendMessage(ChatColor.RED + "You are currently not stuck within a nether-portal.");
/*    */     }
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\CancelCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */