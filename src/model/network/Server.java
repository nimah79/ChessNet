package model.network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import model.User;
import util.RadixTree;

public class Server implements Serializable {

	private static final long serialVersionUID = -602695431814626135L;
	private int port;
	private transient ServerSocket serverSocket;
	private transient Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
	private final Map<String, User> users = new ConcurrentHashMap<>();
	private final RadixTree<User> usernames = new RadixTree<>();
	private String objectFilePath;

	public Server(int port, String objectFilePath) {
		this.port = port;
		this.objectFilePath = objectFilePath;
		this.saveToFile();
	}

	public void start() {
		this.startServer();
		this.startClientListener();
	}

	private void startServer() {
		try {
			serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			System.err.println("Couldn't start server.");
			System.exit(0);
		}
	}

	/**
	 * Listen to new clients and start a new thread for each client
	 */
	private void startClientListener() {
		Server server = this;
		Thread clientListener = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Socket socket = serverSocket.accept();
						ClientHandler temp = new ClientHandler(server, socket);
						(new Thread(temp)).start();
					} catch (IOException e) {
						System.err.println("Couldn't start client handler.");
					}
				}
			}
		});
		clientListener.start();
	}

	public User getUser(String username, String password) {
		if (!this.users.containsKey(username)) {
			return null;
		}
		User user = this.users.get(username);
		if (!user.checkPassword(password)) {
			return null;
		}
		return user;
	}

	public User createUser(String username, String password) {
		if (this.users.containsKey(username)) {
			return null;
		}
		User user = new User(username, password);
		this.users.put(username, user);
		this.usernames.put(username, user);
		this.saveToFile();
		return user;
	}

	public List<String> getUsernamesStaringWith(String prefix) {
		List<String> usernames = new ArrayList<>();
		for (String username : this.users.keySet()) {
			if (username.startsWith(prefix)) {
				usernames.add(username);
			}
		}
		return usernames;
	}

	/**
	 * Remove a client by id
	 * 
	 * @param id
	 *            User id
	 * @return true if the user was exist
	 */
	boolean removeClient(String id) {
		return this.clients.remove(id) != null;
	}

	/**
	 * Serialize object and save data to file
	 */
	synchronized void saveToFile() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(
					this.objectFilePath);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					fileOutputStream);
			objectOutputStream.writeObject(this);
			objectOutputStream.close();
		} catch (Exception e) {
			System.err.println("Failed to save server data to a file.");
		}
	}

	/**
	 * Attempt to get server object from specified file path; Else, create a new
	 * server object.
	 * 
	 * @param port
	 * @param serverFilePath
	 * @return Server object
	 */
	private static Server getServer(int port, String serverFilePath) {
		Server server;
		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File(serverFilePath));
			ObjectInputStream objectInputStream = new ObjectInputStream(
					fileInputStream);
			server = (Server) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
			server = new Server(port, serverFilePath);
		}
		return server;
	}

	private void readObject(ObjectInputStream in)
			throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		this.clients = new ConcurrentHashMap<>();
	}

	public static void main(String[] args) {
		int port = 8080;
		String serverFilePath = "server.ser";
		Server server = getServer(port, serverFilePath);
		server.start();
	}

}