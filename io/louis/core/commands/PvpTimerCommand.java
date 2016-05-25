/*     */ package io.louis.core.commands;
/*     */ 
/*     */ import com.alexandeh.glaedr.events.EntryTickEvent;
/*     */ import com.alexandeh.glaedr.scoreboards.Entry;
/*     */ import com.alexandeh.glaedr.scoreboards.PlayerScoreboard;
/*     */ import com.sk89q.worldguard.protection.managers.RegionManager;
/*     */ import com.sk89q.worldguard.protection.regions.ProtectedRegion;
/*     */ import com.temptingmc.koths.events.PlayerStartCaptureKothEvent;
/*     */ import io.louis.core.Core;
/*     */ import io.louis.core.commands.type.ConfigurableComponent;
/*     */ import io.louis.core.utils.CooldownManager;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.PotionSplashEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.potion.Potion;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ public class PvpTimerCommand extends ConfigurableComponent implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*     */ {
/*     */   public PvpTimerCommand(Core mainPlugin)
/*     */   {
/*  43 */     super(mainPlugin, "pvptimers");
/*  44 */     super.getPlugin().getServer().getPluginManager().registerEvents(this, super.getPlugin());
/*  45 */     setEnabled(true);
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enable)
/*     */   {
/*  50 */     this.enabled = enable;
/*  51 */     if (this.enabled) {
/*  52 */       for (Player player : Bukkit.getOnlinePlayers()) {
/*  53 */         if (isProtected(player)) {
/*  54 */           long timeRemaining = getTimeRemaining(player.getUniqueId()).intValue();
/*  55 */           if (inSpawn(player.getLocation())) {
/*  56 */             super.getPlugin().getCooldownManager().addScore(player, SCORE_NAME, (int)timeRemaining);
/*  57 */             PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(player);
/*  58 */             if (playerScoreboard.getEntry(SCORE_NAME) != null) {
/*  59 */               playerScoreboard.getEntry(SCORE_NAME).setPaused(true);
/*     */             }
/*  61 */             player.sendMessage(ChatColor.YELLOW + "PvP Protection timer paused.");
/*  62 */             player.playSound(player.getLocation(), Sound.NOTE_PLING, 100.0F, 100.0F);
/*     */           }
/*     */           else {
/*  65 */             super.getPlugin().getCooldownManager().tryCooldown(player, SCORE_NAME, timeRemaining * 1000L, false, true, true);
/*     */           }
/*     */         }
/*     */       }
/*  69 */       if (this.bukkitTask == null) {
/*  70 */         this.bukkitTask = new PvPTimerTask(null).runTaskTimerAsynchronously(super.getPlugin(), 20L, 20L);
/*     */       }
/*     */     }
/*     */     else {
/*  74 */       for (Player player : Bukkit.getOnlinePlayers()) {
/*  75 */         super.getPlugin().getCooldownManager().removeCooldown(player, SCORE_NAME);
/*     */       }
/*  77 */       if (this.bukkitTask != null) {
/*  78 */         this.bukkitTask.cancel();
/*  79 */         this.bukkitTask = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean inSpawn(Location loc) {
/*  85 */     RegionManager regionManager = com.sk89q.worldguard.bukkit.WGBukkit.getRegionManager(loc.getWorld());
/*  86 */     com.sk89q.worldguard.protection.ApplicableRegionSet regionSet = regionManager.getApplicableRegions(loc);
/*  87 */     for (ProtectedRegion region : regionSet) {
/*  88 */       if ((region.getId().equalsIgnoreCase("spawn")) || (region.getId().equalsIgnoreCase("nether")) || (region.getId().equalsIgnoreCase("end"))) {
/*  89 */         return true;
/*     */       }
/*     */     }
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isProtected(Player p) {
/*  96 */     Integer timeRemaining = getTimeRemaining(p.getUniqueId());
/*  97 */     return (timeRemaining != null) && (timeRemaining.intValue() > 0) && (isEnabled());
/*     */   }
/*     */   
/*     */   public Integer getTimeRemaining(UUID uuid) {
/* 101 */     return (Integer)super.get(uuid);
/*     */   }
/*     */   
/*     */   public void put(final Player p, int value) {
/* 105 */     super.put(p.getUniqueId(), Integer.valueOf(value));
/* 106 */     if (isEnabled())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 111 */       new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 109 */           PvpTimerCommand.this.getPlugin().getCooldownManager().addScore(p, PvpTimerCommand.SCORE_NAME, PvpTimerCommand.PVP_PROTECTION);
/*     */         }
/* 111 */       }.runTaskLaterAsynchronously(super.getPlugin(), 10L);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTick(EntryTickEvent e) {
/* 117 */     if (e.getEntry().getId().equals(SCORE_NAME)) {
/* 118 */       super.put(e.getPlayer().getUniqueId(), Integer.valueOf(e.getEntry().getTime().intValue() + 1));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/* 123 */     if (!(s instanceof Player)) {
/* 124 */       s.sendMessage(ChatColor.RED + "Only a player can use this command.");
/* 125 */       return true;
/*     */     }
/* 127 */     Player p = (Player)s;
/* 128 */     if (!isEnabled()) {
/* 129 */       p.sendMessage(ChatColor.RED + "PvP protection has been disabled for EOTW.");
/* 130 */       return true;
/*     */     }
/* 132 */     if (args.length == 0) {
/* 133 */       p.sendMessage(ChatColor.GOLD + "*** PvP Timer Help ***");
/* 134 */       p.sendMessage(ChatColor.GRAY + "/" + c.getName() + " enable - Enables PvP.");
/* 135 */       p.sendMessage(ChatColor.GRAY + "/" + c.getName() + " time - Displays time left.");
/* 136 */       return true;
/*     */     }
/* 138 */     if (args[0].equalsIgnoreCase("enable")) {
/* 139 */       UUID playerUUID = p.getUniqueId();
/* 140 */       Integer timeRemaining = getTimeRemaining(playerUUID);
/* 141 */       if (timeRemaining != null) {
/* 142 */         super.remove(playerUUID);
/* 143 */         super.set(playerUUID.toString(), null);
/* 144 */         super.getPlugin().getCooldownManager().removeCooldown(p, SCORE_NAME);
/* 145 */         p.sendMessage(ChatColor.GREEN + "PvP is now enabled.");
/*     */       }
/*     */       else {
/* 148 */         p.sendMessage(ChatColor.RED + "PvP is already enabled.");
/*     */       }
/*     */     }
/* 151 */     else if (args[0].equalsIgnoreCase("time")) {
/* 152 */       UUID playerUUID = p.getUniqueId();
/* 153 */       Integer timeRemaining = getTimeRemaining(playerUUID);
/* 154 */       if (timeRemaining != null) {
/* 155 */         long convertedTime = TimeUnit.MILLISECONDS.convert(timeRemaining.intValue(), TimeUnit.SECONDS);
/* 156 */         p.sendMessage(ChatColor.GREEN + "You still have PvP protection for " + org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(convertedTime, true, true) + ".");
/*     */       }
/*     */       else {
/* 159 */         p.sendMessage(ChatColor.RED + "You are currently not under PvP protection.");
/*     */       }
/*     */     }
/*     */     else {
/* 163 */       p.sendMessage(ChatColor.GOLD + "*** PvP Timer Help ***");
/* 164 */       p.sendMessage(ChatColor.GRAY + "/" + c.getName() + " enable - Enables PvP.");
/* 165 */       p.sendMessage(ChatColor.GRAY + "/" + c.getName() + " time - Displays time left.");
/*     */     }
/* 167 */     return true;
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onInteract(PlayerInteractEvent e) {
/* 172 */     if ((e.getAction().name().contains("RIGHT")) && (e.hasItem()) && (isProtected(e.getPlayer())) && (e.getItem().getType() == org.bukkit.Material.POTION)) {
/* 173 */       Potion p = Potion.fromItemStack(e.getItem());
/* 174 */       if (p.isSplash()) {
/* 175 */         e.setCancelled(true);
/* 176 */         e.getPlayer().updateInventory();
/* 177 */         e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot throw potions while under pvp protection."));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onKothCapture(PlayerStartCaptureKothEvent e) {
/* 184 */     if (isProtected(e.getPlayer())) {
/* 185 */       e.setCancelled(true);
/* 186 */       e.getPlayer().sendMessage(ChatColor.RED + "You must enable PvP to capture Koth points. Type /pvp enable to capture Koth points.");
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
/*     */   public void onMoveSpawn(PlayerMoveEvent e) {
/* 192 */     Player p = e.getPlayer();
/* 193 */     if (isProtected(e.getPlayer())) {
/* 194 */       PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(p);
/* 195 */       if ((inSpawn(e.getTo())) && (!inSpawn(e.getFrom()))) {
/* 196 */         if (playerScoreboard.getEntry(SCORE_NAME) != null) {
/* 197 */           playerScoreboard.getEntry(SCORE_NAME).setPaused(true);
/*     */         }
/* 199 */         p.sendMessage(ChatColor.YELLOW + "PvP Protection timer paused.");
/* 200 */         p.playSound(p.getLocation(), Sound.NOTE_PLING, 100.0F, 100.0F);
/*     */       }
/* 202 */       else if ((!inSpawn(e.getTo())) && (inSpawn(e.getFrom()))) {
/* 203 */         super.getPlugin().getCooldownManager().tryCooldown(e.getPlayer(), SCORE_NAME, getTimeRemaining(e.getPlayer().getUniqueId()).intValue() * 1000, false, true, true);
/* 204 */         if (playerScoreboard.getEntry(SCORE_NAME) != null) {
/* 205 */           playerScoreboard.getEntry(SCORE_NAME).send();
/*     */         } else {
/* 207 */           super.getPlugin().getCooldownManager().addScore(e.getPlayer(), SCORE_NAME, getTimeRemaining(e.getPlayer().getUniqueId()).intValue());
/*     */         }
/* 209 */         p.sendMessage(ChatColor.YELLOW + "PvP Protection timer started.");
/* 210 */         p.playSound(p.getLocation(), Sound.NOTE_PLING, 100.0F, 100.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
/*     */   public void onTeleportSpawn(PlayerTeleportEvent e) {
/* 217 */     Player p = e.getPlayer();
/* 218 */     if (isProtected(e.getPlayer())) {
/* 219 */       PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(p);
/* 220 */       if ((inSpawn(e.getTo())) && (!inSpawn(e.getFrom()))) {
/* 221 */         if (playerScoreboard.getEntry(SCORE_NAME) != null) {
/* 222 */           playerScoreboard.getEntry(SCORE_NAME).setPaused(true);
/*     */         }
/* 224 */         p.sendMessage(ChatColor.YELLOW + "PvP Protection timer paused.");
/* 225 */         p.playSound(p.getLocation(), Sound.NOTE_PLING, 100.0F, 100.0F);
/*     */       }
/* 227 */       else if ((!inSpawn(e.getTo())) && (inSpawn(e.getFrom()))) {
/* 228 */         super.getPlugin().getCooldownManager().tryCooldown(e.getPlayer(), SCORE_NAME, getTimeRemaining(e.getPlayer().getUniqueId()).intValue() * 1000, false, true, true);
/* 229 */         if (playerScoreboard.getEntry(SCORE_NAME) != null) {
/* 230 */           playerScoreboard.getEntry(SCORE_NAME).send();
/*     */         } else {
/* 232 */           super.getPlugin().getCooldownManager().addScore(e.getPlayer(), SCORE_NAME, getTimeRemaining(e.getPlayer().getUniqueId()).intValue());
/*     */         }
/* 234 */         p.sendMessage(ChatColor.YELLOW + "PvP Protection timer started.");
/* 235 */         p.playSound(p.getLocation(), Sound.NOTE_PLING, 100.0F, 100.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onMove(PlayerMoveEvent e) {
/* 242 */     if (isProtected(e.getPlayer())) {
/* 243 */       RegionManager regionManager = com.sk89q.worldguard.bukkit.WGBukkit.getRegionManager(e.getTo().getWorld());
/* 244 */       com.sk89q.worldguard.protection.ApplicableRegionSet regionSet = regionManager.getApplicableRegions(e.getTo());
/* 245 */       for (ProtectedRegion region : regionSet) {
/* 246 */         if (region.getId().toLowerCase().contains("-koth")) {
/* 247 */           e.setTo(e.getFrom());
/* 248 */           e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot enter a koth while under pvp protection. Use /pvp enable."));
/* 249 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onRespawn(PlayerRespawnEvent e) {
/* 257 */     Player p = e.getPlayer();
/* 258 */     super.set(e.getPlayer().getUniqueId().toString(), Integer.valueOf(PVP_PROTECTION));
/* 259 */     put(e.getPlayer(), PVP_PROTECTION);
/* 260 */     if (isEnabled()) {
/* 261 */       e.getPlayer().sendMessage(ChatColor.GREEN + "You have regained PvP protection.");
/* 262 */       p.playSound(p.getLocation(), Sound.NOTE_PLING, 100.0F, 100.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onLogin(final PlayerJoinEvent e)
/*     */   {
/* 274 */     new BukkitRunnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 271 */         PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(e.getPlayer());
/* 272 */         e.getPlayer().setScoreboard(playerScoreboard.getScoreboard());
/*     */       }
/* 274 */     }.runTaskLater(Core.getInstance(), 20L);
/* 275 */     if (!e.getPlayer().hasPlayedBefore()) {
/* 276 */       super.set(e.getPlayer().getUniqueId().toString(), Integer.valueOf(PVP_PROTECTION));
/* 277 */       put(e.getPlayer(), PVP_PROTECTION);
/* 278 */       PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(e.getPlayer());
/* 279 */       if (playerScoreboard.getEntry(SCORE_NAME) != null) {
/* 280 */         playerScoreboard.getEntry(SCORE_NAME).setPaused(true);
/*     */       }
/* 282 */       e.getPlayer().sendMessage(ChatColor.YELLOW + "PvP Protection timer paused.");
/* 283 */       e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_PLING, 100.0F, 100.0F);
/*     */ 
/*     */     }
/* 286 */     else if (isProtected(e.getPlayer())) {
/* 287 */       Integer timeRemaining = getTimeRemaining(e.getPlayer().getUniqueId());
/* 288 */       if (timeRemaining != null) {
/* 289 */         if (inSpawn(e.getPlayer().getLocation())) {
/* 290 */           super.getPlugin().getCooldownManager().addScore(e.getPlayer(), SCORE_NAME, timeRemaining.intValue());
/* 291 */           PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(e.getPlayer());
/* 292 */           if (playerScoreboard.getEntry(SCORE_NAME) != null) {
/* 293 */             playerScoreboard.getEntry(SCORE_NAME).setPaused(true);
/*     */           }
/* 295 */           e.getPlayer().sendMessage(ChatColor.YELLOW + "PvP Protection timer paused.");
/* 296 */           e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.NOTE_PLING, 100.0F, 100.0F);
/*     */         }
/*     */         else {
/* 299 */           super.getPlugin().getCooldownManager().tryCooldown(e.getPlayer(), SCORE_NAME, timeRemaining.intValue() * 1000, false, true, true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onDamage(EntityDamageByEntityEvent e)
/*     */   {
/* 308 */     if ((e.getEntity() instanceof Player)) {
/* 309 */       Player p = (Player)e.getEntity();
/* 310 */       Player d = null;
/* 311 */       if ((e.getDamager() instanceof Player)) {
/* 312 */         d = (Player)e.getDamager();
/*     */       }
/* 314 */       else if ((e.getDamager() instanceof Projectile)) {
/* 315 */         Projectile proj = (Projectile)e.getDamager();
/* 316 */         if ((proj.getShooter() instanceof Player)) {
/* 317 */           d = (Player)proj.getShooter();
/*     */         }
/*     */       }
/* 320 */       if (d == null) {
/* 321 */         return;
/*     */       }
/* 323 */       if (isProtected(d)) {
/* 324 */         e.setCancelled(true);
/* 325 */         if (!d.equals(p)) {
/* 326 */           d.sendMessage(ChatColor.RED + "You currently have PvP protection. Please type /pvp enable to enable combat.");
/*     */         }
/*     */       }
/* 329 */       else if (isProtected(p)) {
/* 330 */         e.setCancelled(true);
/* 331 */         if (!d.equals(p)) {
/* 332 */           d.sendMessage(ChatColor.RED + p.getName() + " currently has PvP protection.");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onSplash(PotionSplashEvent e) {
/* 340 */     for (org.bukkit.entity.LivingEntity entity : e.getAffectedEntities()) {
/* 341 */       if ((entity instanceof Player)) {
/* 342 */         Player p = (Player)entity;
/* 343 */         if (isProtected(p))
/*     */         {
/*     */ 
/* 346 */           e.setIntensity(p, 0.0D); }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPickup(PlayerPickupItemEvent e) {
/* 353 */     if ((isProtected(e.getPlayer())) && (!inSpawn(e.getPlayer().getLocation()))) {
/* 354 */       e.setCancelled(true);
/* 355 */       if (super.getPlugin().getCooldownManager().tryCooldown(e.getPlayer(), "Item Pickup", 5000L, false, false, false)) {
/* 356 */         e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to pickup items whilst in PvP protection. Type /pvp enable to pick up items.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
/* 363 */     if (isProtected(e.getPlayer())) {
/* 364 */       String[] fullCommand = e.getMessage().split(" ");
/* 365 */       String cmd = fullCommand[0].substring(1);
/* 366 */       if (((cmd.equalsIgnoreCase("f")) || (cmd.toLowerCase().startsWith("faction"))) && (fullCommand.length > 1) && (fullCommand[1].equalsIgnoreCase("home"))) {
/* 367 */         e.setCancelled(true);
/* 368 */         e.getPlayer().sendMessage(ChatColor.RED + "You need to enable PvP (/pvp enable) before typing /f home.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent e) {
/* 375 */     Integer timeRemaining = getTimeRemaining(e.getPlayer().getUniqueId());
/* 376 */     if (timeRemaining != null) {
/* 377 */       super.set(e.getPlayer().getUniqueId().toString(), timeRemaining);
/*     */     }
/* 379 */     PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(e.getPlayer());
/* 380 */     if (playerScoreboard.getEntry(SCORE_NAME) != null) {
/* 381 */       playerScoreboard.getEntry(SCORE_NAME).cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKick(PlayerKickEvent e) {
/* 387 */     Integer timeRemaining = getTimeRemaining(e.getPlayer().getUniqueId());
/* 388 */     if (timeRemaining != null) {
/* 389 */       super.set(e.getPlayer().getUniqueId().toString(), timeRemaining);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 394 */     return this.enabled;
/*     */   }
/*     */   
/*     */ 
/* 398 */   private static final int PVP_PROTECTION = (int)TimeUnit.SECONDS.convert(Core.cfg3.getInt("Timers.PvPTimer"), TimeUnit.MINUTES);
/* 399 */   public static final String SCORE_NAME = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.PvPTimer"));
/*     */   private BukkitTask bukkitTask;
/*     */   private boolean enabled;
/*     */   
/*     */   private class PvPTimerTask extends BukkitRunnable {
/*     */     private PvPTimerTask() {}
/*     */     
/* 406 */     public void run() { for (Player p : ) {
/* 407 */         long currentCooldown = PvpTimerCommand.this.getPlugin().getCooldownManager().getCooldown(p, PvpTimerCommand.SCORE_NAME);
/* 408 */         if (currentCooldown > 0L) {
/* 409 */           Integer previousValue = PvpTimerCommand.this.getTimeRemaining(p.getUniqueId());
/* 410 */           if (previousValue != null) {
/* 411 */             previousValue = Integer.valueOf(previousValue.intValue() - 1);
/* 412 */             if (previousValue.intValue() > 1) {
/* 413 */               PvpTimerCommand.this.put(p.getUniqueId(), previousValue);
/*     */             }
/*     */             else {
/* 416 */               PvpTimerCommand.this.remove(p.getUniqueId());
/* 417 */               PvpTimerCommand.this.set(p.getUniqueId().toString(), null);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\PvpTimerCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */