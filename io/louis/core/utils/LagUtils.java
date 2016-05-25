/*    */ package io.louis.core.utils;
/*    */ 
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class LagUtils extends BukkitRunnable
/*    */ {
/*    */   private int tickCount;
/*    */   private long[] tickArray;
/*    */   
/*    */   public LagUtils() {
/* 11 */     this.tickCount = 0;
/* 12 */     this.tickArray = new long['ɘ'];
/*    */   }
/*    */   
/*    */   public double getTPS() {
/* 16 */     return getTPS(100);
/*    */   }
/*    */   
/*    */   private double getTPS(int ticks) {
/* 20 */     if (this.tickCount < ticks) {
/* 21 */       return 20.0D;
/*    */     }
/* 23 */     int target = (this.tickCount - 1 - ticks) % this.tickArray.length;
/* 24 */     long elapsed = System.currentTimeMillis() - this.tickArray[target];
/* 25 */     return ticks / (elapsed / 1000.0D);
/*    */   }
/*    */   
/*    */   public void run() {
/* 29 */     this.tickArray[(this.tickCount % this.tickArray.length)] = System.currentTimeMillis();
/* 30 */     this.tickCount += 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\utils\LagUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */