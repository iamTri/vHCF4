/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ 
/*    */ public class WhoCommand implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
/*    */   {
/* 15 */     if (cmd.getName().equalsIgnoreCase("nwho"))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 25 */       int playercount = Bukkit.getOnlinePlayers().length;
/*    */       
/*    */ 
/* 28 */       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m--&8&m---------------&6&m-----&8&m---------------&6&m--"));
/* 29 */       sender.sendMessage("§eThere are §a" + playercount + ChatColor.YELLOW + "/" + ChatColor.GREEN + Bukkit.getMaxPlayers() + " §eplayers online.");
/* 30 */       sender.sendMessage("§7If you require assistance join " + Core.color(Core.cfg2.getString("Messages.ServerTS")) + "§7.");
/* 31 */       sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m--&8&m---------------&6&m-----&8&m---------------&6&m--"));
/*    */       
/*    */ 
/*    */ 
/* 35 */       return true;
/*    */     }
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\WhoCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */