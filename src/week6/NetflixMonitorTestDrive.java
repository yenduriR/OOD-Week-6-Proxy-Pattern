package week6;

import java.rmi.Naming;

public class NetflixMonitorTestDrive {

	public static void main(String[] args) {

		String location1 = "rmi://localhost/netflixuser/John";
		String location2 = "rmi://localhost/netflixuser/Jack";
		String location3 = "rmi://localhost/netflixuser/tom";

		NetflixRemote netflixUser;
		NetflixMonitor netflixMonitor;

		try {
			netflixUser = (NetflixRemote) Naming.lookup(location1);
			netflixMonitor = new NetflixMonitor(netflixUser);
			netflixMonitor.report();
			
			netflixUser = (NetflixRemote) Naming.lookup(location2);
			netflixMonitor = new NetflixMonitor(netflixUser);
			netflixMonitor.report();
			
			netflixUser = (NetflixRemote) Naming.lookup(location3);
			netflixMonitor = new NetflixMonitor(netflixUser);
			netflixMonitor.report();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
