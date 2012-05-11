package d3s.ensembles;


/**
 * 
 * @author Michael
 * 
 * Base class for creating ensembles.
 *
 */
public abstract class Ensemble<MemberT, CoordinatorT> {
	
	public boolean association(Object member, Object coordinator) {
		try {
			MemberT m = (MemberT) member;
			CoordinatorT c = (CoordinatorT) coordinator;
			return associationSpecific(m, c);
		} catch (Exception e) {
			return false;
		}
	}
	
	protected abstract boolean associationSpecific(MemberT member, CoordinatorT coordinator);
	
	public abstract void mapKnowledge(MemberT member, CoordinatorT coordinator);
	
}
