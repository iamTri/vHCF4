/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.PluginCommand;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class WrenchCommand implements CommandExecutor
/*    */ {
/*    */   private ItemStack wrenchItem;
/*    */   
/*    */   public WrenchCommand()
/*    */   {
/* 22 */     this.wrenchItem = new ItemStack(Material.GOLD_HOE);
/* 23 */     ItemMeta itemMeta = this.wrenchItem.getItemMeta();
/* 24 */     itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Wrench");
/* 25 */     itemMeta.setLore(Arrays.asList(new String[] { ChatColor.GRAY + "Spawner Uses Left: " + ChatColor.AQUA + "2" + ChatColor.GRAY + "/" + ChatColor.AQUA + "2", ChatColor.GRAY + "Ender Frame Uses Left: " + ChatColor.AQUA + "8" + ChatColor.GRAY + "/" + ChatColor.AQUA + "8" }));
/* 26 */     this.wrenchItem.setItemMeta(itemMeta);
/* 27 */     Bukkit.getPluginCommand("wrench").setExecutor(this);
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*    */   {
/* 32 */     Player player = (Player)sender;
/* 33 */     String name = sender.getName();
/* 34 */     String displayName = ((Player)sender).getDisplayName();
/*    */     
/* 36 */     if (!sender.hasPermission("core.wrench")) {
/* 37 */       sender.sendMessage(ChatColor.RED + "No.");
/* 38 */       return true;
/*    */     }
/* 40 */     player.getInventory().addItem(new ItemStack[] { this.wrenchItem });
/*    */     
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\WrenchCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */