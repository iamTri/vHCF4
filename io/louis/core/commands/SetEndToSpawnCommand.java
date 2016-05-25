/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import io.louis.core.commands.type.ConfigurableComponent;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ public class SetEndToSpawnCommand extends ConfigurableComponent implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*    */ {
/*    */   private Location portalLocation;
/*    */   
/*    */   public SetEndToSpawnCommand(Core mainPlugin)
/*    */   {
/* 17 */     super(mainPlugin, "e2spawn");
/* 18 */     if (!super.isEmpty()) {
/* 19 */       org.bukkit.World w = mainPlugin.getServer().getWorld((String)super.get("world"));
/* 20 */       double x = ((Double)super.get("x")).doubleValue();
/* 21 */       double y = ((Double)super.get("y")).doubleValue();
/* 22 */       double z = ((Double)super.get("z")).doubleValue();
/* 23 */       float yaw = Float.intBitsToFloat(((Integer)super.get("yaw")).intValue());
/* 24 */       float pitch = Float.intBitsToFloat(((Integer)super.get("pitch")).intValue());
/* 25 */       setPortalLocation(new Location(w, x, y, z, yaw, pitch));
/*    */     }
/* 27 */     super.getPlugin().getServer().getPluginManager().registerEvents(this, mainPlugin);
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/* 31 */     if (!(s instanceof Player)) {
/* 32 */       s.sendMessage(org.bukkit.ChatColor.RED + "You must be a player to use this command.");
/* 33 */       return true;
/*    */     }
/* 35 */     Player p = (Player)s;
/* 36 */     if (!s.hasPermission("core.setportal")) {
/* 37 */       p.sendMessage(org.bukkit.ChatColor.RED + "No.");
/* 38 */       return true;
/*    */     }
/* 40 */     super.set("world", p.getWorld().getName());
/* 41 */     super.set("x", Double.valueOf(p.getLocation().getX()));
/* 42 */     super.set("y", Double.valueOf(p.getLocation().getY()));
/* 43 */     super.set("z", Double.valueOf(p.getLocation().getZ()));
/* 44 */     super.set("yaw", Integer.valueOf(Float.floatToIntBits(p.getLocation().getYaw())));
/* 45 */     super.set("pitch", Integer.valueOf(Float.floatToIntBits(p.getLocation().getPitch())));
/* 46 */     setPortalLocation(p.getLocation());
/* 47 */     p.sendMessage(org.bukkit.ChatColor.GREEN + "Successfully set the end2overworld portal spawn.");
/* 48 */     return true;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler(priority=org.bukkit.event.EventPriority.HIGHEST)
/*    */   public void onMove(PlayerMoveEvent e) {
/* 53 */     if ((e.getFrom().getWorld().getEnvironment() == org.bukkit.World.Environment.THE_END) && (
/* 54 */       (e.getTo().getBlock().getRelative(0, 0, 0).getType() == org.bukkit.Material.STATIONARY_WATER) || 
/* 55 */       (e.getTo().getBlock().getType() == org.bukkit.Material.STATIONARY_WATER))) {
/* 56 */       e.setCancelled(true);
/* 57 */       e.setTo(this.portalLocation);
/*    */     }
/*    */   }
/*    */   
/*    */   public Location getPortalLocation() {
/* 62 */     return this.portalLocation;
/*    */   }
/*    */   
/*    */   public void setPortalLocation(Location portalLocation) {
/* 66 */     this.portalLocation = portalLocation;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\SetEndToSpawnCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */