package com.org.vote;


import com.org.vote.exceptions.VoteSystemException;
import com.org.vote.implementation.ProcessingStrategy1;
/**
 * 
 * @author JJeyaraman
 *
 */
public class VoteSystemMain {

	public static void main(String args[]) {
		VoteCounter vote = new VoteCounter();
		vote.getInstance(vote);
		try {
			vote.candidatesMap = vote.readInputFile(System.getProperty("user.dir") + "\\src\\resources\\input.txt");
			vote.displayInput(vote.candidatesMap);
			vote.candidatesMap = vote.getUserInput();
			if (vote.candidatesMap != null)
				vote.processVotes(new ProcessingStrategy1(vote));
			else
				System.out.println("There are no valid votes to process");
		} catch (VoteSystemException e) {
			System.out.println("The system has thrown exception" + e.getCause());
		}
	}
}
