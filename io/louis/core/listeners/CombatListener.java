/*     */ package io.louis.core.listeners;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import io.louis.core.utils.CooldownManager;
/*     */ import java.util.Iterator;
/*     */ import java.util.WeakHashMap;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerPortalEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ 
/*     */ public class CombatListener implements org.bukkit.event.Listener
/*     */ {
/*     */   private Core mainPlugin;
/*     */   private WeakHashMap<Player, Integer> combatLog;
/*     */   private String SCORE;
/*     */   
/*     */   public CombatListener(Core mainPlugin)
/*     */   {
/*  28 */     this.SCORE = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.CombatTag"));
/*  29 */     this.mainPlugin = mainPlugin;
/*  30 */     this.combatLog = new WeakHashMap();
/*  31 */     new org.bukkit.scheduler.BukkitRunnable() {
/*     */       public void run() {
/*  33 */         if (!CombatListener.this.combatLog.isEmpty()) {
/*  34 */           Iterator<Player> players = CombatListener.this.combatLog.keySet().iterator();
/*  35 */           while (players.hasNext()) {
/*  36 */             Player p = (Player)players.next();
/*  37 */             int value = ((Integer)CombatListener.this.combatLog.get(p)).intValue();
/*  38 */             if (value > 1) {
/*  39 */               CombatListener.this.combatLog.put(p, Integer.valueOf(value - 1));
/*     */             }
/*     */             else {
/*  42 */               p.sendMessage(ChatColor.RED + "You are no longer combat tagged.");
/*  43 */               players.remove(); } } } } }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  48 */       .runTaskTimer(this.mainPlugin, 20L, 20L);
/*     */   }
/*     */   
/*     */   public Integer getCombatTime(Player p) {
/*  52 */     return (Integer)this.combatLog.get(p);
/*     */   }
/*     */   
/*     */   public void addCombat(Player p, int time) {
/*  56 */     this.combatLog.put(p, Integer.valueOf(time));
/*  57 */     this.mainPlugin.getCooldownManager().tryCooldown(p, this.SCORE, time * 1000, false, true, true);
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=org.bukkit.event.EventPriority.MONITOR)
/*     */   public void onDamage(EntityDamageByEntityEvent e)
/*     */   {
/*  63 */     if ((e.getEntity() instanceof Player)) {
/*  64 */       Player p = (Player)e.getEntity();
/*  65 */       Player d = null;
/*  66 */       if ((e.getDamager() instanceof Player)) {
/*  67 */         d = (Player)e.getDamager();
/*     */       }
/*  69 */       else if ((e.getDamager() instanceof Projectile)) {
/*  70 */         Projectile proj = (Projectile)e.getDamager();
/*  71 */         if ((proj.getShooter() instanceof Player)) {
/*  72 */           d = (Player)proj.getShooter();
/*     */         }
/*     */       }
/*  75 */       if ((d == null) || (d.equals(p))) {
/*  76 */         return;
/*     */       }
/*  78 */       if (!this.combatLog.containsKey(p)) {
/*  79 */         p.sendMessage(ChatColor.RED + "You are now combat tagged.");
/*     */       }
/*  81 */       if (!this.combatLog.containsKey(d)) {
/*  82 */         d.sendMessage(ChatColor.RED + "You are now combat tagged.");
/*     */       }
/*  84 */       addCombat(p, Core.cfg3.getInt("Timers.PvPTag"));
/*  85 */       addCombat(d, Core.cfg3.getInt("Timers.PvPTag"));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=org.bukkit.event.EventPriority.MONITOR)
/*     */   public void onPortal(PlayerPortalEvent e) {
/*  91 */     if ((e.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.END_PORTAL) && (this.combatLog.containsKey(e.getPlayer()))) {
/*  92 */       e.setCancelled(true);
/*  93 */       if (this.mainPlugin.getCooldownManager().tryCooldown(e.getPlayer(), "cep", 5000L, false, false, false)) {
/*  94 */         e.getPlayer().sendMessage(ChatColor.RED + "You cannot jump into an end portal while in combat.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(PlayerMoveEvent e) {
/* 101 */     com.sk89q.worldguard.protection.managers.RegionManager regionManager = com.sk89q.worldguard.bukkit.WGBukkit.getRegionManager(e.getTo().getWorld());
/* 102 */     com.sk89q.worldguard.protection.ApplicableRegionSet regionSet = regionManager.getApplicableRegions(e.getTo());
/* 103 */     for (com.sk89q.worldguard.protection.regions.ProtectedRegion region : regionSet) {
/* 104 */       if (region.getId().equalsIgnoreCase("spawn")) {
/* 105 */         if (!this.combatLog.containsKey(e.getPlayer())) break;
/* 106 */         int combatTime = ((Integer)this.combatLog.get(e.getPlayer())).intValue();
/* 107 */         String cooldownMessage = net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils.formatDurationWords(combatTime * 1000, true, true);
/* 108 */         e.setTo(e.getFrom());
/* 109 */         e.getPlayer().sendMessage(ChatColor.RED + "You are currently combat tagged for another " + cooldownMessage + ".");
/* 110 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @EventHandler
/*     */   public void onCommandPreprocess(PlayerCommandPreprocessEvent e)
/*     */   {
/* 119 */     if (this.combatLog.containsKey(e.getPlayer())) {
/* 120 */       boolean inCombat = false;
/* 121 */       String[] fullCommand = e.getMessage().split(" ");
/* 122 */       String cmd = fullCommand[0].substring(1);
/* 123 */       if ((cmd.equalsIgnoreCase("f")) || (cmd.toLowerCase().startsWith("faction"))) {
/* 124 */         if ((fullCommand.length > 1) && ((fullCommand[1].equalsIgnoreCase("home")) || (fullCommand[1].equalsIgnoreCase("leave")))) {
/* 125 */           inCombat = true;
/*     */         }
/*     */       }
/* 128 */       else if (cmd.equalsIgnoreCase("logout")) {
/* 129 */         inCombat = true;
/*     */       }
/* 131 */       if (inCombat) {
/* 132 */         e.setCancelled(true);
/* 133 */         e.getPlayer().sendMessage(ChatColor.RED + "You are currently in combat.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDeath(PlayerDeathEvent e) {
/* 140 */     this.combatLog.remove(e.getEntity());
/* 141 */     this.mainPlugin.getCooldownManager().removeCooldown(e.getEntity(), this.SCORE);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent e) {
/* 146 */     this.combatLog.remove(e.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKick(PlayerKickEvent e) {
/* 151 */     this.combatLog.remove(e.getPlayer());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\CombatListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */