package com.github.bordertech.wcomponents.showcase.common;

import com.github.bordertech.wcomponents.WDropdown;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class TypeWDropdown<T> extends WDropdown {

	public TypeWDropdown() {
		super();
	}

	public TypeWDropdown(final T[] options) {
		super(options);
	}

	public TypeWDropdown(final T[] options, final boolean addNullOption) {
		super(options);
		if (addNullOption) {
			List<?> opts = new ArrayList<Object>(getOptions());
			opts.add(0, null);
			setOptions(opts);
		}
	}

	@Override
	public T getSelected() {
		return (T) super.getSelected();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDesc(final Object option, final int index) {
		String desc = super.getDesc(option, index);
		if (desc.length() > 1) {
			return desc.charAt(0) + desc.substring(1).replace('_', ' ').toLowerCase();
		} else {
			return desc;
		}
	}

}
