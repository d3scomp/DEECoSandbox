package
{
	import d3s.playground.knowledges.CrossingKnowledge;
	import d3s.playground.knowledges.PlaygroundKnowledge;
	import d3s.playground.vos.CrossingFields;
	import d3s.playground.vos.Field;
	import d3s.playground.vos.Playground;
	
	import flash.crypto.generateRandomBytes;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	
	import view.pathfinder.Pathfinder;
	
	import view.enums.EnumMove;

	public class PlaygroundHelper
	{
		public static const FIELD_SIZE:int = 50;
		
		public static function getPlaygroundFieldById(id:int, cells:Array):Field {
			for each (var item:Field in cells)
				if (item.id == id)
					return item;
			return null;
		}
		
		public static function producePlaygroundKnowledge(map:Vector.<Vector.<int>>, id:int):PlaygroundKnowledge {
			var r:PlaygroundKnowledge = new PlaygroundKnowledge();
			r.crossings = new ArrayCollection();
			r.id = id;
			var p:Playground = new Playground();
			p.rowCount = map.length;
			p.columnCount = map[0].length;
			p.fieldSize = FIELD_SIZE;
			var crossingCells:Vector.<Field> = new Vector.<Field>();
			var mapCell:int;
			var node:Field;
			p.playground = new ArrayCollection();
			p.enabledFields = new ArrayCollection();
			p.disabledFields = new ArrayCollection();
			p.crossings = new ArrayCollection();
			for (var i:int=0; i < p.rowCount; i++)
			{
				for (var j:int=0; j < p.columnCount; j++)
				{
					node = new Field();
					node.x = j;
					node.y = i;
					node.id = i * p.columnCount + j + 1;
					p.playground.addItem(node);
					mapCell = map[i][j];
					if (mapCell == 0) {
						p.disabledFields.addItem(node);
						node.color = PlaygroundCellItemRendedrer.DISABLED_COLOR;
						continue;
					} else if (mapCell == 2) {
						crossingCells.push(node);
					}
					node.traversable = true;
					p.enabledFields.addItem(node);
				}
			}
			var crossing:CrossingFields;
			var fields:Array;
			var item:Field;
			while (crossingCells.length) {
				item = crossingCells.shift();
				crossing = new CrossingFields();
				fields = [];
				fields.push(item);
				fields.push(crossingCells.shift());
				i = 0;
				while (fields.length < 4) {
					if (crossingCells[i].x - 1 == item.x
						|| crossingCells[i].y - 1 == item.y) {
						fields.push(crossingCells[i]);
						crossingCells.splice(i, 1);
					} else 
						i++;
				}
				crossing.fields = new ArrayCollection(fields);
				p.crossings.addItem(crossing);
			};
			p.pathFinder = getPathFinder(r,p.rowCount,p.columnCount);
			p.orientationGenerator = generateOrientationPath;
			r.playground = p;
			resetPlaygroundColors(p);
			return r;
		}
		
		public static function resetPlaygroundColors(playground:Playground):void {
			for each (var n:Field in playground.playground) {
				if (playground.disabledFields.contains(n)) {
					n.color = PlaygroundCellItemRendedrer.DISABLED_COLOR;
				} else {
					n.color = PlaygroundCellItemRendedrer.ENABLED_COLOR;
				}
			}
		}
		
		private static function getPathFinder(pk:PlaygroundKnowledge, rowCount:int, colCount:int):Function {
			return function (origin:Field, destination:Field, excludedNodes:Array = null):Array {
				var getConnectedNodes:Function = function (node:Field):Array {
					var source:Array = pk.playground.playground.source;
					var index:int = source.indexOf(node);
					var neighbour:Field;
					var result:Array
					if (index == 0) {//left top corner
						result = [source[index+1], source[colCount]];
					} else if (index == source.length - 1) {//right down corner
						result = [source[index-1], source[colCount]];
					} else if (index == colCount - 1) {//top right corner
						result = [source[index-1], source[index + colCount]];
					} else if (index == source.length - colCount) {// left down corner
						result = [source[index+1], source[index - colCount]];
					} else {
						if (node.x == 0) {//left side
							result = [source[index-colCount], source[index+1], source[index+colCount]];
						} else if (node.y == 0) {// top side
							result = [source[index-1], source[index+1], source[index+colCount]];
						} else if (node.y == rowCount - 1) {//bottom
							result = [source[index-1], source[index+1], source[index - colCount]];
						} else if (node.x == colCount - 1) {//right side
							result = [source[index-1], source[index-colCount], source[index+colCount]];
						} else {
							result = [source[index-1], source[index+1], source[index-colCount], source[index+colCount]];
						}
					}
					var disabled:Array = result.filter(function (item:Field, nIndex:int, a:Array):Boolean {
						return pk.playground.disabledFields.contains(item) > 0;
					});
					var opposite:Field;
					for each (var d:Field in disabled) {
						for each (var o:Field in result) {
							if (o != d 
								&& (Math.abs(o.x - d.x) == 2 || Math.abs(o.y - d.y) == 2)) {
								opposite = o;
								break;
							}
						}
						if (opposite) {
							result.splice(result.indexOf(o), 1);
						}
						result.splice(result.indexOf(d), 1);
					}
					var crossingIndex:int;
					var observedFields:ArrayCollection;
					var blockedFields:ArrayCollection;
					for each (var ck:CrossingKnowledge in pk.crossings) {
						observedFields = ck.observedFields.fields;
						blockedFields = ck.blockedFields.fields;
						index = observedFields.getItemIndex(node);
						if (index > -1) {
							for each (var b:Field in blockedFields[index]) {
								index = result.indexOf(b);
								if (index > -1)
									result.splice(index, 1);
							}
						}
					}
					if (excludedNodes && excludedNodes.length) {
						for (var i:int=0; i < result.length; i++) {
							if (excludedNodes.indexOf(result[i]) > -1) {
								result.splice(i, 1);
							}
						}
					}
					return result;
				}
				return Pathfinder.findPath(origin, destination, getConnectedNodes);
			}
		}
		
		public static function generateOrientationPath(path:Array):Array
		{
			var result:Array=[];
			var previousNode:Field;
			path.forEach(function (item:Field, index:int, a:Array):void {
				if (index > 0) {
					previousNode = a[index-1];
					if (previousNode.x > item.x) {
						result.push(EnumMove.LEFT);
					} else if (previousNode.x < item.x) {
						result.push(EnumMove.RIGHT);
					} else if (previousNode.y < item.y) {
						result.push(EnumMove.BACKWARD);
					} else if (previousNode.y > item.y) {
						result.push(EnumMove.FORWARD);
					}
				}
			});
			return result;
		}
		
		public static function isPathAcontainedInPathB(a:ArrayCollection, b:ArrayCollection):Boolean {
			var contains:Boolean;
			for each (var fa:Field in a.source) {
				contains = false;
				for each (var fb:Field in b.source) {
					if (fa.id == fb.id) {
						contains = true;
						break;
					}
				}
				if (!contains)
					return false;
			}
			return true;
		}
		
		public static function substractPathAFromPathB(a:ArrayCollection, b:ArrayCollection):Array {
			var result:Array = [];
			for each (var fb:Field in b.source) {
				if (!containsField(fb, a))
					result.push(fb);
			}
			return result;
		}
		
		public static function containsField(field:Field, collection:ArrayCollection):Boolean {
			for each (var f:Field in collection) {
				if (f.id == field.id)
					return true;
			}
			return false;
		}

	}
}