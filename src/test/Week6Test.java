package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import week6.NetflixMonitor;
import week6.NetflixRemote;
import week6.NetflixUser;

class Week6Test {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@BeforeEach
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@AfterEach
	public void tearDown() throws Exception {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test
	void test() throws IOException, ExportException, AlreadyBoundException, NotBoundException {

		////////// SETTING SERVER //////////

		NetflixUser user1 = new NetflixUser("John", "United States", 135);
		NetflixUser user2 = new NetflixUser("Jack", "United Kingdom", 50);
		NetflixUser user3 = new NetflixUser("Tom", "Australia", 25);

		NetflixRemote stub1 = null;
		NetflixRemote stub2 = null;
		NetflixRemote stub3 = null;

		// CREATING STUBS //

		try {
			stub1 = (NetflixRemote) UnicastRemoteObject.exportObject(user1, 0);
		} catch (Exception e) {
			stub1 = (NetflixRemote) UnicastRemoteObject.toStub(user1);
		}

		try {
			stub2 = (NetflixRemote) UnicastRemoteObject.exportObject(user2, 0);
		} catch (Exception e) {
			stub2 = (NetflixRemote) UnicastRemoteObject.toStub(user2);
		}

		try {
			stub3 = (NetflixRemote) UnicastRemoteObject.exportObject(user3, 0);
		} catch (Exception e) {
			stub3 = (NetflixRemote) UnicastRemoteObject.toStub(user3);
		}

		// CREATING REGISTRY //
		Registry registry = null;

		try {
			registry = LocateRegistry.createRegistry(1099);
		} catch (ExportException e) {
			registry = LocateRegistry.getRegistry(1099);
		}

		// BINDING //
		registry.bind("rmi://localhost/netflixuser/John", stub1);
		registry.bind("rmi://localhost/netflixuser/Jack", stub2);
		registry.bind("rmi://localhost/netflixuser/Tom", stub3);

		
		
		////////// SETTING CLIENT //////////

		// RMI CALLS TEST //
		NetflixRemote user;
		NetflixMonitor monitor;

		// John - US
		user = (NetflixRemote) registry.lookup("rmi://localhost/netflixuser/John");
		monitor = new NetflixMonitor(user);
		monitor.report();
		assertEquals("Netflix User: John" + System.lineSeparator() + "Location: United States" + System.lineSeparator()
				+ "Minutes Watched: 135" + System.lineSeparator(), outContent.toString());
		outContent.reset();

		// Jack - UK
		user = (NetflixRemote) registry.lookup("rmi://localhost/netflixuser/Jack");
		monitor = new NetflixMonitor(user);
		monitor.report();
		assertEquals("Netflix User: Jack" + System.lineSeparator() + "Location: United Kingdom" + System.lineSeparator()
				+ "Minutes Watched: 50" + System.lineSeparator(), outContent.toString());
		outContent.reset();

		// Tom - Aus
		user = (NetflixRemote) registry.lookup("rmi://localhost/netflixuser/Tom");
		monitor = new NetflixMonitor(user);
		monitor.report();
		assertEquals("Netflix User: Tom" + System.lineSeparator() + "Location: Australia" + System.lineSeparator()
				+ "Minutes Watched: 25" + System.lineSeparator(), outContent.toString());
		outContent.reset();

		
		
		////////// CLOSING SERVER //////////

		// unregister
		UnicastRemoteObject.unexportObject(user1, true);
		UnicastRemoteObject.unexportObject(user2, true);
		UnicastRemoteObject.unexportObject(user3, true);
		UnicastRemoteObject.unexportObject(registry, true);

	}

}
