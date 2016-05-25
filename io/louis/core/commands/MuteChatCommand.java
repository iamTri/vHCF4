/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.PluginCommand;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class MuteChatCommand implements CommandExecutor
/*    */ {
/*    */   public MuteChatCommand()
/*    */   {
/* 15 */     Bukkit.getPluginCommand("mutechat").setExecutor(this);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*    */   {
/* 21 */     Player player = (Player)sender;
/* 22 */     String name = sender.getName();
/* 23 */     String displayName = ((Player)sender).getDisplayName();
/*    */     
/* 25 */     if (!sender.hasPermission("core.mutechat")) {
/* 26 */       sender.sendMessage(ChatColor.RED + "No.");
/* 27 */       return true;
/*    */     }
/* 29 */     if (io.louis.core.utils.Lists.chatEnabled) {
/* 30 */       Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cChat has been globally muted by &7" + displayName));
/* 31 */       io.louis.core.utils.Lists.chatEnabled = false;
/* 32 */       return true;
/*    */     }
/*    */     
/*    */ 
/* 36 */     Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aChat has been globally unmuted by &7" + displayName));
/* 37 */     io.louis.core.utils.Lists.chatEnabled = true;
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\MuteChatCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */