package helper;

import controller.Controller;
import db.DB;
import model.network.responses.*;

public class ResponseHandler {

	public static Controller controller;

	public static void startResponseHandler() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (controller == null) {
						continue;
					}
					Response response = null;
					synchronized (DB.ois) {
						try {
							response = (Response) DB.ois.readObject();
							controller.handleResponse(response);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		Thread responseHandler = new Thread(runnable);
		responseHandler.setDaemon(true);
		responseHandler.start();
	}

}
