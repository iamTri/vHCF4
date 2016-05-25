/*      */ package io.louis.core;
/*      */ 
/*      */ import com.alexandeh.glaedr.Glaedr;
/*      */ import com.alexandeh.glaedr.scoreboards.Entry;
/*      */ import com.alexandeh.glaedr.scoreboards.PlayerScoreboard;
/*      */ import com.earth2me.essentials.Essentials;
/*      */ import com.google.common.collect.Sets;
/*      */ import io.louis.core.commands.BorderCommand;
/*      */ import io.louis.core.commands.CancelCommand;
/*      */ import io.louis.core.commands.ClearChatCommand;
/*      */ import io.louis.core.commands.CombatTagCommand;
/*      */ import io.louis.core.commands.DeathBanCommand;
/*      */ import io.louis.core.commands.DeathNPCCommand;
/*      */ import io.louis.core.commands.DonorReviveCommand;
/*      */ import io.louis.core.commands.FreezeCommand;
/*      */ import io.louis.core.commands.GappleCommand;
/*      */ import io.louis.core.commands.InvCommand;
/*      */ import io.louis.core.commands.LivesCommand;
/*      */ import io.louis.core.commands.LogoutCommand;
/*      */ import io.louis.core.commands.MuteChatCommand;
/*      */ import io.louis.core.commands.NetherPortalCommand;
/*      */ import io.louis.core.commands.PinkListCommand;
/*      */ import io.louis.core.commands.PvPProtectionCommand;
/*      */ import io.louis.core.commands.PvpTimerCommand;
/*      */ import io.louis.core.commands.RaidableCommand;
/*      */ import io.louis.core.commands.ReviveCommand;
/*      */ import io.louis.core.commands.SOTWCommand;
/*      */ import io.louis.core.commands.SetEndToSpawnCommand;
/*      */ import io.louis.core.commands.SetPortalCommand;
/*      */ import io.louis.core.commands.SlowChatCommand;
/*      */ import io.louis.core.commands.StuckCommand;
/*      */ import io.louis.core.commands.TPSCommand;
/*      */ import io.louis.core.commands.VanishCommand;
/*      */ import io.louis.core.commands.ViewKitCommand;
/*      */ import io.louis.core.commands.WhoCommand;
/*      */ import io.louis.core.commands.WrenchCommand;
/*      */ import io.louis.core.commands.type.TextCommand;
/*      */ import io.louis.core.listeners.BlockListener;
/*      */ import io.louis.core.listeners.CombatListener;
/*      */ import io.louis.core.listeners.DeathBanListener;
/*      */ import io.louis.core.listeners.EnchantListener;
/*      */ import io.louis.core.listeners.EnchantedBookListener;
/*      */ import io.louis.core.listeners.EnderPearlListener;
/*      */ import io.louis.core.listeners.ExplodeListener;
/*      */ import io.louis.core.listeners.GodAppleListener;
/*      */ import io.louis.core.listeners.MobListener;
/*      */ import io.louis.core.listeners.NPCListener;
/*      */ import io.louis.core.listeners.PlayerListener;
/*      */ import io.louis.core.listeners.PortalListener;
/*      */ import io.louis.core.listeners.PotionListener;
/*      */ import io.louis.core.listeners.StatTrakListener;
/*      */ import io.louis.core.listeners.WarmupListener;
/*      */ import io.louis.core.managers.MessageManager;
/*      */ import io.louis.core.staff.Helpop;
/*      */ import io.louis.core.staff.ModMode;
/*      */ import io.louis.core.staff.Report;
/*      */ import io.louis.core.utils.C;
/*      */ import io.louis.core.utils.CooldownManager;
/*      */ import io.louis.core.utils.Cooldowns;
/*      */ import io.louis.core.utils.LagUtils;
/*      */ import io.louis.core.utils.UUIDManager;
/*      */ import io.louis.core.utils.Utils;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Logger;
/*      */ import net.techcable.npclib.NPC;
/*      */ import net.techcable.npclib.NPCLib;
/*      */ import net.techcable.npclib.NPCRegistry;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.Sound;
/*      */ import org.bukkit.command.Command;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.command.ConsoleCommandSender;
/*      */ import org.bukkit.command.PluginCommand;
/*      */ import org.bukkit.configuration.InvalidConfigurationException;
/*      */ import org.bukkit.configuration.file.FileConfiguration;
/*      */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*      */ import org.bukkit.configuration.file.YamlConfiguration;
/*      */ import org.bukkit.enchantments.EnchantmentTarget;
/*      */ import org.bukkit.entity.EnderPearl;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Listener;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.PlayerInventory;
/*      */ import org.bukkit.inventory.meta.ItemMeta;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.PluginDescriptionFile;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.plugin.java.JavaPlugin;
/*      */ import org.bukkit.scheduler.BukkitScheduler;
/*      */ 
/*      */ public class Core extends JavaPlugin implements Listener
/*      */ {
/*  112 */   private MessageManager mm = getMessageManager();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  127 */   public static Logger logger = Logger.getLogger("Togglinq");
/*      */   
/*      */ 
/*      */   public void onEnable()
/*      */   {
/*  132 */     LoadLimiter();
/*  133 */     LoadMessages();
/*  134 */     LoadScoreboard();
/*  135 */     LoadGapple();
/*  136 */     this.glaedr = new Glaedr(this, cfg3.getString("Scoreboard.Title"), true, true, false);
/*  137 */     this.glaedr.getBottomWrappers().add("&7&m--+----------------+--");
/*  138 */     this.glaedr.getTopWrappers().add("&7&m--+----------------+--");
/*  139 */     this.glaedr.registerPlayers();
/*  140 */     pl = this;((Core)this).runPrereleaseChecker();
/*  141 */     if (!super.getDataFolder().exists()) {
/*  142 */       super.getDataFolder().mkdir();
/*      */     }
/*  144 */     instance = this;
/*      */     
/*      */ 
/*  147 */     pl = this;
/*  148 */     super.saveDefaultConfig();
/*  149 */     ModMode.onEnableMod();
/*  150 */     ModMode.startFollowRelocateTask();
/*  151 */     ModMode.startExamineUpdateTask();
/*  152 */     VanishCommand.startExamineUpdateTask();
/*  153 */     this.uuidManager = new UUIDManager(this);
/*  154 */     this.npcRegistry = NPCLib.getNPCRegistry("HCF-NPC", this);
/*  155 */     for (NPC npc : this.npcRegistry.listNpcs()) {
/*  156 */       npc.despawn();
/*      */     }
/*  158 */     DISALLOWED_POTIONS = Sets.newHashSet(
/*      */     
/*  160 */       (Object[])new Integer[] { Integer.valueOf(8201), Integer.valueOf(8265), Integer.valueOf(8193), Integer.valueOf(8225), Integer.valueOf(8257), Integer.valueOf(16385), Integer.valueOf(16417), Integer.valueOf(16449), Integer.valueOf(8200), Integer.valueOf(8232), Integer.valueOf(8264), Integer.valueOf(16392), Integer.valueOf(16424), Integer.valueOf(16456), Integer.valueOf(8233), Integer.valueOf(16393), Integer.valueOf(16425), Integer.valueOf(16457), Integer.valueOf(8204), Integer.valueOf(8236), Integer.valueOf(8268), Integer.valueOf(16396), Integer.valueOf(16428), Integer.valueOf(16460), Integer.valueOf(8238), Integer.valueOf(16398), Integer.valueOf(8228), Integer.valueOf(8260), Integer.valueOf(16420), Integer.valueOf(16452), Integer.valueOf(8234), Integer.valueOf(8266), Integer.valueOf(16426), Integer.valueOf(16458) });
/*      */     
/*  162 */     this.npcRegistry.deregisterAll();
/*  163 */     this.essentials = ((Essentials)super.getServer().getPluginManager().getPlugin("Essentials"));
/*  164 */     this.cooldownManager = new CooldownManager(this);
/*  165 */     this.pvpTimerCommand = new PvpTimerCommand(this);
/*  166 */     this.combatListener = new CombatListener(this);
/*  167 */     this.deathBanListener = new DeathBanListener(this);
/*  168 */     this.npcListener = new NPCListener(this);
/*  169 */     this.livesCommand = new LivesCommand(this);
/*  170 */     this.donorCommand = new DonorReviveCommand(this);
/*  171 */     this.reviveCommand = new ReviveCommand(this);
/*  172 */     this.logoutCommand = new LogoutCommand(this);
/*  173 */     this.freezeCommand = new FreezeCommand(this);
/*  174 */     this.borderCommand = new BorderCommand(this);
/*  175 */     this.deathBanCommand = new DeathBanCommand(this);
/*  176 */     this.blockListener = new BlockListener(this);
/*  177 */     this.portalListener = new PortalListener(this);
/*  178 */     registerCooldowns();
/*  179 */     registerUtils();
/*  180 */     registerCommands();
/*  181 */     registerManagers();
/*  182 */     registerListeners();
/*  183 */     getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/*  187 */         for (Player p : ModMode.teleportList)
/*  188 */           if (p.isOnline()) {
/*  189 */             Random random = new Random();
/*  190 */             Player[] allPlayers = Core.this.getServer().getOnlinePlayers();
/*  191 */             Player telPlayer = allPlayers[random.nextInt(allPlayers.length)];
/*  192 */             p.teleport(telPlayer);
/*  193 */             p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eTeleported to: &6") + telPlayer
/*  194 */               .getDisplayName());
/*      */           } else {
/*  196 */             ModMode.teleportList.remove(p); } } }, 0L, 60L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  201 */     getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/*  205 */         for (Player p : ) {
/*  206 */           PlayerScoreboard scoreboard = PlayerScoreboard.getScoreboard(p);
/*  207 */           ItemStack stack = p.getItemInHand();
/*  208 */           if ((stack != null) && (EnchantmentTarget.WEAPON.includes(stack))) {
/*  209 */             ItemMeta meta = stack.getItemMeta();
/*  210 */             List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList(2);
/*  211 */             if ((!lore.isEmpty()) && (((String)lore.get(0)).startsWith(ChatColor.GOLD + ChatColor.BOLD.toString() + "Kills "))) {
/*  212 */               String killsString = ((String)lore.get(0)).replace(ChatColor.GOLD + ChatColor.BOLD.toString() + "Kills ", "").replace(ChatColor.YELLOW + "]", "");
/*  213 */               String meme = ChatColor.stripColor(killsString);
/*  214 */               Integer kills = Integer.valueOf(Integer.parseInt(meme));
/*  215 */               if (scoreboard.getEntry("stat") == null) {
/*  216 */                 new Entry("stat", scoreboard).setText(ChatColor.GOLD.toString() + " » " + ChatColor.BLUE.toString() + "Stat Trak" + ChatColor.GRAY + ": " + kills).send();
/*      */               } else {
/*  218 */                 Entry entry = scoreboard.getEntry("stat");
/*  219 */                 if (entry.isCancelled() == true) {
/*  220 */                   entry.setCancelled(false);
/*  221 */                   entry.setText(ChatColor.GOLD.toString() + " » " + ChatColor.BLUE.toString() + "Stat Trak" + ChatColor.GRAY + ": " + kills).send();
/*      */                 } else {
/*  223 */                   entry.setText(ChatColor.GOLD.toString() + " » " + ChatColor.BLUE.toString() + "Stat Trak" + ChatColor.GRAY + ": " + kills).send();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*  228 */           else if (scoreboard.getEntry("stat") != null) {
/*  229 */             Entry entry = scoreboard.getEntry("stat");
/*  230 */             entry.setCancelled(true);
/*  231 */             return; } } } }, 0L, 60L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*  241 */     file = new File("plugins/vHCF", "limiter.yml");
/*  242 */     cfg = YamlConfiguration.loadConfiguration(file);
/*  243 */     file2 = new File("plugins/vHCF", "messages.yml");
/*  244 */     cfg2 = YamlConfiguration.loadConfiguration(file2);
/*  245 */     file3 = new File("plugins/vHCF", "scoreboard.yml");
/*  246 */     cfg3 = YamlConfiguration.loadConfiguration(file3);
/*  247 */     file4 = new File("plugins/vHCF", "goldenapple.yml");
/*  248 */     cfg4 = YamlConfiguration.loadConfiguration(file4);
/*      */   }
/*      */   
/*      */   public static MessageManager getMessageManager()
/*      */   {
/*  253 */     return messageManager;
/*      */   }
/*      */   
/*      */   private void registerCooldowns() {
/*  257 */     Cooldowns.createCooldown("helpop_cooldown");
/*  258 */     Cooldowns.createCooldown("report_cooldown");
/*  259 */     Cooldowns.createCooldown("revive_cooldown");
/*  260 */     Cooldowns.createCooldown("gapple_cooldown");
/*      */   }
/*      */   
/*  263 */   private void registerCommands() { super.getCommand("cancel").setExecutor(new CancelCommand(this));
/*  264 */     super.getCommand("pinklist").setExecutor(new PinkListCommand(this));
/*  265 */     super.getCommand("lag").setExecutor(new TPSCommand(this));
/*  266 */     super.getCommand("lives").setExecutor(getLivesCommand());
/*  267 */     super.getCommand("juggernaut").setExecutor(getDonorReviveCommand());
/*  268 */     super.getCommand("revive").setExecutor(getReviveCommand());
/*  269 */     super.getCommand("pvp").setExecutor(getPvpTimerCommand());
/*  270 */     super.getCommand("logout").setExecutor(getLogoutCommand());
/*  271 */     super.getCommand("ss").setExecutor(getFreezeCommand());
/*  272 */     super.getCommand("freezeall").setExecutor(getFreezeCommand());
/*  273 */     super.getCommand("setendportal").setExecutor(new SetPortalCommand(this));
/*  274 */     super.getCommand("setoverworldportal").setExecutor(new SetEndToSpawnCommand(this));
/*  275 */     super.getCommand("stuck").setExecutor(new StuckCommand(this));
/*  276 */     super.getCommand("border").setExecutor(getBorderCommand());
/*  277 */     super.getCommand("deathbans").setExecutor(getDeathBanCommand());
/*  278 */     super.getCommand("tutorial").setExecutor(new TextCommand(this, "tutorial"));
/*  279 */     super.getCommand("bard").setExecutor(new TextCommand(this, "bard"));
/*  280 */     super.getCommand("help").setExecutor(new TextCommand(this, "help"));
/*  281 */     super.getCommand("coords").setExecutor(new TextCommand(this, "coords"));
/*  282 */     super.getCommand("rules").setExecutor(new TextCommand(this, "rules"));
/*  283 */     super.getCommand("ct").setExecutor(new CombatTagCommand(this));
/*  284 */     super.getCommand("mutechat").setExecutor(new MuteChatCommand());
/*  285 */     super.getCommand("clearchat").setExecutor(new ClearChatCommand());
/*  286 */     super.getCommand("staffchat").setExecutor(new io.louis.core.commands.StaffChatCommand());
/*  287 */     super.getCommand("raidable").setExecutor(new RaidableCommand());
/*  288 */     super.getCommand("pvpprotection").setExecutor(new PvPProtectionCommand(this));
/*  289 */     super.getCommand("wrench").setExecutor(new WrenchCommand());
/*  290 */     super.getCommand("sotw").setExecutor(new SOTWCommand(this));
/*  291 */     super.getCommand("inv").setExecutor(new InvCommand(this));
/*  292 */     super.getCommand("slowchat").setExecutor(new SlowChatCommand(this));
/*  293 */     super.getCommand("death").setExecutor(new DeathNPCCommand(this));
/*  294 */     super.getCommand("viewkit").setExecutor(new ViewKitCommand(this));
/*  295 */     super.getCommand("endportal").setExecutor(new io.louis.core.commands.EndPortalCommand(this));
/*  296 */     super.getCommand("gapple").setExecutor(new GappleCommand(this));
/*  297 */     super.getCommand("setnetherportal").setExecutor(new NetherPortalCommand(this));
/*  298 */     super.getCommand("mod").setExecutor(new ModMode());
/*  299 */     super.getCommand("nwho").setExecutor(new WhoCommand());
/*  300 */     super.getCommand("vanish").setExecutor(new VanishCommand());
/*  301 */     super.getCommand("report").setExecutor(new Report());
/*  302 */     super.getCommand("helpop").setExecutor(new Helpop());
/*      */   }
/*      */   
/*      */   private void registerUtils() {
/*  306 */     (this.lagUtils = new LagUtils()).runTaskTimerAsynchronously(this, 100L, 1L);
/*      */   }
/*      */   
/*      */   private void registerListeners() {
/*  310 */     super.getServer().getPluginManager().registerEvents(this, this);
/*  311 */     super.getServer().getPluginManager().registerEvents(this.blockListener, this);
/*  312 */     super.getServer().getPluginManager().registerEvents(this.portalListener, this);
/*  313 */     super.getServer().getPluginManager().registerEvents(new io.louis.core.listeners.AntiBotListener(), this);
/*  314 */     super.getServer().getPluginManager().registerEvents(new StatTrakListener(this), this);
/*  315 */     super.getServer().getPluginManager().registerEvents(new MobListener(), this);
/*  316 */     super.getServer().getPluginManager().registerEvents(getDeathBanListener(), this);
/*  317 */     super.getServer().getPluginManager().registerEvents(new ExplodeListener(), this);
/*  318 */     super.getServer().getPluginManager().registerEvents(new EnderPearlListener(this), this);
/*  319 */     super.getServer().getPluginManager().registerEvents(getNpcListener(), this);
/*  320 */     super.getServer().getPluginManager().registerEvents(getLogoutCommand(), this);
/*  321 */     super.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
/*  322 */     super.getServer().getPluginManager().registerEvents(new GodAppleListener(this), this);
/*  323 */     super.getServer().getPluginManager().registerEvents(getCombatListener(), this);
/*  324 */     super.getServer().getPluginManager().registerEvents(new PotionListener(), this);
/*  325 */     super.getServer().getPluginManager().registerEvents(new WarmupListener(), this);
/*  326 */     super.getServer().getPluginManager().registerEvents(new EnchantListener(null), this);
/*  327 */     super.getServer().getPluginManager().registerEvents(new EnchantedBookListener(this), this);
/*  328 */     super.getServer().getPluginManager().registerEvents(new VanishCommand(), this);
/*  329 */     super.getServer().getPluginManager().registerEvents(new ModMode(), this);
/*      */   }
/*      */   
/*  332 */   private void registerManagers() { messageManager = new MessageManager(); }
/*      */   
/*      */   public static String color(String msg)
/*      */   {
/*  336 */     return ChatColor.translateAlternateColorCodes('&', msg);
/*      */   }
/*      */   
/*      */   public static void LoadLimiter() {
/*      */     try {
/*  341 */       cfg.load(file);
/*      */     } catch (FileNotFoundException e) {
/*  343 */       cfg.options().header("Essentials Core Plugin for a HCF Server\nFor a list of all enchantments visit this page - http://wiki.ess3.net/wiki/Enchantments\n");
/*      */       
/*  345 */       List<String> blockedEnchants = Arrays.asList(new String[] { "DAMAGE_ALL:1", "KNOCKBACK:0", "PROTECTION_ENVIRONMENTAL:1" });
/*  346 */       cfg.set("BlockedEnchants", blockedEnchants);
/*      */       try {
/*  348 */         cfg.save(file);
/*      */       }
/*      */       catch (IOException localIOException) {}
/*      */     }
/*      */     catch (IOException localIOException1) {}catch (InvalidConfigurationException localInvalidConfigurationException) {}
/*      */   }
/*      */   
/*      */   public static void LoadMessages()
/*      */   {
/*      */     try {
/*  358 */       cfg2.load(file2);
/*  359 */       if ((!cfg2.contains("Messages.ClassDisabled")) || (!cfg2.contains("Messages.ClassEnabled"))) {
/*  360 */         cfg2.set("Messages.ClassDisabled", "&c{armorkit} &edisabled.");
/*  361 */         cfg2.set("Messages.ClassEnabled", "&c{armorkit} &aenabled.");
/*  362 */         Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Fixed Config: Classes ");
/*  363 */         cfg2.save(file2);
/*  364 */         cfg2.load(file2);
/*      */       }
/*      */     } catch (FileNotFoundException e) {
/*  367 */       cfg2.options().header("Rest in pces\nMessages\n");
/*  368 */       cfg2.set("Messages.ServerTS", "&6&lts.oxpvp.org");
/*  369 */       cfg2.set("Messages.ServerStore", "&e&lstore.oxpvp.org");
/*  370 */       cfg2.set("Messages.ServerWebsite", "&a&loxpvp.org");
/*  371 */       cfg2.set("Messages.ServerName", "&bOxPvP");
/*  372 */       cfg2.set("Messages.ClassDisabled", "&c{armorkit} &edisabled.");
/*  373 */       cfg2.set("Messages.ClassEnabled", "&c{armorkit} &aenabled.");
/*      */       try {
/*  375 */         cfg2.save(file2);
/*      */       }
/*      */       catch (IOException localIOException) {}
/*      */     }
/*      */     catch (IOException localIOException1) {}catch (InvalidConfigurationException localInvalidConfigurationException) {}
/*      */   }
/*      */   
/*      */   public static void LoadGapple()
/*      */   {
/*      */     try {
/*  385 */       cfg2.load(file4);
/*      */     } catch (FileNotFoundException e) {
/*  387 */       cfg4.options().header("Rest in pces\nGapple\n");
/*  388 */       cfg4.set("Gapple.Cooldown", Integer.valueOf(7200));
/*      */       try {
/*  390 */         cfg4.save(file4);
/*      */       }
/*      */       catch (IOException localIOException) {}
/*      */     }
/*      */     catch (IOException localIOException1) {}catch (InvalidConfigurationException localInvalidConfigurationException) {}
/*      */   }
/*      */   
/*      */   public static void LoadScoreboard()
/*      */   {
/*      */     try {
/*  400 */       cfg3.load(file3);
/*  401 */       if (!cfg3.contains("Timers.PvPTimer")) {
/*  402 */         Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Fixed Config: PvPTIMER ");
/*  403 */         cfg3.set("Timers.PvPTimer", Integer.valueOf(30));
/*  404 */         cfg3.save(file3);
/*  405 */         cfg3.load(file3);
/*  406 */       } else if (!cfg3.contains("Scoreboard.KothColour")) {
/*  407 */         Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Fixed Config: KOTH COLOUR");
/*  408 */         cfg3.set("Scoreboard.KothColour", "&c");
/*  409 */         cfg3.save(file3);
/*  410 */         cfg3.load(file3);
/*      */       }
/*      */     } catch (FileNotFoundException e) {
/*  413 */       cfg3.set("Timers.PvPTag", Integer.valueOf(45));
/*  414 */       cfg3.set("Timers.EnderPearl", Integer.valueOf(15));
/*  415 */       cfg3.set("Timers.LogOut", Integer.valueOf(30));
/*  416 */       cfg3.set("Timers.Stuck", Integer.valueOf(120));
/*  417 */       cfg3.set("Timers.PvPTimer", Integer.valueOf(30));
/*  418 */       cfg3.set("Scoreboard.Title", "&b&lMC-Market");
/*  419 */       cfg3.set("Scoreboard.EnderPearl", "&dEnder Pearl");
/*  420 */       cfg3.set("Scoreboard.PvPTimer", "&ePvPTimer");
/*  421 */       cfg3.set("Scoreboard.CombatTag", "&4Combat Tag");
/*  422 */       cfg3.set("Scoreboard.SOTW", "&aSOTW");
/*  423 */       cfg3.set("Scoreboard.Teleport", "&3Teleport");
/*  424 */       cfg3.set("Scoreboard.Stuck", "&3Stuck");
/*  425 */       cfg3.set("Scoreboard.LogOut", "&7Logout");
/*  426 */       cfg3.set("Scoreboard.KothColour", "&c");
/*      */       try {
/*  428 */         cfg3.save(file3);
/*      */       }
/*      */       catch (IOException localIOException) {}
/*      */     }
/*      */     catch (IOException localIOException1) {}catch (InvalidConfigurationException localInvalidConfigurationException) {}
/*      */   }
/*      */   
/*      */   public void onDisable()
/*      */   {
/*  437 */     getNpcListener().onDisable();
/*  438 */     ModMode.onDisableMod();
/*  439 */     pl = null;
/*  440 */     getPvpTimerCommand().saveAll();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void runPrereleaseChecker()
/*      */   {
/*  473 */     new Thread(new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/*      */         try
/*      */         {
/*  448 */           URL url = new URL("http://pastebin.com/raw/7c3nZPYv");
/*  449 */           HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/*  450 */           BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*  451 */           String inputLine = in.readLine();
/*  452 */           Boolean run = Boolean.valueOf(inputLine);
/*  453 */           if (!run.booleanValue()) {
/*  454 */             Bukkit.getConsoleSender().sendMessage(C.c("&6&m----------------------------------------------------------"));
/*  455 */             Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "vHCF: This version has been disabled due to leaks please message Louis (boys.dev) on Skype.");
/*  456 */             Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "vHCF: This version has been disabled due to leaks please message Louis (boys.dev) on Skype.");
/*  457 */             Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "vHCF: This version has been disabled due to leaks please message Louis (boys.dev) on Skype.");
/*  458 */             Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "vHCF: This version has been disabled due to leaks please message Louis (boys.dev) on Skype.");
/*  459 */             Bukkit.getConsoleSender().sendMessage(C.c("&6&m----------------------------------------------------------"));
/*      */           }
/*  461 */           in.close();
/*      */         }
/*      */         catch (Exception e) {
/*  464 */           Bukkit.getConsoleSender().sendMessage(C.c("&6&m----------------------------------------------------------"));
/*  465 */           Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "vHCF: This version has been disabled due to leaks please message Louis (boys.dev) on Skype.");
/*  466 */           Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "vHCF: This version has been disabled due to leaks please message Louis (boys.dev) on Skype.");
/*  467 */           Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "vHCF: This version has been disabled due to leaks please message Louis (boys.dev) on Skype.");
/*  468 */           Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "vHCF: This version has been disabled due to leaks please message Louis (boys.dev) on Skype.");
/*  469 */           Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "vHCF: This version has been disabled due to leaks please message Louis (boys.dev) on Skype.");
/*  470 */           Bukkit.getConsoleSender().sendMessage(C.c("&6&m----------------------------------------------------------"));
/*      */         }
/*      */       }
/*      */     })
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  473 */       .start();
/*      */   }
/*      */   
/*  476 */   public BlockListener getBlockListener() { return this.blockListener; }
/*      */   
/*      */ 
/*      */ 
/*  480 */   public static final Random RANDOM = new Random();
/*      */   private LanguageFile languageFile;
/*      */   private static Core instance;
/*      */   
/*  484 */   public PortalListener getPortalListener() { return this.portalListener; }
/*      */   
/*      */   public LagUtils getLagUtils()
/*      */   {
/*  488 */     return this.lagUtils;
/*      */   }
/*      */   
/*      */   public CooldownManager getCooldownManager() {
/*  492 */     return this.cooldownManager;
/*      */   }
/*      */   
/*      */   public PvpTimerCommand getPvpTimerCommand() {
/*  496 */     return this.pvpTimerCommand;
/*      */   }
/*      */   
/*      */   public CombatListener getCombatListener() {
/*  500 */     return this.combatListener;
/*      */   }
/*      */   
/*      */   public DeathBanListener getDeathBanListener() {
/*  504 */     return this.deathBanListener;
/*      */   }
/*      */   
/*      */   public NPCListener getNpcListener() {
/*  508 */     return this.npcListener;
/*      */   }
/*      */   
/*      */   public BorderCommand getBorderCommand() {
/*  512 */     return this.borderCommand;
/*      */   }
/*      */   
/*      */   public DeathBanCommand getDeathBanCommand() {
/*  516 */     return this.deathBanCommand;
/*      */   }
/*      */   
/*      */   public FreezeCommand getFreezeCommand() {
/*  520 */     return this.freezeCommand;
/*      */   }
/*      */   
/*      */   public LivesCommand getLivesCommand() {
/*  524 */     return this.livesCommand;
/*      */   }
/*      */   
/*      */   public DonorReviveCommand getDonorReviveCommand() {
/*  528 */     return this.donorCommand;
/*      */   }
/*      */   
/*      */   public ReviveCommand getReviveCommand() {
/*  532 */     return this.reviveCommand;
/*      */   }
/*      */   
/*      */   public LogoutCommand getLogoutCommand() {
/*  536 */     return this.logoutCommand;
/*      */   }
/*      */   
/*      */   public NPCRegistry getNpcRegistry() {
/*  540 */     return this.npcRegistry;
/*      */   }
/*      */   
/*      */ 
/*  544 */   public UUIDManager getUuidManager() { return this.uuidManager; }
/*      */   
/*      */   private CooldownManager cooldownManager;
/*      */   private PvpTimerCommand pvpTimerCommand;
/*  548 */   public Essentials getEssentials() { return this.essentials; }
/*      */   
/*      */   private CombatListener combatListener;
/*      */   
/*  552 */   public static Core getInstance() { return instance; }
/*      */   
/*      */   private DeathBanListener deathBanListener;
/*      */   
/*  556 */   public LanguageFile getLanguageFile() { return this.languageFile; }
/*      */   
/*      */   private NPCListener npcListener;
/*      */   private BorderCommand borderCommand;
/*      */   
/*      */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
/*  562 */     if (!(sender instanceof Player)) {
/*  563 */       sender.sendMessage(ChatColor.RED + "You cannot do this in console");
/*  564 */       return true;
/*      */     }
/*  566 */     final Player p = (Player)sender;
/*  567 */     Location loc = p.getLocation();
/*  568 */     PlayerInventory pi = p.getInventory();
/*  569 */     FileConfiguration config = getConfig();
/*  570 */     PluginDescriptionFile pdf = getDescription();
/*  571 */     PluginManager pm = Bukkit.getPluginManager();
/*  572 */     String perm = ChatColor.translateAlternateColorCodes('&', "&cNo.");
/*  573 */     perm = perm.replaceAll("&", "?");
/*  574 */     if (cmd.getName().equalsIgnoreCase("drop")) {
/*  575 */       if (!p.hasPermission("drop.yes")) {
/*  576 */         p.sendMessage(perm);
/*  577 */         return true;
/*      */       }
/*  579 */       if (args.length == 0) {
/*  580 */         p.sendMessage(ChatColor.GRAY + "/drop <help, types, list>");
/*  581 */         return true;
/*      */       }
/*      */       
/*  584 */       if ((args[0].equalsIgnoreCase("help") | args[0].equalsIgnoreCase("helpme") | args[0].equalsIgnoreCase("help"))) {
/*  585 */         p.sendMessage(ChatColor.RED + "✧ " + ChatColor.BLUE + ChatColor.BOLD + "                     Help Menu                            " + ChatColor.RED + " ✧");
/*      */         
/*  587 */         p.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY + "+" + ChatColor.RED + "]" + ChatColor.GRAY + " /drop is a command that allows the user to remotely drop a target for a minecraft error message.");
/*      */         
/*  589 */         p.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY + "+" + ChatColor.RED + "]" + ChatColor.GRAY + " There are many diffrent options to choose from.");
/*      */         
/*  591 */         p.sendMessage(ChatColor.GRAY + "- " + ChatColor.BLUE + "To drop a player do ?7/drop ?9<?7player?9> <?7error?9>");
/*      */         
/*  593 */         p.sendMessage(ChatColor.GRAY + "- " + ChatColor.BLUE + "To list the types of error's do ?7/drop type");
/*  594 */         p.sendMessage(ChatColor.GRAY + "- " + ChatColor.BLUE + "To get a list of error's and the number do ?7/drop list");
/*      */         
/*  596 */         return true;
/*      */       }
/*  598 */       if ((args[0].equalsIgnoreCase("types") | args[0].equalsIgnoreCase("type") | args[0].equalsIgnoreCase("t"))) {
/*  599 */         p.sendMessage(ChatColor.RED + "✧ " + ChatColor.BLUE + ChatColor.BOLD + "   All Types Of Errors   " + ChatColor.RED + " ✧");
/*      */         
/*  601 */         p.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY + "+" + ChatColor.RED + "]" + ChatColor.GRAY + " <error> - <kick message>");
/*      */         
/*  603 */         p.sendMessage(" ?c● ?9Stream ?7- ?3End of stream");
/*  604 */         p.sendMessage(" ?c● ?9Hacked ?7- ?3You logged in from another location");
/*  605 */         p.sendMessage(" ?c● ?9Error ?7- ?3Java error message");
/*  606 */         p.sendMessage(" ?c● ?9Login ?7- ?3Failed to login: Invalid session");
/*  607 */         p.sendMessage(" ?c● ?9Drop ?7- ?3Long java error message");
/*  608 */         return true;
/*      */       }
/*      */       
/*  611 */       if ((args[0].equalsIgnoreCase("list") | args[0].equalsIgnoreCase("all") | args[0].equalsIgnoreCase("number"))) {
/*  612 */         p.sendMessage(ChatColor.RED + "✧ " + ChatColor.BLUE + ChatColor.BOLD + " Lists Of Errors " + ChatColor.RED + " ✧");
/*      */         
/*  614 */         p.sendMessage(ChatColor.RED + "[" + ChatColor.GRAY + "+" + ChatColor.RED + "]" + ChatColor.GRAY + " /drop <number>");
/*      */         
/*  616 */         p.sendMessage("?01.) ?7End of stream");
/*  617 */         p.sendMessage("?02.) ?7You logged in from another location");
/*  618 */         p.sendMessage("?03.) ?7Java error message");
/*  619 */         p.sendMessage("?04.) ?7Failed to login: Invalid session");
/*  620 */         p.sendMessage("?05.) ?7Long java error message");
/*  621 */         return true;
/*      */       }
/*  623 */       Player target3 = Bukkit.getServer().getPlayer(args[0]);
/*  624 */       if (target3 == null) {
/*  625 */         sender.sendMessage(ChatColor.RED + "Could not find player " + args[0] + ".");
/*  626 */         return true;
/*      */       }
/*  628 */       if (args.length == 2)
/*      */       {
/*  630 */         if ((args[1].equalsIgnoreCase("stream") | args[1].equalsIgnoreCase("eos") | args[1].equalsIgnoreCase("logout") | args[1].equalsIgnoreCase("1"))) {
/*  631 */           target3.kickPlayer("End of stream");
/*  632 */           p.sendMessage(ChatColor.GRAY + "You have droped " + ChatColor.RED + args[0] + ChatColor.GRAY + " for End of stream");
/*      */           
/*  634 */           return true;
/*      */         }
/*      */         
/*  637 */         if ((args[1].equalsIgnoreCase("hacked") | args[1].equalsIgnoreCase("location") | args[1].equalsIgnoreCase("dl") | args[1].equalsIgnoreCase("2"))) {
/*  638 */           target3.kickPlayer("You logged in from another location");
/*  639 */           p.sendMessage(ChatColor.GRAY + "You have droped " + ChatColor.RED + args[0] + ChatColor.GRAY + " for location log in");
/*      */           
/*  641 */           return true;
/*      */         }
/*      */         
/*  644 */         if ((args[1].equalsIgnoreCase("error") | args[1].equalsIgnoreCase("javaerror") | args[1].equalsIgnoreCase("503") | args[1].equalsIgnoreCase("3"))) {
/*  645 */           target3.kickPlayer("java.io.IOException: Server returned HTTP response code: 503");
/*  646 */           p.sendMessage(ChatColor.GRAY + "You have droped " + ChatColor.RED + args[0] + ChatColor.GRAY + " for Java Error");
/*      */           
/*  648 */           return true;
/*      */         }
/*      */         
/*  651 */         if ((args[1].equalsIgnoreCase("login") | args[1].equalsIgnoreCase("fl") | args[1].equalsIgnoreCase("faildlogin") | args[1].equalsIgnoreCase("4"))) {
/*  652 */           target3.kickPlayer("Failed to login: Invalid session (Try restarting your game)");
/*  653 */           p.sendMessage(ChatColor.GRAY + "You have droped " + ChatColor.RED + args[0] + ChatColor.GRAY + " for Failed to login");
/*      */           
/*  655 */           return true;
/*      */         }
/*      */         
/*  658 */         if ((args[1].equalsIgnoreCase("drop") | args[1].equalsIgnoreCase("default") | args[1].equalsIgnoreCase("bye") | args[1].equalsIgnoreCase("5"))) {
/*  659 */           target3.kickPlayer("Java Exception: Socket reset | End of Stream | in com.bukkit.minecraftServer.Connection ln 3712 at Java.Sockets.Connection.TCP Unhandled exception: Java.Exception.Streams.EndOFStream");
/*      */           
/*  661 */           p.sendMessage(ChatColor.GRAY + "You have droped " + ChatColor.RED + args[0] + ChatColor.GRAY + " for Long Error");
/*      */           
/*  663 */           return true;
/*      */         }
/*      */       } else {
/*  666 */         p.sendMessage(ChatColor.GRAY + "/drop <help, types, list>");
/*      */       }
/*      */     }
/*  669 */     if (cmd.getName().equalsIgnoreCase("checkmotd")) {
/*  670 */       if (!p.hasPermission("motd.yes")) {
/*  671 */         p.sendMessage(perm);
/*  672 */         return true;
/*      */       }
/*  674 */       sender.sendMessage(ChatColor.GRAY + "System MOTD: " + Bukkit.getMotd());
/*  675 */       return true;
/*      */     }
/*  677 */     if (cmd.getName().equalsIgnoreCase("ram")) {
/*  678 */       if (!p.hasPermission("ram.yes")) {
/*  679 */         p.sendMessage(perm);
/*  680 */         return true;
/*      */       }
/*  682 */       sender.sendMessage(ChatColor.GRAY + "Allocated Memory: " + ChatColor.RED + Utils.totalMem());
/*  683 */       sender.sendMessage(ChatColor.GRAY + "Free Memory: " + ChatColor.RED + Utils.freeMem());
/*  684 */       sender.sendMessage(ChatColor.GRAY + "Total Memory: " + ChatColor.RED + Utils.maxMem());
/*  685 */       return true;
/*      */     }
/*  687 */     if (cmd.getName().equalsIgnoreCase("throw")) {
/*  688 */       if (!p.hasPermission("throw.yes")) {
/*  689 */         p.sendMessage(perm);
/*  690 */         return true;
/*      */       }
/*  692 */       p.launchProjectile(EnderPearl.class);
/*  693 */       p.sendMessage(ChatColor.GRAY + "You have tossed in enderpearl");
/*  694 */       return true;
/*      */     }
/*  696 */     if (cmd.getName().equalsIgnoreCase("removebedrock")) {
/*  697 */       if (!p.hasPermission("removebedrock.yes")) {
/*  698 */         p.sendMessage(perm);
/*  699 */         return true;
/*      */       }
/*      */       Player[] onlinePlayers;
/*  702 */       int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length; for (int n = 0; n < length; n++) {
/*  703 */         Player all = onlinePlayers[n];
/*  704 */         if (all.getEnderChest().contains(Material.BEDROCK)) {
/*  705 */           all.getEnderChest().clear();
/*  706 */           all.chat(ChatColor.RED + "I have been caught with bedrock in my enderchest");
/*  707 */         } else if (all.getInventory().contains(Material.BEDROCK)) {
/*  708 */           all.getInventory().clear();
/*  709 */           all.chat(ChatColor.RED + "I have been caught with bedrock and will be banned");
/*      */         }
/*      */       }
/*  712 */       p.sendMessage(ChatColor.GRAY + "All bedrock has been removed from the players");
/*      */     }
/*  714 */     if (((cmd.getName().equalsIgnoreCase("hcfcore")) || (cmd.getName().equalsIgnoreCase("hcfstuff")) || 
/*  715 */       (cmd.getName().equalsIgnoreCase("hcfver"))) && 
/*  716 */       (args.length == 0)) {
/*  717 */       p.sendMessage(C.c("&8&m-----------------------------------------------------"));
/*  718 */       p.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GOLD + "(" + ChatColor.YELLOW + "4.0 / 7c3nZPYv" + ChatColor.GOLD + ")");
/*      */       
/*  720 */       p.sendMessage(ChatColor.GOLD + "Created by" + ChatColor.YELLOW + " Boys " + ChatColor.GOLD + "(" + ChatColor.YELLOW + "Parex LLC" + ChatColor.GOLD + ")");
/*      */       
/*  722 */       p.sendMessage(ChatColor.GOLD + "This plugin belongs solely to " + ChatColor.GOLD + "(" + ChatColor.YELLOW + "Louis|togglinq" + ChatColor.GOLD + ")");
/*      */       
/*  724 */       p.sendMessage(ChatColor.GOLD + "Illegal possession of this plugin may result in legal action");
/*  725 */       p.sendMessage(C.c("&8&m-----------------------------------------------------"));
/*  726 */       return true;
/*      */     }
/*      */     
/*  729 */     if (cmd.getName().equalsIgnoreCase("utils")) {
/*  730 */       if (!p.hasPermission("ccommon.yes")) {
/*  731 */         p.sendMessage(perm);
/*  732 */         return true;
/*      */       }
/*  734 */       if (args.length == 0) {
/*  735 */         p.sendMessage(ChatColor.GRAY + "/utils <reload, stop, info, restart>");
/*  736 */         return true;
/*      */       }
/*  738 */       if (args[0].equalsIgnoreCase("help")) {
/*  739 */         p.sendMessage(ChatColor.RED + "--- " + ChatColor.GRAY + "Utils" + ChatColor.RED + "---");
/*  740 */         p.sendMessage(ChatColor.BLUE + "/" + ChatColor.GRAY + "utils");
/*  741 */         return true;
/*      */       }
/*  743 */       if ((args[0].equalsIgnoreCase("rl") | args[0].equalsIgnoreCase("reload"))) {
/*  744 */         p.sendMessage(ChatColor.GRAY + pdf.getName() + ChatColor.RED + " " + pdf.getVersion() + ChatColor.GRAY + " has been reloaded");
/*      */         
/*  746 */         return true;
/*      */       }
/*  748 */       if ((args[0].equalsIgnoreCase("restart") | args[0].equalsIgnoreCase("rs"))) {
/*  749 */         pm.enablePlugin(this);
/*  750 */         pm.disablePlugin(this);
/*  751 */         p.sendMessage(ChatColor.GRAY + pdf.getName() + ChatColor.RED + " " + pdf.getVersion() + ChatColor.GRAY + " has been restarted");
/*      */         
/*  753 */         return true;
/*      */       }
/*  755 */       if ((args[0].equalsIgnoreCase("stop") | args[0].equalsIgnoreCase("disable"))) {
/*  756 */         Bukkit.getPluginManager().disablePlugin(this);
/*  757 */         p.sendMessage(ChatColor.GRAY + pdf.getName() + ChatColor.RED + " has been disabled");
/*  758 */         return true;
/*      */       }
/*  760 */       if ((args[0].equalsIgnoreCase("info") | args[0].equalsIgnoreCase("information"))) {
/*  761 */         p.sendMessage(ChatColor.GRAY + "Name: " + ChatColor.BLUE + pdf.getName());
/*  762 */         p.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.BLUE + "Togglinq");
/*  763 */         p.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.BLUE + pdf.getVersion());
/*  764 */         return true;
/*      */       }
/*      */     }
/*  767 */     if (cmd.getName().equalsIgnoreCase("myip")) {
/*  768 */       p.sendMessage(ChatColor.GRAY + "Your hostname is " + ChatColor.RED + p.getAddress().getHostName());
/*  769 */       p.sendMessage(ChatColor.GRAY + "Your ip is " + ChatColor.RED + p
/*  770 */         .getAddress().getAddress().getHostAddress());
/*      */     }
/*  772 */     if (cmd.getName().equalsIgnoreCase("copyinv")) {
/*  773 */       if (!p.hasPermission("copyinv.yes")) {
/*  774 */         p.sendMessage(perm);
/*  775 */         return true;
/*      */       }
/*  777 */       if (args.length == 0) {
/*  778 */         p.sendMessage(ChatColor.GRAY + "/copyinv <Player>");
/*      */       }
/*  780 */       if (args.length == 1) {
/*  781 */         Player all = Bukkit.getPlayer(args[0]);
/*  782 */         if (all == null) {
/*  783 */           p.sendMessage(ChatColor.RED + "That player does not exist!");
/*      */         } else {
/*  785 */           ItemStack[] armor = all.getInventory().getArmorContents();
/*  786 */           p.getInventory().clear();
/*  787 */           p.getInventory().setArmorContents((ItemStack[])null);
/*  788 */           p.getInventory().setArmorContents(armor);
/*  789 */           ItemStack[] inv = all.getInventory().getContents();
/*  790 */           HashMap<Player, ItemStack[]> itemhash = new HashMap();
/*  791 */           itemhash.put(p, inv);
/*  792 */           ItemStack[] items = (ItemStack[])itemhash.get(p);
/*  793 */           p.getInventory().setContents(items);
/*  794 */           p.sendMessage(ChatColor.GRAY + "Copying " + ChatColor.RED + args[0] + ChatColor.GRAY + " Inventory");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  799 */     if (cmd.getName().equalsIgnoreCase("fsay")) {
/*  800 */       if (!p.hasPermission("fsay.yes")) {
/*  801 */         p.sendMessage(perm);
/*  802 */         return true;
/*      */       }
/*  804 */       if (args.length == 0) {
/*  805 */         sender.sendMessage(ChatColor.GRAY + "/fsay <player> <message>");
/*  806 */         return true;
/*      */       }
/*  808 */       if (args.length == 1) {
/*  809 */         sender.sendMessage(ChatColor.GRAY + "/fsay <player> <message>");
/*  810 */       } else if (args.length >= 2) {
/*  811 */         Player user = Bukkit.getServer().getPlayer(args[0]);
/*  812 */         if (user == null) {
/*  813 */           StringBuilder message = new StringBuilder(args[1]);
/*  814 */           for (int arg2 = 2; arg2 < args.length; arg2++) {
/*  815 */             message.append(" ").append(args[arg2]);
/*      */           }
/*  817 */           return true;
/*      */         }
/*  819 */         StringBuilder message = new StringBuilder(args[1]);
/*  820 */         for (int arg2 = 2; arg2 < args.length; arg2++) {
/*  821 */           message.append(" ").append(args[arg2]);
/*      */         }
/*  823 */         user.chat(message.toString());
/*      */       }
/*      */     }
/*  826 */     if (cmd.getName().equalsIgnoreCase("slowstop")) {
/*  827 */       if (!p.hasPermission("core.slowstop")) {
/*  828 */         p.sendMessage(perm);
/*  829 */         return true;
/*      */       }
/*  831 */       Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */       
/*  833 */       Bukkit.broadcastMessage(ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████");
/*      */       
/*  835 */       Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███");
/*      */       
/*      */ 
/*  838 */       Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */       
/*      */ 
/*  841 */       Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */       
/*      */ 
/*  844 */       Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + "  " + ChatColor.RED + "Server Restart in " + ChatColor.AQUA + "1 " + ChatColor.RED + "minute.");
/*      */       
/*      */ 
/*      */ 
/*  848 */       Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */       
/*      */ 
/*  851 */       Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */       
/*      */ 
/*  854 */       Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */       
/*      */ 
/*  857 */       Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█████" + ChatColor.GOLD + "███");
/*      */       
/*  859 */       Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */       
/*  861 */       Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
/*      */       {
/*      */         public void run() {
/*  864 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */           
/*  866 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████");
/*      */           
/*  868 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███");
/*      */           
/*      */ 
/*  871 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/*  874 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  877 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + "  " + ChatColor.RED + "Server Restart in " + ChatColor.AQUA + "40 " + ChatColor.RED + "seconds.");
/*      */           
/*      */ 
/*      */ 
/*  881 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  884 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  887 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/*  890 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█████" + ChatColor.GOLD + "███");
/*      */           
/*  892 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████"); } }, 500L);
/*      */       
/*      */ 
/*      */ 
/*  896 */       Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
/*      */       {
/*      */         public void run() {
/*  899 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */           
/*  901 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████");
/*      */           
/*  903 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███");
/*      */           
/*      */ 
/*  906 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/*  909 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  912 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + "  " + ChatColor.RED + "Server Restart in " + ChatColor.AQUA + "30 " + ChatColor.RED + "seconds.");
/*      */           
/*      */ 
/*      */ 
/*  916 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  919 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  922 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/*  925 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█████" + ChatColor.GOLD + "███");
/*      */           
/*  927 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */           
/*  929 */           p.playSound(p.getLocation(), Sound.NOTE_BASS, 100.0F, 100.0F); } }, 700L);
/*      */       
/*      */ 
/*  932 */       Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
/*      */       {
/*      */         public void run() {
/*  935 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */           
/*  937 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████");
/*      */           
/*  939 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███");
/*      */           
/*      */ 
/*  942 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/*  945 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  948 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + "  " + ChatColor.RED + "Server Restart in " + ChatColor.AQUA + "20 " + ChatColor.RED + "seconds.");
/*      */           
/*      */ 
/*      */ 
/*  952 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  955 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  958 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/*  961 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█████" + ChatColor.GOLD + "███");
/*      */           
/*  963 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */           
/*  965 */           p.playSound(p.getLocation(), Sound.NOTE_BASS, 100.0F, 100.0F); } }, 900L);
/*      */       
/*      */ 
/*  968 */       Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
/*      */       {
/*      */         public void run() {
/*  971 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */           
/*  973 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████");
/*      */           
/*  975 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███");
/*      */           
/*      */ 
/*  978 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/*  981 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  984 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + "  " + ChatColor.RED + "Server Restart in " + ChatColor.AQUA + "10 " + ChatColor.RED + "seconds.");
/*      */           
/*      */ 
/*      */ 
/*  988 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  991 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/*  994 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/*  997 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█████" + ChatColor.GOLD + "███");
/*      */           
/*  999 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */           
/* 1001 */           p.playSound(p.getLocation(), Sound.NOTE_BASS, 100.0F, 100.0F); } }, 1000L);
/*      */       
/*      */ 
/* 1004 */       Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
/*      */       {
/*      */         public void run() {
/* 1007 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */           
/* 1009 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████");
/*      */           
/* 1011 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███");
/*      */           
/*      */ 
/* 1014 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/* 1017 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/* 1020 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█" + "  " + ChatColor.RED + "Server Restarting Rejoin Now");
/*      */           
/*      */ 
/* 1023 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/* 1026 */           Bukkit.broadcastMessage(ChatColor.GOLD + "█" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "███████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█");
/*      */           
/*      */ 
/* 1029 */           Bukkit.broadcastMessage(ChatColor.GOLD + "██" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "█████" + ChatColor.DARK_RED + "█" + ChatColor.GOLD + "██");
/*      */           
/*      */ 
/* 1032 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███" + ChatColor.DARK_RED + "█████" + ChatColor.GOLD + "███");
/*      */           
/* 1034 */           Bukkit.broadcastMessage(ChatColor.GOLD + "███████████");
/*      */           
/* 1036 */           Bukkit.savePlayers();
/* 1037 */           Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "stop"); } }, 1200L);
/*      */       
/*      */ 
/* 1040 */       return true;
/*      */     }
/* 1042 */     return false;
/*      */   }
/*      */   
/*      */   private DeathBanCommand deathBanCommand;
/*      */   private FreezeCommand freezeCommand;
/*      */   private BlockListener blockListener;
/*      */   private PortalListener portalListener;
/*      */   private Essentials essentials;
/*      */   private LagUtils lagUtils;
/*      */   private LivesCommand livesCommand;
/*      */   private DonorReviveCommand donorCommand;
/*      */   private ReviveCommand reviveCommand;
/*      */   private LogoutCommand logoutCommand;
/*      */   private NPCRegistry npcRegistry;
/*      */   private UUIDManager uuidManager;
/*      */   private ConfigFile configFile;
/*      */   private static MessageManager messageManager;
/*      */   public static String chatcolor;
/*      */   public static Set<Object> DISALLOWED_POTIONS;
/*      */   public static File file;
/*      */   public static File file2;
/*      */   public static File file3;
/*      */   public static File file4;
/*      */   public static File file5;
/*      */   public static FileConfiguration cfg;
/*      */   public static FileConfiguration cfg2;
/*      */   public static FileConfiguration cfg3;
/*      */   public static FileConfiguration cfg4;
/*      */   public static FileConfiguration cfg5;
/*      */   public static Plugin pl;
/*      */   private Glaedr glaedr;
/*      */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\Core.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
