package com.starbank;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import com.starbank.exceptions.DateTimeException;
import com.starbank.exceptions.IdException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.exceptions.MessageException;
import com.starbank.model.dao.repo.MessageRepository;
import com.starbank.model.entity.Message;

public class SendMessageTest {
	
	
//	@Test
//	public void testSendMessageToUser() throws MessageException, IdException, InvalidStringException, DateTimeException {
//		assertTrue(new MessageRepository().sendMessageToUser(new Message(1, "Test message2", "Test OK", LocalDateTime.now()), 1));
//	}
}
