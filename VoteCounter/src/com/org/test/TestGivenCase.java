package com.org.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.org.vote.exceptions.VoteSystemException;

import com.org.vote.VoteCounter;

class TestGivenCase extends Abstracttest {
	
	@BeforeEach
	void readInput() throws VoteSystemException {
		super.readInput();
	}

	@AfterEach
	public void tearDown() {
		super.tearDown();	
	}
	@Test
	void oracleGivenTestcase() {
		String[] input2= {"ABC","BDA","CADB","CDAB","DA","DB","BAC","CBAD"};
		validate.validateInput(addToList(input2));
		assertEquals("B",processVotes(addToList(input2)));
	}
	
}
