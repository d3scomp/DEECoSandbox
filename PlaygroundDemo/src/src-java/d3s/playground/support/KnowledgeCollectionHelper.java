package d3s.playground.support;

import java.util.Collection;
import java.util.List;

import d3s.knowledges.Knowledge;
import d3s.playground.knowledges.RobotKnowledge;

public class KnowledgeCollectionHelper {
	public static void addKnowledge(Knowledge k, List collection) {
		int index = collection.indexOf(k);
		if (index < 0) {
			collection.add(k);
		} else {
			collection.set(index, k);
		}
	}
	
	public static void removeKnowledge(Knowledge k, List collection) {
		collection.remove(k);
	}
	
	public static Knowledge findKnowledgeById(Integer id, Collection<Knowledge> collection) {
		for (Knowledge k: collection) {
			if (k.getId().equals(id))
				return k;
		}
		return null;
	}
}
