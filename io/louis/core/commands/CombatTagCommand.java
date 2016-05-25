/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CombatTagCommand implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   private io.louis.core.LanguageFile lf;
/*    */   private Core mainPlugin;
/*    */   
/*    */   public CombatTagCommand(Core mainPlugin)
/*    */   {
/* 15 */     this.mainPlugin = mainPlugin;
/* 16 */     this.lf = Core.getInstance().getLanguageFile();
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/* 20 */     if (!(s instanceof Player)) {
/* 21 */       s.sendMessage(this.lf.getString("NO_CONSOLE"));
/* 22 */       return true;
/*    */     }
/* 24 */     Player p = (Player)s;
/* 25 */     Integer cooldown = this.mainPlugin.getCombatListener().getCombatTime(p);
/* 26 */     if (cooldown != null) {
/* 27 */       String cooldownMessage = net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils.formatDurationWords(cooldown.intValue() * 1000, true, true);
/*    */       
/* 29 */       p.sendMessage(ChatColor.GRAY + "You are currently in combat for " + cooldownMessage + ChatColor.GRAY + ".");
/*    */     }
/*    */     else {
/* 32 */       p.sendMessage(ChatColor.GRAY + "You are not currently in combat.");
/*    */     }
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\CombatTagCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */