package cn.java.AlgorithmBasic;

public class Search {
	 public static void main(String[] args){
	        int[] arr = {1,2,3,4,5,6,7,8,11,15,17};
	        int des = 11;
	        System.out.println(binarySearch(arr, des));
	        System.out.println(binarySearch(arr, des, 0, 10));

	    }
	
	public static int binarySearch(int[] arr,int des,int low,int high){
        int middle = (low+high)/2;
        if (des<arr[low]||des>arr[high]||low>high) {
            return -1;
        }
        if (arr[middle]<des) {
            return binarySearch(arr, des, middle+1, high);
        }else if (arr[middle]>des) {
            return binarySearch(arr, des, low, middle-1);
        }else{
        return middle;
        }
    }

	 public static int binarySearch(int[] arr,int des){
	        int low = 0;
	        int high = arr.length-1;
	        while(low<=high){
	            int middle = (low+high)/2;
	            if (arr[middle] == des) {
	                return middle;
	            }else if (arr[middle]<des) {
	                low = middle+1;
	            }else {
	                high = middle-1;
	            }
	        }
	        return -1;
	    }

}
