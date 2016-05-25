/*    */ package io.louis.core.listeners;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.enchantments.EnchantmentTarget;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.PlayerDeathEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatTrakListener
/*    */   implements Listener
/*    */ {
/*    */   private Core plugin;
/*    */   
/*    */   public StatTrakListener(Core plugin)
/*    */   {
/* 30 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGH)
/*    */   public void onPlayerDeath(PlayerDeathEvent event) {
/* 35 */     Player player = event.getEntity();
/* 36 */     Player killer = player.getKiller();
/* 37 */     if (killer != null) {
/* 38 */       ItemStack stack = killer.getItemInHand();
/* 39 */       if ((stack != null) && (EnchantmentTarget.WEAPON.includes(stack))) {
/* 40 */         addDeathLore(stack, player, killer);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   private void addDeathLore(ItemStack stack, Player player, Player killer) {
/* 46 */     ItemMeta meta = stack.getItemMeta();
/* 47 */     List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList(2);
/* 48 */     if ((lore.isEmpty()) || (!((String)lore.get(0)).startsWith(ChatColor.GOLD + ChatColor.BOLD.toString() + "Kills "))) {
/* 49 */       lore.add(0, ChatColor.GOLD + ChatColor.BOLD.toString() + "Kills " + ChatColor.RED + 1);
/*    */     }
/*    */     else {
/* 52 */       String killsString = ((String)lore.get(0)).replace(ChatColor.GOLD + ChatColor.BOLD.toString() + "Kills ", "").replace(ChatColor.YELLOW + "]", "");
/* 53 */       String meme = ChatColor.stripColor(killsString);
/* 54 */       Integer kills = Integer.valueOf(1);
/*    */       try {
/* 56 */         kills = Integer.valueOf(Integer.parseInt(meme));
/*    */       }
/*    */       catch (NumberFormatException e) {
/* 59 */         e.printStackTrace();
/*    */       }
/* 61 */       Integer killafteradd = Integer.valueOf(kills.intValue() + 1);
/* 62 */       lore.set(0, ChatColor.GOLD + ChatColor.BOLD.toString() + "Kills " + ChatColor.RED + killafteradd);
/*    */     }
/* 64 */     meta.setLore(lore.subList(0, Math.min(6, lore.size())));
/* 65 */     stack.setItemMeta(meta);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\StatTrakListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */