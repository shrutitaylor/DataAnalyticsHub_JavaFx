package Dao;

import java.util.Comparator;

import Model.Post;

public class PostSharesComparator implements Comparator<Post>{

	@Override
	public int compare(Post p1, Post p2) {
		return Integer.compare(p2.getShares(), p1.getShares());
	}

}
