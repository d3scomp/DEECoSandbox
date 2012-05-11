package d3s.playground.support;

import java.util.ArrayList;
import java.util.Collections;

import d3s.playground.knowledges.CrossingKnowledge;
import d3s.playground.knowledges.RobotKnowledge;

public class CrossingKnowledgeHelper {
	
	public static void copyPublicData(CrossingKnowledge from, CrossingKnowledge to) {
		to.setEntries(from.getEntries());
		to.setExits(from.getExits());
		to.setObservedFields(from.getObservedFields());
		to.setEntriesOccupancy(from.getEntriesOccupancy());
		to.setExitsOccupancy(from.getExitsOccupancy());
		to.setObservedFieldsOccupancy(from.getObservedFieldsOccupancy());
	}
	
	public static void copyPrivateData(CrossingKnowledge from, CrossingKnowledge to) {
		to.setKnowledgeHandler(from.getKnowledgeHandler());
		to.setRobots(Collections.synchronizedList(new ArrayList<RobotKnowledge>(from.getRobots())));
	}
	
}
