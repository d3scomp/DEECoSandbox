package d3s.playground.ensembles;

import java.util.Date;

import d3s.ensembles.Ensemble;
import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.RobotKnowledge;
import d3s.playground.vos.CrossingFields;

public class RobotCrossingEnsemble extends
		Ensemble<RobotKnowledge, CrossingKnowledge> {

	@Override
	public void mapKnowledge(RobotKnowledge member,
			CrossingKnowledge coordinator) {
		if (member != null && coordinator != null) {
			CrossingKnowledge ck = (CrossingKnowledge) coordinator
					.getSharedKnowledge(member);
			ck.setLastUpdate(new Date());
			member.setCrossing(ck);
			RobotKnowledge mk = (RobotKnowledge) member
					.getSharedKnowledge(coordinator);
			mk.setLastUpdate(new Date());
			coordinator.addRobot(mk);
		}
	}

	@Override
	protected boolean associationSpecific(RobotKnowledge member,
			CrossingKnowledge coordinator) {
		if (member != null && coordinator != null) {
			String str = coordinator.getEntries().getFields().toString() + member.getCurrentPosition().toString();
			boolean res =  coordinator.isOnCrossing(member.getCurrentPosition());
			return coordinator.isOnCrossing(member.getCurrentPosition());
		} else
			return false;
	}
}
