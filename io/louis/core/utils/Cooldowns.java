/*    */ package io.louis.core.utils;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class Cooldowns
/*    */ {
/* 10 */   private static HashMap<String, HashMap<UUID, Long>> cooldown = new HashMap();
/*    */   
/*    */ 
/*    */   public static void createCooldown(String k)
/*    */   {
/* 15 */     if (cooldown.containsKey(k)) {
/* 16 */       throw new IllegalArgumentException("Cooldown already exists.");
/*    */     }
/* 18 */     cooldown.put(k, new HashMap());
/*    */   }
/*    */   
/*    */ 
/*    */   public static HashMap<UUID, Long> getCooldownMap(String k)
/*    */   {
/* 24 */     if (cooldown.containsKey(k)) {
/* 25 */       return (HashMap)cooldown.get(k);
/*    */     }
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public static void addCooldown(String k, Player p, int seconds)
/*    */   {
/* 32 */     if (!cooldown.containsKey(k)) {
/* 33 */       throw new IllegalArgumentException(k + " does not exist");
/*    */     }
/* 35 */     long next = System.currentTimeMillis() + seconds * 1000L;
/* 36 */     ((HashMap)cooldown.get(k)).put(p.getUniqueId(), Long.valueOf(next));
/*    */   }
/*    */   
/*    */   public static boolean isOnCooldown(String k, Player p)
/*    */   {
/* 41 */     return (cooldown.containsKey(k)) && (((HashMap)cooldown.get(k)).containsKey(p.getUniqueId())) && (System.currentTimeMillis() <= ((Long)((HashMap)cooldown.get(k)).get(p.getUniqueId())).longValue());
/*    */   }
/*    */   
/*    */ 
/*    */   public static int getCooldownForPlayerInt(String k, Player p)
/*    */   {
/* 47 */     return (int)(((Long)((HashMap)cooldown.get(k)).get(p.getUniqueId())).longValue() - System.currentTimeMillis()) / 1000;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\utils\Cooldowns.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */