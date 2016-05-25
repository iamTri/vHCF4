/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import com.alexandeh.glaedr.scoreboards.Entry;
/*    */ import com.alexandeh.glaedr.scoreboards.PlayerScoreboard;
/*    */ import io.louis.core.utils.Lists;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.PluginCommand;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class StaffChatCommand implements CommandExecutor
/*    */ {
/*    */   public StaffChatCommand()
/*    */   {
/* 19 */     Bukkit.getPluginCommand("staffchat").setExecutor(this);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*    */   {
/* 25 */     Player p = (Player)sender;
/* 26 */     if (!p.hasPermission("core.staffchat")) {
/* 27 */       p.sendMessage(ChatColor.RED + "No.");
/* 28 */       return true;
/*    */     }
/*    */     
/* 31 */     if (args.length == 0) {
/* 32 */       PlayerScoreboard scoreboard = PlayerScoreboard.getScoreboard(p);
/*    */       
/* 34 */       if (!Lists.staffChat.contains(sender.getName())) {
/* 35 */         Lists.staffChat.add(sender.getName());
/* 36 */         sender.sendMessage(ChatColor.AQUA + "Staff chat enabled.");
/* 37 */         if (scoreboard.getEntry("modextras") != null) {
/* 38 */           Entry entry = scoreboard.getEntry("modextras");
/* 39 */           entry.setText(ChatColor.GOLD.toString() + " » " + ChatColor.BLUE.toString() + "Chat Mode" + ChatColor.GRAY + ": " + ChatColor.RED + "Staff").send();
/*    */         }
/* 41 */         return true;
/*    */       }
/* 43 */       if (scoreboard.getEntry("modextras") != null) {
/* 44 */         Entry entry = scoreboard.getEntry("modextras");
/* 45 */         entry.setText(ChatColor.GOLD.toString() + " » " + ChatColor.BLUE.toString() + "Chat Mode" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Global").send();
/*    */       }
/* 47 */       Lists.staffChat.remove(sender.getName());
/* 48 */       sender.sendMessage(ChatColor.AQUA + "Staff chat disabled.");
/* 49 */       return true;
/*    */     }
/* 51 */     if (args.length > 0) {
/* 52 */       StringBuilder msg = new StringBuilder();
/* 53 */       for (int i = 0; i < args.length; i++) {
/* 54 */         msg.append(args[i] + " ");
/*    */       }
/* 56 */       for (Player all : Bukkit.getServer().getOnlinePlayers()) {
/* 57 */         if (all.hasPermission("staff.mod"))
/* 58 */           all.sendMessage(ChatColor.AQUA + sender.getName() + ": " + msg.toString().trim());
/*    */       }
/*    */     }
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\StaffChatCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */