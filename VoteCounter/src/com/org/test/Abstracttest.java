package com.org.test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.org.vote.exceptions.VoteSystemException;

import com.org.vote.VoteCounter;
import com.org.vote.implementation.ProcessingStrategy1;
import com.org.vote.implementation.Validator;
/**
 * 
 * @author JJeyaraman
 *
 */
public class Abstracttest {
	/**
	 * validate - validator instance
	 */
	Validator validate;
	/**
	 * vote - vote instance
	 */
	VoteCounter vote;
	/**
	 * 
	 * @throws VoteSystemException
	 */
	void readInput() throws VoteSystemException {
		vote = new VoteCounter();
		vote.getInstance(vote);
		validate = new Validator(vote);
		vote.candidatesMap = vote.readInputFile(System.getProperty("user.dir") + "\\src\\resources\\input.txt");
	}
	/**
	 * teardown after each
	 */
	public void tearDown() {
		vote = null;
		validate=null;
	}
	/**
	 * This is a random input generating stub for the whole system
	 * @param noOfVotes
	 * @param inputCandidates
	 * @return
	 */
	List generateRandomInputs(int noOfVotes, String inputCandidates) {
		List<String> testCaseData = new ArrayList<String>();
		Random random = null;
		for (int j = 0; j < noOfVotes; j++) {

			int leftLimit = 97; // letter 'a'
			int rightLimit = leftLimit + inputCandidates.length();
			int targetStringLength = 10;
			random = new Random();
			StringBuilder buffer = new StringBuilder(targetStringLength);
			for (int i = 0; i < targetStringLength; i++) {
				int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
				buffer.append((char) randomLimitedInt);
			}
			char[] chars = buffer.toString().toUpperCase().toCharArray();

			Set<Character> charSet = new LinkedHashSet<Character>();
			for (char c : chars) {
				charSet.add(c);
			}

			StringBuilder sb = new StringBuilder();
			for (Character character : charSet) {
				sb.append(character);
			}
			testCaseData.add(sb.toString());
		}
		return testCaseData;
	}
	/**
	 * Converts String array to list
	 * @param input
	 * @return
	 */
	List addToList(String[] input) {
		List<String> inputList = new ArrayList<String>();
		for (String in : input) {
			inputList.add(in);
		}
		return inputList;
	}
	/**
	 * Invokes the process votes after validating the input
	 * @param list
	 * @return
	 */
	String processVotes(List list) {
		vote.candidatesMap = validate.validateInput(list);
		try {
			return vote.processVotes(new ProcessingStrategy1(vote));
		} catch (VoteSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
