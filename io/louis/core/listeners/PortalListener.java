/*     */ package io.louis.core.listeners;
/*     */ 
/*     */ import com.massivecraft.factions.FPlayer;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerPortalEvent;
/*     */ 
/*     */ public class PortalListener implements org.bukkit.event.Listener
/*     */ {
/*     */   private io.louis.core.Core mainPlugin;
/*     */   private Map<Player, Integer> countdownMap;
/*     */   private java.util.Set<BlockFace> blockFaces;
/*     */   
/*     */   public PortalListener(io.louis.core.Core mainPlugin)
/*     */   {
/*  26 */     this.mainPlugin = mainPlugin;
/*  27 */     this.countdownMap = new java.util.WeakHashMap();
/*  28 */     this.blockFaces = new java.util.HashSet(java.util.Arrays.asList(new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST }));
/*  29 */     new org.bukkit.scheduler.BukkitRunnable() {
/*     */       public void run() {
/*  31 */         if (!PortalListener.this.countdownMap.isEmpty()) {
/*  32 */           Iterator<Player> it = PortalListener.this.countdownMap.keySet().iterator();
/*  33 */           while (it.hasNext()) {
/*  34 */             Player p = (Player)it.next();
/*  35 */             int value = ((Integer)PortalListener.this.countdownMap.get(p)).intValue();
/*  36 */             if (value > 1) {
/*  37 */               value--;
/*  38 */               PortalListener.this.countdownMap.put(p, Integer.valueOf(value));
/*  39 */               String time = PortalListener.this.convertToTime(value, TimeUnit.SECONDS);
/*  40 */               p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTeleporting you out of the trap in &c" + time + "&a. Use &e/cancel &ato cancel."));
/*     */             }
/*     */             else {
/*  43 */               FPlayer fPlayer = com.massivecraft.factions.FPlayers.getInstance().getByPlayer(p);
/*     */               String result;
/*  45 */               if ((fPlayer.hasFaction()) && (fPlayer.getFaction().hasHome())) {
/*  46 */                 org.bukkit.Location fHome = fPlayer.getFaction().getHome();
/*  47 */                 String result = "your faction home";
/*  48 */                 p.teleport(fHome);
/*     */               }
/*     */               else {
/*  51 */                 org.bukkit.Bukkit.dispatchCommand(org.bukkit.Bukkit.getConsoleSender(), "spawn " + p.getName());
/*  52 */                 result = "spawn";
/*     */               }
/*  54 */               p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have been teleported out of the nether portal trap to " + result + "."));
/*  55 */               it.remove(); } } } } }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  60 */       .runTaskTimer(mainPlugin, 20L, 20L);
/*     */   }
/*     */   
/*     */   private String convertToTime(int value, TimeUnit unit) {
/*  64 */     return org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(TimeUnit.MILLISECONDS.convert(value, unit), true, true);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onJoin(final PlayerJoinEvent e) {
/*  69 */     if (withinPortal(e.getPlayer()))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  78 */       new org.bukkit.scheduler.BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  72 */           if (PortalListener.this.countdownMap.put(e.getPlayer(), Integer.valueOf(60)) == null) {
/*  73 */             String time = PortalListener.this.convertToTime(((Integer)PortalListener.this.countdownMap.get(e.getPlayer())).intValue(), TimeUnit.SECONDS);
/*  74 */             e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have been detected to be in a nether portal trap."));
/*  75 */             e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTeleporting you out of the trap in &c" + time + "&a. Use &e/cancel &ato cancel.")); } } }
/*     */       
/*     */ 
/*  78 */         .runTaskLater(this.mainPlugin, 2L);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPortal(final PlayerPortalEvent e) {
/*  84 */     if (e.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  93 */       new org.bukkit.scheduler.BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  87 */           if ((PortalListener.this.withinPortal(e.getPlayer())) && (PortalListener.this.countdownMap.put(e.getPlayer(), Integer.valueOf(60)) == null)) {
/*  88 */             String time = PortalListener.this.convertToTime(((Integer)PortalListener.this.countdownMap.get(e.getPlayer())).intValue(), TimeUnit.SECONDS);
/*  89 */             e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have been detected to be in a nether portal trap."));
/*  90 */             e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTeleporting you out of the trap in &c" + time + "&a. Use &e/cancel &ato cancel.")); } } }
/*     */       
/*     */ 
/*  93 */         .runTaskLater(this.mainPlugin, 200L);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {
/*  99 */     this.countdownMap.remove(e.getPlayer());
/*     */   }
/*     */   
/*     */   @EventHandler(priority=org.bukkit.event.EventPriority.LOWEST)
/*     */   public void onDamage(EntityDamageByEntityEvent e) {
/* 104 */     if ((e.getEntity() instanceof Player)) {
/* 105 */       Player p = (Player)e.getEntity();
/* 106 */       Player d = null;
/* 107 */       if ((e.getDamager() instanceof Player)) {
/* 108 */         d = (Player)e.getDamager();
/*     */       }
/* 110 */       else if ((e.getDamager() instanceof Projectile)) {
/* 111 */         Projectile proj = (Projectile)e.getDamager();
/* 112 */         if ((proj.getShooter() instanceof Player)) {
/* 113 */           d = (Player)proj.getShooter();
/*     */         }
/*     */       }
/* 116 */       if (d == null) {
/* 117 */         return;
/*     */       }
/* 119 */       if (this.countdownMap.get(d) != null) {
/* 120 */         e.setCancelled(true);
/* 121 */         d.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSince you have been detected to be in a nether portal trap, you cannot damage anyone until you have teleported out."));
/*     */       }
/* 123 */       else if (this.countdownMap.get(p) != null) {
/* 124 */         e.setCancelled(true);
/* 125 */         d.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis player has been auto-detected to be stuck in a nether portal trap and is about to be teleported out."));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean withinPortal(Player p) {
/* 131 */     Block feetBlock = p.getLocation().getBlock();
/* 132 */     Block eyeBlock = p.getEyeLocation().getBlock();
/* 133 */     if ((feetBlock.getType() == Material.PORTAL) || (eyeBlock.getType() == Material.PORTAL)) {
/* 134 */       return true;
/*     */     }
/* 136 */     for (BlockFace blockFace : this.blockFaces) {
/* 137 */       if ((feetBlock.getRelative(blockFace).getType() == Material.PORTAL) || (eyeBlock.getRelative(blockFace).getType() == Material.PORTAL)) {
/* 138 */         return true;
/*     */       }
/*     */     }
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   public Map<Player, Integer> getCountdownMap() {
/* 145 */     return this.countdownMap;
/*     */   }
/*     */   
/*     */   public java.util.Set<BlockFace> getBlockFaces() {
/* 149 */     return this.blockFaces;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\PortalListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */