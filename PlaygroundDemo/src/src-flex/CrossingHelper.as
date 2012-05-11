package
{
	import d3s.playground.knowledges.CrossingKnowledge;
	import d3s.playground.vos.CrossingFields;
	import d3s.playground.vos.Field;
	import d3s.playground.vos.Playground;
	
	import mx.collections.ArrayCollection;

	public class CrossingHelper
	{
		public static function setUp(ck:CrossingKnowledge, observed:CrossingFields, p:Playground):void {
			ck.observedFields = observed;
			ck.entries = new CrossingFields();
			ck.exits = new CrossingFields();
			ck.blockedFields = new CrossingFields();
			var ckEntries:ArrayCollection = ck.entries.fields = new ArrayCollection([]);
			var ckExits:ArrayCollection = ck.exits.fields = new ArrayCollection([]);
			var ckBlockedFields:ArrayCollection = ck.blockedFields.fields = new ArrayCollection([[],[],[],[]]);
			var cellsCopy:Array = ck.observedFields.fields.source.concat();
			var pg:ArrayCollection = p.playground;
			var colCount:int = p.columnCount;
			
			var node:Field = cellsCopy.shift();
			var index:int = pg.getItemIndex(node);
			var entry:Field = pg[index - colCount];
			var exit:Field = pg[index-1];
			ckEntries.addItem(entry);
			ckBlockedFields[0].push(entry);
			ckBlockedFields[2].push(node);
			ckExits.addItem(exit);
				
			node = cellsCopy.shift();
			index = pg.getItemIndex(node);
			entry = pg[index+1];
			exit = pg[index - colCount];
			ckEntries.addItem(entry);
			ckBlockedFields[1].push(entry);
			ckBlockedFields[0].push(node);
			ckExits.addItem(exit);
			
			node = cellsCopy.shift();
			index = pg.getItemIndex(node);
			entry = pg[index-1];
			exit = pg[index + colCount];
			ckEntries.addItem(entry);
			ckBlockedFields[2].push(entry);
			ckBlockedFields[3].push(node);
			ckExits.addItem(exit);
			
			node = cellsCopy.shift();
			index = pg.getItemIndex(node);
			entry = pg[index + colCount];
			exit = pg[index+1];
			ckEntries.addItem(entry);
			ckBlockedFields[3].push(entry);
			ckBlockedFields[1].push(node);
			ckExits.addItem(exit);
		}
	}
}