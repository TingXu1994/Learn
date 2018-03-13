package cn.java.AlgorithmBasic;

import java.util.Scanner;

public class Sort {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int n = 0;
		while (sc.hasNext()) {
			// 简单处理数组长度输入问题，后面的数组元素同理。可以单独封装一个函数
			// 这里假设用户输入规范，不输入负数、非整数、字符串等
			try {
				n = sc.nextInt();// 数组长度
			} catch (Exception e) {
				System.out.println("must be a int number");
				sc.nextLine();
			}
			if (n < 1) {
				System.out.println("Inlegal number, array number must bigger than 0");
			} else
				break;
		}
		int[] b = new int[n];// 数组元素
		for (int i = 0; i < n; i++) {
			b[i] = sc.nextInt();
		}
//		int[] a = bubbleSort(n, b);
		int[] a = quickSort(0, n, b);
		for (int i = 0; i < n; i++) {
			System.out.print(a[i] + ",");
		}
		sc.close();
	}

	/**
	 * 冒泡排序法 每次两两比较，把大数往前推，一趟下来最大数会排到最前面 当一趟排序下来没有出现交换时，则排序完毕 min:0(n) | max:0(n2) |
	 * avg:0(n2)
	 */
	static int[] bubbleSort(int n, int[] b) {
		// 表示第i趟排序，共n-1轮
		for (int i = 0; i < n; i++) {
			// 表示此趟排序没有交换位置，即排序完毕
			boolean f = true;
			// 每次比较到第i趟排序后未排序的最大位置：n-i
			for (int j = 0; j < n - i - 1; j++) {
				if (b[j] > b[j + 1]) {
					int tmp = b[j + 1];
					b[j + 1] = b[j];
					b[j] = tmp;
					f = false;
				}
			}
			if (f)
				break;
		}
		return b;
	}

	/**
	 * 选择排序：每次找到最小(大)元素，存放在起始位置；重复步以上骤 min:0(n2) | max:0(n2) | avg:0(n2)
	 */
	static int[] selectSort(int n, int[] b) {
		// 共要找n-1轮
		for (int i = 0; i < n - 1; i++) {
			// 假设第i轮的第一个元素是最小的
			int min = i;
			for (int j = i + 1; j < n; j++) {
				if (b[j] < b[min]) {
					min = j;
				}
			}
			// 交换找到的最小值与索引min位置
			if (min != i) {
				int tmp = b[i];
				b[i] = b[min];
				b[min] = tmp;
			}
		}
		return b;
	}

	/**
	 * 插入排序:默认前面i位是排序好的，每次从后往前扫描，选择自己的位置插入 min:o(n) | max:o(n2) | avg=o(n2)
	 */
	static int[] insertSort(int n, int[] b) {
		// 共n-1轮插入过程
		for (int i = 1; i < n; i++) {
			// 当前要插入的数据
			int tmp = b[i];

			int j = i;
			while (j > 0 && tmp < b[j - 1]) {
				b[j] = b[j - 1];
				j--;
			}
			// 插入
			if (j != i) {
				b[j] = tmp;
			}
		}
		return b;
	}

	/**
	 * 归并排序
	 */
	static int[] mergeSort(int n, int[] b) {
		return null;
	}

	/**
	 * 快速排序：冒泡的改进，每次找到当个元素在排序后的最终位置（冒泡是最大）
	 * 
	 */
	static int[] quickSort(int left, int right, int[] b) {
		if (left < right) {
			int pivot = getPivot(left, right, b);
			quickSort(left, pivot - 1, b);
			quickSort(pivot + 1, right, b);
		}
		return b;
	}

	static int getPivot(int left, int right, int[] b) {
		int pivot = left;
		int index = pivot + 1;
		for (int i = index; i < right; i++) {
			System.out.println("=="+i);
			if (b[i] < b[pivot]) {
				swap(b, index, i);
				index++;
			}
		}
		swap(b, pivot, index - 1);
		return index - 1;
	}

	static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	
	
}
