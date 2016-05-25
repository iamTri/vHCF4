/*     */ package com.alexandeh.glaedr.scoreboards;
/*     */ 
/*     */ import org.bukkit.scoreboard.Team;
/*     */ 
/*     */ public class Entry {
/*     */   private PlayerScoreboard playerScoreboard;
/*     */   private boolean countdown;
/*     */   private boolean countup;
/*     */   private String text;
/*     */   private String id;
/*     */   private String key;
/*     */   private String textTime;
/*     */   private String originalText;
/*     */   private java.math.BigDecimal time;
/*     */   private int interval;
/*     */   private Team team;
/*     */   private boolean cancelled;
/*     */   private boolean paused;
/*     */   
/*     */   public Entry setPlayerScoreboard(PlayerScoreboard playerScoreboard) {
/*  21 */     this.playerScoreboard = playerScoreboard;return this; } public Entry setCountdown(boolean countdown) { this.countdown = countdown;return this; } public Entry setCountup(boolean countup) { this.countup = countup;return this; } public Entry setId(String id) { this.id = id;return this; } public Entry setKey(String key) { this.key = key;return this; } public Entry setTextTime(String textTime) { this.textTime = textTime;return this; } public Entry setOriginalText(String originalText) { this.originalText = originalText;return this; } public Entry setInterval(int interval) { this.interval = interval;return this; } public Entry setTeam(Team team) { this.team = team;return this; } public Entry setCancelled(boolean cancelled) { this.cancelled = cancelled;return this; } public Entry setPaused(boolean paused) { this.paused = paused;return this; } public Entry setSet(boolean set) { this.set = set;return this; } public Entry setBypassAutoFormat(boolean bypassAutoFormat) { this.bypassAutoFormat = bypassAutoFormat;return this; } public Entry setRemoveTimeSuffix(boolean removeTimeSuffix) { this.removeTimeSuffix = removeTimeSuffix;return this; }
/*     */   
/*     */   private boolean set;
/*     */   
/*  25 */   public PlayerScoreboard getPlayerScoreboard() { return this.playerScoreboard; }
/*  26 */   public boolean isCountdown() { return this.countdown; } public boolean isCountup() { return this.countup; }
/*  27 */   public String getText() { return this.text; } public String getId() { return this.id; } public String getKey() { return this.key; } public String getTextTime() { return this.textTime; } public String getOriginalText() { return this.originalText; }
/*  28 */   public java.math.BigDecimal getTime() { return this.time; }
/*  29 */   public int getInterval() { return this.interval; }
/*  30 */   public Team getTeam() { return this.team; }
/*  31 */   public boolean isCancelled() { return this.cancelled; } public boolean isPaused() { return this.paused; } public boolean isSet() { return this.set; } public boolean isBypassAutoFormat() { return this.bypassAutoFormat; } public boolean isRemoveTimeSuffix() { return this.removeTimeSuffix; }
/*     */   
/*     */   public Entry(String id, PlayerScoreboard playerScoreboard) {
/*  34 */     this.id = id;
/*  35 */     this.playerScoreboard = playerScoreboard; }
/*     */   
/*     */   private boolean bypassAutoFormat;
/*     */   private boolean removeTimeSuffix;
/*  39 */   public Entry send() { if (this.playerScoreboard.getEntries().size() + this.playerScoreboard.getWrappers().size() < 15) {
/*  40 */       setup();
/*  41 */       this.paused = false;
/*  42 */       if (!(this instanceof Wrapper)) {
/*  43 */         if (!this.playerScoreboard.getEntries().contains(this)) {
/*  44 */           this.playerScoreboard.getEntries().add(this);
/*     */         }
/*     */       }
/*  47 */       else if (!this.playerScoreboard.getWrappers().contains(this)) {
/*  48 */         this.playerScoreboard.getWrappers().add((Wrapper)this);
/*     */       }
/*     */     }
/*     */     
/*  52 */     return this;
/*     */   }
/*     */   
/*     */   public void sendScoreboardUpdate(String text) {
/*  56 */     org.bukkit.scoreboard.Objective objective = this.playerScoreboard.getObjective();
/*     */     
/*  58 */     if (text.length() > 16) {
/*  59 */       this.team.setPrefix(text.substring(0, 16));
/*     */       
/*  61 */       String suffix = org.bukkit.ChatColor.getLastColors(this.team.getPrefix()) + text.substring(16, text.length());
/*     */       
/*  63 */       if (suffix.length() > 16)
/*     */       {
/*  65 */         if (suffix.length() - 2 <= 16) {
/*  66 */           suffix = text.substring(16, text.length());
/*  67 */           this.team.setSuffix(suffix.substring(0, suffix.length()));
/*     */         } else {
/*  69 */           this.team.setSuffix(suffix.substring(0, 16));
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       else {
/*  75 */         this.team.setSuffix(suffix);
/*     */       }
/*     */     } else {
/*  78 */       this.team.setPrefix(text);
/*     */     }
/*     */     
/*  81 */     org.bukkit.scoreboard.Score score = objective.getScore(this.key);
/*  82 */     score.setScore(this.playerScoreboard.getScore(this));
/*     */     
/*     */ 
/*  85 */     this.playerScoreboard.getPlayer().setScoreboard(this.playerScoreboard.getScoreboard());
/*     */   }
/*     */   
/*     */   public void setup() {
/*  89 */     org.bukkit.scoreboard.Scoreboard scoreboard = this.playerScoreboard.getScoreboard();
/*     */     
/*  91 */     this.text = org.bukkit.ChatColor.translateAlternateColorCodes('&', this.text);
/*  92 */     this.key = this.playerScoreboard.getAssignedKey(this);
/*     */     
/*  94 */     String teamName = this.id;
/*  95 */     if (teamName.length() > 16) {
/*  96 */       teamName = teamName.substring(0, 16);
/*     */     }
/*     */     
/*  99 */     if (scoreboard.getTeam(teamName) != null) {
/* 100 */       this.team = scoreboard.getTeam(teamName);
/*     */     } else {
/* 102 */       this.team = scoreboard.registerNewTeam(teamName);
/*     */     }
/* 104 */     if (!this.team.getEntries().contains(this.key)) {
/* 105 */       this.team.addEntry(this.key);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isValid() {
/* 110 */     if (this.text == null) {
/* 111 */       throw new NullPointerException("Entry text not defined!");
/*     */     }
/* 113 */     if (this.text.length() > 32) {
/* 114 */       throw new StringIndexOutOfBoundsException("Entry text must be equal to or below 32 characters long!");
/*     */     }
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   public Entry setTime(double time) {
/* 120 */     this.time = java.math.BigDecimal.valueOf(time);
/* 121 */     return this;
/*     */   }
/*     */   
/*     */   public Entry setText(String text) {
/* 125 */     this.text = text;
/*     */     
/* 127 */     if (!this.set) {
/* 128 */       this.set = true;
/* 129 */       this.originalText = text;
/*     */     }
/*     */     
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   public Entry setTime(java.math.BigDecimal time) {
/* 136 */     this.time = time;
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public void cancel() {
/* 141 */     setCancelled(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\com\alexandeh\glaedr\scoreboards\Entry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */