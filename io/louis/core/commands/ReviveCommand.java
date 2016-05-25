/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import io.louis.core.commands.type.ConfigurableComponent;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ReviveCommand extends ConfigurableComponent implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   public ReviveCommand(Core mainPlugin)
/*    */   {
/* 15 */     super(mainPlugin, "revive");
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/* 19 */     Player p = (Player)s;
/* 20 */     if (!p.hasPermission("core.revive")) {
/* 21 */       p.sendMessage(io.louis.core.utils.C.c("&cNo."));
/* 22 */       return true;
/*    */     }
/* 24 */     if (args.length == 0) {
/* 25 */       s.sendMessage(ChatColor.GOLD + "*** Revive Help ***");
/* 26 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " <player> - Revive a player.");
/* 27 */       return true;
/*    */     }
/* 29 */     if (!(s instanceof Player)) {
/* 30 */       s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/* 31 */       return true;
/*    */     }
/* 33 */     if (args.length != 1) {
/* 34 */       p.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " revive <player>");
/* 35 */       return true;
/*    */     }
/*    */     try
/*    */     {
/* 39 */       toCheck2 = super.getPlugin().getUuidManager().getUUIDFromName(args[0]);
/*    */     } catch (Exception e2) {
/*    */       UUID toCheck2;
/* 42 */       p.sendMessage(ChatColor.RED + "That player could not be found.");
/* 43 */       return true;
/*    */     }
/*    */     UUID toCheck2;
/*    */     try {
/* 47 */       playerName2 = super.getPlugin().getUuidManager().getNameFromUUID(toCheck2);
/*    */     } catch (Exception e4) {
/*    */       String playerName2;
/* 50 */       p.sendMessage(ChatColor.RED + "Error in obtaining the name from UUID.");
/* 51 */       return true; }
/*    */     String playerName2;
/* 53 */     if (super.getPlugin().getDeathBanListener().isDeathBanned(toCheck2, false)) {
/* 54 */       super.getPlugin().getDeathBanListener().removeDeathBan(toCheck2);
/* 55 */       if (!p.hasPermission("core.donor.revive")) {}
/*    */       
/*    */ 
/* 58 */       p.sendMessage(ChatColor.RED + "Revived " + playerName2 + ".");
/* 59 */       org.bukkit.Bukkit.broadcastMessage(ChatColor.YELLOW + p.getName() + io.louis.core.utils.C.c(new StringBuilder().append(" &eremoved the deathban of&6 ").append(playerName2).append("&e.").toString()));
/*    */     }
/*    */     else {
/* 62 */       p.sendMessage(ChatColor.RED + "That player is currently not death-banned.");
/*    */     }
/*    */     
/* 65 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\ReviveCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */