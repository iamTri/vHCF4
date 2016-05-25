/*     */ package io.louis.core.listeners;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import io.louis.core.commands.DeathBanCommand;
/*     */ import io.louis.core.commands.type.ConfigurableComponent;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class DeathBanListener extends ConfigurableComponent implements org.bukkit.event.Listener
/*     */ {
/*     */   private Map<UUID, DeathWrapper> deathWrappers;
/*     */   
/*     */   public DeathBanListener(Core mainPlugin)
/*     */   {
/*  23 */     super(mainPlugin, "deathbans");
/*  24 */     this.deathWrappers = new java.util.HashMap();
/*     */   }
/*     */   
/*     */   public boolean isDeathBanned(UUID uuid, boolean eotw) {
/*  28 */     if (!super.contains(uuid)) {
/*  29 */       return false;
/*     */     }
/*  31 */     long timeRemaining = ((Long)super.get(uuid)).longValue() - System.currentTimeMillis();
/*  32 */     return (timeRemaining >= 0L) || (eotw);
/*     */   }
/*     */   
/*     */   public void addDeathBan(UUID uuid, long delay) {
/*  36 */     super.put(uuid, Long.valueOf(System.currentTimeMillis() + delay));
/*  37 */     super.set(uuid.toString(), Long.valueOf(System.currentTimeMillis() + delay));
/*     */   }
/*     */   
/*     */   public void removeDeathBan(UUID uuid) {
/*  41 */     if (super.contains(uuid)) {
/*  42 */       super.remove(uuid);
/*     */     }
/*  44 */     super.set(uuid.toString(), null);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=org.bukkit.event.EventPriority.HIGHEST)
/*     */   public void onAsyncPreLogin(PlayerLoginEvent e) {
/*  49 */     final Player p = e.getPlayer();
/*  50 */     UUID uuid = p.getUniqueId();
/*  51 */     if (isDeathBanned(uuid, super.getPlugin().getDeathBanCommand().isEotwDeathBans())) {
/*  52 */       if (super.getPlugin().getDeathBanCommand().isEotwDeathBans()) {
/*  53 */         e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', "&bYou have died for end of the world!"));
/*  54 */         return;
/*     */       }
/*  56 */       if ((super.getPlugin().getLivesCommand().getLives(uuid) > 0) || (p.hasPermission("lives.infinite"))) {
/*  57 */         if (!p.hasPermission("lives.infinite")) {
/*  58 */           super.getPlugin().getLivesCommand().editLives(uuid, -1);
/*     */         }
/*  60 */         removeDeathBan(uuid);
/*  61 */         new BukkitRunnable() {
/*     */           public void run() {
/*  63 */             p.sendMessage(ChatColor.RED + "A life was consumed allowing you to be relieved of your deathban.");
/*     */           }
/*  65 */         }.runTaskLater(super.getPlugin(), 60L);
/*     */       }
/*     */       else {
/*  68 */         long delayRemaining = ((Long)super.get(uuid)).longValue() - System.currentTimeMillis();
/*  69 */         String timeRemaining = ChatColor.RED + org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(delayRemaining, true, true);
/*  70 */         String kickMessage = ChatColor.AQUA + "You have died." + System.lineSeparator() + "You can join in " + timeRemaining + ChatColor.AQUA + ".";
/*  71 */         kickMessage = kickMessage + System.lineSeparator() + ChatColor.GOLD + "Purchase a life @ " + Core.color(Core.cfg2.getString("Messages.ServerStore")) + ChatColor.GOLD + ".";
/*  72 */         if (this.deathWrappers.containsKey(e.getPlayer().getUniqueId())) {
/*  73 */           DeathWrapper wrapper = (DeathWrapper)this.deathWrappers.get(e.getPlayer().getUniqueId());
/*  74 */           if (wrapper.getKiller() != null) {
/*  75 */             kickMessage = kickMessage + System.lineSeparator() + ChatColor.DARK_RED + "You were killed by " + ChatColor.GOLD + wrapper.getKiller() + ChatColor.DARK_RED + ".";
/*     */           }
/*  77 */           kickMessage = kickMessage + System.lineSeparator() + ChatColor.GOLD + "You died at " + wrapper.getLoc().getBlockX() + ", " + wrapper.getLoc().getBlockY() + ", " + wrapper.getLoc().getBlockZ() + ".";
/*     */         }
/*  79 */         e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, kickMessage);
/*     */       }
/*     */     }
/*     */     else {
/*  83 */       removeDeathBan(uuid);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=org.bukkit.event.EventPriority.MONITOR)
/*     */   public void onDeath(final PlayerDeathEvent e) {
/*  89 */     if ((!super.getPlugin().getNpcListener().getKilledList().contains(e.getEntity().getUniqueId().toString())) && 
/*  90 */       (!super.getPlugin().getNpcRegistry().isNPC(e.getEntity()))) {
/*  91 */       if (e.getEntity().hasPermission("deathban.bypass"))
/*     */       {
/*     */ 
/*     */ 
/*  95 */         new BukkitRunnable() { public void run() {} }.runTaskLater(super.getPlugin(), 60L);
/*     */       }
/*     */       else {
/*  98 */         long deathBanTime = getDeathBanTime(e.getEntity());
/*  99 */         if (deathBanTime == 0L) {
/* 100 */           return;
/*     */         }
/*     */         try {
/* 103 */           addDeathBan(getPlugin().getUuidManager().getUUIDFromName(e.getEntity().getName()), deathBanTime);
/*     */         }
/*     */         catch (Exception e2) {
/* 106 */           e2.printStackTrace();
/*     */         }
/* 108 */         super.getPlugin().getLogoutCommand().getExemptUUIDs().add(e.getEntity().getUniqueId());
/* 109 */         Player killer = e.getEntity().getKiller();
/* 110 */         this.deathWrappers.put(e.getEntity().getUniqueId(), new DeathWrapper(e.getEntity(), killer == null ? null : killer.getName(), deathBanTime));
/* 111 */         new BukkitRunnable() {
/*     */           public void run() {
/* 113 */             DeathBanListener.this.respawnPlayer(e.getEntity());
/*     */             String kickMessage;
/* 115 */             String kickMessage; if (DeathBanListener.this.getPlugin().getDeathBanCommand().isEotwDeathBans()) {
/* 116 */               kickMessage = ChatColor.translateAlternateColorCodes('&', "&bYou have died for end of the world!");
/*     */             }
/*     */             else {
/* 119 */               kickMessage = ((DeathBanListener.DeathWrapper)DeathBanListener.this.deathWrappers.get(e.getEntity().getUniqueId())).getMessage();
/*     */             }
/* 121 */             e.getEntity().kickPlayer(kickMessage);
/*     */           }
/* 123 */         }.runTaskLater(super.getPlugin(), 5L);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void respawnPlayer(Player p)
/*     */   {
/* 130 */     if (p.isDead()) {
/* 131 */       ((org.bukkit.craftbukkit.v1_7_R4.CraftServer)super.getPlugin().getServer()).getHandle().moveToWorld(((org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer)p).getHandle(), 0, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public long getDeathBanTime(Player p) {
/* 136 */     if (p.hasPermission("deathban.0")) {
/* 137 */       return 0L;
/*     */     }
/* 139 */     for (int i = 1; i < 1000; i++) {
/* 140 */       if (p.hasPermission("deathban." + i)) {
/* 141 */         return TimeUnit.MILLISECONDS.convert(i, TimeUnit.MINUTES);
/*     */       }
/*     */     }
/* 144 */     return TimeUnit.MILLISECONDS.convert(2L, TimeUnit.HOURS);
/*     */   }
/*     */   
/*     */   private class DeathWrapper
/*     */   {
/*     */     private Player p;
/*     */     private long deathBanTime;
/*     */     private Location loc;
/*     */     private String killer;
/*     */     private String message;
/*     */     
/*     */     public DeathWrapper(Player p, String killer, long deathBanTime) {
/* 156 */       this.p = p;
/* 157 */       this.loc = this.p.getLocation();
/* 158 */       this.deathBanTime = deathBanTime;
/* 159 */       this.killer = killer;
/* 160 */       this.message = (ChatColor.AQUA + "You are now deathbanned. You may return in " + ChatColor.RED + org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(this.deathBanTime, true, true) + ChatColor.AQUA + ".");
/* 161 */       if (killer != null) {
/* 162 */         this.message = (this.message + System.lineSeparator() + ChatColor.DARK_RED + "You were killed by " + ChatColor.GOLD + this.killer + ChatColor.DARK_RED + ".");
/*     */       }
/* 164 */       this.message = (this.message + System.lineSeparator() + ChatColor.GOLD + "You died at " + this.loc.getBlockX() + ", " + this.loc.getBlockY() + ", " + this.loc.getBlockZ() + ".");
/* 165 */       this.message = (this.message + System.lineSeparator() + ChatColor.GOLD + "Purchase a life @ " + Core.color(Core.cfg2.getString("Messages.ServerStore")) + ChatColor.GOLD + ".");
/*     */     }
/*     */     
/*     */     public String getMessage() {
/* 169 */       return this.message;
/*     */     }
/*     */     
/*     */     public Player getP()
/*     */     {
/* 174 */       return this.p;
/*     */     }
/*     */     
/*     */     public long getDeathBanTime()
/*     */     {
/* 179 */       return this.deathBanTime;
/*     */     }
/*     */     
/*     */     public Location getLoc() {
/* 183 */       return this.loc;
/*     */     }
/*     */     
/*     */     public String getKiller() {
/* 187 */       return this.killer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\DeathBanListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */