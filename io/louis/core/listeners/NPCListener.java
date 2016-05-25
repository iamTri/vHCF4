/*     */ package io.louis.core.listeners;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.massivecraft.factions.FPlayer;
/*     */ import com.massivecraft.factions.FPlayers;
/*     */ import com.massivecraft.factions.Faction;
/*     */ import com.massivecraft.factions.struct.Relation;
/*     */ import io.louis.core.Core;
/*     */ import io.louis.core.commands.LogoutCommand;
/*     */ import io.louis.core.commands.PvpTimerCommand;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ import net.techcable.npclib.NPC;
/*     */ import net.techcable.npclib.NPCRegistry;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NPCListener
/*     */   implements Listener
/*     */ {
/*     */   private Core mainPlugin;
/*     */   private NPCRegistry npcRegistry;
/*     */   private BiMap<UUID, NPCWrapper> npcWrappers;
/*     */   private File file;
/*     */   private FileConfiguration config;
/*     */   private List<String> killedList;
/*     */   
/*     */   public NPCListener(Core mainPlugin)
/*     */   {
/*  60 */     this.mainPlugin = mainPlugin;
/*  61 */     this.npcRegistry = this.mainPlugin.getNpcRegistry();
/*  62 */     this.file = new File(this.mainPlugin.getDataFolder(), "killed.yml");
/*  63 */     if (!this.file.exists()) {
/*     */       try {
/*  65 */         this.file.createNewFile();
/*     */       }
/*     */       catch (IOException e) {
/*  68 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  71 */     this.config = YamlConfiguration.loadConfiguration(this.file);
/*  72 */     this.killedList = getConfig().getStringList("killed");
/*  73 */     this.npcWrappers = HashBiMap.create();
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  77 */     if (!this.npcWrappers.isEmpty()) {
/*  78 */       for (NPCWrapper npcWrapper : this.npcWrappers.values()) {
/*  79 */         npcWrapper.cancel();
/*     */       }
/*     */     }
/*  82 */     for (NPC npc : this.npcRegistry.listNpcs()) {
/*  83 */       npc.despawn();
/*     */     }
/*  85 */     this.npcRegistry.deregisterAll();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void saveConfig()
/*     */   {
/*  99 */     new BukkitRunnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/*  93 */           NPCListener.this.config.save(NPCListener.this.file);
/*     */         }
/*     */         catch (IOException e) {
/*  96 */           e.printStackTrace(); } } }
/*     */     
/*     */ 
/*  99 */       .runTaskAsynchronously(this.mainPlugin);
/*     */   }
/*     */   
/*     */   public void addKilled(UUID uuid) {
/* 103 */     if (!this.killedList.contains(uuid.toString())) {
/* 104 */       this.killedList.add(uuid.toString());
/* 105 */       getConfig().set("killed", this.killedList);
/* 106 */       saveConfig();
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeKilled(UUID uuid) {
/* 111 */     if (this.killedList.contains(uuid.toString())) {
/* 112 */       this.killedList.remove(uuid.toString());
/* 113 */       getConfig().set("killed", this.killedList);
/* 114 */       saveConfig();
/*     */     }
/*     */   }
/*     */   
/*     */   public UUID getByNPC(NPC npc) {
/* 119 */     for (NPCWrapper npcWrapper : this.npcWrappers.values()) {
/* 120 */       if (npcWrapper.getNpc().equals(npc))
/* 121 */         return (UUID)this.npcWrappers.inverse().get(npcWrapper);
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public void createNPC(Player p) {
/* 127 */     if ((p.isDead()) || (!p.isValid())) {
/* 128 */       return;
/*     */     }
/* 130 */     NPCWrapper npcWrapper = new NPCWrapper(p);
/* 131 */     npcWrapper.runTaskTimer(this.mainPlugin, 0L, 20L);
/* 132 */     this.npcWrappers.put(p.getUniqueId(), npcWrapper);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onPlayerJoin(PlayerJoinEvent e)
/*     */   {
/* 138 */     Player p = e.getPlayer();
/* 139 */     if (this.killedList.contains(e.getPlayer().getUniqueId().toString()))
/*     */     {
/* 141 */       p.setFoodLevel(20);
/* 142 */       p.getInventory().clear();
/* 143 */       p.getInventory().setArmorContents(null);
/* 144 */       Bukkit.getPluginManager().callEvent(new PlayerRespawnEvent(p, ((World)Bukkit.getWorlds().get(0)).getSpawnLocation(), false));
/* 145 */       p.sendMessage(ChatColor.RED + "Your NPC was killed while you were offline!");
/* 146 */       this.killedList.remove(p.getUniqueId().toString());
/* 147 */       removeKilled(p.getUniqueId());
/*     */       
/* 149 */       Core.logger.info(ChatColor.RED + e.getPlayer().getName() + ChatColor.GREEN + "'s NPC has been " + ChatColor.RED + "REMOVED"); }
/*     */     NPCWrapper npcWrapper;
/* 151 */     if ((npcWrapper = (NPCWrapper)this.npcWrappers.get(e.getPlayer().getUniqueId())) != null) {
/* 152 */       npcWrapper.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOW)
/*     */   public void onQuit(PlayerQuitEvent e) {
/* 158 */     if (this.mainPlugin.getLogoutCommand().getExemptUUIDs().contains(e.getPlayer().getUniqueId())) {
/* 159 */       this.mainPlugin.getLogoutCommand().getExemptUUIDs().remove(e.getPlayer().getUniqueId());
/* 160 */       Core.logger.info(ChatColor.RED + e.getPlayer().getName() + ChatColor.GREEN + "'s LOGOUT");
/* 161 */     } else if (!this.mainPlugin.getPvpTimerCommand().isProtected(e.getPlayer())) {
/* 162 */       createNPC(e.getPlayer());
/* 163 */       Core.logger.info(ChatColor.RED + e.getPlayer().getName() + ChatColor.GREEN + "'s NPC has been loaded");
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDamage(EntityDamageByEntityEvent e) {
/* 169 */     if (this.mainPlugin.getNpcRegistry().isNPC(e.getEntity()))
/*     */     {
/* 171 */       Player d = null;
/* 172 */       if ((e.getDamager() instanceof Player)) {
/* 173 */         d = (Player)e.getDamager(); } else { Projectile proj;
/* 174 */         if (((e.getDamager() instanceof Projectile)) && (((proj = (Projectile)e.getDamager()).getShooter() instanceof Player)))
/* 175 */           d = (Player)proj.getShooter();
/*     */       }
/* 177 */       if (d == null) {
/* 178 */         return;
/*     */       }
/* 180 */       NPC npc = this.mainPlugin.getNpcRegistry().getAsNPC(e.getEntity());
/* 181 */       FPlayer player = FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer(npc.getUUID()));
/* 182 */       FPlayer damage = FPlayers.getInstance().getByPlayer(d);
/* 183 */       if ((player.getFaction().getTag().equals(damage.getFaction().getTag())) && (!damage.getFaction().getTag().contains("Wilderness"))) {
/* 184 */         e.setCancelled(true);
/* 185 */         d.sendMessage(ChatColor.RED + "You cannot hit your teammate's NPC.");
/* 186 */       } else if (player.getFaction().getRelationTo(damage) == Relation.ALLY) {
/* 187 */         e.setCancelled(true);
/* 188 */         d.sendMessage(ChatColor.RED + "You cannot hit your ally's NPC.");
/*     */       }
/* 190 */       UUID uuid = getByNPC(npc);
/* 191 */       if (uuid != null) {
/* 192 */         ((NPCWrapper)this.npcWrappers.get(uuid)).updateTimer();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public File getFile() {
/* 198 */     return this.file;
/*     */   }
/*     */   
/*     */   public FileConfiguration getConfig() {
/* 202 */     return this.config;
/*     */   }
/*     */   
/*     */   public List<String> getKilledList() {
/* 206 */     return this.killedList;
/*     */   }
/*     */   
/*     */   public class NPCWrapper extends BukkitRunnable implements Listener
/*     */   {
/*     */     private UUID uuid;
/*     */     private String name;
/*     */     private NPC npc;
/*     */     private long deathBanTime;
/*     */     private BukkitTask bukkitTask;
/*     */     private int i;
/*     */     
/*     */     public NPCWrapper(Player p)
/*     */     {
/* 220 */       this.i = 45;
/* 221 */       this.uuid = p.getUniqueId();
/* 222 */       this.name = p.getName();
/* 223 */       this.deathBanTime = NPCListener.this.mainPlugin.getDeathBanListener().getDeathBanTime(p);
/* 224 */       this.npc = NPCListener.this.npcRegistry.createNPC(EntityType.PLAYER, this.uuid, this.name);
/* 225 */       this.npc.spawn(p.getLocation());
/* 226 */       this.npc.setProtected(false);
/* 227 */       Player npcPlayer = (Player)this.npc.getEntity();
/* 228 */       npcPlayer.getInventory().setContents(p.getInventory().getContents());
/* 229 */       npcPlayer.getInventory().setArmorContents(p.getInventory().getArmorContents());
/* 230 */       npcPlayer.setTotalExperience(p.getTotalExperience());
/* 231 */       NPCListener.this.mainPlugin.getServer().getPluginManager().registerEvents(this, NPCListener.this.mainPlugin);
/*     */     }
/*     */     
/*     */     public void removeNPC() {
/* 235 */       this.npc.despawn();
/* 236 */       NPCListener.this.npcRegistry.deregister(this.npc);
/*     */     }
/*     */     
/*     */     public void updateTimer() {
/* 240 */       this.i = 45;
/*     */     }
/*     */     
/*     */     public void run() {
/* 244 */       if (this.i > 0) {
/* 245 */         this.i -= 1;
/*     */       } else {
/* 247 */         cancel();
/*     */       }
/*     */     }
/*     */     
/*     */     public synchronized void cancel() {
/* 252 */       NPCListener.this.npcWrappers.remove(getUuid());
/* 253 */       removeNPC();
/* 254 */       super.cancel();
/*     */     }
/*     */     
/*     */     @EventHandler
/*     */     public void onDeath(PlayerDeathEvent e) {
/*     */       NPC npc;
/* 260 */       if ((NPCListener.this.npcRegistry.isNPC(e.getEntity())) && ((npc = NPCListener.this.npcRegistry.getAsNPC(e.getEntity())).equals(getNpc()))) {
/* 261 */         FPlayer fPlayer = FPlayers.getInstance().getByOfflinePlayer(Bukkit.getOfflinePlayer(getUuid()));
/* 262 */         fPlayer.onDeath();
/* 263 */         NPCListener.this.mainPlugin.getDeathBanListener().addDeathBan(getUuid(), getDeathBanTime());
/* 264 */         NPCListener.this.addKilled(getUuid());
/*     */         
/* 266 */         cancel();
/*     */       }
/*     */     }
/*     */     
/*     */     public UUID getUuid() {
/* 271 */       return this.uuid;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 275 */       return this.name;
/*     */     }
/*     */     
/*     */     public NPC getNpc() {
/* 279 */       return this.npc;
/*     */     }
/*     */     
/*     */     public long getDeathBanTime() {
/* 283 */       return this.deathBanTime;
/*     */     }
/*     */     
/*     */     public BukkitTask getBukkitTask() {
/* 287 */       return this.bukkitTask;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\listeners\NPCListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */