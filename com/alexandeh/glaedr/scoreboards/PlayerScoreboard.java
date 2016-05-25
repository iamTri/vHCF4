/*     */ package com.alexandeh.glaedr.scoreboards;
/*     */ 
/*     */ import com.alexandeh.glaedr.Glaedr;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Objective;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ import org.bukkit.scoreboard.ScoreboardManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerScoreboard
/*     */ {
/*  24 */   private static Set<PlayerScoreboard> scoreboards = new HashSet();
/*     */   private Player player; private Objective objective; private Scoreboard scoreboard;
/*  26 */   public Player getPlayer() { return this.player; }
/*  27 */   private Map<Entry, String> keys; public void setObjective(Objective objective) { this.objective = objective; } private Map<Entry, Integer> scores;
/*  28 */   public Objective getObjective() { return this.objective; } private List<Entry> entries;
/*  29 */   public void setScoreboard(Scoreboard scoreboard) { this.scoreboard = scoreboard; } private List<Wrapper> wrappers;
/*  30 */   public Scoreboard getScoreboard() { return this.scoreboard; } private BukkitTask task;
/*  31 */   public Map<Entry, String> getKeys() { return this.keys; }
/*  32 */   public Map<Entry, Integer> getScores() { return this.scores; }
/*  33 */   public List<Entry> getEntries() { return this.entries; }
/*  34 */   public List<Wrapper> getWrappers() { return this.wrappers; }
/*  35 */   public BukkitTask getTask() { return this.task; }
/*  36 */   public boolean isCountup() { return this.countup; } private boolean countup = false;
/*     */   
/*     */   public PlayerScoreboard(Glaedr main, Player player) {
/*  39 */     this.player = player;
/*     */     
/*  41 */     this.keys = new HashMap();
/*  42 */     this.scores = new HashMap();
/*  43 */     this.wrappers = new ArrayList();
/*  44 */     this.entries = new ArrayList();
/*     */     
/*  46 */     this.countup = main.isScoreCountUp();
/*     */     
/*  48 */     createScoreboard(main.getTitle(), main.isHook(), main.isOverrideTitle());
/*     */     
/*  50 */     for (int i = 0; i < main.getTopWrappers().size(); i++) {
/*  51 */       String string = (String)main.getTopWrappers().get(i);
/*  52 */       new Wrapper("top_" + i, this, Wrapper.WrapperType.TOP).setText(string).send();
/*     */     }
/*     */     
/*  55 */     for (int i = 0; i < main.getBottomWrappers().size(); i++) {
/*  56 */       String string = (String)main.getBottomWrappers().get(i);
/*  57 */       new Wrapper("bottom_" + i, this, Wrapper.WrapperType.BOTTOM).setText(string).send();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  62 */     scoreboards.add(this);
/*     */   }
/*     */   
/*     */   public String getAssignedKey(Entry entry) {
/*  66 */     if (this.keys.containsKey(entry)) {
/*  67 */       return (String)this.keys.get(entry);
/*     */     }
/*  69 */     for (ChatColor color : ChatColor.values())
/*     */     {
/*  71 */       String colorText = color + "" + ChatColor.WHITE;
/*     */       
/*  73 */       if (entry.getText().length() > 16) {
/*  74 */         String sub = entry.getText().substring(0, 16);
/*  75 */         colorText = colorText + ChatColor.getLastColors(sub);
/*     */       }
/*     */       
/*  78 */       if (!this.keys.values().contains(colorText)) {
/*  79 */         this.keys.put(entry, colorText);
/*  80 */         return colorText;
/*     */       }
/*     */     }
/*  83 */     throw new IndexOutOfBoundsException("No more keys available!");
/*     */   }
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
/*     */ 
/*     */   public int getScore(Entry entry)
/*     */   {
/* 128 */     int start = 15 - getTopWrappers().size();
/* 129 */     int goal = 0;
/*     */     
/* 131 */     if ((entry instanceof Wrapper)) {
/* 132 */       Wrapper wrapper = (Wrapper)entry;
/* 133 */       if (wrapper.getType() == Wrapper.WrapperType.TOP) {
/* 134 */         goal = start;
/* 135 */         start = 15;
/*     */       } else {
/* 137 */         start -= getEntries().size();
/* 138 */         goal = start - getBottomWrappers().size();
/*     */       }
/*     */     }
/*     */     
/* 142 */     for (int i = start; i > goal; i--) {
/* 143 */       if (!this.scores.containsKey(entry)) {
/* 144 */         if (!this.scores.values().contains(Integer.valueOf(i))) {
/* 145 */           this.scores.put(entry, Integer.valueOf(i));
/* 146 */           return i;
/*     */         }
/*     */       } else {
/* 149 */         int score = ((Integer)this.scores.get(entry)).intValue();
/* 150 */         for (int toSub = 0; toSub < start; toSub++) {
/* 151 */           if ((i - toSub > score) && (!this.scores.values().contains(Integer.valueOf(i - toSub)))) {
/* 152 */             this.scores.put(entry, Integer.valueOf(i - toSub));
/* 153 */             return i - toSub;
/*     */           }
/*     */         }
/* 156 */         if (((entry instanceof Wrapper)) && (((Wrapper)entry).getType() == Wrapper.WrapperType.BOTTOM) && 
/* 157 */           (score > start)) {
/* 158 */           this.scores.put(entry, Integer.valueOf(start));
/* 159 */           return start;
/*     */         }
/*     */         
/* 162 */         return score;
/*     */       }
/*     */     }
/* 165 */     return 0;
/*     */   }
/*     */   
/*     */   public Entry getEntry(String id) {
/* 169 */     for (Entry entry : getEntries()) {
/* 170 */       if (entry.getId().equals(id)) {
/* 171 */         return entry;
/*     */       }
/*     */     }
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   public static PlayerScoreboard getScoreboard(Player player)
/*     */   {
/* 179 */     for (PlayerScoreboard playerScoreboard : ) {
/* 180 */       if (playerScoreboard.getPlayer().getName().equals(player.getName())) {
/* 181 */         return playerScoreboard;
/*     */       }
/*     */     }
/* 184 */     return null;
/*     */   }
/*     */   
/*     */   public static Set<PlayerScoreboard> getScoreboards() {
/* 188 */     return scoreboards;
/*     */   }
/*     */   
/*     */   private List<Wrapper> getTopWrappers() {
/* 192 */     List<Wrapper> toReturn = new ArrayList();
/* 193 */     for (Wrapper wrapper : getWrappers()) {
/* 194 */       if (wrapper.getType() == Wrapper.WrapperType.TOP) {
/* 195 */         toReturn.add(wrapper);
/*     */       }
/*     */     }
/* 198 */     return toReturn;
/*     */   }
/*     */   
/*     */   private List<Wrapper> getBottomWrappers() {
/* 202 */     List<Wrapper> toReturn = new ArrayList();
/* 203 */     for (Wrapper wrapper : getWrappers()) {
/* 204 */       if (wrapper.getType() == Wrapper.WrapperType.BOTTOM) {
/* 205 */         toReturn.add(wrapper);
/*     */       }
/*     */     }
/* 208 */     return toReturn;
/*     */   }
/*     */   
/*     */   public void createScoreboard(String title, boolean hook, boolean overrideTitle) {
/* 212 */     if ((hook) && 
/* 213 */       (this.player.getScoreboard() != Bukkit.getScoreboardManager().getMainScoreboard())) {
/* 214 */       this.scoreboard = this.player.getScoreboard();
/*     */       
/* 216 */       if (this.scoreboard.getObjective(DisplaySlot.SIDEBAR) != null) {
/* 217 */         this.objective = this.scoreboard.getObjective(DisplaySlot.SIDEBAR);
/* 218 */         if (overrideTitle) {
/* 219 */           this.objective.setDisplayName(title);
/*     */         }
/*     */       } else {
/* 222 */         this.objective = this.scoreboard.registerNewObjective(this.player.getName(), "dummy");
/* 223 */         this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
/* 224 */         this.objective.setDisplayName(title);
/*     */       }
/* 226 */       return;
/*     */     }
/*     */     
/* 229 */     this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
/* 230 */     this.objective = this.scoreboard.registerNewObjective(this.player.getName(), "dummy");
/* 231 */     this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
/* 232 */     this.objective.setDisplayName(title);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\com\alexandeh\glaedr\scoreboards\PlayerScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */