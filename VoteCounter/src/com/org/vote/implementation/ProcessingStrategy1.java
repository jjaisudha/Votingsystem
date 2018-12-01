package com.org.vote.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.org.vote.exceptions.VoteSystemException;
import com.org.vote.VoteCounter;
import com.org.vote.inter.VoteProcessorStrategy;
/**
 * 
 * @author JJeyaraman
 *
 */
public class ProcessingStrategy1 implements VoteProcessorStrategy {
	/**
	 * thisVote- single instance of the vote counter
	 */
	private VoteCounter thisVote;

	/**
	 * ' Constructor
	 * 
	 * @param vote
	 */
	public ProcessingStrategy1(VoteCounter vote) {
		thisVote = vote;
	}

	/**
	 * Round 1 in the voting system
	 * 
	 * @return
	 * @throws VoteSystemException
	 */

	public Map<String, List<String>> round1Grouping() throws VoteSystemException {
		try {
			thisVote.quota = thisVote.candidatesMap.size() / 2;
			Collections.sort(thisVote.userInputList);
			System.out.println("---------------------------------------------------");
			System.out.println("ROUND - 1 (Grouping the user input per candidate)");
			System.out.println("---------------------------------------------------");

			Map<String, List<String>> uppergroup = new HashMap<String, List<String>>();
			for (int i = 0; i < thisVote.userInputList.size(); i++) {
				List<String> lowerGroup = new ArrayList<String>();
				for (int j = 0; j < thisVote.userInputList.size(); j++) {
					if (thisVote.userInputList.get(i).toString().charAt(0) == thisVote.userInputList.get(j).toString()
							.charAt(0)) {
						lowerGroup.add(thisVote.userInputList.get(j));
					}
				}
				// Has grouped the input
				uppergroup.put(Character.toString(thisVote.userInputList.get(i).toString().charAt(0)), lowerGroup);
				// has grouped based on the count
				thisVote.noOfVotesMap.put(Character.toString(thisVote.userInputList.get(i).toString().charAt(0)),
						lowerGroup.size());
			}
			System.out.println(uppergroup);

			return uppergroup;
		} catch (Exception e) {
			throw new VoteSystemException("Exception", e.getCause());
		}

	}

	/**
	 * This method is used to randomly eliminate the vote if the votes are same for
	 * all candidates
	 * 
	 * @return
	 */
	public String randomPick() {
		List<String> winnerList = new java.util.ArrayList<>(java.util.Arrays.asList(thisVote.otherCands));
		System.out.println("---------------------------------------------------");
		Random rand = null;
		for (String elements : thisVote.otherCands) {
			rand = new Random();
			int index = rand.nextInt(winnerList.size());
			if (winnerList.size() != 1) {
				System.out.println("Vote eliminating Randomly :" + winnerList.get(index));
				winnerList.removeIf(p -> p.equals(winnerList.get(index)));
			} else {
				System.out.println("---------------------------------------------------");
				System.out.println("Winner Candidate after Random elimination : " + winnerList.get(index));
				return winnerList.get(index);
			}
		}
		return null;
	}

	/**
	 * calculates the winner
	 * 
	 * @return
	 * @throws VoteSystemException
	 */
	public String getWinner() throws VoteSystemException {
		try {
			int i = 1;
			Map<String, List<String>> copyMap = round1Grouping();
			thisVote.totalSize = thisVote.userInputList.size();
			String runtillwinner = null;
			if (thisVote.totalSize > 1) {
				do {
					System.out.println("---------------------------------------------------");
					System.out.println("ROUND " + (i+1));
					System.out.println("---------------------------------------------------");
					runtillwinner = otherRounds(copyMap, i);
					i++;

				} while (runtillwinner.equals("continue"));
			} else {
				runtillwinner = "finish";
				// Get the 1st item and return it as there is only one vote
				thisVote.greaterThanQcand[0] = Character.toString(thisVote.userInputList.get(0).toString().charAt(0));
				thisVote.result = thisVote.greaterThanQcand[0];
			}

			if (runtillwinner.equals("finish")) {
				System.out.println("---------------------------------------------------");
				System.out.println("Winner Candidate : " + thisVote.greaterThanQcand[0]);
				thisVote.result = thisVote.greaterThanQcand[0];
			} else if (runtillwinner.equals("rhandomize")) {
				thisVote.result = randomPick();
			}
			System.out.println("---------------------------------------------------");

			return (thisVote.result != null) ? thisVote.result.toUpperCase() : "Error";
		} catch (Exception e) {
			throw new VoteSystemException("Exception", e.getCause());
		}

	}

	/**
	 * Processing for rounds 1-n
	 * 
	 * @param groupMap
	 * @param i
	 * @return
	 * @throws VoteSystemException
	 */
	public String otherRounds(Map<String, List<String>> groupMap, int i) throws VoteSystemException {
		try {
			String rhOrNext = "";

			for (Map.Entry<String, List<String>> value : groupMap.entrySet()) {
				int removedItems = 0;
				int totalItems = 0;
				if (value.getValue().size() == i) {
					for (int k = 0; k < value.getValue().size(); k++) {
						for (int pref = 0; pref < value.getValue().get(k).toString().length(); pref++) {
							//If the current vote and the candidate key are same and the size of the list is 1							
							if (value.getValue().get(k).toString().length() == 1) {
								System.out.println("Eliminated Vote : " + value.getValue().get(k));
								thisVote.noOfVotesMap.put(value.getKey(), 0);
								groupMap.remove(value.getKey(), value.getValue().get(k));
								removedItems++;
							}
							//to avoid the in finte loop 
							int nowSize = 0;
							String current = Character.toString(value.getValue().get(k).toString().charAt(pref));
							if (value.getKey().equals(current)) {
								continue;
							}
							if (groupMap.containsKey(current) && thisVote.noOfVotesMap.get(current) != 0) {

								List headFinder = groupMap.get(current);
								headFinder.add(value.getValue().get(k));
								thisVote.noOfVotesMap.put(current, headFinder.size());
								System.out.println(
										value.getValue().get(k) + " is moved to (Coz of minimum vote): " + current);
								nowSize = thisVote.noOfVotesMap.get(value.getKey());
								thisVote.noOfVotesMap.put(value.getKey(), nowSize - 1);
								break;
							} else {
								// search till the last item in the string and then remove it
								if (pref == value.getValue().get(k).toString().length() - 1) {
									nowSize = thisVote.noOfVotesMap.get(value.getKey());
									thisVote.noOfVotesMap.put(value.getKey(), nowSize - 1);
									System.out.println("Eliminated Vote : " + value.getValue().get(k));
									groupMap.remove(value.getKey(), value.getValue().get(k));
									removedItems++;
								}
							}
						}
					}
				}

				// to get the possible winning candidates
				for (Map.Entry<String, Integer> sizeList : thisVote.noOfVotesMap.entrySet()) {
					if (sizeList.getValue() != 0)
						totalItems++;
				}

				thisVote.totalSize = thisVote.totalSize - removedItems;
				thisVote.quota = (thisVote.totalSize) / 2;
				thisVote.otherCands = new String[totalItems];
				int si = 0;
				Set checkSet = new HashSet();
				for (Map.Entry<String, Integer> sizeList : thisVote.noOfVotesMap.entrySet()) {
					if (sizeList.getValue() != 0) {
						if (sizeList.getValue().doubleValue() > thisVote.quota) {
							thisVote.greaterThanQcand[0] = sizeList.getKey();
							rhOrNext = "finish";
						} else {
							checkSet.add(sizeList.getValue());
							thisVote.otherCands[si] = sizeList.getKey();
							si++;
						}
					}
				}
				if (!rhOrNext.equals("finish")) {

					if (thisVote.greaterThanQcand[0] == null && checkSet.size() != 1) {
						rhOrNext = "continue";
					} else if (checkSet.size() == 1 && thisVote.otherCands[1] != null
							&& thisVote.greaterThanQcand[0] == null) {
						rhOrNext = "rhandomize";
						thisVote.greaterThanQcand = thisVote.otherCands;
					}
				}
			}
			System.out.println("Quota for winning : " + thisVote.quota);
			System.out.println("Vote for each canditate : " + thisVote.noOfVotesMap);
			return rhOrNext;
		} catch (Exception e) {
			throw new VoteSystemException("Exception", e.getCause());
		}
	}

}
