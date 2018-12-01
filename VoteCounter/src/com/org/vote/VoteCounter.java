package com.org.vote;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.org.vote.exceptions.VoteSystemException;
import com.org.vote.implementation.Validator;
import com.org.vote.inter.VoteProcessorStrategy;

/**
 * 
 * @author JJeyaraman
 *
 */
public class VoteCounter extends Validator {
	/**
	 * candidateList - List of all candidates for the voting system
	 */
	public Map<String, String> candidatesMap;
	/**
	 * userInputList - List of all user input given as vote
	 */
	public ArrayList<String> userInputList = new ArrayList<String>();
	/**
	 * noOfVotesMap - Number of vote per each candidate
	 */
	public Map<String, Integer> noOfVotesMap = new HashMap<String, Integer>();
	/**
	 * greaterThanQcand - to determine the winner candidate
	 */
	public String[] greaterThanQcand = new String[1];
	/**
	 * otherCands - to determine if all the candidates fall under same no of votes
	 */
	public String[] otherCands = null;
	/**
	 * quota - to hold the quota value
	 */
	public int quota = 0;
	/**
	 * totalSize - size of user input after input validation
	 */
	public int totalSize = userInputList.size();

	/**
	 * validate - singleton instance is created
	 */
	private static Validator validate = null;
	/**
	 * voteProcessor - singleton instance is created
	 */
	private VoteProcessorStrategy voteProcessor = null;
	/**
	 * instance - singleton instance is created
	 */
//	private static VoteCounter instance = null;
	/**
	 * VoteCounter - the constructor
	 */
	/**
	 * The Winner Result
	 */
	public String result = null;
	
	public VoteCounter() {
		super();
	}
	public  VoteCounter(VoteCounter vote) {
		super(vote);
	}

	/** Lazily create the instance when it is accessed for the first time */
	public void getInstance(VoteCounter instance) {
		validate = new VoteCounter(instance);		
	}

	/**
	 * Reads list of candidates from the file maps them
	 * 
	 * @param path
	 * @return map
	 * @throws VoteSystemException
	 */
	public Map<String, String> readInputFile(String path) throws VoteSystemException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			Map<String, String> input = new HashMap<>();
			String line = br.readLine();
			int count = 0;
			char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			while (line != null) {
				if (count > 25) {
					return null;
				}
				input.put(Character.toString(alphabet[count++]), line.trim());
				line = br.readLine();
			}
			return input;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new VoteSystemException("IOException", e.getCause());
			}
		}
		return null;
	}

	/**
	 * This method displays the candidate list to the user
	 * 
	 * @param map
	 */
	public void displayInput(Map<String, String> map) {
		System.out.println("---------------------------------------------------");
		System.out.println("VIRTUAL VOTING SYSTEM");
		System.out.println("---------------------------------------------------");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ". " + entry.getValue());
		}
		System.out.println(">");
	}

	/**
	 * Gets the user input and then send to the validator to validate it
	 * 
	 * @return
	 * @throws VoteSystemException
	 */
	protected Map getUserInput() throws VoteSystemException {
		String userInput;
		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
			while (true) {
				userInput = scanner.nextLine();
				if (userInput.equalsIgnoreCase("tally")) {
					break;
				} else {
					userInput = userInput.replaceAll(" ", "");
					if (userInput.length() != 0 && validate.check4UniqueVote(userInput.toUpperCase())) {
						userInputList.add(userInput.toUpperCase());

					}

				}
			}
		} catch (Exception e) {
			throw new VoteSystemException("Exception", e.getCause());
		} finally {
			scanner.close();
		}

		return userInputList.size() != 0 ? validate.validateInput(userInputList) : null;
	}

	/**
	 * Process the vote to define the winner
	 * 
	 * @return
	 * @throws VoteSystemException
	 */
	public String processVotes(VoteProcessorStrategy voteProcessor) throws VoteSystemException {
		return voteProcessor.getWinner();
	}
}
