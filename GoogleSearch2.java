/*
 * Aniqua Azad
 * Programming Assignment 1
 * This class mimics a Google search engine.
 * This class focuses on part 2 of the functional requirements. It allows users to choose from options ranging
 * from adding a new link to viewing the top url and its associated score. After the end of every option, the
 * user is allowed to continue or terminate the program. 
 * This class uses the Max-Heap Priority Queue algorithms to sort the PageRank scores and to retrieve 
 * top ranked url.
 */
package gses;
import java.util.*;

public class GoogleSearch2
{
	ArrayList<URLS> pQList; //the priority queue list which holds the first 20 links from the prev class
	HeapAndPQCode hsc; //an object of class HeapAndPQCode, necessary to call the fucntions from that class
	ArrayList<URLS> origUrlAndScore; //the 30 urls and scores from the prev class
	private static Scanner input = new Scanner(System.in);
	private static final int TAKE_NUM = 20; //the number of sorted links to take from origUrlAndScore
	/*
	 * Instantiates the global variables
	 */
	public GoogleSearch2()
	{
		pQList = new ArrayList<>();
		hsc = new HeapAndPQCode(pQList);
	}
	
	/*
	 * This method takes the first 20 url links and their scores out of the 30 links and score from the 
	 * previous class. It then implements the Heap priority queue by calling maxHeapInsert and passing
	 * pqList in to make sure that the elements maintain the Heap property. Finally, pqList is sent
	 * to start to continue the program.
	 * @param origUrlAndScore the top 30 urls and scores from the previous class
	 */
	public void takeFirst20(ArrayList<URLS> origUrlAndScore)
	{
		final int ORIG_ARR_SIZE = origUrlAndScore.size()-1;
		ArrayList<URLS> pqList = new ArrayList<>();
		for(int i = 0; i < TAKE_NUM; i++)
		{
			//takes first 20 urls and their scores
			pqList.add(new URLS(origUrlAndScore.get(ORIG_ARR_SIZE-i).getURL(), origUrlAndScore.get(ORIG_ARR_SIZE-i).getScore()));
			hsc.maxHeapInsert(pqList, pqList.get(i).getScore()); //calls maxHeapInsert to make sure elements maintain Heap property
		}
		start(pqList);
	}
	
	/*
	 * This method allows the user to insert a new link and with a PageRank score. They are then
	 * added to the ArrayList and maxHeapInsert is implemented to make sure that the 
	 * elements follow Heap property. The urls with scores are printed again so the user can see
	 * the newly added link.
	 * @param pqList the ArrayList which contains a certain number of urls and their scores
	 */
	public void insertNewLink(ArrayList<URLS> pqList)
	{
		//Prompts user to enter a new web url
		input.nextLine();
		System.out.print("\nPlease enter a new web url with www.:\t");
		String userUrl = input.nextLine();
		
		//Prompts user to enter a PageRank score for new url
		System.out.print("\nPlease enter a PageRank score (integer) for the webpage:\t");
		int userPRScore = input.nextInt();
		input.nextLine();
		
		pqList.add(new URLS(userUrl, userPRScore));//adds url and score to pqList
		HeapAndPQCode hsc = new HeapAndPQCode(new ArrayList<>());
		
		for(int i = 0; i < pqList.size(); i++)
			hsc.maxHeapInsert(pqList, pqList.get(i).getScore()); //calls maxHeapInsert
		
		//urls and scores printed again for user to see new link added to list
		System.out.println("\nPlease see the updated list:");
		for(int i = 0; i < pqList.size(); i++)
			System.out.println(i+1 + ". " + pqList.get(i).getURL() +"\tPageRank score: "+ pqList.get(i).getScore());
		start(pqList);
	}
	
	/*
	 * Allows user to view the first ranked page with url and score. Calls heapMaxExtract to get information.
	 * @param pqList the ArrayList which contains urls and scores
	 */
	public void viewFirstRankedPage(ArrayList<URLS> pqList)
	{
		URLS topPage = hsc.heapExtractMax(pqList); //gets top ranked url and its PageRank score
		System.out.println("URL: " + topPage.getURL() + "\tPageRank Score: " + topPage.getScore()); //prints out info
		System.out.println();
		start(pqList);
	}
	
	/*
	 * Allows user to increase page ranking of any link in the ArrayList. Prompts them to enter
	 * the number of the link and enter a new score. Calls heapIncrease key to make sure that the new 
	 * number is in the correct place and follows Heap property. The urls and scores are printed out again
	 * for users to see the changes.
	 * @param pqList the ArrayList which contains urls and their scores
	 */
	public void increasePageRanking(ArrayList<URLS> pqList)
	{
		//prompts user to type in the number of the link they wish to increase the ranking of
		System.out.println("Please type in the number of the website you would like to increase the ranking of:");
		//prints out list for users to see options
		for(int i = 0; i < pqList.size(); i++)
			System.out.println(i+1 +". " + pqList.get(i).getURL() + "\tPageRank Score: " + pqList.get(i).getScore());
		int index = input.nextInt();
		//prompts user for a new score
		System.out.print("Please enter a new, increased score:\t");
		int newScore = input.nextInt();
		
		pqList.get(index-1).setScore(newScore);//sets the new score to its respective url
		hsc.heapIncreaseKey(pqList,index-1, newScore);//calls heapIncreaseKey to implement new change and follow Heap property
			
		//Allows users to see changes
		System.out.println("Please see your website increase in rank:");
		for(int i = 0; i < pqList.size(); i++)
			System.out.println(i+1 + ". " + pqList.get(i).getURL() +"\tPageRank score: "+ pqList.get(i).getScore());
		start(pqList);
	}
	
	/*
	 * Lists options for the user and prompts them to enter a number based on the option. They can 
	 * also choose to terminate the program by pressing 0 when prompted.
	 * @param pqList the ArrayList which contains urls and their scores
	 */
	public void start(ArrayList<URLS> pqList)
	{
		ArrayList<URLS> pqListNew = pqList;
		//Prompts user and lists options
		System.out.println("What would you like to do? Press the number according to the options below. If you would "
				+"like to exit, press 0.");
		System.out.println("1. View the first ranked page");
		System.out.println("2. Increase the PageRank of any page on the list");
		System.out.println("3. Insert a new link");
		System.out.println("0. Terminate the program");
		int userNum = input.nextInt();
		//based on the number they enter, the program either runs the respective function or terminates the program 
		//with a message
		if(userNum == 1)
			viewFirstRankedPage(pqListNew);
		else if(userNum == 2)
			increasePageRanking(pqListNew);
		else if(userNum == 3)
			insertNewLink(pqListNew);
		else
		{
			System.out.println("Good-bye!");
			return;
		}
	}
}
