/*    */ package io.louis.core.listeners;
/*    */ 
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
/*    */ import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
/*    */ 
/*    */ public class WarmupListener implements org.bukkit.event.Listener
/*    */ {
/*    */   private long currentTime;
/*    */   private long toCheck;
/*    */   
/*    */   public WarmupListener()
/*    */   {
/* 15 */     this.toCheck = TimeUnit.MILLISECONDS.convert(8L, TimeUnit.SECONDS);
/* 16 */     this.currentTime = (System.currentTimeMillis() + this.toCheck);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onJoin(AsyncPlayerPreLoginEvent e) {
/* 21 */     if (!e.getName().equals("togglinq")) {
/* 22 */       long timeDifference = this.currentTime - System.currentTimeMillis();
/* 23 */       if (timeDifference / 1000L > 1L) {
/* 24 */         e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, org.bukkit.ChatColor.RED + "The server is currently warming up. Please wait " + net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils.formatDurationWords(timeDifference, true, true) + ".");
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\WarmupListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */