/**
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 * 
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.vespucci_model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import de.tud.cs.st.vespucci.vespucci_model.AbstractEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Shape</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.AbstractEnsembleImpl#getSourceConnections <em>Source Connections</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.AbstractEnsembleImpl#getTargetConnections <em>Target Connections</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.AbstractEnsembleImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.AbstractEnsembleImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.AbstractEnsembleImpl#getQuery <em>Query</em>}</li>
 *   <li>{@link de.tud.cs.st.vespucci.vespucci_model.impl.AbstractEnsembleImpl#getKey <em>Key</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractEnsembleImpl extends EObjectImpl implements AbstractEnsemble {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = "<description>";

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getQuery() <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected static final String QUERY_EDEFAULT = "empty";

	/**
	 * The cached value of the '{@link #getQuery() <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected String query = QUERY_EDEFAULT;

	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractEnsembleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Vespucci_modelPackage.Literals.ABSTRACT_ENSEMBLE;
	}

	/**Filters the connections and shows the derived source list.
	 * Returns all <code>Connections</code> which point from another <code>Shape</code> to this one.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @author Robert Cibulla
	 * @return EList<Connection>
	 */
	public EList<Connection> getSourceConnections() {
		List<Connection> connections = getConnectionsHelper(true);
		return new EcoreEList.UnmodifiableEList<Connection>(this, 
				Vespucci_modelPackage.Literals.ABSTRACT_ENSEMBLE__SOURCE_CONNECTIONS, connections.size(), connections.toArray());
	}
	
	
	
	/**Filters the connections and shows the derived target list.
	 * Returns all <code>Connections</code> which point from this <code>Shape</code> to another.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @author Robert Cibulla
	 * @return EList<Connection> 
	 */
	public EList<Connection> getTargetConnections() {
		List<Connection> connections = getConnectionsHelper(false);
		return new EcoreEList.UnmodifiableEList<Connection>(this, 
				Vespucci_modelPackage.Literals.ABSTRACT_ENSEMBLE__TARGET_CONNECTIONS, connections.size(), connections.toArray());
	}
	
	
	/**
	 * Helper method to collect connections.
	 * @author Robert Cibulla
	 * @generated NOT
	 * @param boolean source - determines whether source or target connections are collected.
	 * return List<Connection>;
	 */
	private List<Connection> getConnectionsHelper(boolean source){
		//get DiagramReference:
		ArchitectureModel tempRootReference = getRootReferenceHelper();
		//temporary Arraylist to collect valid Connections
		List<Connection> connections = new ArrayList<Connection>();
		//collect all Connections where the current shape is the Target:
		if (tempRootReference != null) {
			for (Connection conn : tempRootReference.getConnections()) {
				resolveTemporaryConnections(source, conn, connections);
			}
		}
		return connections;
	}
	
	/**
	 *Private method to get the ArchitectureModel.
	 * 
	 * @generated NOT
	 * @author Robert Cibulla
	 * @return ArchitectureModel - reference to the ArchitectureModel
	 */
	private ArchitectureModel getRootReferenceHelper() {
		// temporary variables used to navigate to top node

		EObject parent = this.eContainer();

		while (!(parent instanceof ArchitectureModel) && parent != null) {
			parent = parent.eContainer();
		}
		return (ArchitectureModel) parent;
	}

	/**
	 * Collects the connections and resolves temporary connection references.
	 * @generated NOT
	 * @author Robert Cibulla
	 */
	private void resolveTemporaryConnections(boolean source, Connection con, List<Connection> connections){
		//decide whether method is used in for getSourceConnections or getTargetConnections
		if(source){
			//check if connection is either temporary or temporary on the wrong end (empty OriginalSource/-Target)
			if(!con.isTemp() || (con.getOriginalTarget() != null && con.getOriginalTarget().isEmpty())){
				if(con.getTarget().equals(this))
					connections.add(con);
			} else {
				if(con.getOriginalTarget().get(0).equals(this))
					connections.add(con);
			}
		} else {
			//check if connection is either temporary or temporary on the wrong end (empty OriginalSource/-Target)
			if(!con.isTemp() || (con.getOriginalSource() != null && con.getOriginalSource().isEmpty())){
				if(con.getSource().equals(this))
					connections.add(con);
			} else {
				if(con.getOriginalSource().get(0).equals(this))
					connections.add(con);
			}
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.ABSTRACT_ENSEMBLE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.ABSTRACT_ENSEMBLE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQuery(String newQuery) {
		String oldQuery = query;
		query = newQuery;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.ABSTRACT_ENSEMBLE__QUERY, oldQuery, query));
	}

	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 * @author Robert Cibulla
	 */
	public String getKey() {
		if (key == null)
			this.key = EcoreUtil.generateUUID();
		return key;
	   }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Vespucci_modelPackage.ABSTRACT_ENSEMBLE__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__SOURCE_CONNECTIONS:
				return getSourceConnections();
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__TARGET_CONNECTIONS:
				return getTargetConnections();
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__NAME:
				return getName();
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__DESCRIPTION:
				return getDescription();
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__QUERY:
				return getQuery();
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__KEY:
				return getKey();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__SOURCE_CONNECTIONS:
				getSourceConnections().clear();
				getSourceConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__TARGET_CONNECTIONS:
				getTargetConnections().clear();
				getTargetConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__NAME:
				setName((String)newValue);
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__QUERY:
				setQuery((String)newValue);
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__KEY:
				setKey((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__SOURCE_CONNECTIONS:
				getSourceConnections().clear();
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__TARGET_CONNECTIONS:
				getTargetConnections().clear();
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__QUERY:
				setQuery(QUERY_EDEFAULT);
				return;
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__KEY:
				setKey(KEY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__SOURCE_CONNECTIONS:
				return !getSourceConnections().isEmpty();
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__TARGET_CONNECTIONS:
				return !getTargetConnections().isEmpty();
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__QUERY:
				return QUERY_EDEFAULT == null ? query != null : !QUERY_EDEFAULT.equals(query);
			case Vespucci_modelPackage.ABSTRACT_ENSEMBLE__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", query: ");
		result.append(query);
		result.append(", key: ");
		result.append(key);
		result.append(')');
		return result.toString();
	}

} //ShapeImpl
