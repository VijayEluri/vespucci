/**
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
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
package de.tud.cs.st.vespucci.vespucci_model.provider;


import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelFactory;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.tud.cs.st.vespucci.vespucci_model.Shape} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ShapeItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShapeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
			addQueryPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Shape_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Shape_name_feature", "_UI_Shape_type"),
				 Vespucci_modelPackage.Literals.SHAPE__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Shape_description_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Shape_description_feature", "_UI_Shape_type"),
				 Vespucci_modelPackage.Literals.SHAPE__DESCRIPTION,
				 true,
				 true,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Query feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addQueryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Shape_query_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Shape_query_feature", "_UI_Shape_type"),
				 Vespucci_modelPackage.Literals.SHAPE__QUERY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS);
			childrenFeatures.add(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns Shape.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Shape"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Shape)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Shape_type") :
			getString("_UI_Shape_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Shape.class)) {
			case Vespucci_modelPackage.SHAPE__NAME:
			case Vespucci_modelPackage.SHAPE__DESCRIPTION:
			case Vespucci_modelPackage.SHAPE__QUERY:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case Vespucci_modelPackage.SHAPE__SOURCE_CONNECTIONS:
			case Vespucci_modelPackage.SHAPE__TARGET_CONNECTIONS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createConnection()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createNotAllowed()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createOutgoing()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createIncoming()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createInAndOut()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createExpected()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createGlobalOutgoing()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createGlobalIncoming()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createWarning()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createConnection()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createNotAllowed()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createOutgoing()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createIncoming()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createInAndOut()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createExpected()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createGlobalOutgoing()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createGlobalIncoming()));

		newChildDescriptors.add
			(createChildParameter
				(Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS,
				 Vespucci_modelFactory.eINSTANCE.createWarning()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == Vespucci_modelPackage.Literals.SHAPE__SOURCE_CONNECTIONS ||
			childFeature == Vespucci_modelPackage.Literals.SHAPE__TARGET_CONNECTIONS;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return VespucciEditPlugin.INSTANCE;
	}

}
