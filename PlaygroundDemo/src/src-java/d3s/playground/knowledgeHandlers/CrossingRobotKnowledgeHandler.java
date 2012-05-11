package d3s.playground.knowledgeHandlers;

import java.util.List;

import d3s.knowledgeHandlers.KnowledgeHandler;
import d3s.knowledges.Knowledge;
import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.RobotKnowledge;
import d3s.playground.vos.CrossingFieldsOccupancy;
import d3s.playground.vos.Field;
import d3s.playground.vos.Occupancy;
import flex.messaging.io.ArrayCollection;

public class CrossingRobotKnowledgeHandler extends KnowledgeHandler {

	@Override
	public void run(Knowledge knowledge) {
		if (!(knowledge != null && knowledge instanceof RobotKnowledge))
			return;
		RobotKnowledge self = (RobotKnowledge) knowledge;
		CrossingKnowledge crossing = self.getCrossing();
		Field currentPosition = self.getCurrentPosition();
		if (crossing.isOnCrossing(currentPosition)) {
			if (crossing.isOnEntries(currentPosition)) {
				if (!(isCrossingAwareOfMe(crossing, self.getId()) && enterCrossing(crossing, self))) {
					self.stop();
					return;
				}
			}
		} else {
			self.setCrossing(null);
		}
		self.move();
	}
	
	private Boolean isCrossingAwareOfMe(CrossingKnowledge crossing, Integer robotId) {
		return crossing.getEntriesOccupancy().getOccupancyByRobotId(robotId) != null;
	}

	private Boolean enterCrossing(CrossingKnowledge crossing,
			RobotKnowledge robot) {
		Boolean result = false;
		CrossingFieldsOccupancy eo = crossing.getEntriesOccupancy();
		if (eo != null) {
			Occupancy myOccupancy = crossing.getRobotOccupancy(robot.getId(),
					crossing.getEntriesOccupancy());
			if (myOccupancy != null) {
				CrossingFieldsOccupancy oo = crossing
						.getObservedFieldsOccupancy();
				if (oo != null) {
					Boolean isCrossingAvailableForMe = !isCollidingWithOthers(
							myOccupancy, oo);
					if (isCrossingAvailableForMe) {
						if (areOccupiedByOthers(crossing.getEntriesOccupancy())) {
							result = myOccupancy.equals(getFirst(crossing
									.getEntriesOccupancy()));
						} else {
							result = true;
						}
					} else {
						result = false;
					}
				}
			}
		}
		return result;
	}

	private Boolean areOccupiedByOthers(CrossingFieldsOccupancy occupancies) {
		return occupancies.getOccupancySize() > 1;
	}

	private Boolean isCollidingWithOthers(Occupancy occupancy,
			CrossingFieldsOccupancy others) {
		for (Object o : others.getOccupancies()) {
			if (o != null && !occupancy.equals(o) && areColliding(occupancy, (Occupancy) o)) {
				return true;
			}
		}
		return false;
	}

	private Boolean areColliding(Occupancy a, Occupancy b) {
		if (a == null || b == null) {
			return true;
		}
		List<Field> bPath = b.getPath();
		for (Field f : a.getPath()) {
			if (acContains(bPath, f))
				return true;
		}
		return false;
	}
	
	private boolean acContains(List<Field> collection, Field field) {
		for (Field f: collection) {
			if (f.getId().equals(field.getId()))
				return true;
		}
		return false;
	}

	private Occupancy getMostUnpatient(CrossingFieldsOccupancy cfo) {
		Occupancy result = null;
		for (Object o : cfo.getOccupancies()) {
			if (((Occupancy) o).hasReachedPatienceThreshold()) {
				if (result == null)
					result = (Occupancy) o;
				else
					result = getMostUnpatient(result, (Occupancy) o);
			}
		}

		return result;
	}

	private Occupancy getMostUnpatient(Occupancy a, Occupancy b) {
		Long waitA = a.getUnpatientTime();
		Long waitB = b.getUnpatientTime();
		if (waitA > waitB)
			return a;
		else
			return b;
	}

	private Occupancy getFirst(CrossingFieldsOccupancy cfo) {
		Occupancy result = null;
		long currentTime = Long.MAX_VALUE;
		for (Occupancy o : cfo.getOccupancies()) {
			if (result == null) {
				result = o;
			} else if (o != null) {
				if (o.getSince().getTime() == currentTime) {
					if (result.getRobotId() > o.getRobotId()) {
						result = o;
						currentTime = result.getSince().getTime();
					}
				} else if (currentTime > o.getSince().getTime()) {
					result = o;
					currentTime = result.getSince().getTime();
				}
			}
		}
		return result;
	}
}
