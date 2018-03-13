package cn.java.JavaBasic;

public class hashMapRealize {
	public static void main(String[] args) {
		String a = "sss";
 		String b = new String("sss");
 		String c = new String("sss");
		
 		
 		
		System.out.println("b:"+b);
		System.out.println("a.hashcode:"+a.hashCode());
		System.out.println("b.hashcode:"+b.hashCode());
		System.out.println("c.hashcode:"+c.hashCode());
		
	}
		
		/**
		 * Hash函数的实现
		 */
//		public int hashCode() {
//			 int h = hash;
//			 if (h == 0) {
//			     int off = offset;
//			     char val[] = value;
//			     int len = count;
//
//			            for (int i = 0; i < len; i++) {
//			                h = 31*h + val[off++];
//			            }
//			            hash = h;
//			        }
//			        return h;
//			    }
//
//			}
	/**
	 * Equal函数实现
	 */
//	public boolean equals(Object anObject) {  
//    if (this == anObject) {  
//        return true;  
//    }  
//    if (anObject instanceof String) {  
//        String anotherString = (String) anObject;  
//        int n = value.length;  
//        if (n == anotherString.value.length) {  
//            char v1[] = value;  
//            char v2[] = anotherString.value;  
//            int i = 0;  
//            while (n-- != 0) {  
//                if (v1[i] != v2[i])  
//                        return false;  
//                i++;  
//            }  
//            return true;  
//        }  
//    }  
//    return false;  
//}  
}
