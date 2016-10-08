package com.starbank;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.IbanException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.model.dao.repo.AccountRepository;
import com.starbank.model.entity.CurrentAccount;
import com.starbank.model.entity.Deposit;

public class TransferMoneyTest {

//	@Test
//	public void testTransferMoneyToMyAccounts()
//			throws IbanException, AccountException, InvalidStringException, IdException {
//
//		assertTrue(new AccountRepository().transferMoneyToMyAccount(
//				new CurrentAccount(1, 80, 80, "BG62TTBB94001524310814", 1, "BG"),
//				new Deposit(1, 100, 100, "BG80BNBG96611020345678", 1, "GBP"), 1));
//	}
//
//	@Test
//	public void testTransferMoneyToOtherAccount()
//			throws IbanException, AccountException, InvalidStringException, IdException {
//		assertTrue(new AccountRepository().transferMoneyToOtherAccount(
//				new Deposit(1, 100, 100, "BG80BNBG96611020345678", 1, "GBP"), 20, "BG61TTBB94003115068734"));
//	}
}
