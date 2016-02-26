package physicsday.view;

public enum RenderFlag {

	ANTIALIASING (0), 
	SELECTED (1), 
	OPAQUE (2);
	
	private int shift;
	private RenderFlag(int n){
		this.shift = n;
	}
	
	private static int getFlagNum(RenderFlag[] flags){
		int ret = 0;
		for(int i = 0; i < flags.length; i++){
			ret ^= 1 << flags[i].shift;
		}
		return ret;
	}
	
	public static boolean contains(RenderFlag flag, RenderFlag[] flags){
		int n = getFlagNum(flags);
		return ((n >> flag.shift) & 1) == 1;
	}
}
