/*
 * This class stores the url and its associated score
 */
package gses;

public class URLS 
{
	private String url;
	private int urlScore;
	
	/*
	 * Creates an object of a URLS with url and score
	 * @param theURL the url found with the keyword
	 * @param theURLScore the PageRank score associated with the URL
	 */
	public URLS(String theURL, int theURLScore)
	{
		url = theURL;
		urlScore = theURLScore;
	}
	
	/*
	 * This method returns the url
	 * @return url the url related to the keyword
	 */
	public String getURL()
	{
		return url;
	}
	
	/*
	 * This method returns the PageRank score
	 * @return urlScore the score related to the url
	 */
	public int getScore()
	{
		return urlScore;
	}
	
	/*
	 * This method sets the newScore as the urlScore
	 * @param newScore sets the urlScore with the value of the newScore
	 */
	public void setScore(int newScore)
	{
		urlScore = newScore;
	}
}
