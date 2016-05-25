/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerLoginEvent;
/*    */ 
/*    */ public class PinkListCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*    */ {
/*    */   private Core mainPlugin;
/*    */   private boolean pinkListEnabled;
/*    */   private List<String> pinkList;
/*    */   
/*    */   public PinkListCommand(Core mainPlugin)
/*    */   {
/* 19 */     this.mainPlugin = mainPlugin;
/* 20 */     this.pinkListEnabled = false;
/* 21 */     this.pinkList = new java.util.ArrayList();
/* 22 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler
/*    */   public void onLogin(PlayerLoginEvent e) {
/* 27 */     if ((this.pinkListEnabled) && (!this.pinkList.contains(e.getPlayer().getName().toLowerCase()))) {
/* 28 */       e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_WHITELIST, ChatColor.RED + "Server locked.");
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/* 33 */     if (!s.hasPermission("core.pinklist")) {
/* 34 */       s.sendMessage(ChatColor.RED + "No.");
/* 35 */       return true;
/*    */     }
/* 37 */     if ((args.length < 1) || (args.length > 2)) {
/* 38 */       s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <on, off, list, add, remove> [name]");
/* 39 */       return true;
/*    */     }
/* 41 */     if (args[0].equalsIgnoreCase("on")) {
/* 42 */       this.pinkListEnabled = true;
/* 43 */       s.sendMessage(ChatColor.GREEN + "Pinklist enabled.");
/*    */     }
/* 45 */     else if (args[0].equalsIgnoreCase("off")) {
/* 46 */       this.pinkListEnabled = false;
/* 47 */       s.sendMessage(ChatColor.RED + "Pinklist disabled.");
/*    */     }
/* 49 */     else if (args[0].equalsIgnoreCase("list")) {
/* 50 */       s.sendMessage("Pinklisted players: " + org.apache.commons.lang.StringUtils.join(this.pinkList.toArray(), ", ", 0, this.pinkList.size()) + ".");
/*    */     }
/* 52 */     else if (args[0].equalsIgnoreCase("add")) {
/* 53 */       if (!this.pinkList.contains(args[1].toLowerCase())) {
/* 54 */         this.pinkList.add(args[1].toLowerCase());
/*    */       }
/* 56 */       s.sendMessage(ChatColor.GREEN + "Added " + args[1] + ".");
/*    */     }
/* 58 */     else if (args[0].equalsIgnoreCase("remove")) {
/* 59 */       if (this.pinkList.contains(args[1].toLowerCase())) {
/* 60 */         this.pinkList.remove(args[1].toLowerCase());
/*    */       }
/* 62 */       s.sendMessage(ChatColor.GREEN + "Removed " + args[1] + ".");
/*    */     }
/* 64 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\PinkListCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */