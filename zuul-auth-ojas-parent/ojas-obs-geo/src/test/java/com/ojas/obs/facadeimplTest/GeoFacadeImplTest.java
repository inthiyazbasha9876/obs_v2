package com.ojas.obs.facadeimplTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.facade.GeoFacade;
import com.ojas.obs.facadeimpl.GeoFacadeImpl;
import com.ojas.obs.model.Geo;
import com.ojas.obs.repository.GeoRepository;
import com.ojas.obs.request.GeoRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.GeoResponse;

public class GeoFacadeImplTest { 
	@InjectMocks
	GeoFacadeImpl geofacadeimpl;

	@Mock
	GeoRepository geoRepo;

	@Mock
	GeoFacade geofacadeImpl;

	@Spy
	GeoRequest georeq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	GeoResponse georesponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(georesponse, HttpStatus.OK);

	@Spy
	Geo geo;

	@Before
	public void init() throws Exception {
		geofacadeimpl = new GeoFacadeImpl();
		geoRepo = mock(GeoRepository.class);
		setCollaborator(geofacadeimpl, "geoRepo", geoRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<Geo> getList() {
		List<Geo> geolist = new ArrayList<Geo>();
		Geo geodatalist = new Geo();
		geodatalist.setGeoId(1);
		Geo geodatalist1 = new Geo();
		geodatalist1.setGeoId(2);
		geolist.add(geodatalist);
		geolist.add(geodatalist1);
		return geolist;
	}

	@Test
	public void testSaveError() throws SQLException {

		GeoRequest georeq = new GeoRequest();

		georeq.setTransactionType("save");

		georeq.setGeoList(this.getList());

		Geo geo2 = new Geo();

		when(geoRepo.save(geo2)).thenReturn(geo2);

		ResponseEntity<Object> saveStatus = geofacadeimpl.saveDetails(georeq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testSavesuccescheck() throws SQLException {

		GeoRequest georeq = new GeoRequest();
		List<Geo> geo = new ArrayList<Geo>();
		Geo bug = new Geo();
		bug.setGeoId(5);
		bug.setGeoname("ss");
		bug.setStatus(true);
		geo.add(bug);		
		georeq.setTransactionType("save");
		georeq.setGeoList(geo);
		when(geoRepo.saveAll(geo)).thenReturn(geo);
		ResponseEntity<Object> saveStatus = geofacadeimpl.saveDetails(georeq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);;
	}

	@Test
	public void testupdatesuccesscheck() throws SQLException {

		GeoRequest georeq = new GeoRequest();

		georeq.setTransactionType("update");

		georeq.setGeoList(this.getList());

		Geo geo2 = new Geo();
		geo2.setGeoId(1);
		List<Geo> geo = new ArrayList<Geo>();
		geo.add(geo2);
		georeq.setGeoList(geo);

		Integer id = georeq.getGeoList().get(0).getGeoId();

		when(geoRepo.findById(id)).thenReturn(Optional.of(geo2));

		ResponseEntity<Object> saveStatus = geofacadeimpl.saveDetails(georeq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdateErrorcheck() throws SQLException {

		GeoRequest georeq = new GeoRequest();

		georeq.setTransactionType("update");

		georeq.setGeoList(this.getList());

		Geo geo2 = new Geo();
		geo2.setGeoId(null);
		
		List<Geo> geo = new ArrayList<Geo>();
		geo.add(geo2);
		georeq.setGeoList(geo);

		Integer id = georeq.getGeoList().get(0).getGeoId();

		when(geoRepo.findById(id)).thenReturn(Optional.of(geo2));

		ResponseEntity<Object> saveStatus = geofacadeimpl.saveDetails(georeq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testdeletesuccesscheck() throws SQLException {

		GeoRequest georeq = new GeoRequest();

		georeq.setTransactionType("delete");

		georeq.setGeoList(this.getList());

		Geo geo2 = new Geo();
		geo2.setGeoId(1);
		
		List<Geo> geo = new ArrayList<Geo>();
		geo.add(geo2);
		georeq.setGeoList(geo);

		Integer id = georeq.getGeoList().get(0).getGeoId();

		when(geoRepo.getOne(id)).thenReturn(geo2);

		geo2.setStatus(geo2.getStatus() == null);

		when(geoRepo.save(geo2)).thenReturn(geo2);

		ResponseEntity<Object> saveStatus = geofacadeimpl.saveDetails(georeq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {

		GeoRequest georeq = new GeoRequest();
		georeq.setTransactionType("delete");
		List<Geo> geo = new ArrayList<Geo>();
		Geo geo2 = new Geo();
		geo2.setGeoId(null);
		geo2.setStatus(false);
		geo.add(geo2);
		georeq.setGeoList(geo);
		Integer id = georeq.getGeoList().get(0).getGeoId();
		when(geoRepo.getOne(id)).thenReturn(geo2);
		when(geoRepo.save(geo2)).thenReturn(geo2);
		ResponseEntity<Object> saveStatus = geofacadeimpl.saveDetails(georeq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void TestElseError() throws SQLException {

		GeoRequest georeq = new GeoRequest();

		georeq.setTransactionType("ss");

		georeq.setGeoList(this.getList());

		Geo geo2 = new Geo();

		when(geoRepo.save(geo2)).thenReturn(geo2);

		ResponseEntity<Object> saveStatus = geofacadeimpl.saveDetails(georeq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllSuccess() throws SQLException {

		GeoRequest georeq = new GeoRequest();
		georeq.setTransactionType("delete");
		List<Geo> geo = new ArrayList<Geo>();
		Geo geo2 = new Geo();
		geo2.setGeoId(null);
		geo2.setStatus(false);
		geo.add(geo2);
		georeq.setGeoList(geo);
		when(geoRepo.findAll()).thenReturn(geo);

		ResponseEntity<Object> saveStatus = geofacadeimpl.getDetails(georeq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllError() throws SQLException {
		GeoRequest georeq = new GeoRequest();
		List<Geo> geo = new ArrayList<Geo>();
		Geo geo2 = new Geo();
		geo2.setGeoId(null);
		geo2.setStatus(false);
		geo2.setGeoname(null);
		geo.isEmpty();
		georeq.setTransactionType("getAll");
		georeq.setGeoList(geo);
		when(geoRepo.findAll()).thenReturn(geo);
		ResponseEntity<Object> saveStatus = geofacadeimpl.getDetails(georeq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	@Test
	public void getAllTest() throws SQLException {
		GeoRequest georeq = new GeoRequest();
		List<Geo> geo = new ArrayList<Geo>();
		Geo geo2 = new Geo();
		geo2.setGeoname(null);
		geo2.setStatus(false);
		geo2.setGeoname(null);
		geo.add(geo2);
		georeq.setTransactionType("getAll");
		georeq.setGeoList(geo);
		when(geoRepo.findAll()).thenReturn(geo);
		ResponseEntity<Object> saveStatus = geofacadeimpl.getDetails(georeq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdError() throws SQLException {

		GeoRequest georeq = new GeoRequest();

		georeq.setGeoList(this.getList());

		Geo geo2 = new Geo();
		geo2.setGeoId(1);

		List<Geo> geo = new ArrayList<Geo>();
		geo.add(geo2);
		georeq.setGeoList(geo);

		georeq.setTransactionType("getById");
		Integer id = geo.get(0).getGeoId();

		when(geoRepo.findAll()).thenReturn(geo);

		ResponseEntity<Object> saveStatus = geofacadeimpl.getDetails(georeq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {

		GeoRequest georeq = new GeoRequest();

		georeq.setGeoList(this.getList());

		Geo geo2 = new Geo();
		geo2.setGeoId(1);

		List<Geo> geo = new ArrayList<Geo>();
		geo.add(geo2);
		georeq.setGeoList(geo);

		georeq.setTransactionType("getById");
		Integer id = geo.get(0).getGeoId();

		when(geoRepo.findById(id)).thenReturn(Optional.of(geo2));
		geo.add(geo2);

		ResponseEntity<Object> saveStatus = geofacadeimpl.getDetails(georeq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);

	}
}
