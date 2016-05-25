/*    */ package io.louis.core;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class LanguageFile
/*    */ {
/*    */   private Core main;
/*    */   private File file;
/*    */   private YamlConfiguration configuration;
/*    */   
/*    */   public LanguageFile()
/*    */   {
/* 16 */     this.file = new File(this.main.getDataFolder(), "lang.yml");
/* 17 */     this.configuration = YamlConfiguration.loadConfiguration(this.file);
/*    */   }
/*    */   
/*    */   public String getString(String path) {
/* 21 */     if (this.configuration.contains(path)) {
/* 22 */       return ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
/*    */     }
/* 24 */     return "ERROR: STRING NOT FOUND";
/*    */   }
/*    */   
/*    */   public java.util.List<String> getStringList(String path) {
/* 28 */     if (this.configuration.contains(path)) {
/* 29 */       ArrayList<String> strings = new ArrayList();
/* 30 */       for (String string : this.configuration.getStringList(path)) {
/* 31 */         strings.add(ChatColor.translateAlternateColorCodes('&', string));
/*    */       }
/* 33 */       return strings;
/*    */     }
/* 35 */     return java.util.Arrays.asList(new String[] { "ERROR: STRING LIST NOT FOUND!" });
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\LanguageFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */