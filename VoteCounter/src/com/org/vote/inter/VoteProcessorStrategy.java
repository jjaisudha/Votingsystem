package com.org.vote.inter;

import java.util.List;
import java.util.Map;

import com.org.vote.exceptions.VoteSystemException;

public interface VoteProcessorStrategy {
	public Map<String, List<String>> round1Grouping() throws VoteSystemException;
	public String otherRounds(Map<String, List<String>> groupMap, int i) throws VoteSystemException;
	public String randomPick();
	public String getWinner() throws VoteSystemException;
	
}
