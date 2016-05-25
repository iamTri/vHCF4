/*    */ package io.louis.core;
/*    */ 
/*    */ 
/*    */ public abstract class Manager
/*    */ {
/*    */   private Core plugin;
/*    */   
/*    */   public Manager(Core plugin)
/*    */   {
/* 10 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */ 
/*    */   public void init() {}
/*    */   
/*    */ 
/*    */   public void tear() {}
/*    */   
/*    */   public void reload() {}
/*    */   
/*    */   public Core getPlugin()
/*    */   {
/* 23 */     return this.plugin;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\io\louis\core\Manager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */