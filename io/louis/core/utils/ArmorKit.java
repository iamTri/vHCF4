/*    */ package io.louis.core.utils;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ public class ArmorKit implements org.bukkit.event.Listener
/*    */ {
/*    */   private String armorType;
/*    */   private String name;
/*    */   private java.util.Collection<PotionEffect> effects;
/*    */   
/*    */   public ArmorKit(String name, String armorType, PotionEffect... effects)
/*    */   {
/* 16 */     this.name = name.toString();
/* 17 */     this.armorType = armorType;
/* 18 */     this.effects = new java.util.HashSet(java.util.Arrays.asList(effects));
/*    */   }
/*    */   
/*    */   public boolean wearingArmor(Player p) {
/* 22 */     for (org.bukkit.inventory.ItemStack armor : p.getInventory().getArmorContents()) {
/* 23 */       if ((armor == null) || (!armor.getType().name().contains(this.armorType))) {
/* 24 */         return false;
/*    */       }
/*    */     }
/* 27 */     return true;
/*    */   }
/*    */   
/*    */   public void addEffects(Player p) {
/* 31 */     p.addPotionEffects(this.effects);
/*    */   }
/*    */   
/*    */   public void removeEffects(Player p) {
/* 35 */     for (Iterator localIterator1 = p.getActivePotionEffects().iterator(); localIterator1.hasNext();) { playerEffect = (PotionEffect)localIterator1.next();
/* 36 */       for (PotionEffect effect : this.effects) {
/* 37 */         if ((playerEffect.getType().getName().equals(effect.getType().getName())) && (playerEffect.getAmplifier() == effect.getAmplifier()))
/* 38 */           p.removePotionEffect(playerEffect.getType());
/*    */       }
/*    */     }
/*    */     PotionEffect playerEffect;
/*    */   }
/*    */   
/*    */   public String getArmorType() {
/* 45 */     return this.armorType;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 49 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\utils\ArmorKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */