package com.starbank;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.starbank.model.dao.TransactionFinalizerDAO;

public class TransactionFinalizerTest {
	
	@Test
	public void testFinalizeAllUserTransactions() {
		assertTrue(new TransactionFinalizerDAO().finalizeAllUserTransactions());
	}
}
