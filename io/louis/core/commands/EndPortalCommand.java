/*     */ package io.louis.core.commands;
/*     */ 
/*     */ import com.massivecraft.factions.FLocation;
/*     */ import io.louis.core.Core;
/*     */ import java.util.Map;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class EndPortalCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*     */ {
/*     */   private Core mainPlugin;
/*     */   private final String ITEM_DISPLAYNAME;
/*     */   private Map<String, LocationPair> playerSelections;
/*     */   private io.louis.core.LanguageFile lf;
/*     */   
/*     */   public EndPortalCommand(Core mainPlugin)
/*     */   {
/*  29 */     this.ITEM_DISPLAYNAME = (ChatColor.RED + "Endportal Maker");
/*  30 */     this.mainPlugin = mainPlugin;
/*  31 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/*  32 */     this.lf = Core.getInstance().getLanguageFile();
/*  33 */     this.playerSelections = new java.util.HashMap();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteract(final PlayerInteractEvent e) {
/*  38 */     Player p = e.getPlayer();
/*  39 */     if ((e.hasItem()) && (e.getClickedBlock() != null)) {
/*  40 */       ItemStack itemStack = e.getItem();
/*  41 */       Block b = e.getClickedBlock();
/*  42 */       if ((itemStack.getItemMeta().hasDisplayName()) && (itemStack.getItemMeta().getDisplayName().equals(this.ITEM_DISPLAYNAME))) {
/*  43 */         FLocation fLocation = new FLocation(e.getPlayer().getLocation());
/*  44 */         com.massivecraft.factions.Faction checkFac = com.massivecraft.factions.Board.getInstance().getFactionAt(fLocation);
/*  45 */         if (checkFac.getTag().equalsIgnoreCase("WarZone"))
/*     */         {
/*  47 */           p.sendMessage(io.louis.core.utils.C.c("&cYou cannot do this in Warzone."));
/*  48 */           return;
/*     */         }
/*  50 */         LocationPair locationPair = (LocationPair)this.playerSelections.get(e.getPlayer().getName());
/*  51 */         if (locationPair == null) {
/*  52 */           locationPair = new LocationPair(null, null);
/*  53 */           this.playerSelections.put(e.getPlayer().getName(), locationPair);
/*     */         }
/*  55 */         if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
/*  56 */           if (b.getType() != Material.ENDER_PORTAL_FRAME)
/*     */           {
/*  58 */             e.getPlayer().sendMessage(ChatColor.RED + "You must select an end portal frame.");
/*  59 */             return;
/*     */           }
/*  61 */           locationPair.setFirstLoc(b.getLocation());
/*  62 */           e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully set the first location.");
/*     */ 
/*     */         }
/*  65 */         else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
/*  66 */           if (b.getType() != Material.ENDER_PORTAL_FRAME) {
/*  67 */             e.getPlayer().sendMessage(ChatColor.RED + "You must select an end portal frame.");
/*     */             
/*  69 */             return;
/*     */           }
/*  71 */           if (locationPair.getFirstLoc() == null) {
/*  72 */             e.getPlayer().sendMessage(ChatColor.RED + "Please set the first location (by left clicking the end portal frame).");
/*     */             
/*  74 */             return;
/*     */           }
/*  76 */           locationPair.setSecondLoc(b.getLocation());
/*  77 */           e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully set the second location.");
/*     */           
/*  79 */           Location firstLoc = locationPair.getFirstLoc();
/*  80 */           Location secondLoc = locationPair.getSecondLoc();
/*  81 */           if (firstLoc.distance(secondLoc) > 6.0D)
/*     */           {
/*  83 */             e.getPlayer().sendMessage(ChatColor.RED + "You cannot create an end portal that big.");
/*  84 */             return;
/*     */           }
/*  86 */           if (firstLoc.getBlockY() != secondLoc.getBlockY()) {
/*  87 */             e.getPlayer().sendMessage(ChatColor.RED + "Make sure that the portals have the same elevation.");
/*     */             
/*  89 */             return;
/*     */           }
/*  91 */           int minX = Math.min(firstLoc.getBlockX(), secondLoc.getBlockX());
/*  92 */           int minY = Math.min(firstLoc.getBlockY(), secondLoc.getBlockY());
/*  93 */           int minZ = Math.min(firstLoc.getBlockZ(), secondLoc.getBlockZ());
/*  94 */           int maxX = Math.max(firstLoc.getBlockX(), secondLoc.getBlockX());
/*  95 */           int maxY = Math.max(firstLoc.getBlockY(), secondLoc.getBlockY());
/*  96 */           int maxZ = Math.max(firstLoc.getBlockZ(), secondLoc.getBlockZ());
/*  97 */           for (int x = minX; x <= maxX; x++) {
/*  98 */             for (int y = minY; y <= maxY; y++) {
/*  99 */               for (int z = minZ; z <= maxZ; z++) {
/* 100 */                 Block block = b.getWorld().getBlockAt(x, y, z);
/* 101 */                 if (block.isEmpty()) {
/* 102 */                   block.setType(Material.ENDER_PORTAL);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 107 */           e.setCancelled(true);
/* 108 */           new org.bukkit.scheduler.BukkitRunnable() {
/*     */             public void run() {
/* 110 */               e.getPlayer().setItemInHand((ItemStack)null);
/* 111 */               e.getPlayer().updateInventory(); } }
/*     */           
/* 113 */             .runTask(this.mainPlugin);
/* 114 */           e.getPlayer().sendMessage(ChatColor.GREEN + "Created an end portal");
/*     */           
/* 116 */           this.playerSelections.remove(e.getPlayer().getName());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDrop(PlayerDropItemEvent e) {
/* 124 */     ItemStack itemStack = e.getItemDrop().getItemStack();
/* 125 */     if ((itemStack.getItemMeta().hasDisplayName()) && (itemStack.getItemMeta().getDisplayName().equals(this.ITEM_DISPLAYNAME))) {
/* 126 */       e.getItemDrop().remove();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent e) {
/* 132 */     this.playerSelections.remove(e.getPlayer().getName());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKick(PlayerKickEvent e) {
/* 137 */     this.playerSelections.remove(e.getPlayer().getName());
/*     */   }
/*     */   
/*     */   public boolean onCommand(org.bukkit.command.CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/* 141 */     if (!(s instanceof Player))
/*     */     {
/* 143 */       return true;
/*     */     }
/* 145 */     Player p = (Player)s;
/* 146 */     if (!p.hasPermission("core.portal"))
/*     */     {
/* 148 */       return true;
/*     */     }
/* 150 */     if (p.getInventory().firstEmpty() == -1)
/*     */     {
/* 152 */       return true;
/*     */     }
/* 154 */     ItemStack portalMaker = new ItemStack(Material.GOLD_SWORD);
/* 155 */     ItemMeta itemMeta = portalMaker.getItemMeta();
/* 156 */     itemMeta.setDisplayName(this.ITEM_DISPLAYNAME);
/* 157 */     portalMaker.setItemMeta(itemMeta);
/* 158 */     p.getInventory().addItem(new ItemStack[] { portalMaker });
/*     */     
/* 160 */     return true;
/*     */   }
/*     */   
/*     */   private class LocationPair
/*     */   {
/*     */     private Location firstLoc;
/*     */     private Location secondLoc;
/*     */     
/*     */     public LocationPair(Location firstLoc, Location secondLoc) {
/* 169 */       this.firstLoc = firstLoc;
/* 170 */       this.secondLoc = secondLoc;
/*     */     }
/*     */     
/*     */     public Location getFirstLoc() {
/* 174 */       return this.firstLoc;
/*     */     }
/*     */     
/*     */     public Location getSecondLoc() {
/* 178 */       return this.secondLoc;
/*     */     }
/*     */     
/*     */     public void setFirstLoc(Location firstLoc) {
/* 182 */       this.firstLoc = firstLoc;
/*     */     }
/*     */     
/*     */     public void setSecondLoc(Location secondLoc) {
/* 186 */       this.secondLoc = secondLoc;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\EndPortalCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */