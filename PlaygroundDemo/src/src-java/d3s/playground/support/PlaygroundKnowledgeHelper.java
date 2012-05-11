package d3s.playground.support;

import java.util.ArrayList;
import java.util.LinkedList;

import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.PlaygroundKnowledge;
import d3s.playground.knowledges.RobotKnowledge;

public class PlaygroundKnowledgeHelper {
	public static void copyPublicData(PlaygroundKnowledge from, PlaygroundKnowledge to) {
		to.setId(from.getId());
		to.setPlayground(from.getPlayground());
	}
	
	public static void copyPrivateData(PlaygroundKnowledge from, PlaygroundKnowledge to) {
		to.setKnowledgeHandler(from.getKnowledgeHandler());
		to.setCrossings(new ArrayList<CrossingKnowledge>(from.getCrossings()));
		to.setRobots(new ArrayList<RobotKnowledge>(from.getRobots()));
	}
}
