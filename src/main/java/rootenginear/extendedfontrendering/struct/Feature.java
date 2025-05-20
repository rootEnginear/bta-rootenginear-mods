package rootenginear.extendedfontrendering.struct;

import java.util.Map;

public class Feature {
	public String characters;
	public GlobalShiftOptions global;
	public Map<String, String> classes;
	public Map<String, Map<String, PositionOptions>> positioning;
}
