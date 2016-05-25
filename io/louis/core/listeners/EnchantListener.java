/*     */ package io.louis.core.listeners;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import io.louis.core.utils.C;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.enchantment.EnchantItemEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.event.inventory.InventoryType.SlotType;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.EnchantmentStorageMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantListener
/*     */   implements Listener
/*     */ {
/*     */   Core plugin;
/*     */   
/*     */   public EnchantListener(Core plugin)
/*     */   {
/*  31 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEnchantItem(EnchantItemEvent e) {
/*  36 */     Map<Enchantment, Integer> enchants = e.getEnchantsToAdd();
/*     */     try
/*     */     {
/*  39 */       for (String enchant : Core.cfg.getStringList("BlockedEnchants"))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*  44 */         String[] parse = enchant.split(":");
/*  45 */         Enchantment enchantment = Enchantment.getByName(parse[0]);
/*  46 */         int level = Integer.parseInt(parse[1]);
/*     */         
/*  48 */         if ((enchants.containsKey(enchantment)) && 
/*  49 */           (((Integer)enchants.get(enchantment)).intValue() > level)) {
/*  50 */           enchants.remove(enchantment);
/*  51 */           if (level > 0) {
/*  52 */             enchants.put(enchantment, Integer.valueOf(level));
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  62 */       Bukkit.broadcast(C.c("&4Your BlockedEnchants section of your config file has an error! Please double check it!"), "enchantlimiter.admin");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @EventHandler
/*     */   public void onInventoryClick(InventoryClickEvent e)
/*     */   {
/*  71 */     if ((e.getInventory().getType() == InventoryType.ANVIL) && 
/*  72 */       (e.getSlotType() == InventoryType.SlotType.RESULT)) {
/*  73 */       ItemStack item = e.getCurrentItem();
/*     */       try
/*     */       {
/*  76 */         for (String blockedEnchantments : Core.cfg.getStringList("BlockedEnchants"))
/*     */         {
/*  78 */           String[] parse = blockedEnchantments.split(":");
/*  79 */           Enchantment selectedEnchantment = Enchantment.getByName(parse[0]);
/*  80 */           int level = Integer.parseInt(parse[1]);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  86 */           if (item.getType() == Material.ENCHANTED_BOOK) {
/*  87 */             EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta)item.getItemMeta();
/*     */             
/*  89 */             if ((bookMeta.getStoredEnchants().containsKey(selectedEnchantment)) && 
/*  90 */               (((Integer)bookMeta.getStoredEnchants().get(selectedEnchantment)).intValue() > level)) {
/*  91 */               e.setCancelled(true);
/*  92 */               return;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 100 */           if ((item.getEnchantments().containsKey(selectedEnchantment)) && 
/* 101 */             (((Integer)item.getEnchantments().get(selectedEnchantment)).intValue() > level)) {
/* 102 */             e.setCancelled(true);
/* 103 */             return;
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 112 */         Bukkit.broadcast(C.c("&4Your BlockedEnchants section of your config file has an error! Please double check it!"), "enchantlimiter.admin");
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\EnchantListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */