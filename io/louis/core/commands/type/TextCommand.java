/*    */ package io.louis.core.commands.type;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.Scanner;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class TextCommand implements org.bukkit.command.CommandExecutor
/*    */ {
/*    */   private String name;
/*    */   private File file;
/*    */   
/*    */   public TextCommand(io.louis.core.Core mainPlugin, String name)
/*    */   {
/* 15 */     this.name = name;
/* 16 */     this.file = new File(mainPlugin.getDataFolder(), this.name + ".txt");
/* 17 */     if (!this.file.exists()) {
/*    */       try {
/* 19 */         this.file.createNewFile();
/*    */       }
/*    */       catch (IOException e) {
/* 22 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/*    */     try {
/* 29 */       Scanner scanner = new Scanner(this.file);
/* 30 */       while (scanner.hasNextLine()) {
/* 31 */         s.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', scanner.nextLine()));
/*    */       }
/* 33 */       scanner.close();
/*    */     }
/*    */     catch (Exception e) {
/* 36 */       s.sendMessage(org.bukkit.ChatColor.RED + "Error reading from " + this.name + " file.");
/*    */     }
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\type\TextCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */