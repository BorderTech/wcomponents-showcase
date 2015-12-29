package com.github.bordertech.wcomponents.showcase.util;

import com.github.bordertech.wcomponents.showcase.widgets.Showcase;
import com.github.bordertech.wcomponents.showcase.widgets.WTextAreaShowcase;
import com.github.bordertech.wcomponents.showcase.widgets.WTextFieldShowcase;

/**
 *
 * @author jonathan
 */
public final class DataUtil {

	/**
	 * AJAX examples.
	 */
	public static final Showcase[] INPUT_EXAMPLES = new Showcase[]{
		new WTextFieldShowcase(),
		new WTextAreaShowcase()
	};

	private DataUtil() {
	}

}
