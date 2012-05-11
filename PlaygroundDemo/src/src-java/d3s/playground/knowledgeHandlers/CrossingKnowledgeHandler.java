package d3s.playground.knowledgeHandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import d3s.knowledgeHandlers.KnowledgeHandler;
import d3s.knowledges.Knowledge;
import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.RobotKnowledge;
import d3s.playground.vos.CrossingFields;
import d3s.playground.vos.CrossingFieldsOccupancy;
import d3s.playground.vos.Field;
import d3s.playground.vos.Occupancy;
import d3s.playground.vos.Path;

public class CrossingKnowledgeHandler extends KnowledgeHandler {

	/**
	 * Method just performs complicated mapping. This could be done in Nomin
	 * mapping tool.
	 */
	@Override
	public void run(Knowledge knowledge) {// step function
		if (!(knowledge != null && knowledge instanceof CrossingKnowledge))
			return;
		CrossingKnowledge self = (CrossingKnowledge) knowledge;
		self.setEntriesOccupancy(new CrossingFieldsOccupancy());
		self.setObservedFieldsOccupancy(new CrossingFieldsOccupancy());
		self.setExitsOccupancy(new CrossingFieldsOccupancy());
		List<RobotKnowledge> robots = Collections.synchronizedList(self.getRobots());
		if (robots != null && robots.size() > 0) {
			for (int i = 0; i < robots.size(); i++) {
				updateRobotCrossingState(self, robots.get(i));
			}
		}
	}

	private void updateRobotCrossingState(CrossingKnowledge crossing,
			RobotKnowledge robot) {
		Field rCurrentPosition = robot.getCurrentPosition();
		List<Occupancy> occupancies = null;
		int index = crossing.getEntries().getFieldIndex(rCurrentPosition);
		if (index > -1) {// robot on entries
			occupancies = crossing.getEntriesOccupancy().getOccupancies();
			occupancies.set(
					index,
					new Occupancy(robot, getRobotPartialPath(crossing,
							robot.getPath())));
		} else {
			index = crossing.getObservedFields()
					.getFieldIndex(rCurrentPosition);
			if (index > -1) {// robot on observed
				occupancies = crossing.getObservedFieldsOccupancy()
						.getOccupancies();
				occupancies.set(index, new Occupancy(robot,
						getRobotPartialPath(crossing, robot.getPath())));
			} else {
				index = crossing.getExits().getFieldIndex(rCurrentPosition);
				if (index > -1) {// robot on exits
					occupancies = crossing.getExitsOccupancy().getOccupancies();
					occupancies.set(index, new Occupancy(robot,
							getRobotPartialPath(crossing, robot.getPath())));
				} else {
					crossing.removeRobot(robot);
				}
			}
		}
	}

	private List<Field> getRobotPartialPath(CrossingKnowledge crossing,
			Path path) {
		return getRobotPartialPath(crossing, path.getPath());
	}

	private List<Field> getRobotPartialPath(CrossingKnowledge crossing,
			List<Field> path) {
		List<Field> result = new ArrayList<Field>();
		CrossingFields exits = crossing.getExits();
		for (Field f : path) {
			if (exits.isInFields(f))
				break;
			else
				result.add(f);
		}
		return result;
	}
}
