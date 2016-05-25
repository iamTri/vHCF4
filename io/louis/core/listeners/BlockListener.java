/*     */ package io.louis.core.listeners;
/*     */ 
/*     */ import com.earth2me.essentials.User;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.CreatureSpawner;
/*     */ import org.bukkit.block.Sign;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.block.SignChangeEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.event.vehicle.VehicleEnterEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.PotionMeta;
/*     */ 
/*     */ public class BlockListener implements org.bukkit.event.Listener
/*     */ {
/*     */   private io.louis.core.Core mainPlugin;
/*     */   private ItemStack wrenchItem;
/*     */   private ItemStack potionItem;
/*     */   private java.util.Map<String, Long> cannotHit;
/*     */   
/*     */   public BlockListener(io.louis.core.Core mainPlugin)
/*     */   {
/*  36 */     this.mainPlugin = mainPlugin;
/*  37 */     this.wrenchItem = new ItemStack(Material.GOLD_HOE);
/*  38 */     ItemMeta itemMeta = this.wrenchItem.getItemMeta();
/*  39 */     itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Wrench");
/*  40 */     itemMeta.setLore(java.util.Arrays.asList(new String[] { ChatColor.GRAY + "Spawner Uses Left: " + ChatColor.AQUA + "2" + ChatColor.GRAY + "/" + ChatColor.AQUA + "2", ChatColor.GRAY + "Ender Frame Uses Left: " + ChatColor.AQUA + "8" + ChatColor.GRAY + "/" + ChatColor.AQUA + "8" }));
/*  41 */     this.wrenchItem.setItemMeta(itemMeta);
/*  42 */     this.potionItem = new ItemStack(Material.POTION);
/*  43 */     PotionMeta potionMeta = (PotionMeta)this.potionItem.getItemMeta();
/*  44 */     potionMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Miner's Potion");
/*  45 */     potionMeta.addCustomEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, 12000, 0), true);
/*  46 */     potionMeta.addCustomEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.FAST_DIGGING, 12000, 2), true);
/*  47 */     potionMeta.addCustomEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.FIRE_RESISTANCE, 12000, 0), true);
/*  48 */     this.potionItem.setItemMeta(potionMeta);
/*  49 */     this.cannotHit = new java.util.HashMap();
/*     */   }
/*     */   
/*     */   public boolean canHit(Player p, int delay) {
/*  53 */     Long playerTime = (Long)this.cannotHit.get(p.getName());
/*  54 */     if (playerTime == null) {
/*  55 */       return true;
/*     */     }
/*  57 */     long timeRemaining = System.currentTimeMillis() - playerTime.longValue();
/*  58 */     if (timeRemaining / 1000L > delay) {
/*  59 */       this.cannotHit.remove(p.getName());
/*  60 */       return true;
/*     */     }
/*  62 */     return false;
/*     */   }
/*     */   
/*     */   public void preventDamage(Player p) {
/*  66 */     this.cannotHit.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEnter(VehicleEnterEvent e) {
/*  71 */     if ((e.getEntered() instanceof Player)) {
/*  72 */       Player p = (Player)e.getEntered();
/*  73 */       if (!canHit(p, 1)) {
/*  74 */         e.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onClick(InventoryClickEvent e) {
/*  81 */     if (((e.getInventory() instanceof org.bukkit.inventory.AnvilInventory)) && (e.getRawSlot() == e.getView().convertSlot(e.getRawSlot())) && (e.getRawSlot() == 2)) {
/*  82 */       ItemStack itemStack = e.getCurrentItem();
/*  83 */       if ((itemStack != null) && (itemStack.getType() == Material.MOB_SPAWNER)) {
/*  84 */         e.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onPlace(BlockPlaceEvent e) {
/*  91 */     ItemStack inHand = e.getItemInHand();
/*  92 */     if ((inHand.getType() == Material.MOB_SPAWNER) && (e.getBlock().getType() == Material.MOB_SPAWNER)) {
/*  93 */       if ((e.getPlayer().getWorld().getEnvironment() == org.bukkit.World.Environment.NETHER) && (!e.getPlayer().isOp())) {
/*  94 */         e.setCancelled(true);
/*  95 */         e.getPlayer().sendMessage(ChatColor.RED + "You cannot place spawners in the nether.");
/*  96 */         return;
/*     */       }
/*  98 */       if ((inHand.getItemMeta().hasDisplayName()) && (inHand.getItemMeta().getDisplayName().startsWith(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD))) {
/*  99 */         String displayName = ChatColor.stripColor(inHand.getItemMeta().getDisplayName());
/* 100 */         String creatureSpawnerName = displayName.split(" Spawner")[0];
/* 101 */         CreatureSpawner creatureSpawner = (CreatureSpawner)e.getBlockPlaced().getState();
/* 102 */         creatureSpawner.setCreatureTypeByName(creatureSpawnerName);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onTeleport(PlayerTeleportEvent e) {
/* 109 */     if (e.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
/* 110 */       com.sk89q.worldguard.protection.managers.RegionManager regionManager = com.sk89q.worldguard.bukkit.WGBukkit.getRegionManager(e.getPlayer().getWorld());
/* 111 */       com.sk89q.worldguard.protection.ApplicableRegionSet regionSet = regionManager.getApplicableRegions(e.getPlayer().getLocation());
/* 112 */       for (Object localObject = regionSet.iterator(); ((Iterator)localObject).hasNext();) { region = (com.sk89q.worldguard.protection.regions.ProtectedRegion)((Iterator)localObject).next();
/* 113 */         if (region.getId().toLowerCase().contains("spawn"))
/*     */           return;
/*     */       }
/*     */       com.sk89q.worldguard.protection.regions.ProtectedRegion region;
/* 117 */       if ((!canHit(e.getPlayer(), 2)) || (isBlockedFromTeleporting(e.getTo().getBlock()))) {
/* 118 */         e.setCancelled(true);
/*     */       }
/*     */       else {
/* 121 */         localObject = org.bukkit.block.BlockFace.values();region = localObject.length; for (com.sk89q.worldguard.protection.regions.ProtectedRegion localProtectedRegion1 = 0; localProtectedRegion1 < region; localProtectedRegion1++) { org.bukkit.block.BlockFace blockFace = localObject[localProtectedRegion1];
/* 122 */           Block b = e.getTo().getBlock().getRelative(blockFace);
/* 123 */           if (isBlockedFromTeleporting(b)) {
/* 124 */             e.setCancelled(true);
/* 125 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isBlockedFromTeleporting(Block b) {
/* 133 */     switch (b.getType()) {
/*     */     case FENCE_GATE: 
/*     */     case FENCE: 
/*     */     case TRAP_DOOR: 
/*     */     case STAINED_GLASS_PANE: 
/*     */     case THIN_GLASS: 
/*     */     case WOOD_STAIRS: 
/*     */     case BRICK_STAIRS: 
/*     */     case COBBLESTONE_STAIRS: 
/* 142 */       return true;
/*     */     }
/*     */     
/* 145 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private void addItems(Player p, ItemStack... itemStacks)
/*     */   {
/* 151 */     java.util.HashMap<Integer, ItemStack> itemMap = p.getInventory().addItem(itemStacks);
/* 152 */     if (!itemMap.isEmpty()) {
/* 153 */       for (ItemStack itemStack : itemMap.values()) {
/* 154 */         p.getWorld().dropItem(p.getLocation(), itemStack);
/*     */       }
/* 156 */       p.sendMessage(ChatColor.RED + "Your inventory was full, and the leftover items were dropped onto the ground.");
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDamage(EntityDamageByEntityEvent e) {
/* 162 */     if ((e.getDamager() instanceof Player)) {
/* 163 */       Player d = (Player)e.getDamager();
/* 164 */       if (!canHit(d, 1)) {
/* 165 */         e.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
/*     */   public void onBreak(BlockBreakEvent e) {
/* 172 */     if (e.getBlock().getType().name().contains("ORE")) {
/* 173 */       int enchantLevel = e.getPlayer().getItemInHand().getEnchantmentLevel(org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS);
/* 174 */       if (enchantLevel > 0) {
/* 175 */         e.setExpToDrop(e.getExpToDrop() * (enchantLevel + 1));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onBlockGlitch(BlockBreakEvent e) {
/* 182 */     if (e.getBlock().getType() == Material.MOB_SPAWNER) {
/* 183 */       if ((e.getPlayer().getWorld().getEnvironment() == org.bukkit.World.Environment.NETHER) && (!e.getPlayer().isOp())) {
/* 184 */         e.setCancelled(true);
/* 185 */         e.getPlayer().sendMessage(ChatColor.RED + "You cannot break spawners in the nether.");
/*     */       }
/*     */     }
/* 188 */     else if ((e.getBlock().getType().isSolid()) && (!e.getBlock().getType().name().contains("LEAVES"))) {
/* 189 */       preventDamage(e.getPlayer());
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onInteract(PlayerInteractEvent e)
/*     */   {
/* 196 */     if (e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
/* 197 */       Block b = e.getClickedBlock();
/* 198 */       if ((b.getType() == Material.TRAP_DOOR) && (!com.sk89q.worldguard.bukkit.WGBukkit.getPlugin().canBuild(e.getPlayer(), b)) && (!e.getPlayer().isOp())) {
/* 199 */         e.setCancelled(true);
/*     */       }
/* 201 */       else if (((b.getType() == Material.TRAP_DOOR) || (b.getType() == Material.WOODEN_DOOR) || (b.getType() == Material.FENCE_GATE)) && (e.isCancelled())) {
/* 202 */         preventDamage(e.getPlayer());
/*     */       }
/* 204 */       else if (b.getType() == Material.BOAT) {
/* 205 */         e.setCancelled(true);
/*     */       }
/* 207 */       else if (e.hasItem()) {
/* 208 */         ItemStack inHand = e.getItem();
/* 209 */         if (inHand.getType() == Material.BOAT)
/*     */         {
/* 211 */           List<Block> blockList = e.getPlayer().getLineOfSight((java.util.HashSet)null, 5);
/* 212 */           for (Block block : blockList) {
/* 213 */             if (block.getType().name().contains("WATER")) {
/* 214 */               return;
/*     */             }
/*     */           }
/* 217 */           e.setCancelled(true);
/* 218 */           e.getPlayer().updateInventory();
/* 219 */           return;
/*     */         }
/* 221 */         if ((inHand.getItemMeta().hasDisplayName()) && (inHand.getItemMeta().getDisplayName().equals(this.wrenchItem.getItemMeta().getDisplayName()))) {
/* 222 */           e.setCancelled(true);
/* 223 */           if ((b.getType() == Material.MOB_SPAWNER) || (b.getType() == Material.ENDER_PORTAL_FRAME)) {
/* 224 */             if (!com.sk89q.worldguard.bukkit.WGBukkit.getRegionManager(b.getWorld()).getApplicableRegions(b.getLocation()).canBuild(com.sk89q.worldguard.bukkit.WGBukkit.getPlugin().wrapPlayer(e.getPlayer()))) {
/* 225 */               return;
/*     */             }
/* 227 */             com.massivecraft.factions.FPlayer fPlayer = com.massivecraft.factions.FPlayers.getInstance().getByPlayer(e.getPlayer());
/* 228 */             com.massivecraft.factions.Faction factionAtLocation = com.massivecraft.factions.Board.getInstance().getFactionAt(new com.massivecraft.factions.FLocation(e.getPlayer().getLocation()));
/* 229 */             String checkWilderness = ChatColor.stripColor(factionAtLocation.getTag());
/* 230 */             if ((fPlayer.isInOwnTerritory()) || (checkWilderness.equalsIgnoreCase("Wilderness")) || (factionAtLocation.isRaidable())) {
/* 231 */               int loreNumber = b.getType() != Material.MOB_SPAWNER ? 1 : 0;
/* 232 */               List<String> handLore = inHand.getItemMeta().getLore();
/* 233 */               String[] splitLore = ((String)handLore.get(loreNumber)).split(":");
/* 234 */               Integer usesLeft = Integer.valueOf(Integer.parseInt(ChatColor.stripColor(splitLore[1]).subSequence(1, 2).toString()) - 1);
/* 235 */               if (usesLeft.intValue() < 0) {
/* 236 */                 int checkOther = b.getType() == Material.MOB_SPAWNER ? 1 : 0;
/* 237 */                 String[] checkLore = ((String)handLore.get(checkOther)).split(":");
/* 238 */                 Integer otherUsesLeft = Integer.valueOf(Integer.parseInt(ChatColor.stripColor(checkLore[1]).subSequence(1, 2).toString()) - 1);
/* 239 */                 if (otherUsesLeft.intValue() < 0) {
/* 240 */                   e.getPlayer().setItemInHand((ItemStack)null);
/* 241 */                   e.getPlayer().updateInventory();
/*     */                 }
/* 243 */                 return;
/*     */               }
/* 245 */               if (b.getType() == Material.MOB_SPAWNER) {
/* 246 */                 CreatureSpawner mobSpawn = (CreatureSpawner)b.getState();
/* 247 */                 if ((e.getPlayer().getWorld().getEnvironment() == org.bukkit.World.Environment.NETHER) && (!e.getPlayer().isOp())) {
/* 248 */                   e.getPlayer().sendMessage(ChatColor.RED + "You cannot use the wrench on spawners in the nether.");
/* 249 */                   return;
/*     */                 }
/* 251 */                 ItemStack droppedSpawner = new ItemStack(Material.MOB_SPAWNER, 1, mobSpawn.getCreatureType().getTypeId());
/* 252 */                 ItemMeta droppedMeta = droppedSpawner.getItemMeta();
/* 253 */                 droppedMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + mobSpawn.getSpawnedType().getName() + " Spawner");
/* 254 */                 droppedSpawner.setItemMeta(droppedMeta);
/* 255 */                 addItems(e.getPlayer(), new ItemStack[] { droppedSpawner });
/* 256 */                 b.setType(Material.AIR);
/* 257 */                 splitLore[1] = (ChatColor.AQUA + "" + usesLeft + ChatColor.GRAY + "/" + ChatColor.AQUA + "2");
/*     */               }
/* 259 */               else if (b.getType() == Material.ENDER_PORTAL_FRAME) {
/* 260 */                 ItemStack itemStack = new ItemStack(b.getType());
/* 261 */                 addItems(e.getPlayer(), new ItemStack[] { itemStack });
/* 262 */                 b.setType(Material.AIR);
/* 263 */                 splitLore[1] = (ChatColor.AQUA + "" + usesLeft + ChatColor.GRAY + "/" + ChatColor.AQUA + "8");
/*     */               }
/* 265 */               handLore.set(loreNumber, splitLore[0] + ": " + splitLore[1]);
/* 266 */               ItemMeta itemMeta = inHand.getItemMeta();
/* 267 */               itemMeta.setLore(handLore);
/* 268 */               inHand.setItemMeta(itemMeta);
/* 269 */               e.getPlayer().updateInventory();
/*     */             }
/*     */             else {
/* 272 */               e.getPlayer().sendMessage(ChatColor.RED + "You can only mine spawners within your own territory.");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onSignChange(SignChangeEvent e) {
/* 282 */     String firstLine = e.getLine(0);
/* 283 */     if ((firstLine.equalsIgnoreCase("[wrench]")) || (firstLine.equalsIgnoreCase("[potion]"))) {
/* 284 */       if (!e.getPlayer().hasPermission("wrench.create")) {
/* 285 */         e.setCancelled(true);
/* 286 */         e.getBlock().breakNaturally();
/* 287 */         return;
/*     */       }
/*     */       try {
/* 290 */         Integer price = Integer.valueOf(Integer.parseInt(e.getLine(1)));
/* 291 */         e.setLine(0, ChatColor.DARK_BLUE + "[Buy]");
/* 292 */         e.setLine(1, "1");
/* 293 */         e.setLine(2, firstLine.equalsIgnoreCase("[wrench]") ? "Wrench" : "Miner's Potion");
/* 294 */         e.setLine(3, "$" + price);
/* 295 */         e.getPlayer().sendMessage(ChatColor.GREEN + "Wrench sign successfully made.");
/*     */       }
/*     */       catch (Exception ex) {
/* 298 */         e.getBlock().breakNaturally();
/* 299 */         e.getPlayer().sendMessage(ChatColor.RED + "You must enter a valid price, using just numbers. (Format will be added in after adding it.)");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteractSign(PlayerInteractEvent e) {
/* 306 */     if (e.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
/* 307 */       Block b = e.getClickedBlock();
/* 308 */       if ((b.getState() instanceof Sign)) {
/* 309 */         Sign s = (Sign)b.getState();
/* 310 */         if (!s.getLine(0).equals(ChatColor.DARK_BLUE + "[Buy]")) {
/* 311 */           return;
/*     */         }
/* 313 */         if ((s.getLine(2).equals("Wrench")) || (s.getLine(2).equals("Miner's Potion"))) {
/* 314 */           if (e.getPlayer().getInventory().firstEmpty() == -1) {
/* 315 */             e.getPlayer().sendMessage(ChatColor.RED + "Your inventory is currently full.");
/* 316 */             return;
/*     */           }
/* 318 */           Integer price = Integer.valueOf(Integer.parseInt(s.getLine(3).substring(1)));
/* 319 */           User user = this.mainPlugin.getEssentials().getUser(e.getPlayer());
/* 320 */           double currentMoney = user.getMoney().doubleValue();
/* 321 */           if (currentMoney >= price.intValue()) {
/*     */             try {
/* 323 */               user.setMoney(java.math.BigDecimal.valueOf(currentMoney - price.intValue()));
/* 324 */               if (s.getLine(2).equalsIgnoreCase("Wrench")) {
/* 325 */                 e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully bought a wrench.");
/* 326 */                 e.getPlayer().getInventory().addItem(new ItemStack[] { this.wrenchItem });
/* 327 */                 e.getPlayer().updateInventory();
/*     */               }
/* 329 */               else if (s.getLine(2).equalsIgnoreCase("Miner's Potion")) {
/* 330 */                 e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully bought a Miner's potion.");
/* 331 */                 e.getPlayer().getInventory().addItem(new ItemStack[] { this.potionItem });
/* 332 */                 e.getPlayer().updateInventory();
/*     */               }
/*     */             }
/*     */             catch (net.ess3.api.MaxMoneyException e2) {
/* 336 */               e2.printStackTrace();
/*     */             }
/*     */             
/*     */           } else {
/* 340 */             user.sendMessage(ChatColor.RED + "You do not have enough money to purchase this.");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\BlockListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */