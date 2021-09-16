import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;


class CreateQuestionBLMockTest {
	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Event mockedEvent = Mockito.mock(Event.class);

	BLFacade sut = new BLFacadeImplementation(dataAccess);

	@SuppressWarnings("unchecked")
	@DisplayName("sut.createQuestion: The event has one question with a queryText.")
	@Test
	void test1() {
		try {
			// define paramaters
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");

			try {
				// configure Mock
				Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
				Mockito.when(dataAccess.createQuestion(Mockito.any(Event.class), Mockito.any(String.class),	Mockito.any(Integer.class))).thenThrow(QuestionAlreadyExist.class);

				// invoke System Under Test (sut)
				String queryText = "Query Text";
				Float betMinimum = 2f;
				sut.createQuestion(mockedEvent, queryText, betMinimum);

				// if the program continues fail
				fail("Mock DataAccess should be raise the exception QuestionAlreadyExist ");
			} catch (QuestionAlreadyExist e) {
				// if the program goes to this point OK
				assertTrue(true);
			} catch (EventFinished e) {
				// if the program goes to this point fail
				fail("Mock DataAccess should not raise the exception EventFinished ");
			}
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}

	}

	@Test
	@DisplayName("sut.createQuestion: The event has NOT a question with a queryText.")
	void test2() {
		try {
			// define paramaters
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			String queryText = "Query Text";
			Float betMinimum = 2f;
			try {
				Mockito.doReturn(new Question(queryText, betMinimum, mockedEvent)).when(dataAccess).createQuestion(Mockito.any(Event.class),
						Mockito.any(String.class), Mockito.any(Integer.class));

				// invoke System Under Test (sut)
				sut.createQuestion(mockedEvent, queryText, betMinimum);

				// verify the results
				ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
				ArgumentCaptor<String> questionStringCaptor = ArgumentCaptor.forClass(String.class);
				ArgumentCaptor<Float> betMinimunCaptor = ArgumentCaptor.forClass(Float.class);

				Mockito.verify(dataAccess, Mockito.times(1)).createQuestion(eventCaptor.capture(),
						questionStringCaptor.capture(), betMinimunCaptor.capture());

				assertEquals(eventCaptor.getValue(), mockedEvent);
				assertEquals(questionStringCaptor.getValue(), queryText);
				assertEquals(betMinimunCaptor.getValue(), betMinimum);

			} catch (QuestionAlreadyExist e) {
				fail("Mock DataAccess should not raise the exception QuestionAlreadyExist");
			} catch (EventFinished e) {
				fail("Mock DataAccess should not raise the exception EventFinished");
			}
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}
	}

	@Test
	@DisplayName(" sut.createQuestion: The event is null.")
	void test3() {

		try {
			// define paramaters
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			try {
				String queryText = "Query Text";
				Float betMinimum = 2f;
				Mockito.doReturn(null).when(dataAccess).createQuestion(
						Mockito.any(Event.class), Mockito.any(String.class), Mockito.any(Integer.class));

				// invoke System Under Test (sut)
				Question q = sut.createQuestion(mockedEvent, queryText, betMinimum);

				assertNull(q);

			} catch (QuestionAlreadyExist e) {
				fail("Mock DataAccess should not raise the exception QuestionAlreadyExist");
			} catch (EventFinished e) {
				fail("Mock DataAccess should not raise the exception EventFinished");
			}
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}
	}
}
