package d3s.playground.vos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class CrossingFieldsOccupancy implements Serializable {
	
	private List<Occupancy> occupancies = new LinkedList<Occupancy>();
	
	public CrossingFieldsOccupancy() {
		occupancies = new LinkedList<Occupancy>();
		occupancies.add(null);
		occupancies.add(null);
		occupancies.add(null);
		occupancies.add(null);
	}
	
	public Occupancy getOccupancyByRobotId(Integer robotId) {
		for (Occupancy o: occupancies) {
			if (o != null && o.getRobotId().equals(robotId))
				return (Occupancy) o;
		}
		return null;
	}

	
	
	public List<Occupancy> getOccupancies() {
		return occupancies;
	}



	public void setOccupancies(List<Occupancy> occupancies) {
		this.occupancies = occupancies;
	}



	public Boolean isAnyOccupied() {
		for (Occupancy o: occupancies) {
			if (o != null)
				return true;
		}
		return false;
	}
	
	public int getOccupancySize() {
		int result = 0;
		for (Occupancy o: occupancies) {
			if (o != null)
				result++;
		}
		return result;
	}
	
	public Occupancy removeOccupancyForRobot(Integer robotId) {
		Occupancy o = null;
		for (int i = 0; i < occupancies.size(); i++) {
			o = occupancies.get(i);
			if (o != null && o.getRobotId().equals(robotId)) {
				occupancies.set(i, null);
				return o;
			}
		}
		return null;
	}
}
