package model.network.responses;

import java.util.*;

public class SearchResponse implements Response {

	private static final long serialVersionUID = 8034760052086661224L;
	public List<String> usernames;

	public SearchResponse(List<String> usernames) {
		this.usernames = new ArrayList<>(usernames);
	}

}
