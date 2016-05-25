/*    */ package io.louis.core.commands;
/*    */ 
/*    */ import io.louis.core.Core;
/*    */ import io.louis.core.commands.type.ConfigurableComponent;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerPortalEvent;
/*    */ 
/*    */ public class SetPortalCommand extends ConfigurableComponent implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*    */ {
/*    */   private Location portalLocation;
/*    */   
/*    */   public SetPortalCommand(Core mainPlugin)
/*    */   {
/* 20 */     super(mainPlugin, "portal");
/* 21 */     if (!super.isEmpty()) {
/* 22 */       World w = mainPlugin.getServer().getWorld((String)super.get("world"));
/* 23 */       double x = ((Double)super.get("x")).doubleValue();
/* 24 */       double y = ((Double)super.get("y")).doubleValue();
/* 25 */       double z = ((Double)super.get("z")).doubleValue();
/* 26 */       float yaw = Float.intBitsToFloat(((Integer)super.get("yaw")).intValue());
/* 27 */       float pitch = Float.intBitsToFloat(((Integer)super.get("pitch")).intValue());
/* 28 */       setPortalLocation(new Location(w, x, y, z, yaw, pitch));
/*    */     }
/* 30 */     super.getPlugin().getServer().getPluginManager().registerEvents(this, mainPlugin);
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/* 34 */     if (!(s instanceof Player)) {
/* 35 */       s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/* 36 */       return true;
/*    */     }
/* 38 */     Player p = (Player)s;
/* 39 */     if (!s.hasPermission("core.setportal")) {
/* 40 */       p.sendMessage(ChatColor.RED + "No.");
/* 41 */       return true;
/*    */     }
/* 43 */     super.set("world", p.getWorld().getName());
/* 44 */     super.set("x", Double.valueOf(p.getLocation().getX()));
/* 45 */     super.set("y", Double.valueOf(p.getLocation().getY()));
/* 46 */     super.set("z", Double.valueOf(p.getLocation().getZ()));
/* 47 */     super.set("yaw", Integer.valueOf(Float.floatToIntBits(p.getLocation().getYaw())));
/* 48 */     super.set("pitch", Integer.valueOf(Float.floatToIntBits(p.getLocation().getPitch())));
/* 49 */     setPortalLocation(p.getLocation());
/* 50 */     p.sendMessage(ChatColor.GREEN + "Successfully set the end portal spawn.");
/* 51 */     return true;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler(priority=org.bukkit.event.EventPriority.HIGHEST)
/*    */   public void onTeleport(PlayerPortalEvent e) {
/* 56 */     Player p = e.getPlayer();
/*    */     
/* 58 */     java.util.UUID playerUUID = p.getUniqueId();
/* 59 */     if ((e.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.END_PORTAL) && (getPortalLocation() != null)) {
/* 60 */       e.useTravelAgent(false);
/* 61 */       e.setTo(getPortalLocation());
/*    */     }
/*    */   }
/*    */   
/*    */   public Location getPortalLocation() {
/* 66 */     return this.portalLocation;
/*    */   }
/*    */   
/*    */   public void setPortalLocation(Location portalLocation) {
/* 70 */     this.portalLocation = portalLocation;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\SetPortalCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */