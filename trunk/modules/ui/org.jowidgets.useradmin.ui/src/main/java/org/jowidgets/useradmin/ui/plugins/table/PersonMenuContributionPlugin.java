/*
 * Copyright (c) 2011, grossmann
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

package org.jowidgets.useradmin.ui.plugins.table;

import org.jowidgets.api.command.IAction;
import org.jowidgets.api.model.item.IActionItemModel;
import org.jowidgets.api.model.item.IActionItemModelBuilder;
import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.cap.ui.api.plugin.IBeanTableMenuContributionPlugin;
import org.jowidgets.cap.ui.api.widgets.IBeanTable;
import org.jowidgets.plugin.api.IPluginProperties;
import org.jowidgets.tools.model.item.EnabledStateVisibilityAspect;
import org.jowidgets.tools.model.item.MenuModel;
import org.jowidgets.useradmin.common.bean.IPerson;
import org.jowidgets.useradmin.ui.action.PersonActivateAction;
import org.jowidgets.useradmin.ui.action.PersonDeactivateAction;

public final class PersonMenuContributionPlugin implements IBeanTableMenuContributionPlugin<IPerson> {

	@Override
	public IMenuModel getCellMenu(final IPluginProperties properties, final IBeanTable<IPerson> table) {
		final MenuModel result = new MenuModel();
		result.addItem(createActionModelWithVisibilityAspect(new PersonActivateAction(table.getModel())));
		result.addItem(createActionModelWithVisibilityAspect(new PersonDeactivateAction(table.getModel())));
		return result;
	}

	private IActionItemModel createActionModelWithVisibilityAspect(final IAction action) {
		final IActionItemModelBuilder builder = Toolkit.getModelFactoryProvider().getItemModelFactory().actionItemBuilder();
		builder.setAction(action);
		builder.addVisibilityAspect(new EnabledStateVisibilityAspect());
		return builder.build();
	}

	@Override
	public IMenuModel getHeaderMenu(final IPluginProperties properties, final IBeanTable<IPerson> table) {
		return new MenuModel();
	}

	@Override
	public IMenuModel getTableMenu(final IPluginProperties properties, final IBeanTable<IPerson> table) {
		return new MenuModel();
	}

}
