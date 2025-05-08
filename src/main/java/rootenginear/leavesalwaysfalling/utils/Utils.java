package rootenginear.leavesalwaysfalling.utils;

public class Utils {
	public static float normalizeGravityMultiplier(float optionFloatValue) {
		return optionFloatValue * 9.9f + 0.1f;
	}

	public static String formatFloatOneDecimal(float value) {
		return String.format("%.1f", value);
	}
}
