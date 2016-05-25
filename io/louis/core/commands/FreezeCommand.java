/*     */ package io.louis.core.commands;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import java.util.WeakHashMap;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class FreezeCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*     */ {
/*     */   private Core mainPlugin;
/*     */   private java.util.Set<String> frozenPlayers;
/*     */   private WeakHashMap<Player, String> haltedPlayers;
/*     */   private boolean serverFrozen;
/*     */   
/*     */   public FreezeCommand(Core mainPlugin)
/*     */   {
/*  26 */     this.mainPlugin = mainPlugin;
/*  27 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/*  28 */     this.frozenPlayers = new java.util.HashSet();
/*  29 */     this.haltedPlayers = new WeakHashMap();
/*  30 */     new org.bukkit.scheduler.BukkitRunnable() {
/*     */       public void run() {
/*  32 */         for (Player p : FreezeCommand.this.haltedPlayers.keySet()) {
/*  33 */           p.sendMessage("");
/*  34 */           p.sendMessage("");
/*  35 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m============================================="));
/*  36 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou have been frozen."));
/*  37 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lYou have 3 minutes to join the teamspeak server."));
/*  38 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lTeamspeak IP: " + Core.color(Core.cfg2.getString("Messages.ServerTS"))));
/*  39 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lDo not log off or you will be banned."));
/*  40 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m============================================="));
/*  41 */           p.sendMessage("");
/*  42 */           p.sendMessage("");
/*  43 */           p.playSound(p.getLocation(), org.bukkit.Sound.NOTE_PLING, 10.0F, 10.0F); } } }
/*     */     
/*     */ 
/*  46 */       .runTaskTimerAsynchronously(this.mainPlugin, 40L, 40L);
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/*  50 */     if (!s.hasPermission("core.freeze")) {
/*  51 */       s.sendMessage(ChatColor.RED + "No.");
/*  52 */       return true;
/*     */     }
/*  54 */     if (!c.getName().equalsIgnoreCase("freezeall"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*  59 */       if ((c.getName().equalsIgnoreCase("ss")) && 
/*  60 */         (args.length != 1))
/*     */       {
/*  62 */         s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <player>");
/*  63 */         return true;
/*     */       }
/*     */     }
/*  66 */     Player targetPlayer = this.mainPlugin.getServer().getPlayer(args[0]);
/*  67 */     if (targetPlayer == null) {
/*  68 */       s.sendMessage(ChatColor.RED + "That player is not online.");
/*  69 */       return true;
/*     */     }
/*  71 */     if (this.haltedPlayers.put(targetPlayer, s.getName()) == null) {
/*  72 */       org.bukkit.Bukkit.broadcast(io.louis.core.utils.C.c("&6&l") + ((Player)s).getDisplayName() + ChatColor.translateAlternateColorCodes('&', new StringBuilder().append("&e&l has &5&lfrozen &6&l").append(targetPlayer.getDisplayName()).append(io.louis.core.utils.C.c("&e&l.")).toString()), "staff.mod");
/*  73 */       targetPlayer.sendMessage("");
/*  74 */       targetPlayer.sendMessage("");
/*  75 */       targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m============================================="));
/*  76 */       targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou have been frozen by " + (String)this.haltedPlayers.get(targetPlayer) + "."));
/*  77 */       targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lYou have 3 minutes to join the teamspeak server."));
/*  78 */       targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lTeamspeak IP: " + Core.color(Core.cfg2.getString("Messages.ServerTS"))));
/*  79 */       targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lDo not log off or you will be banned."));
/*  80 */       targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l&m============================================="));
/*  81 */       targetPlayer.sendMessage("");
/*  82 */       targetPlayer.sendMessage("");
/*  83 */       targetPlayer.playSound(targetPlayer.getLocation(), org.bukkit.Sound.NOTE_PLING, 10.0F, 10.0F);
/*     */     }
/*     */     else {
/*  86 */       this.haltedPlayers.remove(targetPlayer);
/*  87 */       if (targetPlayer.hasPotionEffect(PotionEffectType.BLINDNESS)) {
/*  88 */         targetPlayer.removePotionEffect(PotionEffectType.BLINDNESS);
/*     */       }
/*  90 */       org.bukkit.Bukkit.broadcast(io.louis.core.utils.C.c("&6&l") + ((Player)s).getDisplayName() + ChatColor.translateAlternateColorCodes('&', new StringBuilder().append("&e&l has &b&lun-frozen &6&l").append(targetPlayer.getDisplayName()).append(io.louis.core.utils.C.c("&e&l.")).toString()), "staff.mod");
/*  91 */       targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lYou have been un-frozen!"));
/*  92 */       targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lYou have been un-frozen!"));
/*  93 */       targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lYou have been un-frozen!"));
/*  94 */       targetPlayer.setAllowFlight(false);
/*  95 */       if (isFrozen(targetPlayer)) {
/*  96 */         s.sendMessage(ChatColor.RED + "Note that the player you un-halted is still frozen.");
/*     */       }
/*     */     }
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isFrozen(Player p) {
/* 103 */     return (isServerFrozen()) || (getFrozenPlayers().contains(p.getName())) || (this.haltedPlayers.get(p) != null);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
/* 108 */     if (e.getPlayer().hasPotionEffect(PotionEffectType.BLINDNESS)) {
/* 109 */       e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent e)
/*     */   {
/* 116 */     getFrozenPlayers().remove(e.getPlayer().getName());
/* 117 */     if (this.haltedPlayers.remove(e.getPlayer()) != null) {
/* 118 */       if (e.getPlayer().hasPotionEffect(PotionEffectType.BLINDNESS)) {
/* 119 */         e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
/*     */       }
/* 121 */       for (Player p : this.mainPlugin.getServer().getOnlinePlayers()) {
/* 122 */         if (p.hasPermission("staff.mod")) {
/* 123 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l" + e.getPlayer().getName() + " has logged off."));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKick(PlayerKickEvent e)
/*     */   {
/* 132 */     if (org.apache.commons.lang.StringUtils.containsIgnoreCase("Kicked for flying or related", e.getReason())) {
/* 133 */       if ((isFrozen(e.getPlayer())) || (this.haltedPlayers.get(e.getPlayer()) != null)) {
/* 134 */         e.setCancelled(true);
/*     */       }
/*     */     }
/* 137 */     else if (this.haltedPlayers.remove(e.getPlayer()) != null) {
/* 138 */       if (e.getPlayer().hasPotionEffect(PotionEffectType.BLINDNESS)) {
/* 139 */         e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
/*     */       }
/* 141 */       for (Player p : this.mainPlugin.getServer().getOnlinePlayers()) {
/* 142 */         if (p.hasPermission("staff.mod"))
/* 143 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l" + e.getPlayer().getName() + " has logged off while &d&lFrozen&c&l."));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(PlayerMoveEvent event) {
/* 150 */     Location from = event.getFrom();
/* 151 */     Location to = event.getTo();
/* 152 */     if (((from.getX() != to.getX()) || (from.getZ() != to.getZ())) && (isFrozen(event.getPlayer()))) {
/* 153 */       event.setTo(event.getFrom());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @EventHandler
/*     */   public void onProjectileLaunch(org.bukkit.event.entity.ProjectileLaunchEvent event)
/*     */   {
/* 161 */     if (((event.getEntity().getShooter() instanceof Player)) && 
/* 162 */       (isFrozen((Player)event.getEntity().getShooter()))) {
/* 163 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTalk(org.bukkit.event.player.AsyncPlayerChatEvent event)
/*     */   {
/* 170 */     if (isFrozen(event.getPlayer())) {
/* 171 */       event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou are frozen you cannot &b&lspeak&c&l!"));
/* 172 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onCmd(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
/* 178 */     if (isFrozen(event.getPlayer())) {
/* 179 */       event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou are frozen you cannot type &b&lcommands&c&l!"));
/* 180 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDrop(org.bukkit.event.player.PlayerDropItemEvent event) {
/* 186 */     if (isFrozen(event.getPlayer())) {
/* 187 */       event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou are frozen you cannot &b&ldrop items&c&l!"));
/* 188 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPickup(PlayerPickupItemEvent event) {
/* 194 */     if (isFrozen(event.getPlayer())) {
/* 195 */       event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou are frozen you cannot &b&lpickup items&c&l!"));
/* 196 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=org.bukkit.event.EventPriority.LOWEST)
/*     */   public void onDamage(org.bukkit.event.entity.EntityDamageEvent e) {
/* 202 */     if ((e.getEntity() instanceof Player)) {
/* 203 */       Player p = (Player)e.getEntity();
/* 204 */       if (isFrozen(p)) {
/* 205 */         e.setCancelled(true);
/* 206 */         if ((e instanceof EntityDamageByEntityEvent)) {
/* 207 */           org.bukkit.entity.Entity damager = ((EntityDamageByEntityEvent)e).getDamager();
/* 208 */           if ((damager instanceof Player)) {
/* 209 */             Player d = (Player)damager;
/* 210 */             if (this.haltedPlayers.get(p) != null) {
/* 211 */               d.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis player has been frozen by a staff member and is being checked for possible hacks."));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=org.bukkit.event.EventPriority.LOWEST)
/*     */   public void onDamageByEntity(EntityDamageByEntityEvent e) {
/* 221 */     if ((e.getDamager() instanceof Player)) {
/* 222 */       Player d = (Player)e.getDamager();
/* 223 */       if (isFrozen(d)) {
/* 224 */         e.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public java.util.Set<String> getFrozenPlayers() {
/* 230 */     return this.frozenPlayers;
/*     */   }
/*     */   
/*     */   public WeakHashMap<Player, String> getHaltedPlayers() {
/* 234 */     return this.haltedPlayers;
/*     */   }
/*     */   
/*     */   public boolean isServerFrozen() {
/* 238 */     return this.serverFrozen;
/*     */   }
/*     */   
/*     */   public void setServerFrozen(boolean serverFrozen) {
/* 242 */     this.serverFrozen = serverFrozen;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\FreezeCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */