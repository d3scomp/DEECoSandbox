package d3s.playground.runtime;

import java.util.LinkedList;
import java.util.List;

import d3s.ensembles.Ensemble;
import d3s.knowledges.Knowledge;

public class Ensembler {
	
	private LinkedList<Ensemble<Knowledge, Knowledge>> ensemblesDefinitions;
	
	Ensembler() {
		ensemblesDefinitions = new LinkedList<Ensemble<Knowledge, Knowledge>>();
	}
	
	public void addEnsembleDefinition(Ensemble<Knowledge, Knowledge> ensemble) {
		ensemblesDefinitions.add(ensemble);
	}
	
	public void knowledgesUpdated(List<Knowledge> knowledges) {
		if (knowledges != null) {
			performEnsembling(knowledges);
		}
	}
	
	//-------------------------- Utility functions ----------------------------------------
	
	private void performEnsembling(List<Knowledge> knowledges) {
		for (Knowledge ko: knowledges) {
			for (Knowledge ki: knowledges) {
				if (!ko.equals(ki)) {
					ensemble(ko, ki);
				}
			}
		}
	}
	
	private void ensemble(Knowledge a, Knowledge b) {
		for (Ensemble<Knowledge, Knowledge> ensemble: ensemblesDefinitions) {
			if (ensemble.association(a, b)) {
				ensemble.mapKnowledge(a, b);
			}
		}
	}
}
