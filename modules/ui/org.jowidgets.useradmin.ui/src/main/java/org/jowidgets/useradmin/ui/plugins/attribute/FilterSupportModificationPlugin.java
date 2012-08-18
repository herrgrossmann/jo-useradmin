/*
 * Copyright (c) 2012, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package org.jowidgets.useradmin.ui.plugins.attribute;

import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.filter.ArithmeticOperator;
import org.jowidgets.cap.common.api.filter.IOperator;
import org.jowidgets.cap.ui.api.attribute.IAttribute;
import org.jowidgets.cap.ui.api.filter.FilterType;
import org.jowidgets.cap.ui.api.filter.IFilterType;
import org.jowidgets.cap.ui.api.plugin.IBeanTableModelPlugin;
import org.jowidgets.cap.ui.tools.attribute.FilterSupportModificationAttributeDecorator;
import org.jowidgets.cap.ui.tools.attribute.FilterSupportModificationAttributeDecorator.IFilterSupportModificator;
import org.jowidgets.plugin.api.IPluginProperties;

public final class FilterSupportModificationPlugin implements IBeanTableModelPlugin {

	@Override
	public List<IAttribute<Object>> modifyTableAttributes(
		final IPluginProperties properties,
		final List<IAttribute<Object>> attributes) {
		final List<IAttribute<Object>> result = new LinkedList<IAttribute<Object>>();
		final IFilterSupportModificator modificator = new FilterSupportModificator();
		for (final IAttribute<Object> attribute : attributes) {
			result.add(FilterSupportModificationAttributeDecorator.decorateAttribute(attribute, modificator));
		}
		return result;
	}

	private static final class FilterSupportModificator implements IFilterSupportModificator {

		@Override
		public boolean acceptFilter(final IFilterType filterType) {
			if (FilterType.ARITHMETIC_PROPERTY_FILTER == filterType) {
				return false;
			}
			else {
				return true;
			}
		}

		@Override
		public boolean acceptOperator(final IFilterType filterType, final IOperator operator) {
			if (ArithmeticOperator.EQUAL == operator) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public boolean isOperatorInvertible(final IFilterType filterType, final IOperator operator) {
			return false;
		}

	}

}
