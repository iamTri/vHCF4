/*    */ package io.louis.core.utils;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ 
/*    */ public class Lists
/*    */ {
/* 17 */   public static ArrayList<String> staffChat = new ArrayList();
/* 18 */   public static boolean chatEnabled = true;
/* 19 */   public static List<Player> globalChat = new ArrayList();
/* 20 */   public static ArrayList<UUID> staff = new ArrayList();
/*    */   
/*    */ 
/*    */   public static void applyKnockBack(Entity en, final Location loc, final int knock, final boolean sprint)
/*    */   {
/* 25 */     Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable()
/*    */     {
/*    */       public void run()
/*    */       {
/* 29 */         Vector localVector = this.val$en.getLocation().toVector().subtract(loc.toVector()).normalize();
/*    */         
/* 31 */         double d = Lists.getVelocity() + knock * 0.05D;
/* 32 */         if (sprint) {
/* 33 */           d *= 20.0D;
/*    */         }
/* 35 */         ((Entity)localVector).setVelocity(localVector.multiply(d).setY(Lists.getHeight()));
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   public static double getVelocity()
/*    */   {
/* 42 */     return 0.35D;
/*    */   }
/*    */   
/*    */   public static double getHeight()
/*    */   {
/* 47 */     return 0.4D;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 52 */   public static int SAFEZONE_RADIUS = 60;
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\utils\Lists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */