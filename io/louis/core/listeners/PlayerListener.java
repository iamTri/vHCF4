/*     */ package io.louis.core.listeners;
/*     */ 
/*     */ import com.alexandeh.glaedr.scoreboards.Entry;
/*     */ import com.alexandeh.glaedr.scoreboards.PlayerScoreboard;
/*     */ import com.massivecraft.factions.FPlayer;
/*     */ import com.massivecraft.factions.FPlayers;
/*     */ import com.temptingmc.koths.events.KothEndEvent;
/*     */ import com.temptingmc.koths.events.KothStartEvent;
/*     */ import com.temptingmc.koths.events.PlayerCaptureKothEvent;
/*     */ import com.temptingmc.koths.events.PlayerStartCaptureKothEvent;
/*     */ import com.temptingmc.koths.events.PlayerStopCaptureKothEvent;
/*     */ import com.temptingmc.koths.koth.Koth;
/*     */ import com.temptingmc.koths.koth.KothFlag;
/*     */ import io.louis.core.Core;
/*     */ import io.louis.core.utils.ArmorKit;
/*     */ import io.louis.core.utils.CooldownManager;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BrewingStand;
/*     */ import org.bukkit.block.Furnace;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Horse;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.inventory.FurnaceBurnEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryOpenEvent;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerEditBookEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerPortalEvent;
/*     */ import org.bukkit.event.vehicle.VehicleEnterEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.BookMeta;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ 
/*     */ public class PlayerListener implements org.bukkit.event.Listener
/*     */ {
/*     */   private Map<Player, ArmorKit> armorKits;
/*     */   private java.util.List<ArmorKit> armorKitList;
/*     */   private final String HOME_SCORE;
/*     */   private Core mainPlugin;
/*     */   
/*     */   public PlayerListener(Core mainPlugin)
/*     */   {
/*  66 */     this.HOME_SCORE = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.Teleport"));
/*  67 */     this.mainPlugin = mainPlugin;
/*  68 */     this.armorKits = new java.util.WeakHashMap();
/*  69 */     this.armorKitList = new java.util.ArrayList(java.util.Arrays.asList(new ArmorKit[] { new io.louis.core.kits.ArcherKit(), new io.louis.core.kits.MinerKit(), new io.louis.core.kits.BardKit() }));
/*  70 */     new org.bukkit.scheduler.BukkitRunnable() {
/*     */       public void run() {
/*  72 */         for (Player p : )
/*  73 */           PlayerListener.this.updateEffects(p); } }
/*     */     
/*     */ 
/*  76 */       .runTaskTimerAsynchronously(mainPlugin, 20L, 20L);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOW)
/*     */   public void removeDupedItems(PlayerInteractEvent event) {
/*  81 */     Player player = event.getPlayer();
/*  82 */     if (player.getItemInHand().getAmount() > 0) {
/*  83 */       return;
/*     */     }
/*  85 */     player.setItemInHand((ItemStack)null);
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.LOW)
/*     */   public void removeDupedItems(org.bukkit.event.entity.ItemSpawnEvent event) {
/*  90 */     if (event.getEntity().getItemStack().getAmount() > 0) {
/*  91 */       return;
/*     */     }
/*  93 */     event.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.LOW)
/*     */   public void removeDupedItems(org.bukkit.event.block.BlockDispenseEvent event) {
/*  98 */     org.bukkit.inventory.InventoryHolder inventoryHolder = (org.bukkit.inventory.InventoryHolder)event.getBlock().getState();
/*  99 */     for (java.util.ListIterator localListIterator = inventoryHolder.getInventory().iterator(); localListIterator.hasNext();) { ItemStack itemStack = (ItemStack)localListIterator.next();
/* 100 */       if ((itemStack != null) && 
/*     */       
/*     */ 
/* 103 */         (!itemStack.getType().equals(Material.AIR)) && 
/*     */         
/*     */ 
/* 106 */         (itemStack.getAmount() < 0))
/*     */       {
/*     */ 
/* 109 */         event.setCancelled(true); }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.LOW)
/*     */   public void limitPageCount(PlayerEditBookEvent event) {
/* 115 */     Player player = event.getPlayer();
/* 116 */     if (event.getNewBookMeta().getPageCount() <= 50) {
/* 117 */       return;
/*     */     }
/* 119 */     BookMeta newMeta = (BookMeta)Bukkit.getItemFactory().getItemMeta(Material.WRITTEN_BOOK);
/* 120 */     newMeta.setAuthor(event.getPreviousBookMeta().getAuthor());
/* 121 */     newMeta.setTitle(event.getPreviousBookMeta().getTitle());
/* 122 */     newMeta.setPages(newMeta.getPages().subList(0, 49));
/* 123 */     event.setNewBookMeta(newMeta);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onSpeak(AsyncPlayerChatEvent e)
/*     */   {
/* 129 */     Player p = e.getPlayer();
/*     */     
/* 131 */     if (io.louis.core.utils.Lists.staffChat.contains(p.getName())) {
/* 132 */       for (Player online : Bukkit.getOnlinePlayers()) {
/* 133 */         if (online.hasPermission("core.staffchat")) {
/* 134 */           online.sendMessage("§b" + p.getName() + ": " + e.getMessage());
/*     */         }
/*     */       }
/* 137 */       e.setCancelled(true);
/*     */     }
/* 139 */     if ((!io.louis.core.utils.Lists.chatEnabled) && 
/* 140 */       (!p.hasPermission("core.bypass"))) {
/* 141 */       e.setCancelled(true);
/* 142 */       p.sendMessage("§CChat is currently restricted.");
/*     */     }
/*     */     
/* 145 */     for (??? = io.louis.core.utils.Lists.globalChat.iterator(); ((Iterator)???).hasNext();) { Player cantSee = (Player)((Iterator)???).next();
/* 146 */       e.getRecipients().remove(cantSee);
/* 147 */       Player permission = e.getPlayer();
/* 148 */       if (permission.hasPermission("core.bypass")) {
/* 149 */         cantSee.sendMessage(permission.getDisplayName() + " " + e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   void onTarg(org.bukkit.event.entity.EntityTargetEvent event) {
/* 156 */     if (event.getEntityType() == org.bukkit.entity.EntityType.CREEPER) {
/* 157 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onLoginJoinFull(PlayerLoginEvent e) {
/* 163 */     if (e.getResult() == org.bukkit.event.player.PlayerLoginEvent.Result.KICK_FULL) {
/* 164 */       if (e.getPlayer().hasPermission("core.kappa")) {
/* 165 */         e.allow();
/*     */       } else {
/* 167 */         e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, io.louis.core.utils.C.c("&cServer is full! Buy a reserved slot at " + 
/* 168 */           ChatColor.translateAlternateColorCodes('&', Core.cfg2.getString("Messages.ServerStore"))));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
/*     */   public void onDamage(EntityDamageByEntityEvent e) {
/* 175 */     if ((e.getDamager() instanceof Arrow)) {
/* 176 */       Arrow a = (Arrow)e.getDamager();
/* 177 */       if (((e.getEntity() instanceof Player)) && ((a.getShooter() instanceof Player))) {
/* 178 */         Player p = (Player)e.getEntity();
/* 179 */         Player d = (Player)a.getShooter();
/* 180 */         if (p.equals(d)) {
/* 181 */           return;
/*     */         }
/* 183 */         ArmorKit armorKit = (ArmorKit)this.armorKits.get(d);
/* 184 */         ArmorKit playerKit = (ArmorKit)this.armorKits.get(p);
/* 185 */         if ((armorKit != null) && (armorKit.getArmorType().equals("LEATHER"))) {
/* 186 */           if ((playerKit != null) && (playerKit.getArmorType().equals("LEATHER"))) {
/* 187 */             d.sendMessage(ChatColor.RED + "You cannot tag other Archers!");
/* 188 */             return;
/*     */           }
/* 190 */           FPlayer fP = FPlayers.getInstance().getByPlayer(p);
/* 191 */           FPlayer fD = FPlayers.getInstance().getByPlayer(d);
/* 192 */           if ((fP.hasFaction()) && (fD.hasFaction()) && (
/* 193 */             (fP.getRelationTo(fD) == com.massivecraft.factions.struct.Relation.MEMBER) || 
/* 194 */             (fP.getRelationTo(fD) == com.massivecraft.factions.struct.Relation.ALLY))) {
/* 195 */             return;
/*     */           }
/* 197 */           if (this.mainPlugin.getCooldownManager().getCooldown(p, ChatColor.RED + "Archer Tag") <= 0L) {
/* 198 */             p.sendMessage(ChatColor.RED + "You have been archer tagged by " + ChatColor.BOLD + d.getName() + ChatColor.RESET + ChatColor.RED + " for 15 seconds!");
/*     */             
/* 200 */             p.sendMessage(ChatColor.RED + "You will now take " + ChatColor.GOLD + ChatColor.BOLD + "15.0%" + ChatColor.RESET + ChatColor.RED + " more damage!");
/*     */             
/* 202 */             d.sendMessage(ChatColor.RED + "You have archer tagged " + ChatColor.BOLD + p.getName() + ChatColor.RESET + ChatColor.RED + " for 15 seconds!");
/*     */             
/* 204 */             d.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + p.getName() + ChatColor.RESET + "" + ChatColor.RED + " will now take " + ChatColor.GOLD + ChatColor.BOLD + "15.0%" + ChatColor.RESET + ChatColor.RED + " more damage!");
/*     */           }
/*     */           
/*     */ 
/* 208 */           this.mainPlugin.getCooldownManager().tryCooldown(p, ChatColor.RED + "Archer Tag", 15000L, false, true, true);
/*     */         }
/*     */       }
/*     */     }
/* 212 */     else if ((e.getEntity() instanceof Player)) {
/* 213 */       Player p2 = (Player)e.getEntity();
/* 214 */       if (this.mainPlugin.getCooldownManager().getCooldown(p2, ChatColor.RED + "Archer Tag") > 0L) {
/* 215 */         e.setDamage(e.getDamage() * 1.15D);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onJoin(PlayerJoinEvent e) {
/* 222 */     e.setJoinMessage((String)null);
/* 223 */     for (Koth koth : com.temptingmc.koths.NexGenKoths.loadedKoths) {
/* 224 */       String kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + koth.getName() + " Koth:";
/* 225 */       if (koth.getFlagValue(KothFlag.CAPTURE_TIME) == 1800L) {
/* 226 */         kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + koth.getName();
/*     */       }
/* 228 */       if (koth.isActive()) {
/* 229 */         if (koth.getCappingPlayer() == null) {
/* 230 */           this.mainPlugin.getCooldownManager().addScore(e.getPlayer(), kothName, (int)koth.getCaptureTimer());
/*     */         } else {
/* 232 */           this.mainPlugin.getCooldownManager().tryCooldown(e.getPlayer(), kothName, koth.getCaptureTimer() * 1000L, false, true, true);
/*     */         }
/*     */       } else {
/* 235 */         this.mainPlugin.getCooldownManager().removeCooldown(e.getPlayer(), kothName);
/*     */       }
/*     */     }
/* 238 */     for (PotionEffect effect : e.getPlayer().getActivePotionEffects()) {
/* 239 */       if (effect.getDuration() > 12000) {
/* 240 */         e.getPlayer().removePotionEffect(effect.getType());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {
/* 247 */     e.setQuitMessage((String)null);
/* 248 */     for (Koth koth : com.temptingmc.koths.NexGenKoths.loadedKoths) {
/* 249 */       String kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + koth.getName() + " Koth:";
/* 250 */       if (koth.getFlagValue(KothFlag.CAPTURE_TIME) == 1800L) {
/* 251 */         kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + koth.getName();
/*     */       }
/* 253 */       this.mainPlugin.getCooldownManager().removeCooldown(e.getPlayer(), kothName);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onSignInteract(PlayerInteractEvent event) {
/* 259 */     if ((event.getClickedBlock() != null) && (event.getClickedBlock().getType() == Material.SKULL)) {
/* 260 */       org.bukkit.block.Skull sk = (org.bukkit.block.Skull)event.getClickedBlock().getState();
/* 261 */       if (sk.getSkullType() == org.bukkit.SkullType.PLAYER) {
/* 262 */         event.getPlayer().sendMessage(ChatColor.YELLOW + "Head of " + ChatColor.WHITE + sk
/* 263 */           .getOwner() + ChatColor.YELLOW + ".");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGH)
/*     */   public void onPotionSplash(org.bukkit.event.entity.PotionSplashEvent event) {
/* 270 */     ItemStack potion = event.getPotion().getItem();
/* 271 */     for (Object i : Core.DISALLOWED_POTIONS) {
/* 272 */       if (((Integer)i).intValue() == potion.getDurability()) {
/* 273 */         event.setCancelled(true);
/* 274 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGH)
/*     */   public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
/* 281 */     if (event.getItem().getType() != Material.POTION) {
/* 282 */       return;
/*     */     }
/* 284 */     if (Core.DISALLOWED_POTIONS.contains(Integer.valueOf(event.getItem().getDurability()))) {
/* 285 */       event.setCancelled(true);
/* 286 */       event.getPlayer().sendMessage(
/* 287 */         ChatColor.translateAlternateColorCodes('&', "&6Potion &8>> &7This potion is currently disabled!"));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onRainStart(org.bukkit.event.weather.WeatherChangeEvent event) {
/* 293 */     if (!event.isCancelled()) {
/* 294 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onDeath(PlayerDeathEvent e) {
/* 300 */     if (e.getEntity().getKiller() != null)
/*     */     {
/* 302 */       String playerName = ChatColor.RED + e.getEntity().getName() + ChatColor.GOLD + "[" + ChatColor.WHITE + e.getEntity().getStatistic(org.bukkit.Statistic.PLAYER_KILLS) + ChatColor.GOLD + "]";
/*     */       
/* 304 */       String killerName = ChatColor.RED + e.getEntity().getKiller().getName() + ChatColor.GOLD + "[" + ChatColor.WHITE + e.getEntity().getKiller().getStatistic(org.bukkit.Statistic.PLAYER_KILLS) + ChatColor.GOLD + "]";
/*     */       
/* 306 */       String itemName = "";
/* 307 */       ItemStack itemStack = e.getEntity().getKiller().getItemInHand();
/* 308 */       if (itemStack != null) {
/* 309 */         if (itemStack.getType() == Material.AIR) {
/* 310 */           itemName = "Hand";
/* 311 */         } else if (itemStack.getItemMeta().hasDisplayName()) {
/* 312 */           itemName = itemStack.getItemMeta().getDisplayName();
/*     */         } else {
/* 314 */           itemName = org.apache.commons.lang.WordUtils.capitalizeFully(itemStack.getType().name().replaceAll("_", " "));
/*     */         }
/*     */       }
/* 317 */       e.setDeathMessage(playerName + ChatColor.YELLOW + " was killed by " + killerName + ChatColor.YELLOW + " using " + ChatColor.AQUA + itemName + ChatColor.YELLOW + ".");
/*     */     }
/*     */     else {
/* 320 */       String rawMessage = e.getDeathMessage().replaceAll(e.getEntity().getName(), "");
/* 321 */       e.setDeathMessage(ChatColor.RED + e.getEntity().getName() + ChatColor.GOLD + "[" + ChatColor.WHITE + e
/* 322 */         .getEntity().getStatistic(org.bukkit.Statistic.PLAYER_KILLS) + ChatColor.GOLD + "]" + ChatColor.YELLOW + rawMessage + ".");
/*     */     }
/*     */     
/* 325 */     e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());
/* 326 */     e.setKeepLevel(false);
/* 327 */     ItemStack headItem = new ItemStack(Material.SKULL_ITEM, 1, (short)org.bukkit.SkullType.PLAYER.ordinal());
/* 328 */     org.bukkit.inventory.meta.SkullMeta skullMeta = (org.bukkit.inventory.meta.SkullMeta)headItem.getItemMeta();
/* 329 */     skullMeta.setOwner(e.getEntity().getName());
/* 330 */     headItem.setItemMeta(skullMeta);
/* 331 */     e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), headItem);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPortal1(PlayerPortalEvent e) {
/* 336 */     if (e.getTo().getWorld().getEnvironment() == org.bukkit.World.Environment.NETHER) {
/* 337 */       Location l = e.getTo();
/* 338 */       int range = 10;
/* 339 */       int minX = l.getBlockX() - 5;
/* 340 */       int minY = l.getBlockY() - 2;
/* 341 */       int minZ = l.getBlockZ() - 5;
/* 342 */       for (int x = minX; x < minX + 10; x++) {
/* 343 */         for (int y = minY; y < minY + 10; y++) {
/* 344 */           for (int z = minZ; z < minZ + 10; z++) {
/* 345 */             Block block = e.getTo().getWorld().getBlockAt(x, y, z);
/* 346 */             Block platform = e.getTo().getWorld().getBlockAt(x, (int)(l.getY() - 2.0D), z);
/* 347 */             if (platform.getType() != Material.OBSIDIAN) {
/* 348 */               platform.setType(Material.OBSIDIAN);
/*     */             }
/* 350 */             if ((block.getType() == Material.LAVA) || (block.getType() == Material.STATIONARY_LAVA) || 
/* 351 */               (block.getType() == Material.QUARTZ_ORE) || (block.getType() == Material.NETHERRACK) || 
/* 352 */               (block.getType() == Material.GRAVEL) || (block.getType() == Material.SOUL_SAND) || 
/* 353 */               (block.getType() == Material.NETHER_BRICK)) {
/* 354 */               block.setType(Material.AIR);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
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
/*     */   @EventHandler
/*     */   public void onFoodLevelChange(FoodLevelChangeEvent e)
/*     */   {
/* 486 */     if ((e.getEntity() instanceof Player)) {
/* 487 */       Player p = (Player)e.getEntity();
/* 488 */       if (e.getFoodLevel() < p.getFoodLevel()) {
/* 489 */         p.setExhaustion(0.0F);
/* 490 */         p.setSaturation(5.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
/*     */   public void onStartCaptureKoth(PlayerStartCaptureKothEvent e) {
/* 497 */     String kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName() + " Koth:";
/* 498 */     if (e.getKoth().getFlagValue(KothFlag.CAPTURE_TIME) == 1800L) {
/* 499 */       kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName();
/*     */     }
/* 501 */     long kothTime = e.getKoth().getCaptureTimer() * 1000L;
/* 502 */     e.getKoth().setCappingPlayer(e.getPlayer());
/* 503 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 504 */       this.mainPlugin.getCooldownManager().tryCooldown(p, kothName, kothTime, false, true, true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onStopCaptureKoth(PlayerStopCaptureKothEvent e) {
/* 510 */     String kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName() + " Koth:";
/* 511 */     if (e.getKoth().getFlagValue(KothFlag.CAPTURE_TIME) == 1800L) {
/* 512 */       kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName();
/*     */     }
/* 514 */     e.getKoth().setCappingPlayer(null);
/* 515 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 516 */       if (e.getKoth().isActive()) {
/* 517 */         PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(p);
/* 518 */         Entry entry = playerScoreboard.getEntry(kothName);
/* 519 */         if (entry != null) {
/* 520 */           entry.setCountdown(false).setTime((int)e.getKoth().getFlagValue(KothFlag.CAPTURE_TIME)).send();
/*     */         }
/*     */       } else {
/* 523 */         this.mainPlugin.getCooldownManager().removeCooldown(p, kothName);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKothStart(KothStartEvent e) {
/* 530 */     String kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName() + " Koth:";
/* 531 */     if (e.getKoth().getFlagValue(KothFlag.CAPTURE_TIME) == 1800L) {
/* 532 */       kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName();
/*     */     }
/* 534 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 535 */       this.mainPlugin.getCooldownManager().addScore(p, kothName, (int)e.getKoth().getFlagValue(KothFlag.CAPTURE_TIME));
/* 536 */       PlayerScoreboard playerScoreboard = PlayerScoreboard.getScoreboard(p);
/* 537 */       Entry localEntry = playerScoreboard.getEntry(kothName);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKothStop(KothEndEvent e) {
/* 543 */     String kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName() + " Koth:";
/* 544 */     if (e.getKoth().getFlagValue(KothFlag.CAPTURE_TIME) == 1800L) {
/* 545 */       kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName();
/*     */     }
/* 547 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 548 */       this.mainPlugin.getCooldownManager().removeCooldown(p, kothName);
/*     */     }
/* 550 */     this.mainPlugin.getCooldownManager().removeAllCooldownsUnderKey(kothName);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onCapture(PlayerCaptureKothEvent e) {
/* 555 */     String kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName() + " Koth:";
/* 556 */     if (e.getKoth().getFlagValue(KothFlag.CAPTURE_TIME) == 1800L) {
/* 557 */       kothName = ChatColor.translateAlternateColorCodes('&', Core.cfg3.getString("Scoreboard.KothColour")) + e.getKoth().getName();
/*     */     }
/* 559 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 560 */       this.mainPlugin.getCooldownManager().removeCooldown(p, kothName);
/*     */     }
/* 562 */     this.mainPlugin.getCooldownManager().removeAllCooldownsUnderKey(kothName);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGH)
/*     */   public void onInventoryOpen(InventoryOpenEvent event) {
/* 567 */     Player p = (Player)event.getPlayer();
/* 568 */     if (event.getInventory().getType() == org.bukkit.event.inventory.InventoryType.ENDER_CHEST) {
/* 569 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClick111(InventoryClickEvent e) {
/* 575 */     if ((e.getInventory().getHolder() instanceof BrewingStand)) {
/* 576 */       final BrewingStand stand = (BrewingStand)e.getInventory().getHolder();
/* 577 */       new org.bukkit.scheduler.BukkitRunnable() {
/*     */         public void run() {
/* 579 */           if (stand.getBrewingTime() == 400)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 589 */             new org.bukkit.scheduler.BukkitRunnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 582 */                 if (PlayerListener.2.this.val$stand.getBrewingTime() - 70 <= 0) {
/* 583 */                   PlayerListener.2.this.val$stand.setBrewingTime(1);
/* 584 */                   cancel();
/* 585 */                   return;
/*     */                 }
/* 587 */                 PlayerListener.2.this.val$stand.setBrewingTime(PlayerListener.2.this.val$stand.getBrewingTime() - 20);
/*     */               }
/* 589 */             }.runTaskTimer(com.temptingmc.koths.Main.getInstance(), 0L, 4L); } } }
/*     */       
/*     */ 
/* 592 */         .runTaskLater(this.mainPlugin, 2L);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void StopBlockPlace(BlockPlaceEvent event) {
/* 598 */     Player p = event.getPlayer();
/* 599 */     org.bukkit.inventory.PlayerInventory inventory = p.getInventory();
/* 600 */     if (event.getBlock().getType() == Material.RAILS) {
/* 601 */       event.setCancelled(true);
/* 602 */       p.sendMessage(ChatColor.RED + "Temp Disabled due to Duping.");
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockPlaceEvent(BlockPlaceEvent event) {
/* 608 */     Player player = event.getPlayer();
/* 609 */     Block cancelledBlock = event.getBlock();
/*     */     
/* 611 */     if (((player.isOp()) || (player.hasPermission("lol"))) && 
/* 612 */       (cancelledBlock.getType() == Material.STORAGE_MINECART)) {
/* 613 */       return;
/*     */     }
/*     */     
/*     */ 
/* 617 */     if ((!player.isOp()) && (!player.hasPermission("lol")) && 
/* 618 */       (cancelledBlock.getType() == Material.STORAGE_MINECART)) {
/* 619 */       event.setCancelled(true);
/* 620 */       player.sendMessage("ugly ass bk ur not allowed to dupe <3");
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void furnaceBurn(FurnaceBurnEvent event)
/*     */   {
/* 627 */     Furnace furnace = (Furnace)event.getBlock().getState();
/* 628 */     furnace.setCookTime((short)0);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void furnaceSmeltEvent(org.bukkit.event.inventory.FurnaceSmeltEvent event) {
/* 633 */     Furnace furnace = (Furnace)event.getBlock().getState();
/* 634 */     furnace.setCookTime((short)0);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClick11(InventoryClickEvent event)
/*     */   {
/* 640 */     Block blocktype = event.getWhoClicked().getTargetBlock(null, 10);
/*     */     
/* 642 */     if (((blocktype.getType() == Material.FURNACE) || (blocktype.getType() == Material.BURNING_FURNACE)) && 
/* 643 */       ((event.getSlot() == 0) || (event.getSlot() == 1)) && (event.getCursor().getType() != Material.AIR)) {
/* 644 */       Furnace furnace = (Furnace)blocktype.getState();
/* 645 */       furnace.setCookTime((short)0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 650 */   Short cooktime = Short.valueOf((short)100);
/*     */   
/*     */   @EventHandler
/*     */   public void onVehicleEnter(VehicleEnterEvent event) {
/* 654 */     if (((event.getVehicle() instanceof Horse)) && ((event.getEntered() instanceof Player))) {
/* 655 */       Horse horse = (Horse)event.getVehicle();
/* 656 */       Player player = (Player)event.getEntered();
/* 657 */       if ((horse.getOwner() != null) && (!horse.getOwner().getName().equals(player.getName()))) {
/* 658 */         event.setCancelled(true);
/* 659 */         player.sendMessage(ChatColor.RED + "This is not your horse!");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityExplode(org.bukkit.event.entity.EntityExplodeEvent event) {
/* 666 */     event.blockList().clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteract(PlayerInteractEvent e)
/*     */   {
/* 672 */     Player p = e.getPlayer();
/* 673 */     if ((e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) && (e.getPlayer().getItemInHand().getTypeId() == 333)) {
/* 674 */       Block target = e.getClickedBlock();
/* 675 */       if ((target.getTypeId() != 8) && (target.getTypeId() != 9)) {
/* 676 */         p.sendMessage("You can only place boats in water.");
/* 677 */         e.setCancelled(true);
/* 678 */         return;
/*     */       }
/*     */     }
/* 681 */     if (e.isCancelled()) {}
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
/*     */   private void startUpdate(final Furnace tile, final int increase)
/*     */   {
/* 696 */     new org.bukkit.scheduler.BukkitRunnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 689 */         if ((tile.getCookTime() > 0) || (tile.getBurnTime() > 0)) {
/* 690 */           tile.setCookTime((short)(tile.getCookTime() + increase));
/* 691 */           tile.update();
/*     */         } else {
/* 693 */           cancel();
/*     */         }
/*     */       }
/* 696 */     }.runTaskTimer(com.temptingmc.koths.Main.getInstance(), 1L, 1L);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onFurnaceBurn(FurnaceBurnEvent event) {
/* 701 */     startUpdate((Furnace)event.getBlock().getState(), Core.RANDOM.nextBoolean() ? 1 : 2);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onHandleEndEntity(CreatureSpawnEvent e) {
/* 706 */     if ((e.getEntity().getWorld().getEnvironment() == org.bukkit.World.Environment.THE_END) && 
/* 707 */       (e.getSpawnReason() == org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.NATURAL)) {
/* 708 */       e.setCancelled(true);
/*     */     }
/*     */     
/* 711 */     if (e.getEntity().getType() == org.bukkit.entity.EntityType.SQUID) {
/* 712 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDeath(EntityDeathEvent event) {
/* 718 */     if (event.getEntity().getKiller() != null) {
/* 719 */       Player player = event.getEntity().getKiller();
/* 720 */       if ((player.getItemInHand() != null) && 
/* 721 */         (player.getItemInHand().containsEnchantment(org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS))) {
/* 722 */         switch (player.getItemInHand().getEnchantmentLevel(org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS)) {
/*     */         case 0: 
/* 724 */           event.setDroppedExp((int)Math.ceil(event.getDroppedExp() * 1.5D));
/* 725 */           break;
/*     */         
/*     */         case 1: 
/* 728 */           event.setDroppedExp((int)Math.ceil(event.getDroppedExp() * 3.0D));
/* 729 */           break;
/*     */         
/*     */         case 2: 
/* 732 */           event.setDroppedExp((int)Math.ceil(event.getDroppedExp() * 5.0D));
/* 733 */           break;
/*     */         
/*     */         case 3: 
/* 736 */           event.setDroppedExp((int)Math.ceil(event.getDroppedExp() * 10.0D));
/* 737 */           break;
/*     */         
/*     */         case 4: 
/* 740 */           event.setDroppedExp((int)Math.ceil(event.getDroppedExp() * 20.0D));
/* 741 */           break;
/*     */         
/*     */         case 5: 
/* 744 */           event.setDroppedExp((int)Math.ceil(event.getDroppedExp() * 30.0D));
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 752 */     event.setDroppedExp((int)Math.ceil(event.getDroppedExp() * 1));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDamaging(EntityDamageByEntityEvent e) {
/* 757 */     if ((e.getDamager() instanceof Player)) {
/* 758 */       Player p = (Player)e.getDamager();
/* 759 */       if (p.hasPotionEffect(org.bukkit.potion.PotionEffectType.INCREASE_DAMAGE))
/* 760 */         for (PotionEffect effect : p.getActivePotionEffects())
/* 761 */           if (effect.getType().equals(org.bukkit.potion.PotionEffectType.INCREASE_DAMAGE)) {
/* 762 */             int level = effect.getAmplifier() + 1;
/*     */             
/* 764 */             double newDamage = e.getDamage(EntityDamageEvent.DamageModifier.BASE) / (level * 1.45D + 1.1D) + 2 * level;
/*     */             
/*     */ 
/* 767 */             double damagePercent = newDamage / e.getDamage(EntityDamageEvent.DamageModifier.BASE);
/*     */             try {
/* 769 */               e.setDamage(EntityDamageEvent.DamageModifier.ARMOR, e
/* 770 */                 .getDamage(EntityDamageEvent.DamageModifier.ARMOR) * damagePercent);
/*     */             }
/*     */             catch (Exception localException) {}
/*     */             try {
/* 774 */               e.setDamage(EntityDamageEvent.DamageModifier.MAGIC, e
/* 775 */                 .getDamage(EntityDamageEvent.DamageModifier.MAGIC) * damagePercent);
/*     */             }
/*     */             catch (Exception localException1) {}
/*     */             try {
/* 779 */               e.setDamage(EntityDamageEvent.DamageModifier.RESISTANCE, e
/* 780 */                 .getDamage(EntityDamageEvent.DamageModifier.RESISTANCE) * damagePercent);
/*     */             }
/*     */             catch (Exception localException2) {}
/*     */             try {
/* 784 */               e.setDamage(EntityDamageEvent.DamageModifier.BLOCKING, e
/* 785 */                 .getDamage(EntityDamageEvent.DamageModifier.BLOCKING) * damagePercent);
/*     */             }
/*     */             catch (Exception localException3) {}
/* 788 */             e.setDamage(EntityDamageEvent.DamageModifier.BASE, newDamage);
/* 789 */             break;
/*     */           }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
/* 796 */     String[] fullCommand = e.getMessage().split(" ");
/* 797 */     String command = fullCommand[0].substring(1);
/* 798 */     String[] args = new String[fullCommand.length - 1];
/* 799 */     if (fullCommand.length > 1) {
/* 800 */       for (int i = 1; i < fullCommand.length; i++) {
/* 801 */         args[(i - 1)] = fullCommand[i];
/*     */       }
/*     */     }
/* 804 */     if ((command.equalsIgnoreCase("f")) || (command.toLowerCase().startsWith("faction"))) {
/* 805 */       if (args.length < 1) {
/* 806 */         return;
/*     */       }
/* 808 */       if ((args[0].equalsIgnoreCase("sb")) || (args[0].equalsIgnoreCase("scoreboard"))) {
/* 809 */         e.setCancelled(true);
/* 810 */         e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission.");
/*     */       }
/* 812 */       else if (args[0].equalsIgnoreCase("home")) {
/* 813 */         if (e.isCancelled()) {
/* 814 */           return;
/*     */         }
/* 816 */         FPlayer fPlayer = FPlayers.getInstance().getByPlayer(e.getPlayer());
/* 817 */         if ((fPlayer.hasFaction()) && (fPlayer.getFaction().hasHome())) {
/* 818 */           this.mainPlugin.getCooldownManager().tryCooldown(e.getPlayer(), this.HOME_SCORE, 10500L, false, true, true);
/*     */         }
/*     */       }
/* 821 */       else if (args[0].equalsIgnoreCase("tag")) {
/* 822 */         if (!this.mainPlugin.getCooldownManager().tryCooldown(e.getPlayer(), "/f tag", 10000L, true, false, false))
/*     */         {
/* 824 */           e.setCancelled(true);
/*     */         }
/*     */       } else {
/* 827 */         if (args[0].equalsIgnoreCase("top")) {
/* 828 */           e.setCancelled(true);
/* 829 */           return; }
/* 830 */         if (args[0].equals("kick")) {
/* 831 */           if (args.length > 1) {
/* 832 */             Player player = this.mainPlugin.getServer().getPlayer(args[1]);
/*     */             
/* 834 */             if (player != null) {
/* 835 */               FPlayer fPlayer2 = FPlayers.getInstance().getByPlayer(player);
/* 836 */               FPlayer factionPlayer = FPlayers.getInstance().getByPlayer(e.getPlayer());
/* 837 */               if ((player != null) && (this.mainPlugin.getCombatListener().getCombatTime(player) != null) && 
/* 838 */                 (fPlayer2.hasFaction()) && (factionPlayer.hasFaction()) && 
/* 839 */                 (fPlayer2.getFactionId().equals(factionPlayer.getFactionId()))) {
/* 840 */                 e.setCancelled(true);
/* 841 */                 e.getPlayer()
/* 842 */                   .sendMessage(ChatColor.RED + "You cannot kick a player that is currently in combat.");
/*     */               }
/*     */             }
/*     */           }
/* 846 */         } else if ((args[0].equalsIgnoreCase("version")) || (args[0].equalsIgnoreCase("ver"))) {
/* 847 */           Location loc = new Location(Bukkit.getWorld("world"), 0.0D, 64.0D, 0.0D);
/*     */           
/* 849 */           e.setCancelled(true);
/* 850 */           e.getPlayer().sendMessage(
/* 851 */             ChatColor.translateAlternateColorCodes('&', "&bFactions made by drt, forked by Boys."));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onMove(PlayerMoveEvent e)
/*     */   {
/* 860 */     boolean moveX = e.getTo().getBlockX() != e.getFrom().getBlockX();
/* 861 */     boolean moveY = e.getTo().getBlockY() != e.getFrom().getBlockY();
/* 862 */     boolean moveZ = e.getTo().getBlockZ() != e.getFrom().getBlockZ();
/* 863 */     if (((moveX) || (moveY) || (moveZ)) && 
/* 864 */       (this.mainPlugin.getCooldownManager().getCooldown(e.getPlayer(), this.HOME_SCORE) > 0L)) {
/* 865 */       this.mainPlugin.getCooldownManager().removeCooldown(e.getPlayer(), this.HOME_SCORE);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayer(PlayerInteractEvent e) {
/* 871 */     Player p = e.getPlayer();
/* 872 */     if ((e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.BED_BLOCK)) {
/* 873 */       Block block = e.getClickedBlock();
/* 874 */       if (block.getLocation().getWorld().getName().contains("world_nether")) {
/* 875 */         p.sendMessage(ChatColor.RED + "You are not allowed to use a bed in the Nether!");
/* 876 */         e.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTeleport(org.bukkit.event.player.PlayerTeleportEvent e)
/*     */   {
/* 884 */     if (this.mainPlugin.getCooldownManager().getCooldown(e.getPlayer(), this.HOME_SCORE) > 0L) {
/* 885 */       this.mainPlugin.getCooldownManager().removeCooldown(e.getPlayer(), this.HOME_SCORE);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onChat(AsyncPlayerChatEvent e) {
/* 891 */     if (e.getMessage().toLowerCase().contains("stuck")) {
/* 892 */       if (this.mainPlugin.getCooldownManager().tryCooldown(e.getPlayer(), "Stuck", 5000L, false, false, false)) {
/* 893 */         e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIf you are currently stuck, then type &d/stuck&c."));
/*     */       }
/*     */       
/*     */     }
/* 897 */     else if ((e.getMessage().toLowerCase().contains("coords")) || (e.getMessage().toLowerCase().contains("cords"))) {
/* 898 */       if (this.mainPlugin.getCooldownManager().tryCooldown(e.getPlayer(), "Coords", 5000L, false, false, false)) {
/* 899 */         e.getPlayer().sendMessage(
/* 900 */           ChatColor.translateAlternateColorCodes('&', "&cUse &d/coords &cto view locations."));
/*     */       }
/* 902 */     } else if ((e.getMessage().toLowerCase().contains("bard")) && 
/* 903 */       (this.mainPlugin.getCooldownManager().tryCooldown(e.getPlayer(), "Bard", 5000L, false, false, false)))
/* 904 */       e.getPlayer().sendMessage(
/* 905 */         ChatColor.translateAlternateColorCodes('&', "&cUse &d/bard &cto learn about the bard kit."));
/*     */   }
/*     */   
/*     */   private void updateEffects(Player p) {
/* 909 */     for (ArmorKit armorKit : this.armorKitList) {
/* 910 */       if (armorKit.wearingArmor(p)) {
/* 911 */         if ((this.armorKits.containsKey(p)) && (!((ArmorKit)this.armorKits.get(p)).equals(armorKit))) {
/* 912 */           ((ArmorKit)this.armorKits.remove(p)).removeEffects(p);
/*     */         }
/* 914 */         if (this.armorKits.put(p, armorKit) == null) {
/* 915 */           p.sendMessage(io.louis.core.utils.C.c(Core.cfg2.getString("Messages.ClassEnabled").replace("{armorkit}", armorKit.getName())));
/* 916 */           PlayerScoreboard scoreboard = PlayerScoreboard.getScoreboard(p);
/* 917 */           if (scoreboard.getEntry("class") == null) {
/* 918 */             new Entry("class", scoreboard).setText(ChatColor.GOLD + "Class" + ChatColor.GRAY + ": " + ChatColor.RED + armorKit.getName()).send();
/*     */           } else {
/* 920 */             Entry entry = scoreboard.getEntry("class");
/* 921 */             entry.setText(ChatColor.GOLD + "Class" + ChatColor.GRAY + ": " + ChatColor.RED + armorKit.getName()).send();
/*     */           }
/*     */         }
/* 924 */         armorKit.addEffects(p);
/* 925 */         break;
/*     */       }
/* 927 */       if ((this.armorKits.containsKey(p)) && (((ArmorKit)this.armorKits.get(p)).equals(armorKit))) {
/* 928 */         ((ArmorKit)this.armorKits.remove(p)).removeEffects(p);
/* 929 */         p.sendMessage(io.louis.core.utils.C.c(Core.cfg2.getString("Messages.ClassDisabled").replace("{armorkit}", armorKit.getName())));
/* 930 */         PlayerScoreboard scoreboard = PlayerScoreboard.getScoreboard(p);
/* 931 */         Entry entry = scoreboard.getEntry("class");
/* 932 */         entry.setCancelled(true);
/* 933 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\PlayerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */