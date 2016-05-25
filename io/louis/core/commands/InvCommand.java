/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import java.util.Map;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.entity.PlayerDeathEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ 
/*    */ public class InvCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*    */ {
/*    */   private Core mainPlugin;
/*    */   private Map<java.util.UUID, InventorySet> rollbackInv;
/*    */   
/*    */   public InvCommand(Core mainPlugin)
/*    */   {
/* 19 */     this.mainPlugin = mainPlugin;
/* 20 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/* 21 */     this.rollbackInv = new java.util.HashMap();
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/* 25 */     if (!s.hasPermission("core.inv")) {
/* 26 */       s.sendMessage(ChatColor.RED + "No.");
/* 27 */       return true;
/*    */     }
/* 29 */     if (args.length != 1) {
/* 30 */       s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <player>");
/* 31 */       return true;
/*    */     }
/* 33 */     Player p = this.mainPlugin.getServer().getPlayer(args[0]);
/* 34 */     if (p == null) {
/* 35 */       s.sendMessage(ChatColor.RED + "Player is not online.");
/* 36 */       return true;
/*    */     }
/* 38 */     if (!this.rollbackInv.containsKey(p.getUniqueId())) {
/* 39 */       s.sendMessage(ChatColor.RED + p.getName() + " does not have a stored record of their inventory.");
/*    */     }
/*    */     else {
/* 42 */       s.sendMessage(ChatColor.GREEN + p.getName() + "'s inventory has been restored.");
/* 43 */       p.sendMessage(ChatColor.GREEN + "Your inventory was restored by " + s.getName() + ".");
/* 44 */       p.playSound(p.getLocation(), org.bukkit.Sound.NOTE_PLING, 100.0F, 100.0F);
/* 45 */       InventorySet invSet = (InventorySet)this.rollbackInv.get(p.getUniqueId());
/* 46 */       p.getInventory().setContents(invSet.getInv());
/* 47 */       p.getInventory().setArmorContents(invSet.getArmor());
/* 48 */       p.setLevel(invSet.getEXP().intValue());
/* 49 */       this.rollbackInv.remove(p.getUniqueId());
/*    */     }
/* 51 */     return true;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler(priority=org.bukkit.event.EventPriority.LOWEST)
/*    */   public void onDeath(PlayerDeathEvent e) {
/* 56 */     java.util.UUID entityUUID = this.mainPlugin.getUuidManager().getUUIDFromName(e.getEntity().getName());
/* 57 */     this.rollbackInv.put(entityUUID, new InventorySet(e.getEntity()));
/*    */   }
/*    */   
/*    */   public Map<java.util.UUID, InventorySet> getRollbackInv() {
/* 61 */     return this.rollbackInv;
/*    */   }
/*    */   
/*    */   public class InventorySet
/*    */   {
/*    */     private Player p;
/*    */     private ItemStack[] inv;
/*    */     private ItemStack[] armor;
/*    */     private Integer xp;
/*    */     
/*    */     public InventorySet(Player p) {
/* 72 */       this.p = p;
/* 73 */       this.inv = p.getInventory().getContents();
/* 74 */       this.armor = p.getInventory().getArmorContents();
/* 75 */       this.xp = Integer.valueOf(p.getLevel());
/*    */     }
/*    */     
/*    */     public Player getP() {
/* 79 */       return this.p;
/*    */     }
/*    */     
/*    */     public ItemStack[] getInv() {
/* 83 */       return this.inv;
/*    */     }
/*    */     
/*    */     public ItemStack[] getArmor() {
/* 87 */       return this.armor;
/*    */     }
/*    */     
/* 90 */     public Integer getEXP() { return this.xp; }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\InvCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */