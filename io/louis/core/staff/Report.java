/*    */ package io.louis.core.staff;
/*    */ 
/*    */ import io.louis.core.utils.Cooldowns;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Report
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*    */   {
/* 19 */     if (cmd.getName().equalsIgnoreCase("report"))
/*    */     {
/* 21 */       if (args.length < 2)
/*    */       {
/* 23 */         sender.sendMessage("§cCorrect Usage: /report <player> <reason>");
/* 24 */         return true;
/*    */       }
/* 26 */       if (((sender instanceof Player)) && 
/* 27 */         (Cooldowns.isOnCooldown("report_cooldown", (Player)sender)))
/*    */       {
/* 29 */         sender.sendMessage("§cYou cannot do this for another §l" + Cooldowns.getCooldownForPlayerInt("report_cooldown", (Player)sender) + " §cseconds.");
/* 30 */         return true;
/*    */       }
/*    */       Player[] arrayOfPlayer;
/* 33 */       int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
/* 34 */       for (int i = 0; i < j; i++)
/*    */       {
/* 36 */         Player p = (Player)sender;
/* 37 */         Player online = arrayOfPlayer[i];
/* 38 */         Player t = Bukkit.getServer().getPlayer(args[0]);
/* 39 */         StringBuilder message = new StringBuilder();
/* 40 */         for (int i1 = 1; i1 < args.length; i1++) {
/* 41 */           message.append(args[i1] + " ");
/*    */         }
/* 43 */         if (!(sender instanceof Player))
/*    */         {
/* 45 */           if (online.hasPermission("core.report")) {
/* 46 */             if (t == null) {
/* 47 */               online.sendMessage("§9" + sender.getName() + " §bhas reported §4(offline)§c" + args[0].toString() + " §bfor: §e" + message);
/*    */             } else {
/* 49 */               online.sendMessage("§6§m--§8§m---------------§6§m-----§8§m---------------§6§m--");
/* 50 */               online.sendMessage("§7Reporter: §a" + sender.getName() + "§7.");
/* 51 */               online.sendMessage("§7Reported: §a" + t.getName() + "§7.");
/* 52 */               online.sendMessage("§7Reason: §a" + message);
/* 53 */               online.sendMessage("§6§m--§8§m---------------§6§m-----§8§m---------------§6§m--");
/* 54 */               online.playSound(online.getLocation(), Sound.NOTE_SNARE_DRUM, 1000.0F, 1000.0F);
/*    */             }
/*    */           }
/* 57 */           sender.sendMessage("§aThanks for your report, all staff on the network have been notified and will investigate soon.");
/*    */ 
/*    */ 
/*    */         }
/* 61 */         else if (online.hasPermission("core.report")) {
/* 62 */           if (t == null)
/*    */           {
/* 64 */             online.sendMessage("§9" + p.getName() + " §bhas reported §4(offline)§c" + args[0].toString() + " §bfor: §e" + message);
/* 65 */             Cooldowns.addCooldown("report_cooldown", p, 45);
/*    */           }
/* 67 */           if (sender.getName() == args[1]) {
/* 68 */             sender.sendMessage("§aYou cannot report yourself.");
/* 69 */             return true;
/*    */           }
/*    */           
/*    */ 
/* 73 */           online.sendMessage("§6§m--§8§m---------------§6§m-----§8§m---------------§6§m--");
/* 74 */           online.sendMessage("§7Reporter: §9" + sender.getName() + "§7.");
/* 75 */           online.sendMessage("§7Reported: §c" + t.getName() + "§7.");
/* 76 */           online.sendMessage("§7Reason: §e" + message);
/* 77 */           online.sendMessage("§6§m--§8§m---------------§6§m-----§8§m---------------§6§m--");
/* 78 */           online.playSound(online.getLocation(), Sound.NOTE_SNARE_DRUM, 1000.0F, 1000.0F);
/* 79 */           Cooldowns.addCooldown("report_cooldown", p, 45);
/*    */         }
/*    */       }
/*    */       
/*    */ 
/* 84 */       sender.sendMessage("§aThanks for your report, all staff on the network have been notified and will investigate soon.");
/* 85 */       return true;
/*    */     }
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\staff\Report.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */