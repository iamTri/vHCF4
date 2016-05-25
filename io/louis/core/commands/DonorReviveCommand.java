/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import io.louis.core.commands.type.ConfigurableComponent;
/*    */ import io.louis.core.utils.Cooldowns;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class DonorReviveCommand extends ConfigurableComponent implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   public DonorReviveCommand(Core mainPlugin)
/*    */   {
/* 15 */     super(mainPlugin, "juggernaut");
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/* 19 */     Player p = (Player)s;
/* 20 */     if (!p.hasPermission("core.donor.revive")) {
/* 21 */       p.sendMessage(io.louis.core.utils.C.c("&ePurchase the &6Juggernaut &erank on " + Core.cfg2.getString("Messages.ServerStore")));
/* 22 */       return true;
/*    */     }
/* 24 */     if (args.length == 0) {
/* 25 */       s.sendMessage(ChatColor.GOLD + "*** Juggernaut Help ***");
/* 26 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " revive <player> - Revive a player.");
/* 27 */       return true;
/*    */     }
/* 29 */     if (args[0].equalsIgnoreCase("revive")) {
/* 30 */       if (!(s instanceof Player)) {
/* 31 */         s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/* 32 */         return true;
/*    */       }
/* 34 */       if (Cooldowns.isOnCooldown("revive_cooldown", p))
/*    */       {
/* 36 */         p.sendMessage("§cYou cannot do this for another §l" + Cooldowns.getCooldownForPlayerInt("revive_cooldown", p) / 60 + " §cminutes.");
/* 37 */         return true;
/*    */       }
/* 39 */       if (super.getPlugin().getDeathBanCommand().isEotwDeathBans()) {
/* 40 */         s.sendMessage(ChatColor.RED + "You cannot use the /" + c.getName() + " command during EOTW.");
/* 41 */         return true;
/*    */       }
/* 43 */       if (args.length != 2) {
/* 44 */         p.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " revive <player>");
/* 45 */         return true;
/*    */       }
/*    */       try
/*    */       {
/* 49 */         toCheck2 = super.getPlugin().getUuidManager().getUUIDFromName(args[1]);
/*    */       } catch (Exception e2) {
/*    */         java.util.UUID toCheck2;
/* 52 */         p.sendMessage(ChatColor.RED + "That player could not be found.");
/* 53 */         return true;
/*    */       }
/*    */       java.util.UUID toCheck2;
/*    */       try {
/* 57 */         playerName2 = super.getPlugin().getUuidManager().getNameFromUUID(toCheck2);
/*    */       } catch (Exception e4) {
/*    */         String playerName2;
/* 60 */         p.sendMessage(ChatColor.RED + "Error in obtaining the name from UUID.");
/* 61 */         return true; }
/*    */       String playerName2;
/* 63 */       if (super.getPlugin().getDeathBanListener().isDeathBanned(toCheck2, false)) {
/* 64 */         super.getPlugin().getDeathBanListener().removeDeathBan(toCheck2);
/* 65 */         if (!p.hasPermission("core.donor.revive")) {}
/*    */         
/*    */ 
/* 68 */         p.sendMessage(ChatColor.RED + "Revived " + playerName2 + ".");
/* 69 */         org.bukkit.Bukkit.broadcastMessage(p.getDisplayName() + io.louis.core.utils.C.c(new StringBuilder().append(" &eused their &6Juggernaut &erank to revive &6").append(playerName2).append("&e.").toString()));
/* 70 */         Cooldowns.addCooldown("revive_cooldown", p, 3600);
/*    */       }
/*    */       else {
/* 73 */         p.sendMessage(ChatColor.RED + "That player is currently not death-banned.");
/*    */       }
/*    */     }
/*    */     else {
/* 77 */       s.sendMessage(ChatColor.GOLD + "*** Juggernaut Help ***");
/* 78 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " <player>");
/*    */     }
/* 80 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\DonorReviveCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */