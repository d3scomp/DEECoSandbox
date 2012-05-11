package d3s.components;

import d3s.knowledgeHandlers.KnowledgeHandler;
import d3s.knowledges.Knowledge;
import d3s.playground.runtime.DEECoRuntime;
import d3s.playground.support.EnumSchedulingType;
import d3s.playground.support.PeriodsInterval;

public class Component {
	
	
	public static DEECoRuntime runtime;

	
	
	private EnumSchedulingType schedulingType;
	private Knowledge knowledge;
	private Thread main;

	public void start() {
		if (main != null) {
			stop();
			start();
		} else {
			if (schedulingType == EnumSchedulingType.PERIODIC) {
				main = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Knowledge tempKnowledge;
							while (true) {
								tempKnowledge = getKnowledge().getSharedKnowledge(getKnowledge());// returns copy of the knowledge
								KnowledgeHandler handler = tempKnowledge.getKnowledgeHandler();
								if (handler != null) {
									handler.run(tempKnowledge);
									runtime.localKnowledgeChanged(tempKnowledge);
								}
								Thread.sleep(PeriodsInterval.LONG);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				});
				main.start();
			}
		}
	}
	
	public void stop() {
		if (main != null) {
			main.interrupt();
			main = null;
		}
	}

	public Component(final Knowledge knowledge,
			EnumSchedulingType schedulingType) {
		this.knowledge = knowledge;
		this.schedulingType = schedulingType;
	}

	/**
	 * Function called when the knowledge is changed.
	 * 
	 * @param knowledgeHandler
	 *            - handler from the part of the knowledge where the change
	 *            occurred.
	 */
	public void processKnowledge(KnowledgeHandler knowledgeHandler) {
		if (schedulingType == EnumSchedulingType.EVENT) {
			/**
			 * Start new 'Thread' if possible to handle this change. Knowledge
			 * top level handler should be run in that thread or
			 * knowledgeHandler passed in the function parameters should be
			 * deliver to the top level process.
			 */
		} else {
			/**
			 * Ignore that notification and continue to run the top level
			 * knowledge handler periodically.
			 */
		}
	}

	public Knowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

}
