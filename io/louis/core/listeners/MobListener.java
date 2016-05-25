/*    */ package io.louis.core.listeners;
/*    */ 
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*    */ 
/*    */ public class MobListener implements org.bukkit.event.Listener
/*    */ {
/*    */   @EventHandler(priority=org.bukkit.event.EventPriority.LOW)
/*    */   public void onDamage(EntityDamageByEntityEvent e)
/*    */   {
/* 11 */     if ((e.getDamager() instanceof org.bukkit.entity.Ghast)) {
/* 12 */       e.setCancelled(true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\MobListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */