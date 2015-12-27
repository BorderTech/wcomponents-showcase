package com.github.bordertech.wcomponents.showcase.item;

/**
 *
 * @author jonathan
 */
public abstract class AbstractShowcaseItem implements ShowcaseItem {

	private final String showcaseId;

	private final String name;

	public AbstractShowcaseItem(final String showcaseId, final String name) {
		this.showcaseId = showcaseId;
		this.name = name;
	}

	public String getShowcaseId() {
		return showcaseId;
	}

	public String getName() {
		return name;
	}
}
