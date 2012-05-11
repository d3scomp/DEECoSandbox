package events
{
	import d3s.playground.vos.Field;
	
	import flash.events.Event;
	
	public class PGEvent extends Event
	{
		public static const DOUBLE_CLICKED:String = "PGEventDoubleClicked";
		public static const CLICKED:String = "PGEventClicked";
		public static const ROBOTS_ADDED:String = "PGEventRobotsAdded";
		
		public var field:Field;
		public var ctrl:Boolean = false;
		
		public function PGEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}