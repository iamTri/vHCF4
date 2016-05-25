/*     */ package com.alexandeh.glaedr;
/*     */ 
/*     */ import com.alexandeh.glaedr.events.EntryCancelEvent;
/*     */ import com.alexandeh.glaedr.events.EntryFinishEvent;
/*     */ import com.alexandeh.glaedr.events.EntryTickEvent;
/*     */ import com.alexandeh.glaedr.scoreboards.Entry;
/*     */ import com.alexandeh.glaedr.scoreboards.PlayerScoreboard;
/*     */ import com.alexandeh.glaedr.scoreboards.Wrapper;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Glaedr
/*     */   implements Listener
/*     */ {
/*     */   private static JavaPlugin plugin;
/*     */   private String title;
/*     */   private boolean hook;
/*     */   private boolean overrideTitle;
/*     */   private boolean scoreCountUp;
/*     */   private List<String> bottomWrappers;
/*     */   private List<String> topWrappers;
/*     */   
/*  39 */   public String getTitle() { return this.title; }
/*  40 */   public boolean isHook() { return this.hook; } public boolean isOverrideTitle() { return this.overrideTitle; } public boolean isScoreCountUp() { return this.scoreCountUp; }
/*  41 */   public List<String> getBottomWrappers() { return this.bottomWrappers; } public List<String> getTopWrappers() { return this.topWrappers; }
/*     */   
/*     */   public Glaedr(JavaPlugin plugin, String title, boolean hook, boolean overrideTitle, boolean scoreCountUp) {
/*  44 */     plugin = plugin;
/*  45 */     this.title = ChatColor.translateAlternateColorCodes('&', title);
/*  46 */     this.hook = hook;
/*  47 */     this.overrideTitle = overrideTitle;
/*  48 */     this.scoreCountUp = scoreCountUp;
/*     */     
/*  50 */     this.bottomWrappers = new ArrayList();
/*  51 */     this.topWrappers = new ArrayList();
/*     */     
/*  53 */     Bukkit.getPluginManager().registerEvents(this, plugin);
/*     */     
/*  55 */     new BukkitRunnable()
/*     */     {
/*     */       public void run() {
/*  58 */         for (PlayerScoreboard scoreboard : ) {
/*  59 */           Iterator<Entry> entryIterator = scoreboard.getEntries().iterator();
/*  60 */           while (entryIterator.hasNext())
/*     */           {
/*     */ 
/*  63 */             Entry entry = (Entry)entryIterator.next();
/*     */             
/*  65 */             if (entry.isCancelled()) {
/*  66 */               scoreboard.getScoreboard().resetScores(entry.getKey());
/*  67 */               scoreboard.getKeys().remove(entry);
/*  68 */               scoreboard.getScores().remove(entry);
/*  69 */               entryIterator.remove();
/*     */               
/*  71 */               if ((entry.getTime() != null) && (entry.getTime().doubleValue() > 0.0D)) {
/*  72 */                 Bukkit.getPluginManager().callEvent(new EntryCancelEvent(entry, scoreboard));
/*     */               }
/*     */               
/*     */             }
/*     */             else
/*     */             {
/*  78 */               Iterator<Wrapper> wrapperIterator = scoreboard.getWrappers().iterator();
/*  79 */               while (wrapperIterator.hasNext()) {
/*  80 */                 Wrapper wrapper = (Wrapper)wrapperIterator.next();
/*     */                 
/*  82 */                 if (scoreboard.getEntries().size() == 0) {
/*  83 */                   scoreboard.getScoreboard().resetScores(wrapper.getKey());
/*  84 */                   scoreboard.getKeys().remove(wrapper);
/*  85 */                   scoreboard.getScores().remove(wrapper);
/*     */                 }
/*     */                 else
/*     */                 {
/*  89 */                   wrapper.sendScoreboardUpdate(wrapper.getText());
/*     */                 }
/*     */               }
/*  92 */               Bukkit.getPluginManager().callEvent(new EntryTickEvent(entry, scoreboard));
/*     */               
/*  94 */               if ((!entry.isCountdown()) && (!entry.isCountup()) && (entry.getTime() == null)) {
/*  95 */                 entry.sendScoreboardUpdate(entry.getText());
/*     */ 
/*     */ 
/*     */               }
/*  99 */               else if ((entry.getTime().doubleValue() <= 0.0D) && (!entry.isCountup())) {
/* 100 */                 entry.setCancelled(true);
/* 101 */                 Bukkit.getPluginManager().callEvent(new EntryFinishEvent(entry, scoreboard));
/*     */ 
/*     */ 
/*     */               }
/* 105 */               else if ((60 > entry.getTime().intValue()) || (entry.isBypassAutoFormat())) {
/* 106 */                 String toSend = entry.getText() + " " + entry.getTime();
/* 107 */                 if (!entry.isRemoveTimeSuffix()) {
/* 108 */                   toSend = toSend + "s";
/*     */                 }
/* 110 */                 entry.setTextTime(entry.getTime() + "s");
/* 111 */                 entry.sendScoreboardUpdate(toSend);
/* 112 */                 if ((!entry.isPaused()) && ((entry.isCountdown()) || (entry.isCountup()))) {
/* 113 */                   if (entry.isCountup()) {
/* 114 */                     entry.setTime(entry.getTime().add(BigDecimal.valueOf(0.1D)));
/*     */                   } else {
/* 116 */                     entry.setTime(entry.getTime().subtract(BigDecimal.valueOf(0.1D)));
/*     */                   }
/*     */                   
/*     */                 }
/*     */               }
/* 121 */               else if (3600 > entry.getTime().intValue()) {
/* 122 */                 entry.setInterval(entry.getInterval() - 1);
/*     */                 
/* 124 */                 int minutes = entry.getTime().intValue() / 60;
/* 125 */                 int seconds = entry.getTime().intValue() % 60;
/* 126 */                 DecimalFormat formatter = new DecimalFormat("00");
/* 127 */                 String toSend = entry.getText() + " " + formatter.format(minutes) + ":" + formatter.format(seconds);
/* 128 */                 entry.setTextTime(formatter.format(minutes) + ":" + formatter.format(seconds));
/*     */                 
/* 130 */                 if (!entry.isRemoveTimeSuffix()) {
/* 131 */                   toSend = toSend + "m";
/* 132 */                   entry.setTextTime(formatter.format(minutes) + ":" + formatter.format(seconds) + "m");
/*     */                 }
/*     */                 
/* 135 */                 entry.sendScoreboardUpdate(toSend);
/*     */                 
/* 137 */                 if (entry.getInterval() <= 0) {
/* 138 */                   if ((!entry.isPaused()) && ((entry.isCountdown()) || (entry.isCountup()))) {
/* 139 */                     if (entry.isCountup()) {
/* 140 */                       entry.setTime(entry.getTime().add(BigDecimal.ONE));
/*     */                     } else {
/* 142 */                       entry.setTime(entry.getTime().subtract(BigDecimal.ONE));
/*     */                     }
/*     */                   }
/* 145 */                   entry.setInterval(10);
/*     */                 }
/*     */               }
/*     */               else {
/* 149 */                 entry.setInterval(entry.getInterval() - 1);
/*     */                 
/* 151 */                 int hours = entry.getTime().intValue() / 3600;
/* 152 */                 int minutes = entry.getTime().intValue() % 3600 / 60;
/* 153 */                 int seconds = entry.getTime().intValue() % 60;
/*     */                 
/* 155 */                 DecimalFormat formatter = new DecimalFormat("00");
/* 156 */                 String toSend = entry.getText() + " " + formatter.format(hours) + ":" + formatter.format(minutes) + ":" + formatter.format(seconds);
/* 157 */                 entry.setTextTime(formatter.format(hours) + ":" + formatter.format(minutes) + ":" + formatter.format(seconds));
/*     */                 
/* 159 */                 if (!entry.isRemoveTimeSuffix()) {
/* 160 */                   toSend = toSend + "m";
/* 161 */                   entry.setTextTime(formatter.format(minutes) + ":" + formatter.format(seconds) + "m");
/*     */                 }
/*     */                 
/* 164 */                 entry.sendScoreboardUpdate(toSend);
/*     */                 
/* 166 */                 if (entry.getInterval() <= 0) {
/* 167 */                   if ((!entry.isPaused()) && ((entry.isCountdown()) || (entry.isCountup()))) {
/* 168 */                     if (entry.isCountup()) {
/* 169 */                       entry.setTime(entry.getTime().add(BigDecimal.ONE));
/*     */                     } else {
/* 171 */                       entry.setTime(entry.getTime().subtract(BigDecimal.ONE));
/*     */                     }
/*     */                   }
/*     */                   
/* 175 */                   entry.setInterval(10);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 181 */           Iterator<Wrapper> wrapperIterator = scoreboard.getWrappers().iterator();
/* 182 */           while (wrapperIterator.hasNext()) {
/* 183 */             Wrapper wrapper = (Wrapper)wrapperIterator.next();
/*     */             
/* 185 */             if (scoreboard.getEntries().size() == 0) {
/* 186 */               scoreboard.getScoreboard().resetScores(wrapper.getKey());
/* 187 */               scoreboard.getKeys().remove(wrapper);
/* 188 */               scoreboard.getScores().remove(wrapper);
/*     */             }
/*     */             else
/*     */             {
/* 192 */               wrapper.sendScoreboardUpdate(wrapper.getText()); } } } } }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 197 */       .runTaskTimer(plugin, 2L, 2L);
/*     */   }
/*     */   
/*     */   public Glaedr(JavaPlugin plugin, String title) {
/* 201 */     this(plugin, title, false, true, false);
/*     */   }
/*     */   
/*     */   public void registerPlayers() {
/* 205 */     for (Player player : ) {
/* 206 */       long oldTime = System.currentTimeMillis();
/* 207 */       new PlayerScoreboard(this, player);
/* 208 */       player.sendMessage(ChatColor.BLUE + "Scoreboard created in " + (System.currentTimeMillis() - oldTime) + "ms.");
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onJoin(PlayerJoinEvent event) {
/* 214 */     Player player = event.getPlayer();
/* 215 */     PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(player);
/* 216 */     if (playerScoreboard == null) {
/* 217 */       long oldTime = System.currentTimeMillis();
/* 218 */       new PlayerScoreboard(this, player);
/* 219 */       player.sendMessage(ChatColor.BLUE + "Scoreboard created in " + (System.currentTimeMillis() - oldTime) + "ms.");
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
/*     */     }
/*     */     else
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
/* 242 */       player.setScoreboard(playerScoreboard.getScoreboard());
/*     */     }
/*     */   }
/*     */   
/*     */   public static JavaPlugin getPlugin() {
/* 247 */     return plugin;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\com\alexandeh\glaedr\Glaedr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */