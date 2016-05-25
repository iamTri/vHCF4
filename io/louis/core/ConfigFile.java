/*    */ package io.louis.core;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class ConfigFile
/*    */ {
/*    */   private Core main;
/*    */   private File file;
/*    */   private YamlConfiguration configuration;
/*    */   
/*    */   public ConfigFile()
/*    */   {
/* 16 */     (this.main = Core.getInstance()).saveDefaultConfig();
/* 17 */     this.file = new File(this.main.getDataFolder(), "config.yml");
/* 18 */     this.configuration = YamlConfiguration.loadConfiguration(this.file);
/*    */   }
/*    */   
/*    */   public double getDouble(String path) {
/* 22 */     if (this.configuration.contains(path)) {
/* 23 */       return this.configuration.getDouble(path);
/*    */     }
/* 25 */     return 0.0D;
/*    */   }
/*    */   
/*    */   public int getInt(String path) {
/* 29 */     if (this.configuration.contains(path)) {
/* 30 */       return this.configuration.getInt(path);
/*    */     }
/* 32 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean getBoolean(String path) {
/* 36 */     return (this.configuration.contains(path)) && (this.configuration.getBoolean(path));
/*    */   }
/*    */   
/*    */   public String getString(String path) {
/* 40 */     if (this.configuration.contains(path)) {
/* 41 */       return ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
/*    */     }
/* 43 */     return "ERROR: STRING NOT FOUND";
/*    */   }
/*    */   
/*    */   public java.util.List<String> getStringList(String path) {
/* 47 */     if (this.configuration.contains(path)) {
/* 48 */       ArrayList<String> strings = new ArrayList();
/* 49 */       for (String string : this.configuration.getStringList(path)) {
/* 50 */         strings.add(ChatColor.translateAlternateColorCodes('&', string));
/*    */       }
/* 52 */       return strings;
/*    */     }
/* 54 */     return java.util.Arrays.asList(new String[] { "ERROR: STRING LIST NOT FOUND!" });
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\ConfigFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */