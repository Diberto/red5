/*
 * RED5 Open Source Flash Server - http://code.google.com/p/red5/
 * 
 * Copyright 2006-2012 by respective authors (see below). All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.red5.server.adapter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.red5.server.api.IAttributeStore;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IContext;
import org.red5.server.api.IScope;
import org.red5.server.api.IScopeAware;
import org.red5.server.plugin.PluginDescriptor;
import org.springframework.core.io.Resource;

/**
 * StatefulScopeWrappingAdapter class wraps stateful IScope functionality. That
 * is, it has attributes that you can work with, subscopes, associated resources
 * and connections.
 *
 */
public class StatefulScopeWrappingAdapter extends AbstractScopeAdapter implements IScopeAware, IAttributeStore {
	
    /**
     * Wrapped scope
     */
    protected IScope scope;

    /**
	 * List of plug-in descriptors
	 */
	protected List<PluginDescriptor> plugins;

	/** {@inheritDoc} */
    public void setScope(IScope scope) {
		this.scope = scope;
	}

	/**
     * Getter for wrapped scope
     *
     * @return  Wrapped scope
     */
    public IScope getScope() {
		return scope;
	}

    /**
     * Returns any plug-ins descriptors added
     *     
     * @return plug-in descriptor list
     */
	public List<PluginDescriptor> getPlugins() {
		return plugins;
	}

	/**
	 * Adds a list of plug-in descriptors
	 * 
	 * @param plugins
	 */
	public void setPlugins(List<PluginDescriptor> plugins) {
		this.plugins = plugins;
	}

	/** {@inheritDoc} */
    public Object getAttribute(String name) {
		return scope.getAttribute(name);
	}

	/** {@inheritDoc} */
    public Object getAttribute(String name, Object defaultValue) {
		return scope.getAttribute(name, defaultValue);
	}

	/** {@inheritDoc} */
    public Set<String> getAttributeNames() {
		return scope.getAttributeNames();
	}

    /**
     * Wrapper for Scope#getAttributes
     * @return       Scope attributes map
     */
    public Map<String, Object> getAttributes() {
        return scope.getAttributes();
    }

    /** {@inheritDoc} */
    public boolean hasAttribute(String name) {
		return scope.hasAttribute(name);
	}

	/** {@inheritDoc} */
    public boolean removeAttribute(String name) {
		return scope.removeAttribute(name);
	}

	/** {@inheritDoc} */
    public void removeAttributes() {
		scope.removeAttributes();
	}

	/** {@inheritDoc} */
    public boolean setAttribute(String name, Object value) {
		return scope.setAttribute(name, value);
	}

	/** {@inheritDoc} */
    public void setAttributes(IAttributeStore values) {
		scope.setAttributes(values);
	}

	/** {@inheritDoc} */
    public void setAttributes(Map<String, Object> values) {
		scope.setAttributes(values);
	}

    /**
     * Creates child scope
     * @param name        Child scope name
     * @return            <code>true</code> on success, <code>false</code> otherwise
     */
    public boolean createChildScope(String name) {
		return scope.createChildScope(name);
	}

    /**
     * Return child scope
     * @param name        Child scope name
     * @return            Child scope with given name
     */
    public IScope getChildScope(String name) {
		return scope.getScope(name);
	}

	/**
     * Iterator for child scope names
     *
     * @return  Iterator for child scope names
     */
    public Iterator<String> getChildScopeNames() {
		return scope.getScopeNames();
	}

	/**
     * Getter for set of clients
     *
     * @return  Set of clients
     */
    public Set<IClient> getClients() {
		return scope.getClients();
	}

	/**
     * Returns all connections in the scope
     *
     * @return  Connections
     */
    public Collection<Set<IConnection>> getConnections() {
		return scope.getConnections();
	}


    /**
     * Getter for context
     *
     * @return Value for context
     */
    public IContext getContext() {
		return scope.getContext();
	}

	/**
     * Getter for depth
     *
     * @return Value for depth
     */
    public int getDepth() {
		return scope.getDepth();
	}

	/**
     * Getter for name
     *
     * @return Value for name
     */
    public String getName() {
		return scope.getName();
	}

	/**
     * Return  parent scope
     *
     * @return  Parent scope
     */
    public IScope getParent() {
		return scope.getParent();
	}

	/**
     * Getter for stateful scope path
     *
     * @return Value for path
     */
    public String getPath() {
		return scope.getPath();
	}

    /**
     * Whether this scope has a child scope with given name
     * @param name       Child scope name
     * @return           <code>true</code> if it does have it, <code>false</code> otherwise
     */
    public boolean hasChildScope(String name) {
		return scope.hasChildScope(name);
	}

    /**
     * If this scope has a parent
     * @return            <code>true</code> if this scope has a parent scope, <code>false</code> otherwise
     */
    public boolean hasParent() {
		return scope.hasParent();
	}
	
	public Set<IConnection> lookupConnections(IClient client) {
		return scope.lookupConnections(client);
	}
	
	/**
	 * Returns array of resources (as Spring core Resource class instances)
	 * 
	 * @param pattern			Resource pattern
	 * @return					Returns array of resources
	 * @throws IOException		I/O exception
	 */
	public Resource[] getResources(String pattern) throws IOException {
		return scope.getResources(pattern);
	}

    /**
     * Return resource by name
     * @param path              Resource name
     * @return                  Resource with given name
     */
    public Resource getResource(String path) {
		return scope.getResource(path);
	}

}