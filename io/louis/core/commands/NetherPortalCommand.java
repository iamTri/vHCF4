/*     */ package io.louis.core.commands;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerPortalEvent;
/*     */ 
/*     */ public class NetherPortalCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*     */ {
/*     */   private Core mainPlugin;
/*     */   private Location worldToNether;
/*     */   private ArrayList<Location> otherLocations;
/*     */   private File file;
/*     */   private FileConfiguration config;
/*     */   private Random r;
/*     */   
/*     */   public NetherPortalCommand(Core mainPlugin)
/*     */   {
/*  28 */     this.mainPlugin = mainPlugin;
/*  29 */     this.file = new File(this.mainPlugin.getDataFolder(), "netherportal.yml");
/*  30 */     if (!this.file.exists()) {
/*     */       try {
/*  32 */         this.file.createNewFile();
/*     */       }
/*     */       catch (IOException e) {
/*  35 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  38 */     this.config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(this.file);
/*  39 */     this.otherLocations = new ArrayList(100);
/*  40 */     this.r = new Random();
/*  41 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/*  42 */     this.worldToNether = loadLocation("worldToNether");
/*  43 */     ConfigurationSection configSection = this.config.getConfigurationSection("otherLocations");
/*  44 */     if (configSection != null) {
/*  45 */       for (String key : configSection.getKeys(false)) {
/*  46 */         int value = Integer.parseInt(key);
/*  47 */         this.otherLocations.add(value, loadLocation(configSection.getCurrentPath() + "." + key));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private Location getRandomLocation() {
/*  53 */     return (Location)this.otherLocations.get(this.r.nextInt(this.otherLocations.size()));
/*     */   }
/*     */   
/*     */   private Location loadLocation(String path) {
/*  57 */     if (!this.config.contains(path)) {
/*  58 */       return null;
/*     */     }
/*  60 */     ConfigurationSection configSection = this.config.getConfigurationSection(path);
/*  61 */     World w = org.bukkit.Bukkit.getWorld(configSection.getString("world"));
/*  62 */     double x = configSection.getDouble("x");
/*  63 */     double y = configSection.getDouble("y");
/*  64 */     double z = configSection.getDouble("z");
/*  65 */     float yaw = Float.intBitsToFloat(configSection.getInt("yaw"));
/*  66 */     float pitch = Float.intBitsToFloat(configSection.getInt("pitch"));
/*  67 */     System.out.println("Loaded " + path + " location");
/*  68 */     return new Location(w, x, y, z, yaw, pitch);
/*     */   }
/*     */   
/*     */   private void saveLocation(String path, Location loc) {
/*  72 */     if (loc == null) {
/*  73 */       return;
/*     */     }
/*  75 */     ConfigurationSection configSection = this.config.createSection(path);
/*  76 */     configSection.set("world", loc.getWorld().getName());
/*  77 */     configSection.set("x", Double.valueOf(loc.getX()));
/*  78 */     configSection.set("y", Double.valueOf(loc.getY()));
/*  79 */     configSection.set("z", Double.valueOf(loc.getZ()));
/*  80 */     configSection.set("yaw", Integer.valueOf(Float.floatToIntBits(loc.getYaw())));
/*  81 */     configSection.set("pitch", Integer.valueOf(Float.floatToIntBits(loc.getPitch())));
/*  82 */     saveConfig();
/*     */   }
/*     */   
/*     */   private void saveConfig() {
/*     */     try {
/*  87 */       this.config.save(this.file);
/*     */     }
/*     */     catch (IOException e) {
/*  90 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   @org.bukkit.event.EventHandler(priority=org.bukkit.event.EventPriority.HIGHEST)
/*     */   public void onTeleport(PlayerPortalEvent e) {
/*  96 */     if (e.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
/*  97 */       if (e.getFrom().getWorld().getEnvironment() == org.bukkit.World.Environment.NORMAL) {
/*  98 */         if (!this.otherLocations.isEmpty()) {
/*  99 */           e.setPortalTravelAgent((org.bukkit.TravelAgent)null);
/* 100 */           e.setTo(getRandomLocation());
/* 101 */           System.out.println("Set destination to otherLocation.");
/*     */         }
/*     */       }
/* 104 */       else if ((e.getFrom().getWorld().getEnvironment() == org.bukkit.World.Environment.NETHER) && (this.worldToNether != null)) {
/* 105 */         e.setPortalTravelAgent((org.bukkit.TravelAgent)null);
/* 106 */         e.setTo(this.worldToNether);
/* 107 */         System.out.println("Set destination to worldToNether.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender s, org.bukkit.command.Command c, String alias, String[] args) {
/* 113 */     if (!(s instanceof Player)) {
/* 114 */       s.sendMessage(ChatColor.RED + "You must be a player to use this command.");
/* 115 */       return true;
/*     */     }
/* 117 */     Player p = (Player)s;
/* 118 */     if (!p.hasPermission("core.netherpotal")) {
/* 119 */       p.sendMessage(ChatColor.RED + "No.");
/* 120 */       return true;
/*     */     }
/* 122 */     if (args.length == 0) {
/* 123 */       saveLocation("worldToNether", this.worldToNether = p.getLocation());
/* 124 */       p.sendMessage(ChatColor.GREEN + "Successfully saved location.");
/* 125 */       return true;
/*     */     }
/* 127 */     int index = 0;
/*     */     try {
/* 129 */       index = Integer.parseInt(args[0]);
/*     */     }
/*     */     catch (NumberFormatException e) {
/* 132 */       p.sendMessage(ChatColor.RED + "Invalid number.");
/* 133 */       return true;
/*     */     }
/* 135 */     if (index < 0) {
/* 136 */       p.sendMessage(ChatColor.RED + "You must enter a number at least zero or greater.");
/* 137 */       return true;
/*     */     }
/*     */     try {
/* 140 */       this.otherLocations.set(index, p.getLocation());
/* 141 */       saveLocation("otherLocations." + index, (Location)this.otherLocations.get(index));
/*     */     }
/*     */     catch (IndexOutOfBoundsException e2) {
/* 144 */       this.otherLocations.add(p.getLocation());
/* 145 */       saveLocation("otherLocations." + this.otherLocations.indexOf(p.getLocation()), (Location)this.otherLocations.get(index));
/*     */     }
/* 147 */     p.sendMessage(ChatColor.GREEN + "Successfully saved location.");
/* 148 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\NetherPortalCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */