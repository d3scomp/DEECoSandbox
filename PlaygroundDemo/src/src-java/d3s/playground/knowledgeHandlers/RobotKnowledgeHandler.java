package d3s.playground.knowledgeHandlers;

import d3s.knowledgeHandlers.KnowledgeHandler;
import d3s.knowledges.Knowledge;
import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.RobotKnowledge;

public class RobotKnowledgeHandler extends KnowledgeHandler {
	
	@Override
	public void run(Knowledge knowledge) {//step function
		if (!(knowledge != null && knowledge instanceof RobotKnowledge))
			return;
		RobotKnowledge self = (RobotKnowledge) knowledge;
		RobotKnowledge convoy  = self.getConvoyRobot();
		CrossingKnowledge crossing = self.getCrossing();
		if (convoy != null) {
			convoy.getKnowledgeHandler().run(self);
		} else if (crossing != null){
			crossing.getKnowledgeHandler().run(self);
		} else if (self.getNextPosition() != null) {
			self.move();
		} else {
			self.stop();
		}
	}	
}
