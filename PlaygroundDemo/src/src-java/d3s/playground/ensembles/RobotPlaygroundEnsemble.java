package d3s.playground.ensembles;

import java.util.Date;

import d3s.ensembles.Ensemble;
import d3s.playground.knowledges.PlaygroundKnowledge;
import d3s.playground.knowledges.RobotKnowledge;

public class RobotPlaygroundEnsemble extends
		Ensemble<RobotKnowledge, PlaygroundKnowledge> {

	@Override
	protected boolean associationSpecific(RobotKnowledge member,
			PlaygroundKnowledge coordinator) {
		return member != null && coordinator != null;
	}

	@Override
	public void mapKnowledge(RobotKnowledge member,
			PlaygroundKnowledge coordinator) {
		if (member != null && coordinator != null) {
			RobotKnowledge mk = (RobotKnowledge) member
					.getSharedKnowledge(coordinator);
			mk.setLastUpdate(new Date());
			coordinator.addRobot(mk);
			PlaygroundKnowledge ck = (PlaygroundKnowledge) coordinator
					.getSharedKnowledge(member);
			ck.setLastUpdate(new Date());
			member.setPlayground(ck);
		}
	}
}
