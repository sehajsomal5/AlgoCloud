package visualizer;

import utils.ScannerUtil;

import java.util.Scanner;

public class Visualizer {
    public static void start() {
        Scanner scanner = ScannerUtil.getScanner();

        System.out.println("📊 Algorithm Visualizer");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Back to Main Menu");
        System.out.print("Choose an algorithm to visualize: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                visualizeBubbleSort();
                break;
            case 2:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void visualizeBubbleSort() {
        int[] arr = {5, 2, 9, 1, 6};

        System.out.println("Initial Array:");
        printArray(arr);

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            System.out.println("Pass " + (i + 1) + ":");
            for (int j = 0; j < n - i - 1; j++) {
                printCompare(arr, j, j + 1);
                if (arr[j] > arr[j + 1]) {
                    // swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                printArray(arr);
                sleep(700); // pause for visibility
            }
        }

        System.out.println("✅ Sorted Array:");
        printArray(arr);
    }

    private static void printCompare(int[] arr, int i, int j) {
        for (int k = 0; k < arr.length; k++) {
            if (k == i || k == j) {
                System.out.print("[" + arr[k] + "] ");
            } else {
                System.out.print(" " + arr[k] + "  ");
            }
        }
        System.out.println(" ← comparing " + arr[i] + " & " + arr[j]);
    }

    private static void printArray(int[] arr) {
        for (int val : arr) {
            System.out.print(val + "  ");
        }
        System.out.println();
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
