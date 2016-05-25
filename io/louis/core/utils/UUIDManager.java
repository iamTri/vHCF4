/*    */ package io.louis.core.utils;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import io.louis.core.Core;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
/*    */ 
/*    */ public class UUIDManager implements org.bukkit.event.Listener
/*    */ {
/*    */   private Core mainPlugin;
/*    */   private File file;
/*    */   private FileConfiguration config;
/*    */   private final BiMap<UUID, String> storedUUIDs;
/*    */   
/*    */   public UUIDManager(Core mainPlugin)
/*    */   {
/* 21 */     this.mainPlugin = mainPlugin;
/* 22 */     this.file = new File(this.mainPlugin.getDataFolder(), "uuids.yml");
/* 23 */     if (!this.file.exists()) {
/*    */       try {
/* 25 */         this.file.createNewFile();
/*    */       }
/*    */       catch (IOException e) {
/* 28 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 31 */     this.config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(this.file);
/* 32 */     this.storedUUIDs = com.google.common.collect.HashBiMap.create();
/* 33 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void set(final String path, final Object value)
/*    */   {
/* 47 */     new org.bukkit.scheduler.BukkitRunnable()
/*    */     {
/*    */       public void run()
/*    */       {
/*    */         try
/*    */         {
/* 40 */           UUIDManager.this.getConfig().set(path, value);
/* 41 */           UUIDManager.this.getConfig().save(UUIDManager.this.getFile());
/*    */         }
/*    */         catch (IOException e) {
/* 44 */           e.printStackTrace(); } } }
/*    */     
/*    */ 
/* 47 */       .runTaskAsynchronously(this.mainPlugin);
/*    */   }
/*    */   
/*    */   public String getNameFromUUID(UUID uuid)
/*    */   {
/* 52 */     if (this.storedUUIDs.containsKey(uuid)) {
/* 53 */       return (String)this.storedUUIDs.get(uuid);
/*    */     }
/* 55 */     String foundName = getConfig().getString(uuid.toString(), null);
/* 56 */     if (!foundName.equals(null)) {
/* 57 */       this.storedUUIDs.forcePut(uuid, foundName);
/*    */     }
/* 59 */     return foundName;
/*    */   }
/*    */   
/*    */   public UUID getUUIDFromName(String name)
/*    */   {
/* 64 */     if (this.storedUUIDs.containsValue(name)) {
/* 65 */       return (UUID)this.storedUUIDs.inverse().get(name);
/*    */     }
/* 67 */     UUID foundUUID = null;
/* 68 */     for (String playerUUID : getConfig().getKeys(false))
/*    */     {
/* 70 */       String playerName = getConfig().getString(playerUUID);
/* 71 */       if (name.equalsIgnoreCase(playerName))
/*    */       {
/* 73 */         foundUUID = UUID.fromString(playerUUID);
/* 74 */         this.storedUUIDs.forcePut(foundUUID, playerName);
/* 75 */         break;
/*    */       }
/*    */     }
/* 78 */     return foundUUID;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPreLogin(AsyncPlayerPreLoginEvent e)
/*    */   {
/* 84 */     set(e.getUniqueId().toString(), e.getName());
/* 85 */     getStoredUUIDs().forcePut(e.getUniqueId(), e.getName());
/*    */   }
/*    */   
/*    */   public File getFile() {
/* 89 */     return this.file;
/*    */   }
/*    */   
/*    */   public FileConfiguration getConfig() {
/* 93 */     return this.config;
/*    */   }
/*    */   
/*    */   public BiMap<UUID, String> getStoredUUIDs() {
/* 97 */     return this.storedUUIDs;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\utils\UUIDManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */