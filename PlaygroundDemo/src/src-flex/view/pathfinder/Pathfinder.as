package view.pathfinder
{
	import d3s.playground.vos.Field;
	
	import flash.geom.Orientation3D;

	public class Pathfinder
	{
		public static var heuristic:Function = Pathfinder.euclidianHeuristic;			
		
		public static function findPath( firstField:Field, destinationField:Field, connectedNodeFunction:Function ):Array 
		{
			var openNodes:Array = [];
			var closedNodes:Array = [];			
			
			var currentField:Field = firstField;
			var testField:Field;
			
			var l:int;
			var i:int;
		
			var connectedNodes:Array;
			var travelCost:Number = 1.0;
			
			var g:Number;
			var h:Number;
			var f:Number;
			
			currentField.g = 0;
			currentField.h = Pathfinder.heuristic(currentField, destinationField, travelCost);
			currentField.f = currentField.g + currentField.h;
			
			while (currentField != destinationField) {
				
				connectedNodes = connectedNodeFunction( currentField );			
				//trace((currentNode as Node).id + " neighbours: " +connectedNodes.toString());
				l = connectedNodes.length;
				
				for (i = 0; i < l; ++i) {
					
					testField = connectedNodes[i];
					
					if (testField == currentField || testField.traversable == false) continue;					
					
					//g = currentNode.g + Pathfinder.heuristic( currentNode, testNode, travelCost); //This is what we had to use here at Untold for our situation.
					//If you have a world where diagonal movements cost more than regular movements then you would need to determine if a movement is diagonal and then adjust
					//the value of travel cost accordingly here.
					g = currentField.g + travelCost;
					h = Pathfinder.heuristic( testField, destinationField, travelCost);
					f = g + h;
					
					if ( Pathfinder.isOpen(testField, openNodes) || Pathfinder.isClosed( testField, closedNodes) )
					{
						if(testField.f > f)
						{
							testField.f = f;
							testField.g = g;
							testField.h = h;
							testField.parentField = currentField;
						}
					}
					else {
						testField.f = f;
						testField.g = g;
						testField.h = h;
						testField.parentField = currentField;
						openNodes.push(testField);
					}
					
				}
				closedNodes.push( currentField );
				
				if (openNodes.length == 0) {
					return null;
				}
				openNodes.sortOn('f', Array.NUMERIC);
				currentField = openNodes.shift() as Field;
			}
			
			return Pathfinder.buildPath(destinationField, firstField);
		}
		
		
		public static function buildPath(destinationField:Field, startField:Field):Array {			
			var path:Array = [];
			var field:Field = destinationField;
			path.push(field);
			while (field != startField) {
				field = field.parentField;
				path.unshift( field );
			}
			if (path.indexOf(startField) < 0)
				path.unshift(startField);
			return path;			
		}
		
		public static function isOpen(field:Field, openNodes:Array):Boolean {
			
			var l:int = openNodes.length;
			for (var i:int = 0; i < l; ++i) {
				if ( openNodes[i] == field ) return true;
			}
			
			return false;			
		}
		
		public static function isClosed(field:Field, closedNodes:Array):Boolean {
			
			var l:int = closedNodes.length;
			for (var i:int = 0; i < l; ++i) {
				if (closedNodes[i] == field ) return true;
			}
			
			return false;
		}
		
		/****************************************************************************** 
		*
		*	These are our avaailable heuristics 
		*
		******************************************************************************/		
		public static function euclidianHeuristic(field:Field, destinationField:Field, cost:Number = 1.0):Number
		{
			var dx:Number = field.x - destinationField.x;
			var dy:Number = field.y - destinationField.y;
			
			return Math.sqrt( dx * dx + dy * dy ) * cost;
		}
		
		public static function manhattanHeuristic(field:Field, destinationField:Field, cost:Number = 1.0):Number
		{
			return Math.abs(field.x - destinationField.x) * cost + 
				   Math.abs(field.y + destinationField.y) * cost;
		}
		
		public static function diagonalHeuristic(field:Field, destinationField:Field, cost:Number = 1.0, diagonalCost:Number = 1.0):Number
		{
			var dx:Number = Math.abs(field.x - destinationField.x);
			var dy:Number = Math.abs(field.y - destinationField.y);
			
			var diag:Number = Math.min( dx, dy );
			var straight:Number = dx + dy;
			
			return diagonalCost * diag + cost * (straight - 2 * diag);
		}
		

	}

}