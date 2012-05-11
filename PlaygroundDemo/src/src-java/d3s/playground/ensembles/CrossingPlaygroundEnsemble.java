package d3s.playground.ensembles;

import java.util.Date;

import d3s.ensembles.Ensemble;
import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.PlaygroundKnowledge;

public class CrossingPlaygroundEnsemble extends Ensemble<CrossingKnowledge, PlaygroundKnowledge> {

	@Override
	protected boolean associationSpecific(CrossingKnowledge member, PlaygroundKnowledge coordinator) {
		return member != null && coordinator != null;
	}

	@Override
	public void mapKnowledge(CrossingKnowledge member, PlaygroundKnowledge coordinator) {
		if (member != null && coordinator != null)  {
			CrossingKnowledge mk = (CrossingKnowledge) member.getSharedKnowledge(coordinator);
			mk.setLastUpdate(new Date());
			coordinator.addCrossing(mk);
		}
	}

}
