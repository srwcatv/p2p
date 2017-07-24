package com.catv.service;

/**
 * 冒泡排序
 */
public class Sort {

    public static void main(String[] args) {
        int[] data = {3, 55, 67, 23, 46, 99, 10, 26, 45, 0};
        bubbleSort3(data);
        print(data);
    }

    public static void sort(int[] src) {
        int temp = 0, i, j;
        boolean flag = true;
        for (i = 1; i < src.length; i++) {
            for (j = 0; j < src.length - i; j++) {
                if (src[j] > src[j + 1]) {
                    temp = src[j];
                    src[j] = src[j + 1];
                    src[j + 1] = temp;
                }
            }
        }
    }

    public static void bubbleSort3(int[] data) {
        int temp;
        for (int i = 0; i < data.length; i++) {
            boolean flag = false;
            for (int j = 0; j < data.length - i - 1; j++) {
                if (data[j] < data[j + 1]) {
                    temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) break;
            System.out.print("第" + i + "遍:{");
            for (int k = 0; k < data.length; k++) {
                System.out.print(data[k] + ",");
            }
            System.out.println("}");
        }
    }

    public static void print(int[] src) {
        for (int i : src) {
            System.out.println(i);
        }
    }
}
