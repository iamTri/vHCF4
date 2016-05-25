/*     */ package io.louis.core.commands;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ 
/*     */ public class ViewKitCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*     */ {
/*     */   private Core mainPlugin;
/*     */   private java.io.File file;
/*     */   private FileConfiguration config;
/*     */   private Inventory viewKitInventory;
/*     */   private String invTitle;
/*     */   private Set<String> editMode;
/*     */   
/*     */   public ViewKitCommand(Core mainPlugin)
/*     */   {
/*  26 */     this.mainPlugin = mainPlugin;
/*  27 */     this.file = new java.io.File(this.mainPlugin.getDataFolder(), "viewkit.yml");
/*  28 */     if (!this.file.exists()) {
/*     */       try {
/*  30 */         this.file.createNewFile();
/*     */       }
/*     */       catch (IOException e) {
/*  33 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  36 */     this.config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(this.file);
/*  37 */     this.invTitle = (ChatColor.DARK_RED + "" + ChatColor.BOLD + "Map Kit");
/*  38 */     this.viewKitInventory = org.bukkit.Bukkit.createInventory((org.bukkit.inventory.InventoryHolder)null, 54, this.invTitle);
/*  39 */     if (this.config.contains("invItems")) {
/*  40 */       this.viewKitInventory.setContents((org.bukkit.inventory.ItemStack[])((java.util.List)this.config.get("invItems")).toArray(new org.bukkit.inventory.ItemStack[this.viewKitInventory.getSize()]));
/*     */     }
/*  42 */     this.editMode = new java.util.HashSet();
/*  43 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/*     */   }
/*     */   
/*     */   public void saveConfig() {
/*     */     try {
/*  48 */       this.config.save(this.file);
/*     */     }
/*     */     catch (IOException e) {
/*  51 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClose(InventoryCloseEvent e) {
/*  57 */     if (e.getInventory().getTitle().equals(this.invTitle)) {
/*  58 */       this.config.set("invItems", this.viewKitInventory.getContents());
/*  59 */       saveConfig();
/*     */     }
/*  61 */     this.editMode.remove(e.getPlayer().getName());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onClick(InventoryClickEvent e) {
/*  66 */     if ((e.getWhoClicked() instanceof Player)) {
/*  67 */       Player p = (Player)e.getWhoClicked();
/*  68 */       if (e.getInventory().getTitle().equals(this.invTitle)) {
/*  69 */         if (e.getSlotType() == org.bukkit.event.inventory.InventoryType.SlotType.OUTSIDE) {
/*  70 */           return;
/*     */         }
/*  72 */         if (!this.editMode.contains(p.getName())) {
/*  73 */           e.setCancelled(true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {
/*  81 */     this.editMode.remove(e.getPlayer().getName());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKick(org.bukkit.event.player.PlayerKickEvent e) {
/*  86 */     this.editMode.remove(e.getPlayer().getName());
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/*  90 */     if (!(s instanceof Player)) {
/*  91 */       s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/*  92 */       return true;
/*     */     }
/*  94 */     Player p = (Player)s;
/*  95 */     if (args.length == 0) {
/*  96 */       p.openInventory(this.viewKitInventory);
/*  97 */       return true;
/*     */     }
/*  99 */     if (!s.hasPermission("core.viewkit")) {
/* 100 */       p.sendMessage(ChatColor.RED + "No.");
/* 101 */       return true;
/*     */     }
/* 103 */     if (args[0].equalsIgnoreCase("edit")) {
/* 104 */       this.editMode.add(p.getName());
/* 105 */       p.openInventory(this.viewKitInventory);
/* 106 */       p.sendMessage(ChatColor.GREEN + "Add in the items you want to include or take out items to remove them in the inventory.");
/*     */     }
/* 108 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\ViewKitCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */