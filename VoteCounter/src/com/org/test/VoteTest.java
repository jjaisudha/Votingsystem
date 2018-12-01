package com.org.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.org.vote.exceptions.VoteSystemException;


class VoteTest extends Abstracttest{
	

	@BeforeEach
	void readInput() throws VoteSystemException {
		super.readInput();
	}

	@AfterEach
	public void tearDown() {
		super.tearDown();
		
	}	
	@Test
	void testduplicatChar() {
		assertTrue(validate.check4UniqueVote("ABC"));
		assertTrue(validate.check4UniqueVote("EFG"));
		assertEquals(false, validate.check4UniqueVote("AAA"));
		String [] input3= {"AAA","CCC","FFF","AAA","AAA","BBB","CCC","FFF","CCC"};
		for(String in : input3) {
			assertEquals(false, validate.check4UniqueVote(in));
		}
				
	}

	@Test
	void testInvalidVote() {
		String[] input= {"KOP","LOP","ABC"};		
		validate.validateInput(addToList(input));
		assertEquals(1, vote.userInputList.size());
		String[] input2= {"MNO","LOP","UIO"};		
		validate.validateInput(addToList(input2));
		assertEquals(0, vote.userInputList.size());
			
				
	}
	

	@Test
	void testgenerateRandominput() {
		List<String> autoInputList1 = generateRandomInputs(10, "ABCDEFGHI");
		processVotes(autoInputList1);
		/*List<String> autoInputList2 = generateRandomInputs(100, "ABCDEFGHI");
		processVotes(autoInputList2);*/
	}
	@Test
	void testSpaceinInput() {
		String[] input= {" ","A B"," BC"};		
		validate.validateInput(addToList(input));
		assertEquals(2, vote.userInputList.size());
	}

	@Test
	void borderCase() {
		String[] input= {"ABC"};	
		assertEquals("A", processVotes(addToList(input)));
		// In this case the winner is randomly selected by the system,
		// i have chosen A as winner so the assertion fails if its does'nt match		
		String[] input1= {"A","C","B"};	
		assertEquals("A", processVotes(addToList(input1)));
	}

	@Test
	void testotherRandomCases() {
		// i have chosen C as winner so the assertion fails if its does'nt match		
		String[] input= {"A","A","B","c","c","c","D"};	
		assertEquals("C", processVotes(addToList(input)));
	}
	@Test
	void randomElimination1() {
		String[] input1= {"ABEF","BDFA","CIA","CBG","AD","EGCA","FCGD","GADE"};	
		processVotes(addToList(input1));		
		String[] input2= {"ABC","HIC","GAB","EFG","FGE","IAB","GBA","CAB"};
		processVotes(addToList(input2));
	}
	@Test
	void extensiveCoverageCase1() {
		String[] input2= {"BCD","AEH","HGC","DAC","CAI","IFE","EFG","HIA","DBF","HHH","H"};	
		assertEquals("H",processVotes(addToList(input2)));
	}
	
	@Test
	void extensiveCoverageCase4() {
		String[] input2= {"ACDHEF","BGCFD","BGHDA","CAEDBG","FAEGC","FGBHADC","GEFBH","HBCFDE","HCGDBE","HGEDCB"};
		assertEquals("H",processVotes(addToList(input2)));
	}
	@Test
	void extensiveCoverageCase5() {
		String[] input2= {"ACDHEF","BGCFD","BGHDA","CAEDBG","FAEGC","FGBHADC","GEFBH","HBCFDE","HCGDBE","HGEDCB"};
		assertEquals("H",processVotes(addToList(input2)));
	}
	@Test
	void extensiveCoverageCase2() {
		String[] input2= {"A","A","A","A","A","A","A","A","B","B","B","B","B","B","B","C","C","C","C","D","D","D","D","E","E","E","F","F","G","I"};
		assertEquals("A",processVotes(addToList(input2)));
	}
	@Test
	void extensiveCoverageCase3() {
		String[] input2= {"ABCD","ABCD","DCAB","ACB","DB","BI","IH","GEF","A","H","IEFG","DACF"};
		assertEquals("A",processVotes(addToList(input2)));
	}

}
