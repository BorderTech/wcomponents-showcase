package com.github.bordertech.wcomponents.showcase.util;

import com.github.bordertech.wcomponents.showcase.Showcase;
import com.github.bordertech.wcomponents.showcase.widgets.WDateFieldShowcase;
import com.github.bordertech.wcomponents.showcase.widgets.WDropdownShowcase;
import com.github.bordertech.wcomponents.showcase.widgets.WMultiSelectShowcase;
import com.github.bordertech.wcomponents.showcase.widgets.WPartialDateFieldShowcase;
import com.github.bordertech.wcomponents.showcase.widgets.WTextAreaShowcase;
import com.github.bordertech.wcomponents.showcase.widgets.WTextFieldShowcase;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author jonathan
 */
public final class DataUtil {

	private DataUtil() {
	}

	/**
	 * TEXT showcases.
	 */
	private static final Showcase[] INPUT_TEXT_SHOWCASES = new Showcase[]{
		new WTextFieldShowcase(),
		new WTextAreaShowcase()
	};

	/**
	 * DATE showcases.
	 */
	private static final Showcase[] INPUT_DATE_SHOWCASES = new Showcase[]{
		new WDateFieldShowcase(),
		new WPartialDateFieldShowcase()
	};

	/**
	 * DATE showcases.
	 */
	private static final Showcase[] INPUT_SELECT_SHOWCASES = new Showcase[]{
		new WDropdownShowcase(),
		new WMultiSelectShowcase()
	};

	private static final Map<String, Showcase[]> SHOWCASES;

	static {
		Map<String, Showcase[]> map = new LinkedHashMap<>();
		map.put("Text inputs", INPUT_TEXT_SHOWCASES);
		map.put("Date inputs", INPUT_DATE_SHOWCASES);
		map.put("Select inputs", INPUT_SELECT_SHOWCASES);
		SHOWCASES = Collections.unmodifiableMap(map);
	}

	/**
	 *
	 * @return the showcases.
	 */
	public static Map<String, Showcase[]> getShowcases() {
		return SHOWCASES;
	}

}
