/*     */ package io.louis.core.commands;
/*     */ 
/*     */ import io.louis.core.Core;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ public class SlowChatCommand implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener
/*     */ {
/*     */   private Core mainPlugin;
/*     */   private long slowChatTime;
/*     */   private BukkitTask bukkitTask;
/*     */   private Map<String, Long> playerChatTimes;
/*     */   
/*     */   public SlowChatCommand(Core mainPlugin)
/*     */   {
/*  23 */     this.mainPlugin = mainPlugin;
/*  24 */     this.slowChatTime = 0L;
/*  25 */     this.playerChatTimes = new java.util.HashMap();
/*  26 */     this.mainPlugin.getServer().getPluginManager().registerEvents(this, this.mainPlugin);
/*     */   }
/*     */   
/*     */   private long convertToMillis(long time, TimeUnit unit) {
/*  30 */     return TimeUnit.MILLISECONDS.convert(time, unit);
/*     */   }
/*     */   
/*     */   private String getTimeMessage(long time) {
/*  34 */     return org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(time, true, true);
/*     */   }
/*     */   
/*     */   @org.bukkit.event.EventHandler(priority=org.bukkit.event.EventPriority.LOWEST)
/*     */   public void onChat(AsyncPlayerChatEvent e) {
/*  39 */     if (e.getPlayer().hasPermission("core.slowchat")) {
/*  40 */       return;
/*     */     }
/*  42 */     if (this.slowChatTime > 0L) {
/*  43 */       if (!this.playerChatTimes.containsKey(e.getPlayer().getName())) {
/*  44 */         this.playerChatTimes.put(e.getPlayer().getName(), Long.valueOf(System.currentTimeMillis() + this.slowChatTime));
/*     */       }
/*     */       else {
/*  47 */         long timeRemaining = ((Long)this.playerChatTimes.get(e.getPlayer().getName())).longValue() - System.currentTimeMillis();
/*  48 */         if (timeRemaining / 1000L > 0L) {
/*  49 */           e.setCancelled(true);
/*  50 */           String timeMessage = getTimeMessage(timeRemaining);
/*  51 */           e.getPlayer().sendMessage(ChatColor.RED + "Chat has been slowed. You can speak in " + ChatColor.YELLOW + timeMessage + ChatColor.RED + ".");
/*     */         }
/*     */         else {
/*  54 */           this.playerChatTimes.put(e.getPlayer().getName(), Long.valueOf(System.currentTimeMillis() + this.slowChatTime));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender s, Command c, String alias, String[] args) {
/*  61 */     if (!s.hasPermission("core.slowchat")) {
/*  62 */       s.sendMessage(ChatColor.RED + "No.");
/*  63 */       return true;
/*     */     }
/*  65 */     if (args.length != 1) {
/*  66 */       s.sendMessage(ChatColor.RED + "Correct Usage: /" + c.getName() + " <{seconds} | off>");
/*  67 */       return true;
/*     */     }
/*  69 */     if (args[0].equalsIgnoreCase("off")) {
/*  70 */       if (this.slowChatTime == 0L) {
/*  71 */         s.sendMessage(ChatColor.RED + "SlowChat is already off.");
/*     */       }
/*     */       else {
/*  74 */         this.slowChatTime = 0L;
/*  75 */         if (this.bukkitTask != null) {
/*  76 */           this.bukkitTask.cancel();
/*  77 */           this.bukkitTask = null;
/*     */         }
/*  79 */         this.playerChatTimes.clear();
/*  80 */         this.mainPlugin.getServer().broadcastMessage(ChatColor.GRAY + "Chat is no longer being slowed.");
/*     */       }
/*     */     }
/*     */     else {
/*     */       try {
/*  85 */         Integer time = Integer.valueOf(Integer.parseInt(args[0]));
/*  86 */         if (time.intValue() == 0) {
/*  87 */           s.sendMessage(ChatColor.RED + "You must supply a number greater than zero.");
/*  88 */           s.sendMessage(ChatColor.RED + "If you want to turn off slowchat, use /slowchat off.");
/*     */         }
/*     */         else {
/*  91 */           if (this.bukkitTask != null) {
/*  92 */             this.bukkitTask.cancel();
/*     */           }
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
/* 104 */           this.bukkitTask = new org.bukkit.scheduler.BukkitRunnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*  97 */               for (Player p : )
/*  98 */                 if (p.hasPermission("staff.mod")) {
/*  99 */                   String slowChatMessage = org.apache.commons.lang.time.DurationFormatUtils.formatDurationWords(SlowChatCommand.this.slowChatTime, true, true);
/* 100 */                   p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&lThe chat is still slowed (delay of &6&l" + slowChatMessage + "&d&l).")); } } }
/*     */           
/*     */ 
/*     */ 
/* 104 */             .runTaskTimerAsynchronously(this.mainPlugin, 6000L, 6000L);
/* 105 */           this.slowChatTime = convertToMillis(time.intValue(), TimeUnit.SECONDS);
/* 106 */           this.mainPlugin.getServer().broadcastMessage(ChatColor.GRAY + "Chat has been slowed by " + s.getName() + ".");
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 110 */         s.sendMessage(ChatColor.RED + "You must provide a valid number.");
/*     */       }
/*     */     }
/* 113 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\commands\SlowChatCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */