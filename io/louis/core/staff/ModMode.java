/*     */ package io.louis.core.staff;
/*     */ 
/*     */ import com.alexandeh.glaedr.scoreboards.Entry;
/*     */ import com.alexandeh.glaedr.scoreboards.PlayerScoreboard;
/*     */ import io.louis.core.Core;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.Chest;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Damageable;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.ExperienceOrb;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class ModMode implements org.bukkit.event.Listener, org.bukkit.command.CommandExecutor
/*     */ {
/*  59 */   public static ArrayList<String> modMode = new ArrayList();
/*  60 */   public static ArrayList<Player> teleportList = new ArrayList();
/*  61 */   private static HashMap<String, ItemStack[]> armorContents = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*  65 */   private static HashMap<String, ItemStack[]> inventoryContents = new HashMap();
/*  66 */   private static HashMap<String, Integer> xplevel = new HashMap();
/*  67 */   public static Map<Player, Player> examineTasks = new HashMap();
/*     */   
/*     */   public void ModMode()
/*     */   {
/*  71 */     main = main;
/*     */   }
/*     */   
/*     */ 
/*  75 */   public static Map<Player, Player> followingPlayers = new HashMap();
/*  76 */   public static Map<Player, Location> playersLocations = new HashMap();
/*  77 */   public static Map<Player, GameMode> playersGms = new HashMap();
/*     */   private static Core main;
/*     */   
/*     */   public static void saveInventory(Player p) {
/*  81 */     armorContents.put(p.getName(), p.getInventory().getArmorContents());
/*  82 */     inventoryContents.put(p.getName(), p.getInventory().getContents());
/*  83 */     xplevel.put(p.getName(), Integer.valueOf(p.getLevel()));
/*     */   }
/*     */   
/*     */   public static void loadInventory(Player p) {
/*  87 */     p.getInventory().clear();
/*     */     
/*  89 */     p.getInventory().setContents((ItemStack[])inventoryContents.get(p.getName()));
/*  90 */     p.getInventory().setArmorContents((ItemStack[])armorContents.get(p.getName()));
/*  91 */     p.setLevel(((Integer)xplevel.get(p.getName())).intValue());
/*     */     
/*  93 */     inventoryContents.remove(p.getName());
/*  94 */     armorContents.remove(p.getName());
/*  95 */     xplevel.remove(p.getName());
/*  96 */     if (followingPlayers.containsKey(p)) {
/*  97 */       followingPlayers.remove(p);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void giveHat(Player player) {
/* 102 */     ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
/* 103 */     LeatherArmorMeta lam = (LeatherArmorMeta)lhelmet.getItemMeta();
/* 104 */     lam.setColor(org.bukkit.Color.GREEN);
/* 105 */     lhelmet.setItemMeta(lam);
/* 106 */     player.getInventory().setHelmet(lhelmet);
/*     */   }
/*     */   
/*     */   public static void modItems(Player p) {
/* 110 */     Inventory inv = p.getInventory();
/*     */     
/* 112 */     inv.clear();
/*     */     
/* 114 */     ItemStack compass = new ItemStack(Material.COMPASS);
/* 115 */     ItemStack book = new ItemStack(Material.BOOK);
/* 116 */     ItemStack chest = new ItemStack(Material.CHEST);
/* 117 */     ItemStack slime = new ItemStack(Material.SLIME_BALL);
/* 118 */     ItemStack tp = new ItemStack(Material.RECORD_5);
/* 119 */     ItemStack carpet = new ItemStack(Material.LEASH);
/*     */     
/* 121 */     ItemMeta compassMeta = compass.getItemMeta();
/* 122 */     ItemMeta bookMeta = book.getItemMeta();
/* 123 */     ItemMeta chestMeta = chest.getItemMeta();
/* 124 */     ItemMeta slimeMeta = slime.getItemMeta();
/* 125 */     ItemMeta eggMeta = tp.getItemMeta();
/* 126 */     ItemMeta carpetMeta = carpet.getItemMeta();
/*     */     
/* 128 */     compassMeta.setDisplayName("§aZoom");
/* 129 */     bookMeta.setDisplayName("§aExamine Book");
/* 130 */     chestMeta.setDisplayName("§aChest Inspector");
/* 131 */     slimeMeta.setDisplayName("§9Find a player (3 seconds switching)");
/* 132 */     eggMeta.setDisplayName("§9Teleport to a random player");
/* 133 */     carpetMeta.setDisplayName("§aFollower");
/*     */     
/* 135 */     compass.setItemMeta(compassMeta);
/* 136 */     book.setItemMeta(bookMeta);
/* 137 */     chest.setItemMeta(chestMeta);
/* 138 */     slime.setItemMeta(slimeMeta);
/* 139 */     tp.setItemMeta(eggMeta);
/* 140 */     carpet.setItemMeta(carpetMeta);
/*     */     
/* 142 */     inv.addItem(new ItemStack[] { compass });
/* 143 */     inv.addItem(new ItemStack[] { book });
/* 144 */     inv.addItem(new ItemStack[] { carpet });
/* 145 */     inv.setItem(7, slime);
/* 146 */     inv.setItem(8, tp);
/*     */   }
/*     */   
/*     */   public String color(String msg) {
/* 150 */     return ChatColor.translateAlternateColorCodes('&', msg);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteract1(PlayerInteractEvent e) {
/* 155 */     if (((e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (e.getAction().equals(Action.RIGHT_CLICK_AIR))) && 
/* 156 */       (e.getPlayer().getItemInHand() != null) && 
/* 157 */       (e.getPlayer().getItemInHand().hasItemMeta()) && 
/* 158 */       (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()))
/*     */     {
/* 160 */       if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(color("&9Teleport to a random player"))) {
/* 161 */         if (e.getPlayer().hasPermission("core.mod")) {
/* 162 */           Random random = new Random();
/* 163 */           Player[] allPlayers = Bukkit.getServer().getOnlinePlayers();
/* 164 */           Player telPlayer = allPlayers[random.nextInt(allPlayers.length)];
/* 165 */           e.getPlayer().teleport(telPlayer);
/* 166 */           e.getPlayer().sendMessage(ChatColor.AQUA + "Teleported to: " + ChatColor.GREEN + telPlayer
/* 167 */             .getDisplayName());
/*     */         } else {
/* 169 */           e.getPlayer().sendMessage("Report that you have this object to staff");
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
/*     */   public static void startFollowRelocateTask()
/*     */   {
/* 182 */     Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.pl, new Runnable()
/*     */     {
/*     */       public void run() {
/* 185 */         for (Map.Entry<Player, Player> entry : new HashMap(ModMode.followingPlayers)
/* 186 */           .entrySet())
/* 187 */           if (!((Player)entry.getValue()).isOnline()) {
/* 188 */             ModMode.followingPlayers.remove(entry.getKey());
/*     */           } else {
/* 190 */             Location loc = ((Player)entry.getValue()).getLocation();
/* 191 */             loc.setDirection(((Player)entry.getKey()).getLocation().getDirection());
/* 192 */             ((Player)entry.getKey()).teleport(loc); } } }, 10L, 10L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @EventHandler
/*     */   public void slimeInteract(PlayerInteractEvent e)
/*     */   {
/* 201 */     if (((e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (e.getAction().equals(Action.RIGHT_CLICK_AIR))) && 
/* 202 */       (e.getPlayer().getItemInHand() != null) && 
/* 203 */       (e.getPlayer().getItemInHand().hasItemMeta()) && 
/* 204 */       (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()))
/*     */     {
/* 206 */       if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(color("&9Find a player (3 seconds switching)"))) {
/* 207 */         if (e.getPlayer().hasPermission("core.mod")) {
/* 208 */           if (teleportList.contains(e.getPlayer())) {
/* 209 */             teleportList.remove(e.getPlayer());
/* 210 */             e.getPlayer()
/* 211 */               .sendMessage(ChatColor.AQUA + "You are not longer in the teleport list.");
/*     */           } else {
/* 213 */             e.getPlayer().sendMessage(ChatColor.AQUA + "You are in the teleport list now.");
/* 214 */             teleportList.add(e.getPlayer());
/*     */           }
/*     */         } else {
/* 217 */           e.getPlayer().sendMessage("Report that you have this object to staff");
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
/*     */   public static boolean isStaffChat(Player p)
/*     */   {
/* 230 */     if (io.louis.core.utils.Lists.staffChat.contains(p.getName())) {
/* 231 */       return true;
/*     */     }
/* 233 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isStaffMode(Player p)
/*     */   {
/* 238 */     if (modMode.contains(p.getName())) {
/* 239 */       return true;
/*     */     }
/* 241 */     return false;
/*     */   }
/*     */   
/*     */   public static void enterMod(Player p)
/*     */   {
/* 246 */     PlayerScoreboard scoreboard = PlayerScoreboard.getScoreboard(p);
/* 247 */     if (scoreboard.getEntry("mod") == null) {
/* 248 */       new Entry("mod", scoreboard).setText(ChatColor.BLUE.toString() + ChatColor.BOLD + "Staff Mode" + ChatColor.GRAY + ": " + ChatColor.GREEN + "True").send();
/* 249 */       new Entry("modextras", scoreboard).setText(ChatColor.GOLD.toString() + " » " + ChatColor.BLUE.toString() + "Chat Mode" + ChatColor.GRAY + ": " + (isStaffChat(p) ? ChatColor.RED + "Staff" : new StringBuilder().append(ChatColor.GREEN).append("Global").toString())).send();
/*     */     } else {
/* 251 */       Entry entry = scoreboard.getEntry("mod");
/* 252 */       entry.setText(ChatColor.BLUE.toString() + ChatColor.BOLD + "Staff Mode" + ChatColor.GRAY + ": " + ChatColor.GREEN + "True").send();
/* 253 */       new Entry("modextras", scoreboard).setText(ChatColor.GOLD.toString() + " » " + ChatColor.BLUE.toString() + "Chat Mode" + ChatColor.GRAY + ": " + (isStaffChat(p) ? ChatColor.RED + "Staff" : new StringBuilder().append(ChatColor.GREEN).append("Global").toString())).send();
/*     */     }
/* 255 */     modMode.add(p.getName());
/* 256 */     saveInventory(p);
/* 257 */     playersLocations.put(p, p.getLocation());
/* 258 */     playersGms.put(p, p.getGameMode());
/* 259 */     p.getInventory().clear();
/* 260 */     p.getInventory().setHelmet(null);
/* 261 */     p.getInventory().setChestplate(null);
/* 262 */     p.getInventory().setLeggings(null);
/* 263 */     p.getInventory().setBoots(null);
/* 264 */     p.setExp(0.0F);
/* 265 */     p.setGameMode(GameMode.CREATIVE);
/* 266 */     modItems(p);
/* 267 */     p.sendMessage("§eModerator Mode: §2Enabled!");
/*     */     Player[] arrayOfPlayer1;
/* 269 */     int j = (arrayOfPlayer1 = Bukkit.getOnlinePlayers()).length;
/* 270 */     for (int i = 0; i < j; i++) {
/* 271 */       Player online = arrayOfPlayer1[i];
/*     */       Player[] arrayOfPlayer2;
/* 273 */       int m = (arrayOfPlayer2 = Bukkit.getOnlinePlayers()).length;
/* 274 */       for (int k = 0; k < m; k++) {
/* 275 */         Player onlineStaff = arrayOfPlayer2[k];
/* 276 */         if ((onlineStaff.hasPermission("core.mod")) && (modMode.contains(onlineStaff.getName())) && 
/* 277 */           (!online.hasPermission("core.mod"))) {
/* 278 */           online.hidePlayer(onlineStaff);
/*     */         }
/*     */       }
/*     */     }
/* 282 */     if (followingPlayers.containsKey(p)) {
/* 283 */       followingPlayers.remove(p);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void leaveMod(Player p) {
/* 288 */     PlayerScoreboard scoreboard = PlayerScoreboard.getScoreboard(p);
/* 289 */     Entry entry = scoreboard.getEntry("mod");
/* 290 */     Entry staff = scoreboard.getEntry("modextras");
/* 291 */     entry.setText(ChatColor.BLUE.toString() + ChatColor.BOLD + "Staff Mode" + ChatColor.GRAY + ": " + ChatColor.RED + "False");
/* 292 */     staff.setCancelled(true);
/* 293 */     modMode.remove(p.getName());
/* 294 */     p.getInventory().clear();
/* 295 */     loadInventory(p);
/* 296 */     p.sendMessage("§eModerator Mode: §4Disabled!");
/* 297 */     for (Player online : Bukkit.getOnlinePlayers()) {
/* 298 */       if (p.hasPermission("core.mod")) {
/* 299 */         online.showPlayer(p);
/* 300 */         modMode.remove(p.getName());
/*     */       }
/*     */       
/* 303 */       if (followingPlayers.containsKey(p)) {
/* 304 */         followingPlayers.remove(p);
/*     */       }
/* 306 */       if ((!p.hasPermission("core.mod.relocate")) && 
/* 307 */         (playersLocations.containsKey(p))) {
/* 308 */         p.teleport((Location)playersLocations.get(p));
/*     */       }
/* 310 */       if (playersGms.containsKey(p)) {
/* 311 */         p.setGameMode((GameMode)playersGms.get(p));
/*     */       }
/* 313 */       playersLocations.remove(p);
/* 314 */       playersGms.remove(p);
/*     */     }
/* 316 */     p.setGameMode(GameMode.SURVIVAL);
/*     */   }
/*     */   
/*     */   public static void onDisableMod() {
/*     */     Player[] arrayOfPlayer;
/* 321 */     int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
/* 322 */     for (int i = 0; i < j; i++) {
/* 323 */       Player online = arrayOfPlayer[i];
/* 324 */       if (modMode.contains(online.getName())) {
/* 325 */         online.setMetadata("ModMode", new FixedMetadataValue(Core.pl, Integer.valueOf(1)));
/* 326 */         leaveMod(online);
/* 327 */         teleportList.remove(online);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void onEnableMod() {
/*     */     Player[] arrayOfPlayer;
/* 334 */     int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
/* 335 */     for (int i = 0; i < j; i++) {
/* 336 */       Player online = arrayOfPlayer[i];
/* 337 */       if (online.hasMetadata("ModMode")) {
/* 338 */         online.removeMetadata("ModMode", Core.pl);
/* 339 */         enterMod(online);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void rightClick(PlayerInteractEntityEvent e) {
/* 346 */     if (!(e.getRightClicked() instanceof Player)) {
/* 347 */       return;
/*     */     }
/* 349 */     Player staff = e.getPlayer();
/* 350 */     Player p = (Player)e.getRightClicked();
/* 351 */     if ((modMode.contains(staff.getName())) && (staff.getGameMode() == GameMode.CREATIVE) && ((p instanceof Player)) && ((staff instanceof Player)) && 
/* 352 */       (staff.getItemInHand().getType() == Material.BOOK)) {
/* 353 */       examinePlayer(e.getPlayer(), (Player)e.getRightClicked());
/* 354 */       examineTasks.put(e.getPlayer(), (Player)e.getRightClicked());
/*     */     }
/* 356 */     if ((modMode.contains(staff.getName())) && (e.getRightClicked().getType().equals(EntityType.PLAYER)) && 
/* 357 */       (e.getPlayer().getItemInHand().getType().equals(Material.LEASH))) {
/* 358 */       if (!followingPlayers.containsKey(e.getPlayer())) {
/* 359 */         followingPlayers.put(e.getPlayer(), (Player)e.getRightClicked());
/* 360 */         e.getPlayer().sendMessage(ChatColor.YELLOW + "You are now following " + ChatColor.DARK_AQUA + 
/* 361 */           ((Player)e.getRightClicked()).getName());
/*     */       } else {
/* 363 */         followingPlayers.remove(e.getPlayer());
/* 364 */         e.getPlayer().sendMessage(ChatColor.YELLOW + "You are no longer following " + ChatColor.DARK_AQUA + 
/* 365 */           ((Player)e.getRightClicked()).getName());
/*     */       }
/*     */     }
/* 368 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onInventoryClose(InventoryCloseEvent e) {
/* 373 */     if (examineTasks.containsKey(e.getPlayer())) {
/* 374 */       examineTasks.remove(e.getPlayer());
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
/*     */   public void onEntityTargetPlayer(EntityTargetEvent e) {
/* 380 */     if (!(e.getTarget() instanceof Player)) {
/* 381 */       return;
/*     */     }
/* 383 */     Player target = (Player)e.getTarget();
/* 384 */     if (modMode.contains(target)) {
/* 385 */       e.setCancelled(true);
/* 386 */       if ((e.getEntity() instanceof ExperienceOrb)) {
/* 387 */         repellExpOrb(target, (ExperienceOrb)e.getEntity());
/* 388 */         e.setTarget((Entity)null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 393 */   private void repellExpOrb(Player player, ExperienceOrb orb) { Location pLoc = player.getLocation();
/* 394 */     Location oLoc = orb.getLocation();
/* 395 */     Vector dir = oLoc.toVector().subtract(pLoc.toVector());
/* 396 */     double dx = Math.abs(dir.getX());
/* 397 */     double dz = Math.abs(dir.getZ());
/* 398 */     if ((dx == 0.0D) && (dz == 0.0D)) {
/* 399 */       dir.setX(0.001D);
/*     */     }
/* 401 */     if ((dx < 3.0D) && (dz < 3.0D)) {
/* 402 */       Vector nDir = dir.normalize();
/* 403 */       Vector newV = nDir.clone().multiply(0.3D);
/* 404 */       newV.setY(0);
/* 405 */       orb.setVelocity(newV);
/* 406 */       if ((dx < 1.0D) && (dz < 1.0D)) {
/* 407 */         orb.teleport(oLoc.clone().add(nDir.multiply(1.0D)));
/*     */       }
/* 409 */       if ((dx < 0.5D) && (dz < 0.5D)) {
/* 410 */         orb.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void chestInspector(PlayerInteractEvent e) {
/* 417 */     Player staff = e.getPlayer();
/* 418 */     if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
/* 419 */       (e.getPlayer().hasPermission("core.mod")) && 
/* 420 */       (modMode.contains(staff.getName())) && (
/* 421 */       (e.getClickedBlock().getType().equals(Material.CHEST)) || 
/* 422 */       (e.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) || 
/* 423 */       (e.getClickedBlock().getType().equals(Material.DISPENSER)) || 
/* 424 */       (e.getClickedBlock().getType().equals(Material.DROPPER)))) {
/* 425 */       Inventory inv = ((Chest)e.getClickedBlock().getState()).getInventory();
/* 426 */       Inventory fakeInv = Bukkit.createInventory(null, inv.getSize(), inv.getTitle());
/* 427 */       fakeInv.setContents(inv.getContents());
/*     */       
/* 429 */       e.getPlayer().openInventory(fakeInv);
/* 430 */       staff.sendMessage(ChatColor.RED + "Opening the chest silently.");
/* 431 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @EventHandler
/*     */   public void onJoin(PlayerJoinEvent e)
/*     */   {
/* 440 */     Player p = e.getPlayer();
/* 441 */     if (p.hasPermission("core.mod")) {
/* 442 */       enterMod(p);
/*     */     }
/* 444 */     if (!p.hasPermission("core.mod")) {
/*     */       Player[] arrayOfPlayer;
/* 446 */       int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
/* 447 */       for (int i = 0; i < j; i++) {
/* 448 */         Player online = arrayOfPlayer[i];
/* 449 */         if (modMode.contains(online.getName())) {
/* 450 */           p.hidePlayer(online);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void examinePlayer(Player examiner, Player examinee) {
/* 457 */     Damageable dexaminee = examinee;
/* 458 */     double health = dexaminee.getHealth();
/* 459 */     Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "Examining: " + examinee.getName());
/* 460 */     ItemStack is; for (int i = 0; i < 36; i++) {
/* 461 */       is = examinee.getInventory().getItem(i);
/* 462 */       inv.setItem(i, is);
/*     */     }
/* 464 */     inv.setItem(36, examinee.getInventory().getHelmet());
/* 465 */     inv.setItem(37, examinee.getInventory().getChestplate());
/* 466 */     inv.setItem(38, examinee.getInventory().getLeggings());
/* 467 */     inv.setItem(39, examinee.getInventory().getBoots());
/*     */     
/* 469 */     inv.setItem(40, examinee.getItemInHand());
/* 470 */     for (int i = 0; i < 3; i++) {
/* 471 */       inv.setItem(41 + i, setItemName(new ItemStack(Material.STAINED_GLASS_PANE, 1), ChatColor.RED + ""));
/*     */     }
/* 473 */     inv.setItem(44, setItemName(new ItemStack(Material.FIREBALL, 1), ChatColor.RED + "Ban " + ChatColor.DARK_AQUA + examinee
/* 474 */       .getName()));
/*     */     
/* 476 */     inv.setItem(45, 
/* 477 */       setItemName(new ItemStack(Material.SPECKLED_MELON, (int)health), ChatColor.RED + "Health"));
/* 478 */     inv.setItem(46, 
/* 479 */       setItemName(new ItemStack(Material.COOKED_BEEF, examinee.getFoodLevel()), ChatColor.GOLD + "Hunger"));
/* 480 */     inv.setItem(47, setItemName(new ItemStack(Material.ROTTEN_FLESH, (int)examinee.getSaturation()), ChatColor.YELLOW + "Saturation"));
/*     */     
/* 482 */     inv.setItem(48, setItemName(new ItemStack(Material.DIAMOND_BARDING, 1), ChatColor.GRAY + "Is Riding Mob: " + ChatColor.BLUE + (examinee
/* 483 */       .getVehicle() != null ? "Yes" : "No")));
/*     */     
/* 485 */     ItemStack peItem = setItemName(new ItemStack(Material.BREWING_STAND_ITEM, examinee
/* 486 */       .getActivePotionEffects().size()), ChatColor.LIGHT_PURPLE + "Potion Effects");
/*     */     
/* 488 */     for (PotionEffect pe : examinee.getActivePotionEffects()) {
/* 489 */       addLore(peItem, ChatColor.GRAY + pe.getType().getName() + " " + (pe.getAmplifier() + 1) + ": " + pe
/* 490 */         .getDuration() / 20);
/*     */     }
/* 492 */     inv.setItem(49, peItem);
/*     */     
/* 494 */     inv.setItem(50, 
/* 495 */       setItemName(new ItemStack(Material.POTION, examinee.getLevel()), ChatColor.GREEN + "Experience Level"));
/* 496 */     inv.setItem(51, setItemName(new ItemStack(Material.WOOD_PICKAXE, 1), ChatColor.WHITE + "GameMode: " + ChatColor.AQUA + examinee
/* 497 */       .getGameMode().toString()));
/*     */     
/* 499 */     Location examineeLoc = examinee.getLocation();
/* 500 */     inv.setItem(52, setItemName(new ItemStack(Material.COMPASS, 1), String.format(ChatColor.DARK_GRAY + "X: %s, Y: %s, Z: %s", new Object[] {ChatColor.GRAY
/*     */     
/* 502 */       .toString() + examineeLoc.getBlockX() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 503 */       .toString() + examineeLoc.getBlockY() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 504 */       .toString() + examineeLoc.getBlockZ() + ChatColor.DARK_GRAY.toString() })));
/*     */     
/* 506 */     inv.setItem(53, 
/* 507 */       setItemName(new ItemStack(Material.BED, 1), ChatColor.GOLD + "IP Address: " + ChatColor.YELLOW + (examiner
/* 508 */       .hasPermission("core.mod.seeip") ? examinee
/* 509 */       .getAddress().getAddress().getHostAddress() : "Hidden")));
/*     */     
/* 511 */     examiner.openInventory(inv);
/*     */   }
/*     */   
/*     */   public static void startExamineUpdateTask() {
/* 515 */     Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.pl, new Runnable()
/*     */     {
/*     */       public void run() {
/* 518 */         for (Map.Entry<Player, Player> entry : ModMode.examineTasks.entrySet())
/* 519 */           ModMode.updateExamination((Player)entry.getKey(), (Player)entry.getValue()); } }, 10L, 10L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void updateExamination(Player examiner, Player examinee)
/*     */   {
/* 526 */     if (!examinee.isOnline()) {
/* 527 */       examiner.closeInventory();
/* 528 */       return;
/*     */     }
/* 530 */     Damageable dexaminee = examinee;
/* 531 */     double health = dexaminee.getHealth();
/* 532 */     Inventory inv = examiner.getOpenInventory().getTopInventory();
/* 533 */     ItemStack is; for (int i = 0; i < 36; i++) {
/* 534 */       is = examinee.getInventory().getItem(i);
/* 535 */       inv.setItem(i, is);
/*     */     }
/* 537 */     inv.setItem(36, examinee.getInventory().getHelmet());
/* 538 */     inv.setItem(37, examinee.getInventory().getChestplate());
/* 539 */     inv.setItem(38, examinee.getInventory().getLeggings());
/* 540 */     inv.setItem(39, examinee.getInventory().getBoots());
/*     */     
/* 542 */     inv.setItem(40, examinee.getItemInHand());
/* 543 */     for (int i = 0; i < 3; i++) {
/* 544 */       inv.setItem(41 + i, setItemName(new ItemStack(Material.STAINED_GLASS_PANE, 1), ChatColor.RED + ""));
/*     */     }
/* 546 */     inv.setItem(44, setItemName(new ItemStack(Material.FIREBALL, 1), ChatColor.RED + "Ban " + ChatColor.DARK_AQUA + examinee
/* 547 */       .getName()));
/*     */     
/* 549 */     inv.setItem(45, 
/* 550 */       setItemName(new ItemStack(Material.SPECKLED_MELON, (int)health), ChatColor.RED + "Health"));
/* 551 */     inv.setItem(46, 
/* 552 */       setItemName(new ItemStack(Material.COOKED_BEEF, examinee.getFoodLevel()), ChatColor.GOLD + "Hunger"));
/* 553 */     inv.setItem(47, setItemName(new ItemStack(Material.ROTTEN_FLESH, (int)examinee.getSaturation()), ChatColor.YELLOW + "Saturation"));
/*     */     
/* 555 */     inv.setItem(48, setItemName(new ItemStack(Material.DIAMOND_BARDING, 1), ChatColor.GRAY + "Is Riding Mob: " + ChatColor.BLUE + (examinee
/* 556 */       .getVehicle() != null ? "Yes" : "No")));
/*     */     
/* 558 */     ItemStack peItem = setItemName(new ItemStack(Material.BREWING_STAND_ITEM, examinee
/* 559 */       .getActivePotionEffects().size()), ChatColor.LIGHT_PURPLE + "Potion Effects");
/*     */     
/* 561 */     for (PotionEffect pe : examinee.getActivePotionEffects()) {
/* 562 */       addLore(peItem, ChatColor.GRAY + pe.getType().getName() + " " + (pe.getAmplifier() + 1) + ": " + pe
/* 563 */         .getDuration() / 20);
/*     */     }
/* 565 */     inv.setItem(49, peItem);
/*     */     
/* 567 */     inv.setItem(50, 
/* 568 */       setItemName(new ItemStack(Material.POTION, examinee.getLevel()), ChatColor.GREEN + "Experience Level"));
/* 569 */     inv.setItem(51, setItemName(new ItemStack(Material.WOOD_PICKAXE, 1), ChatColor.WHITE + "GameMode: " + ChatColor.AQUA + examinee
/* 570 */       .getGameMode().toString()));
/*     */     
/* 572 */     Location examineeLoc = examinee.getLocation();
/* 573 */     inv.setItem(52, setItemName(new ItemStack(Material.COMPASS, 1), String.format(ChatColor.DARK_GRAY + "X: %s, Y: %s, Z: %s", new Object[] {ChatColor.GRAY
/*     */     
/* 575 */       .toString() + examineeLoc.getBlockX() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 576 */       .toString() + examineeLoc.getBlockY() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 577 */       .toString() + examineeLoc.getBlockZ() + ChatColor.DARK_GRAY.toString() })));
/*     */     
/* 579 */     inv.setItem(53, 
/* 580 */       setItemName(new ItemStack(Material.BED, 1), ChatColor.GOLD + "IP Address: " + ChatColor.YELLOW + (examiner
/* 581 */       .hasPermission("core.seeip") ? examinee
/* 582 */       .getAddress().getAddress().getHostAddress() : "Hidden")));
/*     */   }
/*     */   
/*     */   private static ItemStack setItemName(ItemStack itemStack, String name) {
/* 586 */     ItemMeta meta = itemStack.getItemMeta();
/* 587 */     meta.setDisplayName(name);
/* 588 */     itemStack.setItemMeta(meta);
/* 589 */     return itemStack;
/*     */   }
/*     */   
/*     */   private static ItemStack addLore(ItemStack itemStack, String lore) {
/* 593 */     ItemMeta meta = itemStack.getItemMeta();
/* 594 */     if (meta.getLore() != null) {
/* 595 */       meta.getLore().add(lore);
/*     */     } else {
/* 597 */       meta.setLore(Arrays.asList(new String[] { lore }));
/*     */     }
/* 599 */     itemStack.setItemMeta(meta);
/* 600 */     return itemStack;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTag(EntityDamageByEntityEvent e) {
/* 605 */     if ((!(e.getEntity() instanceof Player)) || (!(e.getDamager() instanceof Player))) {
/* 606 */       return;
/*     */     }
/* 608 */     Player staff = (Player)e.getDamager();
/* 609 */     if (modMode.contains(staff.getName())) {
/* 610 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPickup(PlayerPickupItemEvent e) {
/* 616 */     Player staff = e.getPlayer();
/* 617 */     if (modMode.contains(staff.getName())) {
/* 618 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClick(InventoryClickEvent e) {
/* 624 */     Player p = (Player)e.getWhoClicked();
/* 625 */     if ((modMode.contains(p.getName())) && (p.getGameMode().equals(GameMode.CREATIVE)) && (!p.hasPermission("core.bypass"))) {
/* 626 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDrop(PlayerDropItemEvent e) {
/* 632 */     Player p = e.getPlayer();
/* 633 */     if ((modMode.contains(p.getName())) && (p.getGameMode().equals(GameMode.CREATIVE))) {
/* 634 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent e) {
/* 640 */     Player p = e.getPlayer();
/* 641 */     if ((p.hasPermission("core.mod")) && 
/* 642 */       (modMode.contains(p.getName()))) {
/* 643 */       leaveMod(p);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onCompass(PlayerInteractEvent e) {
/* 649 */     Player p = e.getPlayer();
/* 650 */     if ((p.getItemInHand().getType() == Material.COMPASS) && (!p.hasPermission("core.mod")) && (!modMode.contains(p.getName()))) {
/* 651 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBreak(BlockBreakEvent e) {
/* 657 */     Player p = e.getPlayer();
/* 658 */     if ((modMode.contains(p.getName())) && (!p.hasPermission("core.bypass"))) {
/* 659 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlace(BlockPlaceEvent e) {
/* 665 */     Player p = e.getPlayer();
/* 666 */     if ((modMode.contains(p.getName())) && (p.getGameMode().equals(GameMode.CREATIVE)) && (!p.hasPermission("core.bypass"))) {
/* 667 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 672 */     if (cmd.getName().equalsIgnoreCase("mod")) {
/* 673 */       if (!sender.hasPermission("core.mod")) {
/* 674 */         sender.sendMessage(ChatColor.RED + "No.");
/* 675 */         return true;
/*     */       }
/* 677 */       if (args.length < 1) {
/* 678 */         if (!(sender instanceof Player)) {
/* 679 */           sender.sendMessage(ChatColor.RED + "No Loot.");
/* 680 */           return true;
/*     */         }
/* 682 */         if (modMode.contains(sender.getName())) {
/* 683 */           leaveMod((Player)sender);
/* 684 */           return true;
/*     */         }
/* 686 */         enterMod((Player)sender);
/* 687 */         return true;
/*     */       }
/* 689 */       if (!sender.hasPermission("core.mod.others")) {
/* 690 */         sender.sendMessage(ChatColor.RED + "No.");
/* 691 */         return true;
/*     */       }
/* 693 */       Player t = Bukkit.getPlayer(args[0]);
/* 694 */       if (t == null) {
/* 695 */         sender.sendMessage("§6Could not find player §f" + args[0].toString() + "§6.");
/* 696 */         return true;
/*     */       }
/* 698 */       if (modMode.contains(t.getName())) {
/* 699 */         leaveMod(t);
/* 700 */         sender.sendMessage("§eModerator Mode: §4Disabled! §c(" + t.getName() + ")");
/* 701 */         return true;
/*     */       }
/* 703 */       enterMod(t);
/* 704 */       sender.sendMessage("§eModerator Mode: §2Enabled! §c(" + t.getName() + ")");
/* 705 */       return true;
/*     */     }
/* 707 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\staff\ModMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */