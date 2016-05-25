/*     */ package io.louis.core.utils;
/*     */ 
/*     */ import com.alexandeh.glaedr.scoreboards.Entry;
/*     */ import com.alexandeh.glaedr.scoreboards.PlayerScoreboard;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.Table;
/*     */ import com.temptingmc.koths.NexGenKoths;
/*     */ import com.temptingmc.koths.koth.Koth;
/*     */ import io.louis.core.Core;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.commons.lang.time.DurationFormatUtils;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Objective;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ 
/*     */ public class CooldownManager implements org.bukkit.event.Listener
/*     */ {
/*     */   private Table<String, String, Long> cooldownTable;
/*     */   private String scoreboardTitle;
/*     */   private BukkitTask bukkitTask;
/*     */   
/*     */   public CooldownManager(Core mainPlugin)
/*     */   {
/*  35 */     this.cooldownTable = HashBasedTable.create();
/*  36 */     this.scoreboardTitle = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.Title"));
/*     */   }
/*     */   
/*     */   public long getCooldown(Player player, String key) {
/*  40 */     return calculateRemainder((Long)this.cooldownTable.get(player.getName(), key));
/*     */   }
/*     */   
/*     */   public long setCooldown(Player player, String key, long delay) {
/*  44 */     return calculateRemainder((Long)this.cooldownTable.put(player.getName(), key, Long.valueOf(System.currentTimeMillis() + delay)));
/*     */   }
/*     */   
/*     */   public void removeCooldown(Player p, String key) {
/*  48 */     this.cooldownTable.remove(p.getName(), key);
/*  49 */     PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(p);
/*  50 */     if (playerScoreboard.getEntry(key) != null) {
/*  51 */       playerScoreboard.getEntry(key).setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeAllCooldownsUnderKey(String key) {
/*  56 */     this.cooldownTable.column(key).clear();
/*     */   }
/*     */   
/*     */   public void removeCooldowns(Player player) {
/*  60 */     this.cooldownTable.row(player.getName()).clear();
/*  61 */     Scoreboard scoreboard = player.getScoreboard();
/*  62 */     Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
/*  63 */     if (objective != null) {
/*  64 */       objective.unregister();
/*  65 */       scoreboard.clearSlot(DisplaySlot.SIDEBAR);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean tryCooldown(Player player, String key, long delay, boolean cooldownMessage, boolean override, boolean useScore) {
/*  70 */     long timeRemaining = getCooldown(player, key);
/*  71 */     delay += 500L;
/*  72 */     if ((timeRemaining / 1000L < 1L) || (override)) {
/*  73 */       setCooldown(player, key, delay);
/*  74 */       if (useScore) {
/*  75 */         addScore(player, key, Long.valueOf(TimeUnit.SECONDS.convert(getCooldown(player, key) - 500L, TimeUnit.MILLISECONDS)).intValue());
/*     */       }
/*  77 */       return true;
/*     */     }
/*  79 */     if (cooldownMessage) {
/*  80 */       player.sendMessage(ChatColor.RED + "Still on " + ChatColor.stripColor(key) + " cooldown for " + DurationFormatUtils.formatDurationWords(timeRemaining, true, true) + ".");
/*     */     }
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   private long calculateRemainder(Long expireTime) {
/*  86 */     return expireTime != null ? expireTime.longValue() - System.currentTimeMillis() : Long.MIN_VALUE;
/*     */   }
/*     */   
/*     */   public void addScore(Player p, String key, int value) {
/*  90 */     PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(p);
/*  91 */     if (playerScoreboard.getEntry(key) == null) {
/*  92 */       if (key.contains("Koth")) {
/*  93 */         Koth koth = NexGenKoths.getKothByName(ChatColor.stripColor(key.replace(" Koth:", "")));
/*  94 */         if (koth != null) {
/*  95 */           if (koth.getFlagValue(com.temptingmc.koths.koth.KothFlag.CAPTURE_TIME) == value) {
/*  96 */             new Entry(key, playerScoreboard).setText(key).setCountdown(false).setTime(value).send();
/*     */           } else {
/*  98 */             new Entry(key, playerScoreboard).setText(key).setCountdown(true).setTime(value).send();
/*     */           }
/* 100 */           return;
/*     */         }
/*     */       }
/* 103 */       new Entry(key, playerScoreboard).setText(key).setCountdown(true).setTime(value).send();
/*     */     } else {
/* 105 */       Entry entry = playerScoreboard.getEntry(key);
/* 106 */       if ((key.equals(ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.CombatTag")))) && (value == Core.cfg3.getInt("Timers.PvPTag"))) {
/* 107 */         playerScoreboard.getEntry(key).setTime(value);
/*     */       }
/* 109 */       if (key.contains("Koth")) {
/* 110 */         Koth koth = NexGenKoths.getKothByName(ChatColor.stripColor(key.replace(" Koth:", "")));
/* 111 */         if ((koth != null) && 
/* 112 */           (koth.getCappingPlayer() != null) && 
/* 113 */           (!entry.isCountdown())) {
/* 114 */           entry.setText(key).setCountdown(true).setTime(value).send();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGH)
/*     */   public void onJoin(PlayerJoinEvent e)
/*     */   {
/* 124 */     for (Map.Entry<String, Long> data : this.cooldownTable.row(e.getPlayer().getName()).entrySet()) {
/* 125 */       long remainingTime = calculateRemainder((Long)data.getValue());
/* 126 */       int seconds = Long.valueOf(TimeUnit.SECONDS.convert(remainingTime, TimeUnit.MILLISECONDS)).intValue();
/* 127 */       if (seconds >= 1) {
/* 128 */         addScore(e.getPlayer(), (String)data.getKey(), seconds);
/* 129 */         Core.logger.info(ChatColor.RED + e.getPlayer().getName() + ChatColor.GREEN + "'s scoreboard has been loaded");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetScoreboard(Player p) {}
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
/*     */   public void onQuit(PlayerQuitEvent e)
/*     */   {
/* 139 */     resetScoreboard(e.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
/*     */   public void onKick(PlayerKickEvent e) {
/* 144 */     resetScoreboard(e.getPlayer());
/*     */   }
/*     */   
/*     */   public BukkitTask getBukkitTask() {
/* 148 */     return this.bukkitTask;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\utils\CooldownManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */