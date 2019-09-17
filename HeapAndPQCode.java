package gses;
import java.util.*;

public class HeapAndPQCode 
{
	private ArrayList<URLS> arr; //ArrayList of URLS which contains urls and scores
	private static int heapSize; //current size of heap
	
	public HeapAndPQCode(ArrayList<URLS> newArr)
	{
		arr = newArr;
		heapSize = arr.size(); //primarily sets heapsize to size of ArrayList
	}
	
	/*
	 * finds the left child node of parent node i
	 * @return left child node
	 */
	public int left(int i)
	{
		return 2 * i;
	}
	/*
	 * finds the right child node of parent node i
	 * @return right child node
	 */
	public int right(int i)
	{
		return (2 * i) + 1;
	}
	/*
	 * finds the parent node of child node i
	 * @return parent node
	 */
	public int parent(int i)
	{
		return i/2;
	}
	
	/*
	 * maxHeapify makes sure that part of the ArrayList (the element at node i and its subtree)
	 * follows the heap property, where the value of the parent node is greater than or equal to 
	 * the value of its child node. (based on PageRank score)
	 * @param arr an ArrayList with urls and scores representing a heap
	 * @param i the parent node
	 */
	public void maxHeapify(ArrayList<URLS> arr, int i)
	{
		int leftChild = left(i); //finds value of left child
		int rightChild = right(i); //finds value of right child
		int largest = i;
		//if left child is greater than parent node, largest is set to left child node
		if(leftChild <= heapSize-1 && arr.get(leftChild).getScore() > arr.get(i).getScore())
		{
			largest = leftChild;
		}
		//if right child is greater than parent node, largest is set to right child node
		if(rightChild <= heapSize-1 && arr.get(rightChild).getScore() > arr.get(largest).getScore())
		{
			largest = rightChild;
		}
		/*
		 * if the largest node isn't the same as the param i node, then switch the two values and recursively call 
		 * until node and subtree maintain property
		 */
		if(largest != i)
		{
			Collections.swap(arr, i, largest);
			maxHeapify(arr, largest);
		}
	}
	
	/*
	 * Calls maxHeapify to make sure that the entire ArrayList follows the heap property and double checks
	 * that all nodes satisfy the property.
	 * @param arr an ArrayList with urls and scores representing a heap
	 */
	public void buildMaxHeap(ArrayList<URLS> arr)
	{
		heapSize = arr.size(); 
		for(int i = arr.size()-1/2; i >= 0; i--)//starts at half of the heap and works it to the root
		{
			maxHeapify(arr, i); //calls maxHeapify with ArrayList and node i, which is decremented by 1
		}
	}
	
	/*
	 * Takes an unsorted ArrayList with urls and scores and modifies it to be sorted 
	 * in ascending order based on PageRank scores
	 * @param arr an ArrayList with urls and scores representing a heap
	 */
	public void heapsort(ArrayList<URLS> arr)
	{
		buildMaxHeap(arr);
		for(int i = arr.size()-1; i >= 1; i--)
		{
			Collections.swap(arr,0,i); //swaps the root and node i if node i's values are greater than the root's
			heapSize--;
			maxHeapify(arr, 0); //calls maxHeapify to maintain heap property of root and subtree
		}
	}
	
	/*
	 * Modifies ArrayList to include the value of key. Calls heapIncreaseKey to place the new
	 * value in its correct position in the heap
	 * @param arr an ArrayList with urls and scores representing a heap
	 * @param key the value to insert in the heap
	 */
	public void maxHeapInsert(ArrayList<URLS> arr, int key)
	{
		heapSize++;
		arr.get(heapSize-1).setScore(Integer.MIN_VALUE); //sets the index of heapSize-1 to the smallest int possible
		heapIncreaseKey(arr, heapSize-1, key); //calls heapIncreaseKey to compare key with parent element
	}
	
	/*
	 * As heapIncreaseKey goes through its path, it compares key with the element
	 * of its parent, exchanging their keys and continuing if key' value is larger.
	 * It stops once the key is smaller than its parents' value and when the max-heap
	 * property holds.
	 * @param arr an ArrayList with urls and scores representing a heap
	 * @param i index of ArrayList where key is checked with
	 * @param key the value to insert in the heap
	 */
	public void heapIncreaseKey(ArrayList<URLS> arr, int i, int key) throws IllegalArgumentException
	{
		if(key < arr.get(i).getScore()) //if key's score is smaller than node i's score
		{
			String err = "New key is smaller than current key";
			throw new IllegalArgumentException(err);
		}
		int parentNum = parent(i); //find parent node of node i
		arr.get(i).setScore(key); //sets node i's sore to key
		/*
		 * swaps node i and node parentNum if node i is greater than its parent.
		 * Then, it compares node i with its new parent until node i is less 
		 * than its parent
		 */
		while(i > 0 && arr.get(parentNum).getScore() < arr.get(i).getScore())
		{
			Collections.swap(arr, i, parentNum);
			i = parent(i);
			parentNum = parent(i);
		}
	}
	
	/*
	 * Returns the maximum URLS object of the heap 
	 * @param arr an ArrayList with urls and scores representing a heap
	 * @return the first ranked web url and score
	 */
	public URLS heapMaximum(ArrayList<URLS> arr)
	{
		return arr.get(0);
	}
	
	/*
	 * Allows user to view the first ranked web url and score stored in max-heap.
	 * Then, the heap removes the element and bumps the next element to the top.  
	 * @param arr an ArrayList with urls and scores representing a heap
	 * @return max the first ranked web url ans score
	 */
	public URLS heapExtractMax(ArrayList<URLS> arr) throws IllegalArgumentException
	{
		if(heapSize - 1 < 0)
		{
			String err = "Heap underflow";
			throw new IllegalArgumentException(err);
		}
		URLS max = arr.get(0); //gets maximum object
		arr.set(0, arr.get(heapSize-1)); //sets the next largest to the top
		heapSize--;
		maxHeapify(arr, 0); //maxHeapify to maintain heap property
		return max;
	}
	
	/*
	 * This method is for testing purposes. It prints out the contents of the ArrayList for 
	 * testing the functional requirements.
	 * @param arr an ArrayList<URLS> which contains urls and scores
	 */
	public void printArray(ArrayList<URLS> arr)
	{
		for(int i = 0; i < arr.size(); i++)
			System.out.println(arr.get(i).getURL() + "\t" + arr.get(i).getScore());
	}
	
	/*
	 * This method is for testing purposes. It creates an ArrayList<URLS> and add values to it. It 
	 * then tests Heapsort and Max-Heap code to make sure they run properly.
	 */
	public static void main(String[] args)
	{
		ArrayList<URLS> pracArray = new ArrayList<>();
		HeapAndPQCode hapqc = new HeapAndPQCode(pracArray);
		pracArray.add(new URLS("web1", 1));
		pracArray.add(new URLS("web2", 150));
		pracArray.add(new URLS("web3", 180));
		pracArray.add(new URLS("web4", 78));
		pracArray.add(new URLS("web5", 93));
		pracArray.add(new URLS("web6", 43));
		pracArray.add(new URLS("web7", 27));
		pracArray.add(new URLS("web8", 10));
		pracArray.add(new URLS("web9", 193));
		pracArray.add(new URLS("web10", 341));
		
		System.out.println("Testing heapsort:");
		hapqc.heapsort(pracArray);
		hapqc.printArray(pracArray);
		
		/*System.out.println("Testing Max-Heap Priority:");
		pracArray.add(new URLS("web11", 156));
		pracArray.add(new URLS("web12", 839));
		for(int i = 0; i < pracArray.size(); i++)
			hapqc.maxHeapInsert(pracArray, pracArray.get(i).getScore());
		hapqc.printArray(pracArray);
		System.out.println();
		System.out.println("Testing heapExtractMax():");
		URLS heapMax1 = hapqc.heapExtractMax(pracArray);
		System.out.println("Heap maximum1 is: " + heapMax1.getURL() + " " + heapMax1.getScore());
		System.out.println("Testing heapMaximum():");
		URLS heapMax2 = hapqc.heapMaximum(pracArray);
		System.out.println("Heap maximum2 is: " + heapMax2.getURL() + " " + heapMax2.getScore());*/
	}
}