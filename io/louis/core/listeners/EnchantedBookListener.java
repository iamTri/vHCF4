/*    */ package io.louis.core.listeners;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import io.louis.core.utils.C;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.block.Action;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class EnchantedBookListener implements org.bukkit.event.Listener
/*    */ {
/*    */   public EnchantedBookListener(Core mainPlugin) {}
/*    */   
/*    */   @EventHandler
/*    */   public void onInt(PlayerInteractEvent e)
/*    */   {
/* 19 */     Player p = e.getPlayer();
/* 20 */     if ((p.getItemInHand().getType().equals(Material.ENCHANTED_BOOK)) && 
/* 21 */       (e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE)) && 
/* 22 */       (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
/* 23 */       e.setCancelled(true);
/* 24 */       p.getItemInHand().setType(Material.BOOK);
/* 25 */       p.sendMessage(C.c("&aYou have successfully removed all enchants from this book."));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\EnchantedBookListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */