/*    */ package com.alexandeh.glaedr.scoreboards;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Wrapper
/*    */   extends Entry
/*    */ {
/*    */   private WrapperType type;
/*    */   
/*    */ 
/*    */   public Wrapper setType(WrapperType type)
/*    */   {
/* 13 */     this.type = type;return this;
/*    */   }
/*    */   
/*    */   public static enum WrapperType
/*    */   {
/* 18 */     TOP, 
/* 19 */     BOTTOM;
/*    */     
/*    */     private WrapperType() {} }
/* 22 */   public WrapperType getType() { return this.type; }
/*    */   
/*    */   public Wrapper(String id, PlayerScoreboard playerScoreboard, WrapperType type) {
/* 25 */     super(id, playerScoreboard);
/*    */     
/* 27 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\AppData\Roaming\Skype\My Skype Received Files\vHCF.jar!\com\alexandeh\glaedr\scoreboards\Wrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */