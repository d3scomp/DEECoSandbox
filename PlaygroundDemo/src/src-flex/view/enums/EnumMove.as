package view.enums
{
	[RemoteClass]
	public class EnumMove extends EnumClass
	{
		public static const FORWARD:EnumMove = new EnumMove(0);
		public static const BACKWARD:EnumMove = new EnumMove(1);
		public static const LEFT:EnumMove = new EnumMove(2);
		public static const RIGHT:EnumMove = new EnumMove(3);
		
		public function EnumMove(ordinal:int = 0)
		{
			switch (ordinal) {
				case 0: name = "FORWARD"; break;
				case 1: name = "BACKWARD"; break;
				case 2: name = "LEFT"; break;
				case 3: name = "RIGHT"; break;
			}
			this.ordinal = ordinal;
		}
		
		public static function get values():Array {
			return [FORWARD, BACKWARD, LEFT, RIGHT];
		}
	}
}