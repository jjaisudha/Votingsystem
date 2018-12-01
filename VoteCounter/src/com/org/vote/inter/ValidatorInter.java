package com.org.vote.inter;

import java.util.List;
import java.util.Map;

public interface ValidatorInter {
	public Map validateInput(List<String> inputList);
	public boolean check4UniqueVote(String input);
}
