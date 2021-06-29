/*    */ package com.ustc.utils;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.RoundingMode;
/*    */ import org.springframework.util.StringUtils;
/*    */ 
/*    */ public class CapacityUtils
/*    */ {
/*    */   public static String convertDetail(Long size) {
/* 10 */     if (size.longValue() == 0L) {
/* 11 */       return "0B";
/*    */     }
/* 13 */     if (size.longValue() < 1024L)
/* 14 */       return size + "B"; 
/* 15 */     if (1024.0D <= size.longValue() && size.longValue() < 1048576.0D)
/* 16 */       return formatForInteger(size.longValue() / 1024.0D) + "KB"; 
/* 17 */     if (1048576.0D <= size.longValue() && size.longValue() < 1.073741824E9D)
/* 18 */       return formatForInteger(size.longValue() / 1048576.0D) + "MB"; 
/* 19 */     if (1.073741824E9D <= size.longValue() && size.longValue() < 1.292785156096E12D) {
/* 20 */       return formatForInteger(size.longValue() / 1.073741824E9D) + "GB";
/*    */     }
/* 22 */     return formatForInteger(size.longValue() / 1.099511627776E12D) + "TB";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String convert(Long size) {
/* 30 */     if (size == null) {
/* 31 */       return "0B";
/*    */     }
/* 33 */     if (size.longValue() == 0L) {
/* 34 */       return "0B";
/*    */     }
/* 36 */     if (size.longValue() < 1024L)
/* 37 */       return size + "B"; 
/* 38 */     if (1024.0D <= size.longValue() && size.longValue() < 1048576.0D)
/* 39 */       return formatDouble(size.longValue() / 1024.0D) + "KB"; 
/* 40 */     if (1048576.0D <= size.longValue() && size.longValue() < 1.073741824E9D)
/* 41 */       return formatDouble(size.longValue() / 1048576.0D) + "MB"; 
/* 42 */     if (1.073741824E9D <= size.longValue() && size.longValue() < 1.292785156096E12D) {
/* 43 */       return formatDouble(size.longValue() / 1.073741824E9D) + "GB";
/*    */     }
/* 45 */     return formatDouble(size.longValue() / 1.099511627776E12D) + "TB";
/*    */   }
/*    */   
/*    */   public static String convert(String filesize) {
/* 49 */     if (StringUtils.isEmpty(filesize)) {
/* 50 */       return "-";
/*    */     }
/* 52 */     long size = Long.parseLong(filesize);
/* 53 */     if (size == 0L) {
/* 54 */       return "-";
/*    */     }
/* 56 */     String str = "";
/* 57 */     if (size < 1024L) {
/* 58 */       str = size + "B";
/* 59 */     } else if (1024.0D <= size && size < 1048576.0D) {
/* 60 */       str = formatDouble(size / 1024.0D) + "KB";
/* 61 */     } else if (1048576.0D <= size && size < 1.073741824E9D) {
/* 62 */       str = formatDouble(size / 1048576.0D) + "MB";
/* 63 */     } else if (1.073741824E9D <= size && size < 1.292785156096E12D) {
/* 64 */       str = formatDouble(size / 1.073741824E9D) + "GB";
/*    */     } else {
/* 66 */       str = formatDouble(size / 1.099511627776E12D) + "TB";
/*    */     } 
/* 68 */     return str;
/*    */   }
/*    */   
/*    */   public static String formatDouble(double d) {
/* 72 */     BigDecimal bg = (new BigDecimal(d)).setScale(2, RoundingMode.UP);
/* 73 */     double num = bg.doubleValue();
/* 74 */     if (Math.round(num) - num == 0.0D) {
/* 75 */       return String.valueOf((long)num);
/*    */     }
/* 77 */     return String.valueOf(num);
/*    */   }
/*    */   public static String formatForInteger(double d) {
/* 80 */     BigDecimal bg = (new BigDecimal(d)).setScale(100000, RoundingMode.UP);
/* 81 */     double num = bg.doubleValue();
/* 82 */     if (Math.round(num) - num == 0.0D) {
/* 83 */       return String.valueOf((long)num);
/*    */     }
/* 85 */     return String.valueOf(num);
/*    */   }
/*    */ }


/* Location:              D:\workspace\IDEA\disk - 副本\target\classes\!\co\\ust\\utils\CapacityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */