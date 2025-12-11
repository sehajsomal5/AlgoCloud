package logic.Sorting;

public class MergeSortVisualizer {

    public static void visualize(int[] array) {
        System.out.println("🔍 Starting Merge Sort Visualization...\nOriginal Array:");
        printArray(array);

        mergeSort(array, 0, array.length - 1);
        
        System.out.println("\n✅ Sorted Array:");
        printArray(array);
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);

        printStep(arr, left, right);  // Visualize each merge step
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] merged = new int[right - left + 1];

        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            merged[k++] = (arr[i] < arr[j]) ? arr[i++] : arr[j++];
        }
        while (i <= mid) merged[k++] = arr[i++];
        while (j <= right) merged[k++] = arr[j++];

        System.arraycopy(merged, 0, arr, left, merged.length);
    }

    private static void printStep(int[] arr, int left, int right) {
        System.out.print("🧩 Merged [" + left + "-" + right + "]: ");
        for (int i = left; i <= right; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    private static void printArray(int[] arr) {
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
