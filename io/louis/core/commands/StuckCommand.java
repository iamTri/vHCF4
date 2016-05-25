/*     */ package io.louis.core.commands;
/*     */ 
/*     */ import com.massivecraft.factions.FPlayer;
/*     */ import com.massivecraft.factions.FPlayers;
/*     */ import io.louis.core.Core;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ 
/*     */ public class StuckCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*     */ {
/*     */   private Core mainPlugin;
/*     */   private Map<String, StuckTask> stuckTasks;
/*     */   
/*     */   public StuckCommand(Core mainPlugin)
/*     */   {
/*  23 */     this.mainPlugin = mainPlugin;
/*  24 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/*  25 */     this.stuckTasks = new java.util.HashMap();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onCommandProcess(org.bukkit.event.player.PlayerCommandPreprocessEvent e) {
/*  30 */     String[] fullCommand = e.getMessage().split(" ");
/*  31 */     String cmd = fullCommand[0].substring(1);
/*  32 */     String[] args = new String[fullCommand.length - 1];
/*  33 */     if (fullCommand.length > 1) {
/*  34 */       for (int i = 1; i < fullCommand.length; i++) {
/*  35 */         args[(i - 1)] = fullCommand[i];
/*     */       }
/*  37 */       if ((cmd.equalsIgnoreCase("f")) && (args[0].equalsIgnoreCase("stuck"))) {
/*  38 */         e.setMessage("/stuck");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {
/*  45 */     if (this.stuckTasks.containsKey(e.getPlayer().getName())) {
/*  46 */       ((StuckTask)this.stuckTasks.get(e.getPlayer().getName())).cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKick(PlayerKickEvent e) {
/*  52 */     if (this.stuckTasks.containsKey(e.getPlayer().getName())) {
/*  53 */       ((StuckTask)this.stuckTasks.get(e.getPlayer().getName())).cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=org.bukkit.event.EventPriority.MONITOR)
/*     */   public void onDamage(EntityDamageByEntityEvent e)
/*     */   {
/*  60 */     if ((e.getEntity() instanceof Player)) {
/*  61 */       Player p = (Player)e.getEntity();
/*  62 */       Player d = null;
/*  63 */       if (this.stuckTasks.containsKey(p.getName())) {
/*  64 */         ((StuckTask)this.stuckTasks.get(p.getName())).cancel();
/*  65 */         p.sendMessage(ChatColor.RED + "You have been hit, teleport canceled.");
/*     */       }
/*  67 */       if ((e.getDamager() instanceof Player)) {
/*  68 */         d = (Player)e.getDamager();
/*     */       }
/*  70 */       else if ((e.getDamager() instanceof Projectile)) {
/*  71 */         Projectile proj = (Projectile)e.getDamager();
/*  72 */         if ((proj.getShooter() instanceof Player)) {
/*  73 */           d = (Player)proj.getShooter();
/*     */         }
/*     */       }
/*  76 */       if (d == null) {
/*  77 */         return;
/*     */       }
/*  79 */       if (this.stuckTasks.containsKey(d.getName())) {
/*  80 */         ((StuckTask)this.stuckTasks.remove(d.getName())).cancel();
/*  81 */         d.sendMessage(ChatColor.RED + "You have hit someone, teleport canceled.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(org.bukkit.command.CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/*  87 */     if (!(s instanceof Player)) {
/*  88 */       s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/*  89 */       return true;
/*     */     }
/*  91 */     Player p = (Player)s;
/*  92 */     if (this.stuckTasks.containsKey(p.getName())) {
/*  93 */       p.sendMessage(ChatColor.RED + "You are already being scheduled to teleport back to spawn.");
/*  94 */       return true;
/*     */     }
/*  96 */     FPlayer fPlayer = FPlayers.getInstance().getByPlayer(p);
/*  97 */     if ((fPlayer.hasFaction()) && (fPlayer.isInOwnTerritory())) {
/*  98 */       p.sendMessage(ChatColor.RED + "You can only perform this command in areas other than your claimed territory.");
/*  99 */       return true;
/*     */     }
/* 101 */     StuckTask stuckTask = new StuckTask(p);
/* 102 */     stuckTask.runTaskTimer(this.mainPlugin, 0L, 20L);
/*     */     
/* 104 */     Location loc = new Location(org.bukkit.Bukkit.getWorld("world"), 0.0D, 64.0D, 0.0D);
/*     */     
/* 106 */     this.stuckTasks.put(p.getName(), stuckTask);
/* 107 */     return true;
/*     */   }
/*     */   
/*     */   private class StuckTask extends org.bukkit.scheduler.BukkitRunnable
/*     */   {
/*     */     private Player player;
/*     */     private Location startingLocation;
/*     */     private int i;
/*     */     private final String SCORE;
/*     */     
/*     */     public StuckTask(Player player) {
/* 118 */       this.i = Core.cfg3.getInt("Timers.Stuck");
/* 119 */       this.SCORE = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.Stuck"));
/* 120 */       this.player = player;
/* 121 */       this.startingLocation = player.getLocation().clone();
/* 122 */       String timeMessage = ChatColor.YELLOW + net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils.formatDurationWords(TimeUnit.MILLISECONDS.convert(this.i, TimeUnit.SECONDS), true, true);
/* 123 */       StuckCommand.this.mainPlugin.getCooldownManager().tryCooldown(getPlayer(), this.SCORE, this.i * 1000, false, true, true);
/* 124 */       this.player.sendMessage(ChatColor.RED + "Teleporting you out in " + timeMessage + ChatColor.RED + ".");
/* 125 */       this.player.sendMessage(ChatColor.RED + "Don't move more than 5 blocks or go into combat.");
/*     */     }
/*     */     
/*     */     public void run() {
/* 129 */       if (this.startingLocation.distance(this.player.getLocation()) >= 5.0D) {
/* 130 */         this.player.sendMessage(ChatColor.RED + "You have moved more than 5 blocks away from your starting position. Teleport canceled.");
/* 131 */         cancel();
/* 132 */         return;
/*     */       }
/* 134 */       if (this.i > 0) {
/* 135 */         this.i -= 1;
/* 136 */         if ((this.i % 30 == 0) && (this.i > 0)) {
/* 137 */           String timeMessage = net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils.formatDurationWords(TimeUnit.MILLISECONDS.convert(this.i, TimeUnit.SECONDS), true, true);
/* 138 */           this.player.sendMessage(ChatColor.RED + "Teleporting you out in " + timeMessage + ChatColor.RED + ".");
/* 139 */           this.player.sendMessage(ChatColor.RED + "Don't move more than 5 blocks or go into combat.");
/*     */         }
/*     */       }
/*     */       else {
/* 143 */         FPlayer fPlayer = FPlayers.getInstance().getByPlayer(getPlayer());
/* 144 */         if ((fPlayer.hasFaction()) && (fPlayer.getFaction().hasHome())) {
/* 145 */           this.player.teleport(fPlayer.getFaction().getHome());
/*     */         }
/*     */         else {
/* 148 */           org.bukkit.Bukkit.dispatchCommand(org.bukkit.Bukkit.getConsoleSender(), "spawn " + this.player.getName());
/*     */         }
/* 150 */         cancel();
/*     */       }
/*     */     }
/*     */     
/*     */     public synchronized void cancel() {
/* 155 */       StuckCommand.this.mainPlugin.getCooldownManager().removeCooldown(getPlayer(), this.SCORE);
/* 156 */       StuckCommand.this.stuckTasks.remove(this.player.getName());
/* 157 */       super.cancel();
/*     */     }
/*     */     
/*     */     public Player getPlayer() {
/* 161 */       return this.player;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\StuckCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */