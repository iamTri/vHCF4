/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class GappleCommand implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   private Core mainPlugin;
/*    */   
/*    */   public GappleCommand(Core mainPlugin)
/*    */   {
/* 15 */     this.mainPlugin = mainPlugin;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/* 19 */     if (!(s instanceof Player)) {
/* 20 */       s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/* 21 */       return true;
/*    */     }
/* 23 */     Player p = (Player)s;
/* 24 */     if (!io.louis.core.utils.Cooldowns.isOnCooldown("gapple_cooldown", p)) {
/* 25 */       p.sendMessage(ChatColor.RED + "You're not on cooldown.");
/* 26 */       return true;
/*    */     }
/* 28 */     p.sendMessage(ChatColor.RED + "Your golden apple cooldown is " + ChatColor.GOLD + 
/* 29 */       io.louis.core.utils.Cooldowns.getCooldownForPlayerInt("gapple_cooldown", p) + ChatColor.RED + " seconds.");
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\GappleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */