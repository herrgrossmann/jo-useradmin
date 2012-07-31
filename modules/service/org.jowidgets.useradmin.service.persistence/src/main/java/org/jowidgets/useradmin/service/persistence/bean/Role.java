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
package org.jowidgets.useradmin.service.persistence.bean;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;
import org.jowidgets.cap.service.jpa.tools.entity.EntityManagerProvider;
import org.jowidgets.useradmin.common.bean.IRole;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Role extends Bean implements IRole {

	@Basic
	@Index(name = "RoleNameIndex")
	private String name;

	@Basic
	private String description;

	@OneToMany(mappedBy = "role")
	private List<PersonRoleLink> personRoleLinks = new LinkedList<PersonRoleLink>();

	@OneToMany(mappedBy = "role")
	private List<RoleAuthorizationLink> roleAuthorizationLinks = new LinkedList<RoleAuthorizationLink>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	public List<PersonRoleLink> getPersonRoleLinks() {
		return personRoleLinks;
	}

	public void setPersonRoleLinks(final List<PersonRoleLink> personRoleLinks) {
		this.personRoleLinks = personRoleLinks;
	}

	public List<RoleAuthorizationLink> getRoleAuthorizationLinks() {
		return roleAuthorizationLinks;
	}

	public void setRoleAuthorizationLinks(final List<RoleAuthorizationLink> roleAuthorizationLinks) {
		this.roleAuthorizationLinks = roleAuthorizationLinks;
	}

	public Set<String> getAuthorizations() {
		final Set<String> result = new HashSet<String>();
		for (final RoleAuthorizationLink roleAuthorizationLink : getRoleAuthorizationLinks()) {
			final Authorization authorization = roleAuthorizationLink.getAuthorization();
			result.add(authorization.getKey());
		}
		return result;
	}

	@Override
	public boolean getInUse() {
		if (getId() != null) {
			final EntityManager entityManager = EntityManagerProvider.get();
			final TypedQuery<PersonRoleLink> query = entityManager.createQuery(
					"SELECT p FROM PersonRoleLink p WHERE p.role = :role",
					PersonRoleLink.class);
			query.setParameter("role", this);
			query.setFirstResult(0).setMaxResults(1);
			return query.getResultList().size() > 0;
		}
		else {
			return false;
		}
	}
}
