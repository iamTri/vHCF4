/*     */ package io.louis.core.commands;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import io.louis.core.utils.CooldownManager;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class SOTWCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*     */ {
/*     */   private Core mainPlugin;
/*     */   private SOTWTask sotwTask;
/*     */   private final String SCORE;
/*     */   private long[] sotwTimers;
/*     */   private Inventory sotwInv;
/*     */   
/*     */   public SOTWCommand(Core mainPlugin)
/*     */   {
/*  31 */     this.SCORE = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.SOTW"));
/*  32 */     this.mainPlugin = mainPlugin;
/*  33 */     this.sotwTimers = new long[] { TimeUnit.SECONDS.convert(10L, TimeUnit.MINUTES), TimeUnit.SECONDS.convert(30L, TimeUnit.MINUTES), TimeUnit.SECONDS.convert(1L, TimeUnit.HOURS), TimeUnit.SECONDS.convert(2L, TimeUnit.HOURS), TimeUnit.SECONDS.convert(3L, TimeUnit.HOURS) };
/*  34 */     this.sotwInv = Bukkit.createInventory((org.bukkit.inventory.InventoryHolder)null, 9, this.SCORE + " Timer");
/*  35 */     for (long timer : this.sotwTimers) {
/*  36 */       ItemStack itemStack = new org.bukkit.material.Wool(org.bukkit.DyeColor.RED).toItemStack(1);
/*  37 */       ItemMeta itemMeta = itemStack.getItemMeta();
/*  38 */       String duration = org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(timer * 1000L, true, true);
/*  39 */       itemMeta.setDisplayName(ChatColor.RED + duration);
/*  40 */       itemMeta.setLore(java.util.Arrays.asList(new String[] { timer + "" }));
/*  41 */       itemStack.setItemMeta(itemMeta);
/*  42 */       this.sotwInv.addItem(new ItemStack[] { itemStack });
/*     */     }
/*  44 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {
/*  49 */     this.mainPlugin.getCooldownManager().removeCooldown(e.getPlayer(), this.SCORE);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onClick(InventoryClickEvent e) {
/*  54 */     if ((e.getWhoClicked() instanceof Player)) {
/*  55 */       Player p = (Player)e.getWhoClicked();
/*  56 */       if (e.getInventory().getTitle().equals(this.sotwInv.getTitle())) {
/*  57 */         if (e.getSlotType() == org.bukkit.event.inventory.InventoryType.SlotType.OUTSIDE) {
/*  58 */           return;
/*     */         }
/*  60 */         e.setCancelled(true);
/*  61 */         ItemStack currentItem = e.getCurrentItem();
/*  62 */         if (currentItem != null) {
/*  63 */           ItemMeta itemMeta = currentItem.getItemMeta();
/*  64 */           if (itemMeta.hasLore()) {
/*  65 */             long addedValue = Long.parseLong((String)itemMeta.getLore().get(0));
/*  66 */             if (this.sotwTask != null) {
/*  67 */               this.sotwTask.setCount(p, (int)addedValue);
/*     */             }
/*     */             else {
/*  70 */               p.sendMessage(ChatColor.RED + "SOTW is currently not running.");
/*     */             }
/*  72 */             p.closeInventory();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args)
/*     */   {
/*  81 */     if (!s.hasPermission("core.sotw")) {
/*  82 */       s.sendMessage(ChatColor.RED + "No.");
/*  83 */       return true;
/*     */     }
/*  85 */     if (args.length != 1) {
/*  86 */       s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <start | stop>");
/*  87 */       return true;
/*     */     }
/*  89 */     if (args[0].equalsIgnoreCase("start")) {
/*  90 */       if (this.sotwTask != null) {
/*  91 */         s.sendMessage(ChatColor.RED + "SOTW is already enabled.");
/*     */       }
/*     */       else {
/*  94 */         (this.sotwTask = new SOTWTask(this.mainPlugin)).runTaskTimer(this.mainPlugin, 0L, 20L);
/*  95 */         for (Player p : Bukkit.getOnlinePlayers()) {
/*  96 */           this.mainPlugin.getCooldownManager().tryCooldown(p, this.SCORE, this.sotwTask.getCount() * 1000L, false, true, true);
/*     */         }
/*  98 */         s.sendMessage(ChatColor.GREEN + "SOTW has started.");
/*     */       }
/*     */     }
/* 101 */     else if (args[0].equalsIgnoreCase("stop")) {
/* 102 */       if (this.sotwTask == null) {
/* 103 */         s.sendMessage(ChatColor.RED + "SOTW is not currently running.");
/*     */       }
/*     */       else {
/* 106 */         this.sotwTask.cancel();
/* 107 */         this.sotwTask = null;
/* 108 */         for (Player p : Bukkit.getOnlinePlayers()) {
/* 109 */           this.mainPlugin.getCooldownManager().removeCooldown(p, this.SCORE);
/*     */         }
/* 111 */         s.sendMessage(ChatColor.GREEN + "SOTW has stopped.");
/*     */       }
/*     */     }
/* 114 */     else if (args[0].equalsIgnoreCase("settimer")) {
/* 115 */       if (!(s instanceof Player)) {
/* 116 */         s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/* 117 */         return true;
/*     */       }
/* 119 */       Player p2 = (Player)s;
/* 120 */       p2.openInventory(this.sotwInv);
/*     */     }
/*     */     else {
/* 123 */       s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <start | stop>");
/*     */     }
/* 125 */     return true;
/*     */   }
/*     */   
/*     */   private class SOTWTask extends org.bukkit.scheduler.BukkitRunnable implements org.bukkit.event.Listener
/*     */   {
/*     */     private Core mainPlugin;
/*     */     private long count;
/*     */     private World w;
/*     */     
/*     */     public SOTWTask(Core mainPlugin) {
/* 135 */       this.mainPlugin = mainPlugin;
/* 136 */       this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/* 137 */       this.count = 10800L;
/* 138 */       this.w = ((World)this.mainPlugin.getServer().getWorlds().get(0));
/*     */     }
/*     */     
/*     */     @EventHandler(priority=org.bukkit.event.EventPriority.MONITOR)
/*     */     public void onFoodLevelChange(FoodLevelChangeEvent e) {
/* 143 */       if ((e.getEntity() instanceof Player)) {
/* 144 */         e.setCancelled(true);
/* 145 */         e.setFoodLevel(20);
/*     */       }
/*     */     }
/*     */     
/*     */     @EventHandler(priority=org.bukkit.event.EventPriority.HIGHEST)
/*     */     public void onDamage(EntityDamageEvent e) {
/* 151 */       if (((e.getEntity() instanceof Player)) && (e.getCause() != org.bukkit.event.entity.EntityDamageEvent.DamageCause.VOID)) {
/* 152 */         e.setCancelled(true);
/*     */       }
/*     */     }
/*     */     
/*     */     @EventHandler(priority=org.bukkit.event.EventPriority.MONITOR)
/*     */     public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
/* 158 */       this.mainPlugin.getCooldownManager().tryCooldown(e.getPlayer(), SOTWCommand.this.SCORE, this.count * 1000L, false, true, true);
/*     */     }
/*     */     
/*     */     public void run() {
/* 162 */       if (this.count > 0L) {
/* 163 */         this.w.setFullTime(6000L);
/* 164 */         this.w.setStorm(false);
/* 165 */         if (((this.count % 300L == 0L) && (this.count > 60L)) || ((this.count <= 60L) && (this.count % 10L == 0L)) || (this.count <= 10L)) {
/* 166 */           String timeMessage = org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(TimeUnit.MILLISECONDS.convert(this.count, TimeUnit.SECONDS), true, true);
/* 167 */           this.mainPlugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&lAll players will lose invincibility in " + timeMessage + "."));
/*     */         }
/* 169 */         this.count -= 1L;
/*     */       }
/*     */       else {
/* 172 */         this.mainPlugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&lAll players have lost invincibility."));
/* 173 */         cancel();
/*     */       }
/*     */     }
/*     */     
/*     */     public void setCount(CommandSender sender, int addCount) {
/* 178 */       this.count = addCount;
/* 179 */       String setTime = org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(this.count * 1000L, true, true);
/* 180 */       Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c&l" + sender.getName() + " has set the SOTW timer back up to " + setTime + "."));
/*     */     }
/*     */     
/*     */     public synchronized void cancel() throws IllegalStateException {
/* 184 */       org.bukkit.event.HandlerList.unregisterAll(this);
/* 185 */       super.cancel();
/* 186 */       SOTWCommand.this.sotwTask = null;
/*     */     }
/*     */     
/*     */     public long getCount() {
/* 190 */       return this.count;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\SOTWCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */