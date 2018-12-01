package com.org.vote.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.org.vote.VoteCounter;
import com.org.vote.inter.ValidatorInter;


/**
 * 
 * @author JJeyaraman
 *
 */
public class Validator implements ValidatorInter{
	/**
	 * thisVote this current vote object
	 */
	private VoteCounter thisVote;
	
	public Validator() {
		
	}
	/**
	 * Validator - constructor
	 */
	public Validator(VoteCounter vote) {
		thisVote = vote;
	}

	/**
	 * This method validates the input by discarding invalid input updates the
	 * global userInputList and candidatesMap
	 * 
	 * @param inputList
	 * @return
	 */
	public Map validateInput(List<String> inputList) {
		Map finalCandidateMap = new HashMap();
		List<String> validatedinputList = new ArrayList<String>();
		for (String in : inputList) {
			in = in.replaceAll(" ", "");
			if (in.length() != 0) {
				char[] inputVal = in.toUpperCase().toCharArray();
				boolean isStringFine = true;
				for (char c : inputVal) {
					boolean itemInCandList = true;
					for (Map.Entry<String, String> entry : thisVote.candidatesMap.entrySet()) {
						// Condition that checks
						// Add candidates to the map which has vote
						// candidite not in the first position then dont add to the map.
						if (entry.getKey().equals(Character.toString(c))) {
							if (!finalCandidateMap.containsKey(Character.toString(c))) {
								finalCandidateMap.put(entry.getKey(), entry.getValue());
							}
							itemInCandList = true;
							break;
						} else {
							itemInCandList = false;
						}
					}
					if (!itemInCandList) {
						isStringFine = false;
						break;
					} else {
						isStringFine = true;
					}
				}
				if (isStringFine) {
					validatedinputList.add(in);
				}
			}
		}
		thisVote.userInputList = (ArrayList<String>) validatedinputList;
		return finalCandidateMap;
	}

	/**
	 * This method checks for the duplicate char in a string
	 * 
	 * @param input
	 * @return
	 */
	public boolean check4UniqueVote(String input) {
		// Create a Set to insert characters
		Set<Character> set = new HashSet<>();

		// get all characters form String
		char[] characters = input.toCharArray();

		for (Character c : characters) {
			if (!set.add(c)) {
				return false;
			}
		}
		return true;
	}
}
