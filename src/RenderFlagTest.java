import physicsday.view.RenderFlag;

public class RenderFlagTest {
	public static void main(String[] args){
		RenderFlag[] flags = {RenderFlag.OPAQUE};
		assert(RenderFlag.contains(RenderFlag.OPAQUE, flags));
		assert(!RenderFlag.contains(RenderFlag.ANTIALIASING, flags));
		assert(!RenderFlag.contains(RenderFlag.SELECTED, flags));
		
		flags = new RenderFlag[]{RenderFlag.ANTIALIASING, RenderFlag.SELECTED};
		assert(RenderFlag.contains(RenderFlag.ANTIALIASING, flags));
		assert(RenderFlag.contains(RenderFlag.SELECTED, flags));
		assert(!RenderFlag.contains(RenderFlag.OPAQUE, flags));
	}
}
