/*     */ package io.louis.core.utils;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class Utils extends org.bukkit.plugin.java.JavaPlugin
/*     */ {
/*     */   static Core main;
/*     */   public static World world;
/*     */   public static Core plugin;
/*     */   public static World nether;
/*     */   public static org.bukkit.Server server;
/*     */   
/*     */   public static void Initialize(Core plugin)
/*     */   {
/*  23 */     plugin = plugin;
/*  24 */     server = plugin.getServer();
/*  25 */     world = (World)server.getWorlds().get(0);
/*     */   }
/*     */   
/*     */   public static void sendBoldMessage(Player p, ChatColor ch, String msg) {
/*  29 */     p.sendMessage(ChatColor.GRAY + ch + ChatColor.BOLD + msg);
/*     */   }
/*     */   
/*     */   public static void give(Player p, Material item, int amount) {
/*  33 */     p.getInventory().addItem(new org.bukkit.inventory.ItemStack[] { new org.bukkit.inventory.ItemStack(item, amount) });
/*     */   }
/*     */   
/*     */   public static void build(Location loc1, Location loc2, World world, Material block) {
/*  37 */     int minx = Math.min(loc1.getBlockX(), loc2.getBlockX());
/*  38 */     int miny = Math.min(loc1.getBlockY(), loc2.getBlockY());
/*  39 */     int minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
/*  40 */     int maxx = Math.max(loc1.getBlockX(), loc2.getBlockX());
/*  41 */     int maxy = Math.max(loc1.getBlockY(), loc2.getBlockY());
/*  42 */     int maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
/*  43 */     for (int x = minx; x <= maxx; x++) {
/*  44 */       for (int y = miny; y <= maxy; y++) {
/*  45 */         for (int z = minz; z <= maxz; z++) {
/*  46 */           org.bukkit.block.Block b = world.getBlockAt(x, y, z);
/*  47 */           b.setType(block);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void removepots(Player p) {
/*  54 */     for (org.bukkit.potion.PotionEffect effects : p.getActivePotionEffects())
/*  55 */       p.removePotionEffect(effects.getType());
/*     */   }
/*     */   
/*     */   public static void tp(Player p, int x, int y, int z) {
/*  59 */     p.teleport(new Location(p.getWorld(), x, y, z));
/*     */   }
/*     */   
/*     */   public static void spawn(Player p) {
/*  63 */     p.teleport(p.getWorld().getSpawnLocation());
/*     */   }
/*     */   
/*     */   public static String getMessage(String[] args, int start) {
/*  67 */     StringBuilder stringBuilder = new StringBuilder();
/*  68 */     for (int i = start; i < args.length; i++) {
/*  69 */       if (i != start) {
/*  70 */         stringBuilder.append(" ");
/*     */       }
/*  72 */       stringBuilder.append(args[i]);
/*     */     }
/*  74 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public static void clearinv(Player p) {
/*  78 */     p.getInventory().clear();
/*  79 */     p.getInventory().setArmorContents(new org.bukkit.inventory.ItemStack[4]);
/*     */   }
/*     */   
/*     */   public static void addPotion(Player p, PotionEffectType potion, int time, int level) {
/*  83 */     int strength = level - 1;
/*  84 */     p.addPotionEffect(new org.bukkit.potion.PotionEffect(potion, time, strength));
/*     */   }
/*     */   
/*     */   public static void delPotion(Player p, PotionEffectType potion) {
/*  88 */     p.removePotionEffect(potion);
/*     */   }
/*     */   
/*     */   public static long freeMem() {
/*  92 */     return Runtime.getRuntime().freeMemory() / 1024L / 1024L;
/*     */   }
/*     */   
/*     */   public static long maxMem() {
/*  96 */     return Runtime.getRuntime().maxMemory() / 1024L / 1024L;
/*     */   }
/*     */   
/*     */   public static long totalMem() {
/* 100 */     return Runtime.getRuntime().totalMemory() / 1024L / 1024L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void addToList(String str, List<String> list)
/*     */   {
/* 108 */     list.add(str);
/*     */   }
/*     */   
/*     */   public static void msg(Player p, ChatColor ch, String msg) {
/* 112 */     p.sendMessage(ch + msg);
/*     */   }
/*     */   
/*     */   public static void forcesay(Player p, String msg) {
/* 116 */     p.chat(msg);
/*     */   }
/*     */   
/*     */   public static void kick(Player p, String msg) {
/* 120 */     p.kickPlayer(msg);
/*     */   }
/*     */   
/*     */   public static void getname(Player p) {
/* 124 */     p.getDisplayName();
/*     */   }
/*     */   
/*     */   public static void heal(Player p) {
/* 128 */     p.setSaturation(2.14748365E9F);
/* 129 */     p.setFoodLevel(20);
/* 130 */     p.setHealth(20.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\utils\Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */