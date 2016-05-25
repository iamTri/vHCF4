/*    */ package io.louis.core.staff;
/*    */ 
/*    */ import io.louis.core.utils.Cooldowns;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Helpop
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*    */   {
/* 21 */     if (!(sender instanceof Player))
/*    */     {
/* 23 */       sender.sendMessage(ChatColor.RED + "No Console.");
/* 24 */       return true;
/*    */     }
/* 26 */     Player p = (Player)sender;
/* 27 */     if (cmd.getName().equalsIgnoreCase("helpop"))
/*    */     {
/* 29 */       if (args.length < 1)
/*    */       {
/* 31 */         p.sendMessage("§cCorrect Usage: /helpop <Message>");
/* 32 */         return true;
/*    */       }
/* 34 */       if (Cooldowns.isOnCooldown("helpop_cooldown", p))
/*    */       {
/* 36 */         p.sendMessage("§cYou cannot do this for another §l" + Cooldowns.getCooldownForPlayerInt("helpop_cooldown", p) + " §cseconds.");
/* 37 */         return true;
/*    */       }
/* 39 */       StringBuilder message = new StringBuilder();
/* 40 */       for (int i = 0; i < args.length; i++) {
/* 41 */         message.append(args[i] + " ");
/*    */       }
/*    */       Player[] arrayOfPlayer;
/* 44 */       int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
/* 45 */       for (int i = 0; i < j; i++)
/*    */       {
/*    */ 
/* 48 */         Player online = arrayOfPlayer[i];
/* 49 */         Location locLoc = p.getLocation();
/* 50 */         if (online.hasPermission("core.helpop")) {
/* 51 */           online.sendMessage("§6§m--§8§m---------------§6§m-----§8§m---------------§6§m--");
/* 52 */           online.sendMessage("§7Player: §9" + p.getDisplayName() + "§7.");
/* 53 */           online.sendMessage("");
/* 54 */           online.sendMessage("§7Message: §e" + message + "§7.");
/* 55 */           online.sendMessage("§7Coords: §e" + String.format(new StringBuilder().append(ChatColor.DARK_GRAY).append("X: %s, Y: %s, Z: %s").toString(), new Object[] {ChatColor.GRAY
/*    */           
/* 57 */             .toString() + locLoc.getBlockX() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 58 */             .toString() + locLoc.getBlockY() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 59 */             .toString() + locLoc.getBlockZ() + ChatColor.DARK_GRAY.toString() }));
/* 60 */           online.sendMessage("§6§m--§8§m---------------§6§m-----§8§m---------------§6§m--");
/* 61 */           online.playSound(online.getLocation(), Sound.NOTE_BASS_DRUM, 5.0E8F, 5.0E8F);
/*    */         }
/*    */       }
/*    */       
/* 65 */       p.sendMessage("§aThank you for your message, all staff on the network have been notified and will try to help as soon as possible.");
/* 66 */       p.sendMessage("§3If you need help faster use our live support on the website.");
/* 67 */       Cooldowns.addCooldown("helpop_cooldown", p, 45);
/* 68 */       return true;
/*    */     }
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\staff\Helpop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */