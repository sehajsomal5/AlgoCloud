package visualizer;

import util.ScannerUtil;

import java.util.Scanner;

public class Visualizer {
    public static void start() {
        Scanner scanner = ScannerUtil.getScanner();

        System.out.println("📊 Algorithm Visualizer");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Insertion Sort");
        System.out.println("4. Binary Search");
        System.out.println("5. Back to Main Menu");
        System.out.print("Choose an algorithm to visualize: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                visualizeBubbleSort();
                break;
            case 2:
                visualizeSelectionSort();
                break;
            case 3:
                visualizeInsertionSort();
                break;
            case 4:
                visualizeBinarySearch();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void visualizeInsertionSort() {
        int[] arr = { 12, 11, 13, 5, 6 };
        System.out.println("Initial Array: ");
        printArray(arr);

        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            System.out.println("\nPass " + i + ": Insert " + key + " into sorted part (0 to " + (i - 1) + ")");

            while (j >= 0 && arr[j] > key) {
                printCompare(arr, j, j + 1); // Visualizing comparison
                arr[j + 1] = arr[j];
                System.out.println("Moved " + arr[j] + " to pos " + (j + 1));
                j = j - 1;
                printArray(arr);
                sleep(800);
            }
            arr[j + 1] = key;
            System.out.println("Inserted " + key + " at pos " + (j + 1));
            printArray(arr);
        }
        System.out.println("✅ Sorted Array:");
        printArray(arr);
    }

    private static void visualizeBinarySearch() {
        int[] arr = { 2, 5, 8, 12, 16, 23, 38, 56, 72, 91 };
        System.out.println("Sorted Array for Binary Search:");
        printArray(arr);

        Scanner scanner = ScannerUtil.getScanner();
        System.out.print("Enter number to search: ");
        int target = scanner.nextInt();

        int low = 0, high = arr.length - 1;
        boolean found = false;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            System.out.println(
                    "\nSearching range: [" + low + " to " + high + "], Mid index: " + mid + ", Mid value: " + arr[mid]);
            printSearchRange(arr, low, high, mid);
            sleep(1000);

            if (arr[mid] == target) {
                System.out.println("✅ Found " + target + " at index " + mid);
                found = true;
                break;
            }

            if (arr[mid] < target) {
                System.out.println(arr[mid] + " < " + target + " -> Ignore left half.");
                low = mid + 1;
            } else {
                System.out.println(arr[mid] + " > " + target + " -> Ignore right half.");
                high = mid - 1;
            }
        }

        if (!found) {
            System.out.println("❌ Element " + target + " not found.");
        }
    }

    private static void visualizeSelectionSort() {
        int[] arr = { 64, 25, 12, 22, 11 };
        System.out.println("Initial Array: ");
        printArray(arr);

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            System.out.println("\nPass " + (i + 1) + ": Finding minimum from index " + i + " to " + (n - 1));

            for (int j = i + 1; j < n; j++) {
                printCompare(arr, min_idx, j);
                if (arr[j] < arr[min_idx])
                    min_idx = j;
            }

            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;

            System.out.println("Found min: " + arr[i] + ". Swapped with index " + i);
            printArray(arr);
            sleep(800);
        }
        System.out.println("✅ Sorted Array:");
        printArray(arr);
    }

    private static void visualizeBubbleSort() {
        int[] arr = { 5, 2, 9, 1, 6 };

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

    private static void printSearchRange(int[] arr, int low, int high, int mid) {
        for (int k = 0; k < arr.length; k++) {
            if (k == mid) {
                System.out.print("[" + arr[k] + "] ");
            } else if (k >= low && k <= high) {
                System.out.print(arr[k] + "  ");
            } else {
                System.out.print(".  "); // Dim out of range
            }
        }
        System.out.println();
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
