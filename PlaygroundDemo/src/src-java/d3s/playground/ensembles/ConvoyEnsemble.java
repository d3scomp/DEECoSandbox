package d3s.playground.ensembles;

import java.util.Date;

import d3s.ensembles.Ensemble;
import d3s.playground.knowledges.RobotKnowledge;
import d3s.playground.vos.Field;

public class ConvoyEnsemble extends Ensemble<RobotKnowledge, RobotKnowledge> {

	
	@Override
	public void mapKnowledge(RobotKnowledge member,
			RobotKnowledge coordinator) {
		if (member != null && coordinator != null) {
			RobotKnowledge ck = (RobotKnowledge) coordinator
					.getSharedKnowledge(member);
			ck.setLastUpdate(new Date());
			member.setConvoyRobot(ck);
		}
	}

	@Override
	protected boolean associationSpecific(RobotKnowledge member,
			RobotKnowledge coordinator) {
		if (member != null && coordinator != null) {
			Field membNextPosition = member.getNextPosition();
			Field coordCurPosition = coordinator.getCurrentPosition();
			return coordCurPosition.equals(membNextPosition);
		} else
			return false;
	}

}
