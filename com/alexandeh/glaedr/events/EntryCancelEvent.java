/*    */ package com.alexandeh.glaedr.events;
/*    */ 
/*    */ import com.alexandeh.glaedr.scoreboards.Entry;
/*    */ import com.alexandeh.glaedr.scoreboards.PlayerScoreboard;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ public class EntryCancelEvent
/*    */   extends org.bukkit.event.Event
/*    */ {
/* 12 */   public void setEntry(Entry entry) { this.entry = entry; } public void setScoreboard(PlayerScoreboard scoreboard) { this.scoreboard = scoreboard; } public void setPlayer(Player player) { this.player = player; }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 18 */   private static final HandlerList handlers = new HandlerList();
/* 19 */   public Entry getEntry() { return this.entry; }
/* 20 */   public PlayerScoreboard getScoreboard() { return this.scoreboard; }
/* 21 */   public Player getPlayer() { return this.player; }
/*    */   
/*    */   public EntryCancelEvent(Entry entry, PlayerScoreboard scoreboard) {
/* 24 */     this.entry = entry;
/* 25 */     this.scoreboard = scoreboard;
/* 26 */     this.player = scoreboard.getPlayer(); }
/*    */   
/*    */   private Entry entry;
/* 29 */   public HandlerList getHandlers() { return handlers; }
/*    */   
/*    */   private PlayerScoreboard scoreboard;
/*    */   private Player player;
/* 33 */   public static HandlerList getHandlerList() { return handlers; }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\com\alexandeh\glaedr\events\EntryCancelEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */