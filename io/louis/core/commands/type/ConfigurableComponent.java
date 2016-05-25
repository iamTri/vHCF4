/*     */ package io.louis.core.commands.type;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ 
/*     */ public class ConfigurableComponent
/*     */ {
/*     */   private Core plugin;
/*     */   private String name;
/*     */   private File file;
/*     */   private FileConfiguration config;
/*     */   public Map<String, Object> dataMap;
/*     */   
/*     */   public ConfigurableComponent(Core mainPlugin, String name)
/*     */   {
/*  20 */     this.plugin = mainPlugin;
/*  21 */     this.name = name;
/*  22 */     this.file = new File(this.plugin.getDataFolder(), this.name + ".yml");
/*  23 */     if (!this.file.exists()) {
/*     */       try {
/*  25 */         this.file.createNewFile();
/*     */       }
/*     */       catch (IOException e) {
/*  28 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  31 */     this.config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(this.file);
/*  32 */     this.dataMap = new java.util.HashMap();
/*  33 */     loadConfig();
/*     */   }
/*     */   
/*     */   public void saveAll() {
/*  37 */     for (Map.Entry<String, Object> data : getDataMap().entrySet()) {
/*  38 */       getConfig().set(this.name + "." + (String)data.getKey(), data.getValue());
/*     */     }
/*  40 */     saveConfig(true);
/*     */   }
/*     */   
/*     */   public void saveConfig(boolean shutdown) {
/*  44 */     if (!shutdown)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  56 */       new org.bukkit.scheduler.BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  47 */           synchronized (ConfigurableComponent.this.config) {
/*     */             try {
/*  49 */               ConfigurableComponent.this.config.save(ConfigurableComponent.this.file);
/*     */             }
/*     */             catch (IOException e) {
/*  52 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*  56 */       }.runTaskAsynchronously(getPlugin());
/*     */     } else {
/*     */       try
/*     */       {
/*  60 */         this.config.save(this.file);
/*     */       }
/*     */       catch (IOException e) {
/*  63 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  69 */     return getDataMap().isEmpty();
/*     */   }
/*     */   
/*     */   public boolean contains(Object key) {
/*  73 */     return getDataMap().containsKey(key.toString());
/*     */   }
/*     */   
/*     */   public void set(String path, Object value) {
/*  77 */     getConfig().set(getName() + "." + path, value);
/*  78 */     saveConfig(false);
/*     */   }
/*     */   
/*     */   public void loadConfig() { org.bukkit.configuration.ConfigurationSection configSection;
/*  82 */     if (getConfig().contains(getName())) {
/*  83 */       configSection = getConfig().getConfigurationSection(getName());
/*  84 */       for (String key : configSection.getKeys(false)) {
/*  85 */         getDataMap().put(key, configSection.get(key));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/*  91 */     getConfig().set(getName(), null);
/*  92 */     saveConfig(false);
/*  93 */     getDataMap().clear();
/*     */   }
/*     */   
/*     */   public Object put(Object key, Object value) {
/*  97 */     return getDataMap().put(key.toString(), value);
/*     */   }
/*     */   
/*     */   public Object remove(Object key) {
/* 101 */     return getDataMap().remove(key.toString());
/*     */   }
/*     */   
/*     */   public Object get(Object key) {
/* 105 */     return getDataMap().get(key.toString());
/*     */   }
/*     */   
/*     */   public Core getPlugin() {
/* 109 */     return this.plugin;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 113 */     return this.name;
/*     */   }
/*     */   
/*     */   public File getFile() {
/* 117 */     return this.file;
/*     */   }
/*     */   
/*     */   public FileConfiguration getConfig() {
/* 121 */     return this.config;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getDataMap() {
/* 125 */     return this.dataMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\type\ConfigurableComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */