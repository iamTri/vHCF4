/*    */ package io.louis.core.listeners;
/*    */ 
/*    */ import org.bukkit.event.inventory.BrewEvent;
/*    */ import org.bukkit.inventory.BrewerInventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.potion.PotionType;
/*    */ 
/*    */ public class PotionListener implements org.bukkit.event.Listener
/*    */ {
/*    */   @org.bukkit.event.EventHandler
/*    */   public void onBrew(BrewEvent e)
/*    */   {
/* 13 */     switch (e.getContents().getIngredient().getType()) {
/*    */     case BLAZE_POWDER: 
/*    */     case GHAST_TEAR: 
/* 16 */       e.setCancelled(true);
/* 17 */       break;
/*    */     
/*    */     case GLOWSTONE_DUST: 
/*    */     case REDSTONE: 
/* 21 */       if (contains(e.getContents().getContents(), PotionType.POISON)) {
/* 22 */         e.setCancelled(true);
/*    */ 
/*    */       }
/* 25 */       else if (contains(e.getContents().getContents(), new ItemStack(org.bukkit.Material.POTION)))
/* 26 */         e.setCancelled(true);
/* 27 */       break;
/*    */     
/*    */ 
/*    */ 
/*    */     case FERMENTED_SPIDER_EYE: 
/* 32 */       if (contains(e.getContents().getContents(), new ItemStack(org.bukkit.Material.POTION))) {
/* 33 */         e.setCancelled(true);
/*    */ 
/*    */       }
/* 36 */       else if (contains(e.getContents().getContents(), PotionType.INSTANT_HEAL)) {
/* 37 */         e.setCancelled(true);
/*    */ 
/*    */       }
/* 40 */       else if (contains(e.getContents().getContents(), PotionType.POISON)) {
/* 41 */         e.setCancelled(true);
/*    */ 
/*    */       }
/* 44 */       else if (contains(e.getContents().getContents(), new ItemStack(org.bukkit.Material.POTION, 1, (short)16)))
/* 45 */         e.setCancelled(true);
/* 46 */       break;
/*    */     
/*    */ 
/*    */ 
/*    */     case SPECKLED_MELON: 
/* 51 */       if (contains(e.getContents().getContents(), new ItemStack(org.bukkit.Material.POTION))) {
/* 52 */         e.setCancelled(true);
/*    */       }
/*    */       break;
/*    */     }
/*    */     
/*    */   }
/*    */   
/*    */   private boolean contains(ItemStack[] toSearch, ItemStack toFind)
/*    */   {
/* 61 */     for (ItemStack itemStack : toSearch) {
/* 62 */       if ((itemStack != null) && 
/* 63 */         (toFind.isSimilar(itemStack))) {
/* 64 */         return true;
/*    */       }
/*    */     }
/*    */     
/* 68 */     return false;
/*    */   }
/*    */   
/*    */   private boolean contains(ItemStack[] toSearch, PotionType potionType) {
/* 72 */     for (ItemStack itemStack : toSearch) {
/*    */       try {
/* 74 */         org.bukkit.potion.Potion p = org.bukkit.potion.Potion.fromItemStack(itemStack);
/* 75 */         if (p.getType() == potionType) {
/* 76 */           return true;
/*    */         }
/*    */       }
/*    */       catch (Exception localException) {}
/*    */     }
/* 81 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\PotionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */