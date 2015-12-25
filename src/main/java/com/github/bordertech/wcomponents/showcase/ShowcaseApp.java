package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WText;

/**
 * Showcase tope level applciation class.
 *
 * @author Jonathan Austin
 * @since 1.0.3
 */
public class ShowcaseApp extends WApplication {

	/**
	 * Construct the controller.
	 */
	public ShowcaseApp() {
		add(new WText("Hello world"));
	}
}
