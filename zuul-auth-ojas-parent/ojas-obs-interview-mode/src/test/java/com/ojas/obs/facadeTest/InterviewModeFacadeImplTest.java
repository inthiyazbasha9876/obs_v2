package com.ojas.obs.facadeTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.interviewmode.facadeimpl.InterviewModeFacadeImpl;
import com.ojas.obs.interviewmode.model.InterviewMode;
import com.ojas.obs.interviewmode.repository.InterviewModeRepository;
import com.ojas.obs.interviewmode.request.InterviewModeRequest;
import com.ojas.obs.interviewmode.response.ErrorResponse;
import com.ojas.obs.interviewmode.response.InterviewModeResponse;

public class InterviewModeFacadeImplTest {

	@InjectMocks
	InterviewModeFacadeImpl impl;
	@Mock
	InterviewModeRepository InterviewModeRepository;;
	@Spy
	InterviewMode InterviewMode;
	@Spy
	InterviewModeRequest request;
	@Spy
	InterviewModeResponse response;
	@Spy
	ErrorResponse errorResponse;

	@Before
	public void init() throws Exception {
		impl = new InterviewModeFacadeImpl();
		InterviewModeRepository = mock(InterviewModeRepository.class);
		setCollaborator(impl, "interviewModeRepository", InterviewModeRepository);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<InterviewMode> getInterviewmodeList() {
		List<InterviewMode> list = new ArrayList<InterviewMode>();
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setInterviewmodeId(1);
		InterviewMode.setInterviewMode("kusuma");
		list.add(InterviewMode);
		return list;
	}

	@Test
	public void setTransaction() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setTransactionType("save");
		request.setInterviewmodeList(getInterviewmodeList());
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setInterviewmodeId(2);
		when(InterviewModeRepository.save(request.getInterviewmodeList().get(0))).thenReturn(InterviewMode);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setTransactionNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setTransactionType("save");
		request.setInterviewmodeList(getInterviewmodeList());
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setInterviewmodeId(null);
		when(InterviewModeRepository.save(request.getInterviewmodeList().get(0))).thenReturn(InterviewMode);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setUpdateNotNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setTransactionType("update");
		request.setInterviewmodeList(getInterviewmodeList());
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setInterviewmodeId(3);
		when(InterviewModeRepository.getOne(1)).thenReturn(InterviewMode);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setUpdateNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setTransactionType("update");
		request.setInterviewmodeList(getInterviewmodeList());
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setInterviewmodeId(null);
		when(InterviewModeRepository.getOne(1)).thenReturn(InterviewMode);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setTransactionType("delete");
		request.setInterviewmodeList(getInterviewmodeList());
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setStatus(false);
		when(InterviewModeRepository.getOne(1)).thenReturn(InterviewMode);
		when(InterviewModeRepository.save(InterviewMode)).thenReturn(request.getInterviewmodeList().get(0));
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setDeleteNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setTransactionType("delete");
		request.setInterviewmodeList(getInterviewmodeList());
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setStatus(getInterviewmodeList().isEmpty());
		when(InterviewModeRepository.getOne(1)).thenReturn(InterviewMode);
		// when(InterviewModeRepository.save(InterviewMode)).thenReturn(request.getInterviewmodeList().get(0));
		when(InterviewModeRepository.save(InterviewMode)).thenReturn(InterviewMode);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotAcceptable() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setTransactionType("");
		request.setInterviewmodeList(getInterviewmodeList());
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setStatus(getInterviewmodeList().isEmpty());
		when(InterviewModeRepository.getOne(1)).thenReturn(InterviewMode);
		when(InterviewModeRepository.save(InterviewMode)).thenReturn(InterviewMode);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE,statusCode);
	}

	@Test
	public void setGetAllNotNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewmodeList());
		request.setTransactionType("getall");
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setStatus(false);
		when(InterviewModeRepository.findAll()).thenReturn(request.getInterviewmodeList());
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetAllNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewmodeList());
		request.setTransactionType("getall");
		request.getInterviewmodeList().get(0).setInterviewmodeId(1);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdNotNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewmodeList());
		request.setTransactionType("getbyid");
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setInterviewmodeId(1);
		when(InterviewModeRepository.getOne(1)).thenReturn(InterviewMode);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetByIdNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewmodeList());
		request.setTransactionType("getbyid");
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setInterviewmodeId(null);
		when(InterviewModeRepository.getOne(1)).thenReturn(InterviewMode);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdMethodNull() throws DuplicateKeyException, Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewmodeList());
		request.setTransactionType("");
		InterviewMode InterviewMode = new InterviewMode();
		when(InterviewModeRepository.getOne(1)).thenReturn(InterviewMode);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE,status);
	}
}