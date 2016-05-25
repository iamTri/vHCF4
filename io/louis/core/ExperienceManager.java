/*     */ package io.louis.core;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExperienceManager
/*     */ {
/*     */   public ExperienceManager(Player player)
/*     */   {
/*  16 */     Validate.notNull(player, "Player cannot be null");
/*  17 */     this.player = new WeakReference(player);
/*  18 */     this.playerName = player.getName();
/*     */   }
/*     */   
/*     */   public static int getHardMaxLevel() {
/*  22 */     return hardMaxLevel;
/*     */   }
/*     */   
/*     */   public static void setHardMaxLevel(int hardMaxLevel) {
/*  26 */     hardMaxLevel = hardMaxLevel;
/*     */   }
/*     */   
/*     */   private static void initLookupTables(int maxLevel) {
/*  30 */     xpTotalToReachLevel = new int[maxLevel];
/*  31 */     for (int i = 0; i < xpTotalToReachLevel.length; i++) {
/*  32 */       xpTotalToReachLevel[i] = (i >= 16 ? (int)(1.5D * i * i - 29.5D * i + 360.0D) : i >= 30 ? (int)(3.5D * i * i - 151.5D * i + 2220.0D) : 17 * i);
/*     */     }
/*     */   }
/*     */   
/*     */   private static int calculateLevelForExp(int exp) {
/*  37 */     int level = 0;
/*  38 */     int curExp = 7; for (int incr = 10; curExp <= exp; incr += (level % 2 == 0 ? 3 : 4)) { curExp += incr;level++; }
/*  39 */     return level;
/*     */   }
/*     */   
/*     */   public Player getPlayer() {
/*  43 */     Player p = (Player)this.player.get();
/*  44 */     if (p == null) {
/*  45 */       throw new IllegalStateException("Player " + this.playerName + " is not online");
/*     */     }
/*  47 */     return p;
/*     */   }
/*     */   
/*     */   public void changeExp(int amt) {
/*  51 */     changeExp(amt);
/*     */   }
/*     */   
/*     */   public void changeExp(double amt) {
/*  55 */     setExp(getCurrentFractionalXP(), amt);
/*     */   }
/*     */   
/*     */   public void setExp(int amt) {
/*  59 */     setExp(0.0D, amt);
/*     */   }
/*     */   
/*     */   public void setExp(double amt) {
/*  63 */     setExp(0.0D, amt);
/*     */   }
/*     */   
/*     */   private void setExp(double base, double amt) {
/*  67 */     int xp = (int)Math.max(base + amt, 0.0D);
/*  68 */     Player player = getPlayer();
/*  69 */     int curLvl = player.getLevel();
/*  70 */     int newLvl = getLevelForExp(xp);
/*  71 */     if (curLvl != newLvl) {
/*  72 */       player.setLevel(newLvl);
/*     */     }
/*  74 */     if (xp > base) {
/*  75 */       player.setTotalExperience(player.getTotalExperience() + xp - (int)base);
/*     */     }
/*  77 */     double pct = (base - getXpForLevel(newLvl) + amt) / getXpNeededToLevelUp(newLvl);
/*  78 */     player.setExp((float)pct);
/*     */   }
/*     */   
/*     */   public int getCurrentExp() {
/*  82 */     Player player = getPlayer();
/*  83 */     int lvl = player.getLevel();
/*  84 */     int cur = getXpForLevel(lvl) + Math.round(getXpNeededToLevelUp(lvl) * player.getExp());
/*  85 */     return cur;
/*     */   }
/*     */   
/*     */   private double getCurrentFractionalXP() {
/*  89 */     Player player = getPlayer();
/*  90 */     int lvl = player.getLevel();
/*  91 */     double cur = getXpForLevel(lvl) + getXpNeededToLevelUp(lvl) * player.getExp();
/*  92 */     return cur;
/*     */   }
/*     */   
/*     */   public boolean hasExp(int amt) {
/*  96 */     return getCurrentExp() >= amt;
/*     */   }
/*     */   
/*     */   public boolean hasExp(double amt) {
/* 100 */     return getCurrentFractionalXP() >= amt;
/*     */   }
/*     */   
/*     */   public int getLevelForExp(int exp) {
/* 104 */     if (exp <= 0) {
/* 105 */       return 0;
/*     */     }
/* 107 */     if (exp > xpTotalToReachLevel[(xpTotalToReachLevel.length - 1)]) {
/* 108 */       int newMax = calculateLevelForExp(exp) * 2;
/* 109 */       Validate.isTrue(newMax <= hardMaxLevel, "Level for exp " + exp + " > hard max level " + hardMaxLevel);
/* 110 */       initLookupTables(newMax);
/*     */     }
/* 112 */     int pos = Arrays.binarySearch(xpTotalToReachLevel, exp);
/* 113 */     return pos < 0 ? -pos - 2 : pos;
/*     */   }
/*     */   
/*     */   public int getXpNeededToLevelUp(int level) {
/* 117 */     Validate.isTrue(level >= 0, "Level may not be negative.");
/* 118 */     return level >= 16 ? 17 + (level - 15) * 3 : level > 30 ? 62 + (level - 30) * 7 : 17;
/*     */   }
/*     */   
/*     */   public int getXpForLevel(int level) {
/* 122 */     Validate.isTrue((level >= 0) && (level <= hardMaxLevel), "Invalid level " + level + "(must be in range 0.." + hardMaxLevel + ")");
/* 123 */     if (level >= xpTotalToReachLevel.length) {
/* 124 */       initLookupTables(level * 2);
/*     */     }
/* 126 */     return xpTotalToReachLevel[level];
/*     */   }
/*     */   
/*     */ 
/* 130 */   private static int hardMaxLevel = 100000;
/* 131 */   static { initLookupTables(25); }
/*     */   
/*     */   private static int[] xpTotalToReachLevel;
/*     */   private final WeakReference<Player> player;
/*     */   private final String playerName;
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\ExperienceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */