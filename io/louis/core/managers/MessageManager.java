/*    */ package io.louis.core.managers;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ public class MessageManager
/*    */ {
/*    */   private String addLine(String string, String toadd) {
/*  8 */     return ChatColor.translateAlternateColorCodes('&', string + "\n" + toadd);
/*    */   }
/*    */   
/*    */   private String addTo(String string, String toadd) {
/* 12 */     return string + " " + toadd;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 16 */     String version = "";
/* 17 */     version = addLine(version, "&8&m-----------------------------------------------------");
/* 18 */     version = addLine(version, ChatColor.GOLD + "Factions version: " + ChatColor.GOLD + "(" + ChatColor.YELLOW + io.louis.core.Core.getInstance().getDescription().getVersion() + ChatColor.GOLD + ")");
/* 19 */     version = addLine(version, ChatColor.GOLD + "Created by" + ChatColor.YELLOW + " Boys " + ChatColor.GOLD + "(" + ChatColor.YELLOW + "Phoenix" + ChatColor.GOLD + ")");
/* 20 */     version = addLine(version, ChatColor.GOLD + "This plugin belongs solely to " + ChatColor.GOLD + "(" + ChatColor.YELLOW + "OXPVP.ORG + VANILLAHCF.COM" + ChatColor.GOLD + ")");
/* 21 */     version = addLine(version, ChatColor.GOLD + "Illegal possession of this plugin may result in legal action");
/* 22 */     version = addLine(version, "&8&m-----------------------------------------------------");
/* 23 */     return version;
/*    */   }
/*    */   
/*    */   public String getHelp(int page) {
/* 27 */     String help = "";
/* 28 */     if (page == 4) {
/* 29 */       help = addLine(help, "&8&m-----------------------------------------------------");
/* 30 */       help = addLine(help, "&6Faction Help &e(Page 4/4)");
/* 31 */       help = addLine(help, "&a/f unsubclaim > &7Removes subclaims from your faction.");
/* 32 */       help = addLine(help, "&a/f withdraw > &7Withdraws money from the faction balance.");
/* 33 */       help = addLine(help, "&6You are currently on &fPage 4/4.");
/* 34 */       help = addLine(help, "&6To view other pages, use &e/f help <page#>.");
/* 35 */       help = addLine(help, "&8&m-----------------------------------------------------");
/* 36 */     } else if (page == 2) {
/* 37 */       help = addLine(help, "&8&m-----------------------------------------------------");
/* 38 */       help = addLine(help, "&6Faction Help &e(Page 2/4)");
/* 39 */       help = addLine(help, "&a/f disband > &7Disband your faction.");
/* 40 */       help = addLine(help, "&a/f home > &7Teleport to the faction home.");
/* 41 */       help = addLine(help, "&a/f invite > &7Invite a player to the faction.");
/* 42 */       help = addLine(help, "&a/f invites > &7View faction invitations.");
/* 43 */       help = addLine(help, "&a/f kick > &7Kick a player from the faction.");
/* 44 */       help = addLine(help, "&a/f leader > &7Sets the new leader for your faction.");
/* 45 */       help = addLine(help, "&a/f leave > &7Leave your current faction.");
/* 46 */       help = addLine(help, "&a/f list > &7See a list of all factions.");
/* 47 */       help = addLine(help, "&a/f map > &7View all claims around your location.");
/* 48 */       help = addLine(help, "&a/f msg > &7Sends a message to your faction.");
/* 49 */       help = addLine(help, "&6You are currently on &fPage 2/4.");
/* 50 */       help = addLine(help, "&6To view other pages, use &e/f help <page#>.");
/* 51 */       help = addLine(help, "&8&m-----------------------------------------------------");
/* 52 */     } else if (page == 3) {
/* 53 */       help = addLine(help, "&8&m-----------------------------------------------------");
/* 54 */       help = addLine(help, "&6Faction Help &e(Page 3/4)");
/* 55 */       help = addLine(help, "&a/f open > &7Opens the faction to the public.");
/* 56 */       help = addLine(help, "&a/f rename > &7Change the name of your faction.");
/* 57 */       help = addLine(help, "&a/f promote > &7Promotes a player to a captain.");
/* 58 */       help = addLine(help, "&a/f sethome > &7Sets the faction home location.");
/* 59 */       help = addLine(help, "&a/f show > &7Get details about a faction.");
/* 60 */       help = addLine(help, "&a/f subclaim > &7Shows the subclaim help page.");
/* 61 */       help = addLine(help, "&a/f unclaim > &7Unclaims land from your faction.");
/* 62 */       help = addLine(help, "&a/f uninvite > &7Revoke an invitation to a player.");
/* 63 */       help = addLine(help, "&6You are currently on &fPage 3/4.");
/* 64 */       help = addLine(help, "&6To view other pages, use &e/f help <page#>.");
/* 65 */       help = addLine(help, "&8&m-----------------------------------------------------");
/*    */     } else {
/* 67 */       help = addLine(help, "&8&m-----------------------------------------------------");
/* 68 */       help = addLine(help, "&6Faction Help &e(Page 1/4)");
/* 69 */       help = addLine(help, "&a/f accept > &7Accept a join request from an existing faction.");
/* 70 */       help = addLine(help, "&a/f chat > &7Toggle faction chat only mode on or off.");
/* 71 */       help = addLine(help, "&a/f claim > &7Claim land in the Wilderness.");
/* 72 */       help = addLine(help, "&a/f create > &7Create a faction.");
/* 73 */       help = addLine(help, "&a/f demote > &7Demotes a player to a member.");
/* 74 */       help = addLine(help, "&a/f deposit > &7Deposits money to the faction balance.");
/* 75 */       help = addLine(help, "&6You are currently on &fPage 1/4.");
/* 76 */       help = addLine(help, "&6To view other pages, use &e/f help <page#>");
/* 77 */       help = addLine(help, "&8&m-----------------------------------------------------");
/*    */     }
/*    */     
/* 80 */     return help;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\managers\MessageManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */