/*     */ package io.louis.core.commands;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import io.louis.core.commands.type.ConfigurableComponent;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class LivesCommand extends ConfigurableComponent implements org.bukkit.command.CommandExecutor
/*     */ {
/*     */   public LivesCommand(Core mainPlugin)
/*     */   {
/*  13 */     super(mainPlugin, "lives");
/*     */   }
/*     */   
/*     */   public int getLives(java.util.UUID uuid) {
/*  17 */     return ((Integer)(super.contains(uuid) ? super.get(uuid) : Integer.valueOf(0))).intValue();
/*     */   }
/*     */   
/*     */   public void editLives(java.util.UUID uuid, int modifiedLives) {
/*  21 */     int currentLives = 0;
/*  22 */     if (super.contains(uuid)) {
/*  23 */       currentLives = ((Integer)super.get(uuid)).intValue();
/*     */     }
/*  25 */     currentLives += modifiedLives;
/*  26 */     currentLives = Math.max(currentLives, 0);
/*  27 */     if (currentLives == 0) {
/*  28 */       if (super.contains(uuid)) {
/*  29 */         super.remove(uuid);
/*     */       }
/*  31 */       super.set(uuid.toString(), null);
/*     */     }
/*     */     else {
/*  34 */       super.put(uuid, Integer.valueOf(currentLives));
/*  35 */       super.set(uuid.toString(), Integer.valueOf(currentLives));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/*  40 */     if (args.length == 0) {
/*  41 */       s.sendMessage(ChatColor.GOLD + "*** Lives Help ***");
/*  42 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " check - Check your own lives.");
/*  43 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " check [player] - Check another player's lives.");
/*  44 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " give [player] [amount] - Give another player some of your lives.");
/*  45 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " revive [player] - Use one of your lives to relieve a player of a deathban.");
/*  46 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " edit [player] [amount] - Add players lives.");
/*  47 */       return true;
/*     */     }
/*  49 */     if (args[0].equalsIgnoreCase("edit")) {
/*  50 */       if ((!s.hasPermission("core.lives.edit")) && (!s.isOp())) {
/*  51 */         s.sendMessage(ChatColor.RED + "No.");
/*  52 */         return true;
/*     */       }
/*  54 */       if (args.length != 3) {
/*  55 */         s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " edit [player] [amount]");
/*  56 */         return true;
/*     */       }
/*     */       try {
/*  59 */         java.util.UUID playerUUID = super.getPlugin().getUuidManager().getUUIDFromName(args[1]);
/*  60 */         String playerName = super.getPlugin().getUuidManager().getNameFromUUID(playerUUID);
/*  61 */         int givenLives = Integer.parseInt(args[2]);
/*  62 */         editLives(playerUUID, givenLives);
/*  63 */         s.sendMessage(ChatColor.GREEN + playerName + " was given " + givenLives + " live(s) by " + s.getName() + ".");
/*     */       }
/*     */       catch (Exception e) {
/*  66 */         s.sendMessage(ChatColor.RED + "Wrong arguments, or invalid UUID found.");
/*     */       }
/*     */     }
/*  69 */     else if (args[0].equalsIgnoreCase("check")) {
/*  70 */       java.util.UUID toCheck = null;
/*  71 */       String name = null;
/*     */       
/*  73 */       if (args.length >= 2) {
/*     */         try {
/*  75 */           toCheck = super.getPlugin().getUuidManager().getUUIDFromName(args[1]);
/*  76 */           name = super.getPlugin().getUuidManager().getNameFromUUID(toCheck);
/*     */         }
/*     */         catch (Exception e2)
/*     */         {
/*  80 */           s.sendMessage(ChatColor.RED + "Player does not exist.");
/*  81 */           return true;
/*     */         }
/*     */       }
/*  84 */       else if ((s instanceof Player)) {
/*  85 */         toCheck = ((Player)s).getUniqueId();
/*  86 */         name = s.getName();
/*     */       }
/*     */       
/*  89 */       if (toCheck == null) {
/*  90 */         s.sendMessage(ChatColor.RED + "Could not find player.");
/*  91 */         return true;
/*     */       }
/*  93 */       s.sendMessage(ChatColor.GRAY + name + "'s Lives: " + ChatColor.GOLD + getLives(toCheck));
/*     */     }
/*  95 */     else if (args[0].equalsIgnoreCase("give")) {
/*  96 */       if (super.getPlugin().getDeathBanCommand().isEotwDeathBans()) {
/*  97 */         s.sendMessage(ChatColor.RED + "You cannot use the /" + c.getName() + " command during EOTW.");
/*  98 */         return true;
/*     */       }
/* 100 */       Player p = (Player)s;
/*     */       try {
/* 102 */         java.util.UUID targetUUID = null;
/*     */         try {
/* 104 */           targetUUID = super.getPlugin().getUuidManager().getUUIDFromName(args[1]);
/*     */         }
/*     */         catch (Exception e2) {
/* 107 */           s.sendMessage(ChatColor.RED + "That player does not exist.");
/* 108 */           return true;
/*     */         }
/* 110 */         if (targetUUID == null) {
/* 111 */           return true;
/*     */         }
/* 113 */         int toGive = Integer.parseInt(args[2]);
/* 114 */         int currentLives = p.hasPermission("lives.infinite") ? Integer.MAX_VALUE : getLives(p.getUniqueId());
/* 115 */         if (toGive < 0) {
/* 116 */           p.sendMessage(ChatColor.RED + "You cannot supply negative amounts.");
/* 117 */           return true;
/*     */         }
/* 119 */         if (toGive == 0) {
/* 120 */           p.sendMessage(ChatColor.RED + "You cannot supply zero amounts.");
/* 121 */           return true;
/*     */         }
/* 123 */         if (toGive > currentLives) {
/* 124 */           p.sendMessage(ChatColor.RED + "You do not have enough lives to give with that amount.");
/* 125 */           return true;
/*     */         }
/* 127 */         editLives(targetUUID, toGive);
/* 128 */         if (!p.hasPermission("core.lives.infinite")) {
/* 129 */           editLives(p.getUniqueId(), -toGive);
/*     */         }
/* 131 */         p.sendMessage(ChatColor.GREEN + "Successfully given " + toGive + " live(s) to " + super.getPlugin().getUuidManager().getNameFromUUID(targetUUID) + ".");
/* 132 */         Player given = super.getPlugin().getServer().getPlayer(targetUUID);
/* 133 */         if (given != null) {
/* 134 */           given.sendMessage(ChatColor.GREEN + p.getName() + ChatColor.GRAY + " gave you " + ChatColor.GOLD + toGive + " " + (toGive == 1 ? "life" : "lives") + ChatColor.GRAY + ".");
/*     */         }
/*     */       }
/*     */       catch (Exception e3) {
/* 138 */         p.sendMessage(ChatColor.RED + "Invalid arguments. Correct Usage: /" + c.getName() + " [player] [amount]");
/*     */       }
/*     */     }
/* 141 */     else if (args[0].equalsIgnoreCase("revive")) {
/* 142 */       if (!(s instanceof Player)) {
/* 143 */         s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/* 144 */         return true;
/*     */       }
/* 146 */       if (super.getPlugin().getDeathBanCommand().isEotwDeathBans()) {
/* 147 */         s.sendMessage(ChatColor.RED + "You cannot use the /" + c.getName() + " command during EOTW.");
/* 148 */         return true;
/*     */       }
/* 150 */       Player p = (Player)s;
/* 151 */       if (args.length != 2) {
/* 152 */         p.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " revive <player>");
/* 153 */         return true;
/*     */       }
/*     */       try
/*     */       {
/* 157 */         toCheck2 = super.getPlugin().getUuidManager().getUUIDFromName(args[1]);
/*     */       } catch (Exception e2) {
/*     */         java.util.UUID toCheck2;
/* 160 */         p.sendMessage(ChatColor.RED + "That player could not be found.");
/* 161 */         return true; }
/*     */       java.util.UUID toCheck2;
/* 163 */       if ((!p.hasPermission("core.lives.infinite")) && (getLives(p.getUniqueId()) == 0)) {
/* 164 */         p.sendMessage(ChatColor.RED + "You do not have any lives to revive someone with.");
/* 165 */         return true;
/*     */       }
/*     */       try
/*     */       {
/* 169 */         playerName2 = super.getPlugin().getUuidManager().getNameFromUUID(toCheck2);
/*     */       } catch (Exception e4) {
/*     */         String playerName2;
/* 172 */         p.sendMessage(ChatColor.RED + "Error in obtaining the name from UUID.");
/* 173 */         return true; }
/*     */       String playerName2;
/* 175 */       if (super.getPlugin().getDeathBanListener().isDeathBanned(toCheck2, false)) {
/* 176 */         super.getPlugin().getDeathBanListener().removeDeathBan(toCheck2);
/* 177 */         if (!p.hasPermission("core.lives.infinite")) {
/* 178 */           editLives(p.getUniqueId(), -1);
/*     */         }
/* 180 */         p.sendMessage(ChatColor.RED + "Revived " + playerName2 + ".");
/*     */       }
/*     */       else {
/* 183 */         p.sendMessage(ChatColor.RED + "That player is currently not death-banned.");
/*     */       }
/*     */     }
/* 186 */     else if (args[0].equalsIgnoreCase("consolerevive"))
/*     */     {
/* 188 */       if (args.length != 2) {
/* 189 */         s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " consolerevive <player>");
/* 190 */         return true;
/*     */       }
/*     */       try
/*     */       {
/* 194 */         toCheck2 = super.getPlugin().getUuidManager().getUUIDFromName(args[1]);
/*     */       } catch (Exception e2) {
/*     */         java.util.UUID toCheck2;
/* 197 */         s.sendMessage(ChatColor.RED + "That player could not be found.");
/* 198 */         return true; }
/*     */       java.util.UUID toCheck2;
/* 200 */       if (!s.hasPermission("core.lives.infinite")) {
/* 201 */         s.sendMessage(ChatColor.RED + "You do not have any lives to revive someone with.");
/* 202 */         return true;
/*     */       }
/*     */       try
/*     */       {
/* 206 */         playerName2 = super.getPlugin().getUuidManager().getNameFromUUID(toCheck2);
/*     */       } catch (Exception e4) {
/*     */         String playerName2;
/* 209 */         s.sendMessage(ChatColor.RED + "Error in obtaining the name from UUID.");
/* 210 */         return true; }
/*     */       String playerName2;
/* 212 */       if (super.getPlugin().getDeathBanListener().isDeathBanned(toCheck2, false)) {
/* 213 */         super.getPlugin().getDeathBanListener().removeDeathBan(toCheck2);
/* 214 */         if (!s.hasPermission("core.lives.infinite")) {}
/*     */         
/*     */ 
/* 217 */         s.sendMessage(ChatColor.RED + "You have consumed a life and revived " + playerName2 + ".");
/*     */       }
/*     */       else {
/* 220 */         s.sendMessage(ChatColor.RED + "That player is currently not death-banned.");
/*     */       }
/*     */     }
/*     */     else {
/* 224 */       s.sendMessage(ChatColor.GOLD + "*** Lives Help ***");
/* 225 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " check - Check your own lives.");
/* 226 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " check [player] - Check another player's lives.");
/* 227 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " give [player] [amount] - Give another player some of your lives.");
/* 228 */       s.sendMessage(ChatColor.GRAY + "/" + c.getName() + " revive [player] - Use one of your lives to relieve a player of a deathban.");
/*     */     }
/* 230 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\LivesCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */