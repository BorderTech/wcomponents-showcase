package com.github.bordertech.wcomponents.showcase.util;

import com.github.bordertech.wcomponents.showcase.Showcase;
import com.github.bordertech.wcomponents.showcase.widgets.WDateFieldShowcase;
import com.github.bordertech.wcomponents.showcase.widgets.WPartialDateFieldShowcase;
import com.github.bordertech.wcomponents.showcase.widgets.WTextAreaShowcase;
import com.github.bordertech.wcomponents.showcase.widgets.WTextFieldShowcase;

/**
 *
 * @author jonathan
 */
public final class DataUtil {

	private DataUtil() {
	}

	/**
	 * TEXT examples.
	 */
	public static final Showcase[] INPUT_TEXT_EXAMPLES = new Showcase[]{
		new WTextFieldShowcase(),
		new WTextAreaShowcase()
	};

	/**
	 * DATE examples.
	 */
	public static final Showcase[] INPUT_DATE_EXAMPLES = new Showcase[]{
		new WDateFieldShowcase(),
		new WPartialDateFieldShowcase()
	};

}
