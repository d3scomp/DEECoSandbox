package d3s.playground.support;

import d3s.knowledges.Knowledge;

public class TemplateHelper {
	public static Knowledge getNewInstance(Knowledge knowledge) {
		if (knowledge != null) {
			try {
				return knowledge.getClass().newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
}
