/*     */ package io.louis.core.commands;
/*     */ 
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
/*     */ import org.bukkit.DyeColor;
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
/*     */ import org.bukkit.entity.ExperienceOrb;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.material.Dye;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class VanishCommand implements org.bukkit.command.CommandExecutor, Listener
/*     */ {
/*  51 */   public static ArrayList<String> Van = new ArrayList();
/*     */   
/*  53 */   public static ArrayList<Player> teleportList = new ArrayList();
/*     */   
/*  55 */   private static HashMap<String, ItemStack[]> armorContents = new HashMap();
/*     */   
/*  57 */   private static HashMap<String, ItemStack[]> inventoryContents = new HashMap();
/*     */   
/*  59 */   private static HashMap<String, Integer> xplevel = new HashMap();
/*     */   
/*  61 */   public static Map<Player, Player> examineTasks = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*  65 */   public static ArrayList<String> inVanish = new ArrayList();
/*     */   private static ItemStack playerTogglerOn;
/*     */   private static ItemStack playerTogglerOff;
/*     */   
/*  69 */   public static void saveInventory(Player p) { armorContents.put(p.getName(), p.getInventory().getArmorContents());
/*  70 */     inventoryContents.put(p.getName(), p.getInventory().getContents());
/*  71 */     xplevel.put(p.getName(), Integer.valueOf(p.getLevel()));
/*     */   }
/*     */   
/*     */   public static void loadInventory(Player p) {
/*  75 */     p.getInventory().clear();
/*     */     
/*  77 */     p.getInventory().setContents((ItemStack[])inventoryContents.get(p.getName()));
/*  78 */     p.getInventory().setArmorContents((ItemStack[])armorContents.get(p.getName()));
/*  79 */     p.setLevel(((Integer)xplevel.get(p.getName())).intValue());
/*     */     
/*  81 */     inventoryContents.remove(p.getName());
/*  82 */     armorContents.remove(p.getName());
/*  83 */     xplevel.remove(p.getName());
/*     */   }
/*     */   
/*     */   public static void leaveVanish(Player p) {
/*  87 */     Van.remove(p.getName());
/*  88 */     p.getInventory().clear();
/*  89 */     p.getInventory().setHelmet(null);
/*  90 */     p.getInventory().setChestplate(null);
/*  91 */     p.getInventory().setLeggings(null);
/*  92 */     p.getInventory().setBoots(null);
/*  93 */     loadInventory(p);
/*     */   }
/*     */   
/*     */   public static void vanishItems(Player p) {
/*  97 */     Inventory inv = p.getInventory();
/*  98 */     Dye dye = new Dye();
/*  99 */     dye.setColor(DyeColor.LIME);
/* 100 */     ItemStack greenDyeItem = dye.toItemStack(1);
/* 101 */     ItemMeta greenDyeMeta = greenDyeItem.getItemMeta();
/* 102 */     greenDyeMeta.setDisplayName("§bVanish: §aON");
/* 103 */     greenDyeItem.setItemMeta(greenDyeMeta);
/* 104 */     playerTogglerOn = greenDyeItem;
/* 105 */     dye.setColor(DyeColor.GRAY);
/* 106 */     ItemStack grayDyeItem = dye.toItemStack(1);
/* 107 */     ItemMeta grayDyeMeta = grayDyeItem.getItemMeta();
/* 108 */     grayDyeMeta.setDisplayName("§bVanish: §cOFF");
/* 109 */     grayDyeItem.setItemMeta(grayDyeMeta);
/* 110 */     playerTogglerOff = grayDyeItem;
/* 111 */     ItemStack compass = new ItemStack(Material.COMPASS);
/* 112 */     ItemStack book = new ItemStack(Material.BOOK);
/* 113 */     ItemStack tp = new ItemStack(Material.RECORD_5);
/* 114 */     ItemMeta compassMeta = compass.getItemMeta();
/* 115 */     ItemMeta bookMeta = book.getItemMeta();
/* 116 */     ItemMeta eggMeta = tp.getItemMeta();
/* 117 */     eggMeta.setDisplayName("§3Teleport to a random player");
/*     */     
/* 119 */     compassMeta.setDisplayName("§aZoom");
/* 120 */     bookMeta.setDisplayName("§dExaminerFAM");
/* 121 */     tp.setItemMeta(eggMeta);
/* 122 */     compass.setItemMeta(compassMeta);
/* 123 */     book.setItemMeta(bookMeta);
/*     */     
/* 125 */     inv.addItem(new ItemStack[] { compass });
/* 126 */     inv.addItem(new ItemStack[] { book });
/* 127 */     inv.setItem(8, playerTogglerOn);
/* 128 */     inv.setItem(2, tp);
/*     */   }
/*     */   
/*     */   public static void enterVanish(Player p) {
/* 132 */     Van.add(p.getName());
/* 133 */     saveInventory(p);
/* 134 */     p.getInventory().clear();
/* 135 */     p.getInventory().setHelmet(null);
/* 136 */     p.getInventory().setChestplate(null);
/* 137 */     p.getInventory().setLeggings(null);
/* 138 */     p.getInventory().setBoots(null);
/* 139 */     p.setGameMode(GameMode.CREATIVE);
/* 140 */     vanishItems(p);
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
/*     */   public String color(String msg)
/*     */   {
/* 153 */     return ChatColor.translateAlternateColorCodes('&', msg);
/*     */   }
/*     */   
/*     */   public static void updateExamination(Player examiner, Player examinee) {
/* 157 */     if (!examinee.isOnline()) {
/* 158 */       examiner.closeInventory();
/* 159 */       return;
/*     */     }
/* 161 */     Damageable dexaminee = examinee;
/* 162 */     double health = dexaminee.getHealth();
/* 163 */     Inventory inv = examiner.getOpenInventory().getTopInventory();
/* 164 */     ItemStack is; for (int i = 0; i < 36; i++) {
/* 165 */       is = examinee.getInventory().getItem(i);
/* 166 */       inv.setItem(i, is);
/*     */     }
/* 168 */     inv.setItem(36, examinee.getInventory().getHelmet());
/* 169 */     inv.setItem(37, examinee.getInventory().getChestplate());
/* 170 */     inv.setItem(38, examinee.getInventory().getLeggings());
/* 171 */     inv.setItem(39, examinee.getInventory().getBoots());
/*     */     
/* 173 */     inv.setItem(40, examinee.getItemInHand());
/* 174 */     for (int i = 0; i < 3; i++) {
/* 175 */       inv.setItem(41 + i, setItemName(new ItemStack(Material.STAINED_GLASS_PANE, 1), ChatColor.RED + ""));
/*     */     }
/* 177 */     inv.setItem(44, setItemName(new ItemStack(Material.FIREBALL, 1), ChatColor.RED + "Ban " + ChatColor.DARK_AQUA + examinee
/* 178 */       .getName()));
/*     */     
/* 180 */     inv.setItem(45, 
/* 181 */       setItemName(new ItemStack(Material.SPECKLED_MELON, (int)(health / 2.0D)), ChatColor.RED + "Health"));
/* 182 */     inv.setItem(46, 
/* 183 */       setItemName(new ItemStack(Material.COOKED_BEEF, examinee.getFoodLevel()), ChatColor.GOLD + "Hunger"));
/* 184 */     inv.setItem(47, setItemName(new ItemStack(Material.ROTTEN_FLESH, (int)examinee.getSaturation()), ChatColor.YELLOW + "Saturation"));
/*     */     
/* 186 */     inv.setItem(48, setItemName(new ItemStack(Material.DIAMOND_BARDING, 1), ChatColor.GRAY + "Is Riding Mob: " + ChatColor.BLUE + (examinee
/* 187 */       .getVehicle() != null ? "Yes" : "No")));
/*     */     
/* 189 */     ItemStack peItem = setItemName(new ItemStack(Material.BREWING_STAND_ITEM, examinee
/* 190 */       .getActivePotionEffects().size()), ChatColor.LIGHT_PURPLE + "Potion Effects");
/*     */     
/* 192 */     for (PotionEffect pe : examinee.getActivePotionEffects()) {
/* 193 */       addLore(peItem, ChatColor.GRAY + pe.getType().getName() + " " + (pe.getAmplifier() + 1) + ": " + pe
/* 194 */         .getDuration() / 20);
/*     */     }
/* 196 */     inv.setItem(49, peItem);
/*     */     
/* 198 */     inv.setItem(50, 
/* 199 */       setItemName(new ItemStack(Material.POTION, examinee.getLevel()), ChatColor.GREEN + "Experience Level"));
/* 200 */     inv.setItem(51, setItemName(new ItemStack(Material.WOOD_PICKAXE, 1), ChatColor.WHITE + "GameMode: " + ChatColor.AQUA + examinee
/* 201 */       .getGameMode().toString()));
/*     */     
/* 203 */     Location examineeLoc = examinee.getLocation();
/* 204 */     inv.setItem(52, setItemName(new ItemStack(Material.COMPASS, 1), String.format(ChatColor.DARK_GRAY + "X: %s, Y: %s, Z: %s", new Object[] {ChatColor.GRAY
/*     */     
/* 206 */       .toString() + examineeLoc.getBlockX() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 207 */       .toString() + examineeLoc.getBlockY() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 208 */       .toString() + examineeLoc.getBlockZ() + ChatColor.DARK_GRAY.toString() })));
/*     */     
/* 210 */     inv.setItem(53, 
/* 211 */       setItemName(new ItemStack(Material.BED, 1), ChatColor.GOLD + "IP Address: " + ChatColor.YELLOW + (examiner
/* 212 */       .hasPermission("staff.seeip") ? examinee
/* 213 */       .getAddress().getAddress().getHostAddress() : "Hidden")));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteract1(PlayerInteractEvent e) {
/* 218 */     if (((e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (e.getAction().equals(Action.RIGHT_CLICK_AIR))) && 
/* 219 */       (e.getPlayer().getItemInHand() != null) && 
/* 220 */       (e.getPlayer().getItemInHand().hasItemMeta()) && 
/* 221 */       (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()))
/*     */     {
/* 223 */       if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(color("&3Teleport to a random player"))) {
/* 224 */         if (e.getPlayer().hasPermission("command.tp")) {
/* 225 */           Random random = new Random();
/* 226 */           Player[] allPlayers = Bukkit.getServer().getOnlinePlayers();
/* 227 */           Player telPlayer = allPlayers[random.nextInt(allPlayers.length)];
/* 228 */           e.getPlayer().teleport(telPlayer);
/* 229 */           e.getPlayer().sendMessage(ChatColor.AQUA + "Teleported to: " + ChatColor.GREEN + telPlayer
/* 230 */             .getDisplayName());
/*     */         } else {
/* 232 */           e.getPlayer().sendMessage("Report that you have this object to staff");
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
/*     */   private static ItemStack setItemName(ItemStack itemStack, String name)
/*     */   {
/* 245 */     ItemMeta meta = itemStack.getItemMeta();
/* 246 */     meta.setDisplayName(name);
/* 247 */     itemStack.setItemMeta(meta);
/* 248 */     return itemStack;
/*     */   }
/*     */   
/*     */   private static ItemStack addLore(ItemStack itemStack, String lore) {
/* 252 */     ItemMeta meta = itemStack.getItemMeta();
/* 253 */     if (meta.getLore() != null) {
/* 254 */       meta.getLore().add(lore);
/*     */     } else {
/* 256 */       meta.setLore(Arrays.asList(new String[] { lore }));
/*     */     }
/* 258 */     itemStack.setItemMeta(meta);
/* 259 */     return itemStack;
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
/*     */   public void onEntityTargetPlayer(EntityTargetEvent e) {
/* 264 */     if (!(e.getTarget() instanceof Player)) {
/* 265 */       return;
/*     */     }
/* 267 */     Player target = (Player)e.getTarget();
/* 268 */     if (inVanish.contains(target)) {
/* 269 */       e.setCancelled(true);
/* 270 */       if ((e.getEntity() instanceof ExperienceOrb)) {
/* 271 */         repellExpOrb(target, (ExperienceOrb)e.getEntity());
/* 272 */         e.setTarget((Entity)null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void repellExpOrb(Player player, ExperienceOrb orb) {
/* 278 */     Location pLoc = player.getLocation();
/* 279 */     Location oLoc = orb.getLocation();
/* 280 */     Vector dir = oLoc.toVector().subtract(pLoc.toVector());
/* 281 */     double dx = Math.abs(dir.getX());
/* 282 */     double dz = Math.abs(dir.getZ());
/* 283 */     if ((dx == 0.0D) && (dz == 0.0D)) {
/* 284 */       dir.setX(0.001D);
/*     */     }
/* 286 */     if ((dx < 3.0D) && (dz < 3.0D)) {
/* 287 */       Vector nDir = dir.normalize();
/* 288 */       Vector newV = nDir.clone().multiply(0.3D);
/* 289 */       newV.setY(0);
/* 290 */       orb.setVelocity(newV);
/* 291 */       if ((dx < 1.0D) && (dz < 1.0D)) {
/* 292 */         orb.teleport(oLoc.clone().add(nDir.multiply(1.0D)));
/*     */       }
/* 294 */       if ((dx < 0.5D) && (dz < 0.5D)) {
/* 295 */         orb.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onInventoryClose(InventoryCloseEvent e) {
/* 302 */     if (examineTasks.containsKey(e.getPlayer())) {
/* 303 */       examineTasks.remove(e.getPlayer());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 308 */     if (!cmd.getName().equalsIgnoreCase("vanish")) {
/* 309 */       return false;
/*     */     }
/* 311 */     if (!sender.hasPermission("core.vanish")) {
/* 312 */       sender.sendMessage(ChatColor.RED + "No.");
/* 313 */       return true;
/*     */     }
/* 315 */     if (args.length < 1) {
/* 316 */       if (!(sender instanceof Player)) {
/* 317 */         sender.sendMessage(ChatColor.RED + "No.");
/* 318 */         return true;
/*     */       }
/* 320 */       if (inVanish.contains(sender.getName())) {
/* 321 */         inVanish.remove(sender.getName());
/*     */         Player[] onlinePlayers;
/* 323 */         int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length; for (int i = 0; i < length; i++) {
/* 324 */           Player online = onlinePlayers[i];
/* 325 */           online.showPlayer((Player)sender);
/*     */         }
/* 327 */         sender.sendMessage("§6You are now visible.");
/* 328 */         leaveVanish((Player)sender);
/* 329 */         return true;
/*     */       }
/* 331 */       inVanish.add(sender.getName());
/*     */       Player[] onlinePlayers2;
/* 333 */       int length2 = (onlinePlayers2 = Bukkit.getOnlinePlayers()).length; for (int j = 0; j < length2; j++) {
/* 334 */         Player online = onlinePlayers2[j];
/* 335 */         if (!online.hasPermission("staff.vanish")) {
/* 336 */           online.hidePlayer((Player)sender);
/*     */         }
/*     */       }
/*     */       
/* 340 */       sender.sendMessage("§6You are now invisible.");
/* 341 */       enterVanish((Player)sender);
/* 342 */       return true;
/*     */     }
/* 344 */     if ((!sender.hasPermission("Core.vanish.other")) && (!sender.hasPermission("Core.*"))) {
/* 345 */       sender.sendMessage(ChatColor.RED + "No.");
/* 346 */       return true;
/*     */     }
/* 348 */     Player t = Bukkit.getPlayer(args[0]);
/* 349 */     if (t == null) {
/* 350 */       sender.sendMessage("§6Could not find player §f" + args[0].toString() + "§6.");
/* 351 */       return true;
/*     */     }
/* 353 */     if (inVanish.contains(t.getName())) {
/* 354 */       inVanish.remove(t.getName());
/*     */       Player[] onlinePlayers3;
/* 356 */       int length3 = (onlinePlayers3 = Bukkit.getOnlinePlayers()).length; for (int k = 0; k < length3; k++) {
/* 357 */         Player online2 = onlinePlayers3[k];
/* 358 */         online2.showPlayer(t);
/*     */       }
/* 360 */       sender.sendMessage("§6You have made player §f" + t.getName() + " §6visibile.");
/* 361 */       t.sendMessage("§6You have been made visible.");
/* 362 */       leaveVanish(t);
/* 363 */       return true;
/*     */     }
/* 365 */     sender.sendMessage("§6You have made player §f" + t.getName() + " §6invisibile.");
/* 366 */     t.sendMessage("§6You have been made invisible.");
/* 367 */     inVanish.add(t.getName());
/*     */     Player[] onlinePlayers4;
/* 369 */     int length4 = (onlinePlayers4 = Bukkit.getOnlinePlayers()).length; for (int l = 0; l < length4; l++) {
/* 370 */       Player online2 = onlinePlayers4[l];
/* 371 */       if (!online2.hasPermission("staff.yes")) {
/* 372 */         online2.hidePlayer(t);
/* 373 */         enterVanish(t);
/*     */       }
/*     */     }
/* 376 */     return true;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void rightClick(PlayerInteractEntityEvent e)
/*     */   {
/* 382 */     if (!(e.getRightClicked() instanceof Player)) {
/* 383 */       return;
/*     */     }
/* 385 */     Player staff = e.getPlayer();
/* 386 */     Player p = (Player)e.getRightClicked();
/* 387 */     if ((inVanish.contains(staff.getName())) && ((p instanceof Player)) && ((staff instanceof Player)) && 
/* 388 */       (staff.getItemInHand().getType() == Material.BOOK)) {
/* 389 */       if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(color("&dExaminerFAM"))) {
/* 390 */         examinePlayer(e.getPlayer(), (Player)e.getRightClicked());
/* 391 */         examineTasks.put(e.getPlayer(), (Player)e.getRightClicked());
/*     */       }
/* 393 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/* 397 */   private static void examinePlayer(Player examiner, Player examinee) { Damageable dexaminee = examinee;
/* 398 */     double health = dexaminee.getHealth();
/* 399 */     Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "Examining: " + examinee.getName());
/* 400 */     ItemStack is; for (int i = 0; i < 36; i++) {
/* 401 */       is = examinee.getInventory().getItem(i);
/* 402 */       inv.setItem(i, is);
/*     */     }
/* 404 */     inv.setItem(36, examinee.getInventory().getHelmet());
/* 405 */     inv.setItem(37, examinee.getInventory().getChestplate());
/* 406 */     inv.setItem(38, examinee.getInventory().getLeggings());
/* 407 */     inv.setItem(39, examinee.getInventory().getBoots());
/*     */     
/* 409 */     inv.setItem(40, examinee.getItemInHand());
/* 410 */     for (int i = 0; i < 3; i++) {
/* 411 */       inv.setItem(41 + i, setItemName(new ItemStack(Material.STAINED_GLASS_PANE, 1), ChatColor.RED + ""));
/*     */     }
/* 413 */     inv.setItem(44, setItemName(new ItemStack(Material.FIREBALL, 1), ChatColor.RED + "Ban " + ChatColor.DARK_AQUA + examinee
/* 414 */       .getName()));
/*     */     
/* 416 */     inv.setItem(45, 
/* 417 */       setItemName(new ItemStack(Material.SPECKLED_MELON, (int)(health / 2.0D)), ChatColor.RED + "Health"));
/* 418 */     inv.setItem(46, 
/* 419 */       setItemName(new ItemStack(Material.COOKED_BEEF, examinee.getFoodLevel()), ChatColor.GOLD + "Hunger"));
/* 420 */     inv.setItem(47, setItemName(new ItemStack(Material.ROTTEN_FLESH, (int)examinee.getSaturation()), ChatColor.YELLOW + "Saturation"));
/*     */     
/* 422 */     inv.setItem(48, setItemName(new ItemStack(Material.DIAMOND_BARDING, 1), ChatColor.GRAY + "Is Riding Mob: " + ChatColor.BLUE + (examinee
/* 423 */       .getVehicle() != null ? "Yes" : "No")));
/*     */     
/* 425 */     ItemStack peItem = setItemName(new ItemStack(Material.BREWING_STAND_ITEM, examinee
/* 426 */       .getActivePotionEffects().size()), ChatColor.LIGHT_PURPLE + "Potion Effects");
/*     */     
/* 428 */     for (PotionEffect pe : examinee.getActivePotionEffects()) {
/* 429 */       addLore(peItem, ChatColor.GRAY + pe.getType().getName() + " " + (pe.getAmplifier() + 1) + ": " + pe
/* 430 */         .getDuration() / 20);
/*     */     }
/* 432 */     inv.setItem(49, peItem);
/*     */     
/* 434 */     inv.setItem(50, 
/* 435 */       setItemName(new ItemStack(Material.POTION, examinee.getLevel()), ChatColor.GREEN + "Experience Level"));
/* 436 */     inv.setItem(51, setItemName(new ItemStack(Material.WOOD_PICKAXE, 1), ChatColor.WHITE + "GameMode: " + ChatColor.AQUA + examinee
/* 437 */       .getGameMode().toString()));
/*     */     
/* 439 */     Location examineeLoc = examinee.getLocation();
/* 440 */     inv.setItem(52, setItemName(new ItemStack(Material.COMPASS, 1), String.format(ChatColor.DARK_GRAY + "X: %s, Y: %s, Z: %s", new Object[] {ChatColor.GRAY
/*     */     
/* 442 */       .toString() + examineeLoc.getBlockX() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 443 */       .toString() + examineeLoc.getBlockY() + ChatColor.DARK_GRAY.toString(), ChatColor.GRAY
/* 444 */       .toString() + examineeLoc.getBlockZ() + ChatColor.DARK_GRAY.toString() })));
/*     */     
/* 446 */     inv.setItem(53, 
/* 447 */       setItemName(new ItemStack(Material.BED, 1), ChatColor.GOLD + "IP Address: " + ChatColor.YELLOW + (examiner
/* 448 */       .hasPermission("staff.seeip") ? examinee
/* 449 */       .getAddress().getAddress().getHostAddress() : "Hidden")));
/*     */     
/* 451 */     examiner.openInventory(inv);
/*     */   }
/*     */   
/* 454 */   public static void startExamineUpdateTask() { Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.pl, new Runnable()
/*     */     {
/*     */       public void run() {
/* 457 */         for (Map.Entry<Player, Player> entry : VanishCommand.examineTasks.entrySet())
/* 458 */           VanishCommand.updateExamination((Player)entry.getKey(), (Player)entry.getValue()); } }, 10L, 10L); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @EventHandler
/*     */   public void chestInspector(PlayerInteractEvent e)
/*     */   {
/* 466 */     Player staff = e.getPlayer();
/* 467 */     if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
/* 468 */       (e.getPlayer().hasPermission("staff.yes")) && 
/* 469 */       (inVanish.contains(staff.getName())) && (
/* 470 */       (e.getClickedBlock().getType().equals(Material.CHEST)) || 
/* 471 */       (e.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) || 
/* 472 */       (e.getClickedBlock().getType().equals(Material.DISPENSER)) || 
/* 473 */       (e.getClickedBlock().getType().equals(Material.DROPPER)))) {
/* 474 */       Inventory inv = ((Chest)e.getClickedBlock().getState()).getInventory();
/* 475 */       Inventory fakeInv = Bukkit.createInventory(null, inv.getSize(), inv.getTitle());
/* 476 */       fakeInv.setContents(inv.getContents());
/*     */       
/* 478 */       e.getPlayer().openInventory(fakeInv);
/* 479 */       staff.sendMessage(ChatColor.RED + "Opening the chest silently.");
/* 480 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @EventHandler
/*     */   public void onPickup(PlayerPickupItemEvent e)
/*     */   {
/* 489 */     Player staff = e.getPlayer();
/* 490 */     if (Van.contains(staff.getName())) {
/* 491 */       e.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onJoinVanish(PlayerJoinEvent e) {
/* 497 */     Player p = e.getPlayer();
/*     */     Player[] onlinePlayers;
/* 499 */     int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length; for (int i = 0; i < length; i++) {
/* 500 */       Player online = onlinePlayers[i];
/* 501 */       if (inVanish.contains(online.getName())) {
/* 502 */         p.hidePlayer(online);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\VanishCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */