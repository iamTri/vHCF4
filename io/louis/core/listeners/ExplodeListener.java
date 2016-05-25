/*    */ package io.louis.core.listeners;
/*    */ 
/*    */ import org.bukkit.event.entity.EntityExplodeEvent;
/*    */ 
/*    */ public class ExplodeListener implements org.bukkit.event.Listener
/*    */ {
/*    */   @org.bukkit.event.EventHandler(ignoreCancelled=false, priority=org.bukkit.event.EventPriority.LOWEST)
/*    */   public void onExplode(EntityExplodeEvent e)
/*    */   {
/* 10 */     e.setCancelled(true);
/* 11 */     e.setYield(0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\ExplodeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */