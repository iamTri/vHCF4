/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class TPSCommand implements CommandExecutor, org.bukkit.event.Listener
/*    */ {
/*    */   private Core mainPlugin;
/*    */   private Map<String, Long> cooldownMap;
/*    */   
/*    */   public TPSCommand(Core mainPlugin)
/*    */   {
/* 20 */     this.mainPlugin = mainPlugin;
/* 21 */     this.cooldownMap = new java.util.HashMap();
/*    */   }
/*    */   
/*    */   private boolean tryCooldown(Player p, long time, TimeUnit sourceUnit) {
/* 25 */     long nextTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(time, sourceUnit);
/* 26 */     if (!this.cooldownMap.containsKey(p.getName())) {
/* 27 */       this.cooldownMap.put(p.getName(), Long.valueOf(nextTime));
/* 28 */       return true;
/*    */     }
/* 30 */     long remainingTime = ((Long)this.cooldownMap.get(p.getName())).longValue() - System.currentTimeMillis();
/* 31 */     if (remainingTime / 1000L > 0L) {
/* 32 */       String cooldownMessage = org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(remainingTime, true, true);
/* 33 */       p.sendMessage(ChatColor.RED + "You are still on cooldown for " + cooldownMessage + ".");
/* 34 */       return false;
/*    */     }
/* 36 */     this.cooldownMap.put(p.getName(), Long.valueOf(nextTime));
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/* 41 */     if ((s instanceof Player)) {
/* 42 */       Player p = (Player)s;
/* 43 */       if (!tryCooldown(p, 5L, TimeUnit.SECONDS)) {
/* 44 */         return true;
/*    */       }
/*    */     }
/* 47 */     double serverTPS = Math.min(20.0D, this.mainPlugin.getLagUtils().getTPS());
/* 48 */     NumberFormat numberFormat = NumberFormat.getPercentInstance();
/* 49 */     numberFormat.setMinimumFractionDigits(2);
/* 50 */     String serverLagPercentage = numberFormat.format(1.0D - serverTPS / 20.0D);
/* 51 */     s.sendMessage(ChatColor.GRAY + "Server TPS: " + ChatColor.RED + NumberFormat.getNumberInstance().format(serverTPS));
/* 52 */     s.sendMessage(ChatColor.GRAY + "Lag: " + ChatColor.RED + serverLagPercentage);
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\TPSCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */