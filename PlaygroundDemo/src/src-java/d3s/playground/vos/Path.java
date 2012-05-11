package d3s.playground.vos;

import java.io.Serializable;
import java.util.List;

public class Path implements Serializable{
	
	private static final int FIRST_ELEMENT_INDEX = 0;
	
	private List<Field> path;

	public List<Field> getPath() {
		return path;
	}

	public void setPath(List<Field> path) {
		this.path = path;
	}
	
	public void advance() {
		if (path.size() > 1)
			path.remove(FIRST_ELEMENT_INDEX);
	}

	public Field getCurrentPosition() {
		if (path != null && path.size() > 0)
			return (Field) path.get(0);
		return null;
	}
	
	public Field getNextPosition() {
		if (path != null && path.size() > 1) {
			return path.get(1);
		}
		return null;
	}
}
