/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import java.util.UUID;
/*    */ import net.techcable.npclib.NPC;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class DeathNPCCommand implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   private Core mainPlugin;
/*    */   private io.louis.core.LanguageFile lf;
/*    */   
/*    */   public DeathNPCCommand(Core mainPlugin)
/*    */   {
/* 18 */     this.mainPlugin = mainPlugin;
/* 19 */     this.lf = Core.getInstance().getLanguageFile();
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args)
/*    */   {
/* 24 */     if (!s.hasPermission("core.deathnpc")) {
/* 25 */       s.sendMessage(ChatColor.RED + "No.");
/* 26 */       return true;
/*    */     }
/* 28 */     if (args.length != 1) {
/* 29 */       s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <npc>");
/* 30 */       return true;
/*    */     }
/* 32 */     UUID targetUUID = this.mainPlugin.getUuidManager().getUUIDFromName(args[0]);
/* 33 */     if (targetUUID == null) {
/* 34 */       s.sendMessage(ChatColor.RED + "Player does not exist.");
/*    */ 
/*    */     }
/* 37 */     else if (this.mainPlugin.getServer().getPlayer(targetUUID) != null) {
/* 38 */       s.sendMessage(ChatColor.RED + "Player is currently online.");
/*    */     }
/*    */     else
/*    */     {
/* 42 */       NPC npc = this.mainPlugin.getNpcRegistry().getByUUID(targetUUID);
/* 43 */       if (npc == null) {
/* 44 */         s.sendMessage(ChatColor.RED + "NPC doesn't exist.");
/*    */         
/* 46 */         return true;
/*    */       }
/* 48 */       if ((npc.getEntity() instanceof Player)) {
/* 49 */         Player p = (Player)npc.getEntity();
/* 50 */         p.setHealth(0);
/*    */         
/* 52 */         s.sendMessage(ChatColor.GREEN + "Successfully killed " + npc.getName() + "'s NPC.");
/*    */       }
/*    */     }
/* 55 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\DeathNPCCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */