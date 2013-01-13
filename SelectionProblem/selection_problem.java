// Student: Richard The (009012796)
// Instructor: Dr. Gilbert Young
// Class : CS331 (Spring 2012)
// Project #2 - Comparing different algorithms for selection problem
// Date : 5/29/2012

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class selection_problem {
   
   private static void print(int key, int[] array, int[] temp){
      long time = System.currentTimeMillis();
      System.out.print("SELECT 1; Element #" + key + " is " + select_1(array, key));
      System.out.print("; time = " + (System.currentTimeMillis() - time) + " ms\n");
      /* reset array elements after every function call
      so that the test results are accurate */
      for(int i=0; i<array.length; i++) array[i] = temp[i];
      
      time = System.currentTimeMillis();
      System.out.print("SELECT 2; Element #" + key + " is " + select_2(array, key));
      System.out.print("; time = " + (System.currentTimeMillis() - time) + " ms\n");
      for(int i=0; i<array.length; i++) array[i] = temp[i];
      
      time = System.currentTimeMillis();
      System.out.print("SELECT 3; Element #" + key + " is " + select_3(array, key));
      System.out.print("; time = " + (System.currentTimeMillis() - time) + " ms\n");
      for(int i=0; i<array.length; i++) array[i] = temp[i];
      
      time = System.currentTimeMillis();
      System.out.print("SELECT 4; Element #" + key + " is " + select_4(array, key));
      System.out.print("; time = " + (System.currentTimeMillis() - time) + " ms\n\n");
      for(int i=0; i<array.length; i++) array[i] = temp[i];
   }
   
   // selection problem 1: use sorting
   private static int select_1(int[] n, int key){
      quick_sort(n, 0, n.length);
      return n[key-1];
   }
   
   // selection problem 2: iterative function
   private static int select_2(int[] n, int key){
      key -= 1; // index = key - 1
      int p = 0, q = n.length;
      int n1, n2, pivot = partition(n, p, q, n[p]); // partition the array, set pivot
      
      while(pivot != key){
         n1 = pivot - p; n2 = q - n1 - 1; // Compute the sizes of the two pieces
         if(pivot > key) {
            q = n1;
            pivot = partition(n, p, q, n[p]);
         }
         else {
            p = pivot + 1;
            q = n2;
            pivot = partition(n, p, q, n[p]);
         }
      }
      
      return n[pivot];
   }
   
   // selection problem 3: recursive function
   private static int select_3(int[] n, int key){
      return select_3(n, 0, n.length, key - 1);
   }
   
   private static int select_3(int[] n, int p, int q, int key){
      int n1, n2, pivot = partition(n, p, q, n[p]); // partition the array, set pivot
      
      if(pivot == key) return n[pivot];
      
      else {
         n1 = pivot - p; n2 = q - n1 - 1; // Compute the sizes of the two pieces
         if(pivot > key) return select_3(n, p, n1, key);
         else return select_3(n, pivot + 1, n2, key);
      }
   }
   
   private static int select_4(int[] n, int key){
      return select_4(n, key - 1, 7, 0, n.length);
   }
   
   private static int select_4(int[] n, int key, int r, int p, int q){
      
      // base case
      if(n.length <= r){
         quick_sort(n, 0, n.length);
         return n[key];
      }
      
      int[] median = new int[n.length/r]; // the set of medians
      
      // partition the array into (n.length/r) subsets of size r each
      int index = 0;
      for(int i = 0; i < n.length; i+=r){
         
         if(i+r <= n.length) { // ignore excess elements
            int temp[] = new int[r];
            
            for(int j = i; j < i+r && j < n.length; j++)
               temp[j-i] = n[j];
            
            median[index++] = select_4(temp, temp.length/2, r, 0, temp.length); 
         }
      }
      
      int MM = select_4(median, median.length/2, r, 0, median.length);
      int pivot = partition(n, p, q, MM);
      
      if(pivot == key) return n[pivot];
      else {
         if(pivot > key) {
            int A[] = new int[pivot];
            for(int i=0; i<A.length; i++)
               A[i] = n[i+p];
            return select_4(A, key, r, 0, A.length);
         }
         else {
            int A[] = new int[q-pivot-1];
            for(int i=0; i<A.length; i++)
               A[i] = n[i+pivot+1];
            return select_4(A, key - pivot - 1, r, 0, A.length);
         }
      }
   }
   
   // quick sort function
   private static void quick_sort(int[] n, int p, int q){
      int pivot, n1, n2;
      if(q > 1){
         pivot = partition(n, p, q, n[p]); // Partition the array, and set the pivot index
         n1 = pivot - p; n2 = q - n1 - 1; // Compute the sizes of the two pieces
         quick_sort(n, p, n1);
         quick_sort(n, pivot + 1, n2);
      }
   }
   
   // partition function
   private static int partition(int[] n, int p, int q, int pp){
      int big = p + 1, small = p + q - 1, temp;
      
      while(big <= small){
         while(big < n.length && n[big] <= pp) big++;
         while(n[small] > pp) small--;
         if(big < small){
            temp = n[big]; n[big] = n[small];
            n[small] = temp;
         }
      }
      
      n[p] = n[small]; n[small] = pp;
      return small;
   }

   public static void main(String[] args) {
      
      System.out.println("Enter any number < 1 to quit the program\n");
      
      while(true){
         // prompt the user to input the size of the array
         System.out.println("Enter the size of the array: ");
         Scanner in = new Scanner(System.in);
         int size = in.nextInt();
         if(size < 1) return; // stop the loop if input is < 1
         int array[] = new int[size], temp[] = new int[size];
         
         // initialize random values of the array
         Random r = new Random();
         for(int i=0; i<array.length; i++){
            array[i] = r.nextInt(1000);
            temp[i] = array[i];
         }

         if(size < 50)
            System.out.print("Array is " + Arrays.toString(array) + "\n");
         
         int key = 1; print(key, array, temp);
         key = size/4; print(key, array, temp);
         key = size/2; print(key, array, temp);
         key = 3 * size / 4; print(key, array, temp);
         key = size; print(key, array, temp);
      }
   }
}