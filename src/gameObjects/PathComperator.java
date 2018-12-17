package gameObjects;

import java.util.Comparator;

public class PathComperator implements Comparator<PathPoint> {

	@Override
	public int compare(PathPoint arg0, PathPoint arg1) {
		if (arg1.getSeconds() > arg0.getSeconds())
			return 1;
		else if (arg1.getSeconds() < arg0.getSeconds())
			return -1;
		return 0;
	}

}
