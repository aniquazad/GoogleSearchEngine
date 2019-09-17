/*
 * Aniqua Azad
 * Programming Assignment 1
 * This class mimics a Google search engine.
 * This class focuses on part 1 of the functional requirements. The user is prompted to type in a keyword,
 * and from there, the first 30 URL links are shown to them. Next, the URLs are shown with their respective scores
 * for each of the 4 PageRank factors. Finally, the users are shown the URLs with their total PageRank score in 
 * descending order.
 * This class uses the Heapsort algorithm to sort the PageRank scores.
 */
package gses;
import java.util.*;

public class GoogleSearchEngine
{
	private static Scanner input = new Scanner(System.in);
	private String keyword; //keyword given by user
	private int prScore; //total PageRank score
	
	public GoogleSearchEngine()
	{
		keyword = "";
		prScore = 0;
	}
	
	/*
	 * This method prompts the user to input a keyword
	 */
	public void userInput()
	{
		System.out.print("Please type in a keyword with no spaces(i.e. San Jose = SanJose) and then press 'Enter': ");
		keyword = input.nextLine();
	}
	
	/*
	 * displayUrls() shows the user the urls found for the keyword the typed in.
	 * It calls on the WebCrawler class and uses its search() and getUrls() methods
	 * to find the corresponding urls.
	 * @return urls a Set<String> of the urls found for the keyword
	 */
	public ArrayList<String> displayUrls()
	{
		WebCrawler wc = new WebCrawler(keyword);
		wc.search();
		Set<String> urls = wc.getUrls();
		//prints out the urls
		ArrayList<String> allUrls = new ArrayList<>();
		ArrayList<String> url30 = new ArrayList<>();
		for(String indvUrl : urls)
		{
			allUrls.add(indvUrl); //retrieves all urls
		}
		for(int i = 0; i < 30; i++)
		{
			System.out.println(i+1 + ".\t" + allUrls.get(i)); //prints out first 30 urls
			url30.add(allUrls.get(i)); //gets the first 30 urls
		}
		return url30;
	}
	
	/*
	 * This method generates the urls with the 4 factor scores(which make up the PageRank score).
	 * It uses a random number generator to generate the 4 factor scores. Then, each url is put in a map 
	 * with its associated total PageRank score as it value. 
	 * @param urls the urls found related to the keyword
	 * @return urlToScore a Map of the urls as keys and their corresponding PageRank score as values
	 */
	public Map<String, Integer> generateFactors(ArrayList<String> url30)
	{
		int count = 1; //to show the number of the url in the set
		Map<String, Integer> urlToScoreMap = new HashMap<>();
		//tells user what each number means in regards to the PageRank score
		System.out.println("\nA Web page's PageRank depends on the FREQUENCY of keyword, AGE of the webpage, LINKS to the webpage, and AMOUNT paid for ads");
		System.out.println("Here are the following scores for each of the websites, respectively:\n");
		for(String url: url30)
		{
			//random numbers are generated instead of asking for user input for efficiency
			int fac1 = generateRandNum();
			int fac2 = generateRandNum();
			int fac3 = generateRandNum();
			int fac4 = generateRandNum();
			prScore = fac1 + fac2 + fac3 + fac4; //total PageRank score
			System.out.println(count + ".\t" + url + ":\t" + fac1 +"\t"+ fac2+"\t"+ fac3+"\t"+ fac4 ); //prints out url with 4 scores
			count++;
			urlToScoreMap.put(url, prScore); //adds url and score to Map
		}
		System.out.println();
		return urlToScoreMap;
	}
	
	/*
	 * This method generates a random number for the 4 factors instead of asking the user
	 * to input each number; this was created for efficiency.
	 * @return randScore the score for each of the 4 factors
	 */
	public int generateRandNum()
	{
		int randScore = (int)(Math.random() * 100 + 1); //generates a random integer between 1-100
		return randScore;
	}
	
	/*
	 * This method has 3 parts:
	 * 1) It takes each url and its associated score from the Map and adds it to the ArrayList<URLS>
	 * 2) Then, it uses the Heapsort algorithm in the HeapAndPQCode class to sort the PageRank scores in ascending order
	 * 3) Finally, it prints out each url with its score in descending order (greatest score -> smallest score)
	 * @param urlToScore the Map of urls with their corresponding PageRank score
	 * @return urls a sorted ArrayList<URLS> with urls and PageRank in ascending order
	 */
	public ArrayList<URLS> pageRankOrder(Map<String, Integer> urlToScoreMap)
	{
		//prompts user to either continue or terminate the program
		System.out.println("If you would like to see the links based on PageRank score, press 1. Else press 0:\t");
		int check = input.nextInt();
		
		if(check == 0)
		{
			System.out.println("Good-bye!");
			System.exit(0);
		}
		
		ArrayList<URLS> urlAndScore = new ArrayList<>();
		for(String url : urlToScoreMap.keySet())//switches from a HashMap to an ArrayList of URLS
		{
			Integer score = urlToScoreMap.get(url);
			urlAndScore.add(new URLS(url, score));
		}
		
		HeapAndPQCode hsc = new HeapAndPQCode(urlAndScore);
		hsc.heapsort(urlAndScore); //sorts the PageRank scores
		
		int count = 1;
		//prints out the urls with their scores in descending order
		for(int i = urlAndScore.size()-1; i >= 0; i--)
		{
			System.out.println(count++ + ".\t" + urlAndScore.get(i).getURL() + "\tPageRank Score: "+ urlAndScore.get(i).getScore());
		}
		return urlAndScore;
	}
	/*
	 * Calls all the methods in this class. Also allows user to either continue onto the next
	 * part of the simulation or terminate it.
	 */
	public static void main(String[] args)
	{
		GoogleSearchEngine gse = new GoogleSearchEngine();
		gse.userInput(); //asks for user input
		ArrayList<String> url30 = gse.displayUrls(); //displays first 30 urls
		Map<String, Integer> urlToScoreMap = gse.generateFactors(url30); //displays urls with 4 factor scores
		ArrayList<URLS> origUrlAndScore = gse.pageRankOrder(urlToScoreMap); //displays urls in PageRank order
		
		//prompts user if they would like to continue
		System.out.println("If you would like to continue, press 1. Else, press 0:\t");
		int check = input.nextInt();
		
		if(check == 0)//if no
		{
			System.out.println("Good-bye!");//Message is printed is simulation is terminated
			return;
		}
		else
		{
			GoogleSearch2 gs2 = new GoogleSearch2();//calls next class to continue simulation
			gs2.takeFirst20(origUrlAndScore);
		}
	}
}
