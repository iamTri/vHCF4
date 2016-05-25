/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ClearChatCommand implements CommandExecutor
/*    */ {
/*    */   private io.louis.core.LanguageFile lf;
/*    */   
/*    */   public ClearChatCommand()
/*    */   {
/* 17 */     Bukkit.getPluginCommand("clearchat").setExecutor(this);
/* 18 */     this.lf = Core.getInstance().getLanguageFile();
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*    */   {
/* 23 */     Player player = (Player)sender;
/* 24 */     String name = sender.getName();
/* 25 */     String displayName = ((Player)sender).getDisplayName();
/*    */     
/* 27 */     if (!sender.hasPermission("core.clearchat")) {
/* 28 */       sender.sendMessage(ChatColor.RED + "No.");
/* 29 */       return true;
/*    */     }
/* 31 */     for (int i = 0; i < 300; i++) {
/* 32 */       Bukkit.broadcastMessage(" ");
/*    */     }
/*    */     
/* 35 */     Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aChat has been cleared by &r" + displayName));
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\ClearChatCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */