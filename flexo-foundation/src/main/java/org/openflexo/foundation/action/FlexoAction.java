/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2011-2012, AgileBirds
 * 
 * This file is part of Flexo-foundation, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.foundation.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObservable;
import org.openflexo.foundation.FlexoProjectObject;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.action.FlexoUndoManager.FlexoActionCompoundEdit;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.utils.FlexoProgress;
import org.openflexo.foundation.utils.FlexoProgressFactory;
import org.openflexo.logging.FlexoLogger;

/**
 * Abstract representation of an action on Flexo model (model edition primitive)
 * 
 * T2 is arbitrary and should be removed in the long run. There is absolutely no guarantee on the actual type of T2. No assertions can be
 * made. T1 can be kept if we ensure that only actions of the type FlexoAction<A extends FlexoAction<A, T1>, T1 extends FlexoModelObject>
 * are actually returned for a given object of type T1.
 * 
 * @author sguerin
 */
public abstract class FlexoAction<A extends FlexoAction<A, T1, T2>, T1 extends FlexoObject, T2 extends FlexoObject> extends FlexoObservable {

	private static final Logger logger = FlexoLogger.getLogger(FlexoAction.class.getPackage().getName());

	private FlexoActionType<A, T1, T2> _actionType;
	private T1 _focusedObject;
	private Vector<T2> _globalSelection;
	private Object _context;
	private Object _invoker;
	private FlexoProgress _flexoProgress;
	private FlexoEditor _editor;

	private FlexoActionCompoundEdit compoundEdit;

	public boolean delete() {
		_editor = null;
		_flexoProgress = null;
		_invoker = null;
		_context = null;
		if (_globalSelection != null) {
			_globalSelection.clear();
		}
		_globalSelection = null;
		_focusedObject = null;
		_actionType = null;
		return true;
	}

	@Override
	public String getDeletedProperty() {
		return null;
	}

	public enum ExecutionStatus {
		NEVER_EXECUTED,
		EXECUTING_CORE,
		HAS_SUCCESSFULLY_EXECUTED,
		FAILED_EXECUTION,
		EXECUTING_UNDO_CORE,
		HAS_SUCCESSFULLY_UNDONE,
		FAILED_UNDO_EXECUTION,
		EXECUTING_REDO_CORE,
		HAS_SUCCESSFULLY_REDONE,
		FAILED_REDO_EXECUTION;

		public boolean hasActionExecutionSucceeded() {
			return this == ExecutionStatus.HAS_SUCCESSFULLY_EXECUTED;
		}

		public boolean hasActionUndoExecutionSucceeded() {
			return this == HAS_SUCCESSFULLY_UNDONE;
		}

		public boolean hasActionRedoExecutionSucceeded() {
			return this == HAS_SUCCESSFULLY_REDONE;
		}

	}

	/*private boolean _isExecutingInitializer = false;
	private boolean _isExecutingFinalizer = false;
	private boolean _isExecutingCore = false;
	private boolean _isExecuting = false;*/

	// private boolean actionExecutionSucceeded = false;

	protected ExecutionStatus executionStatus = ExecutionStatus.NEVER_EXECUTED;
	private FlexoException thrownException = null;

	public FlexoAction(FlexoActionType<A, T1, T2> actionType, T1 focusedObject, Vector<T2> globalSelection, FlexoEditor editor) {
		super();
		_editor = editor;
		_actionType = actionType;
		_focusedObject = focusedObject;
		if (globalSelection != null) {
			_globalSelection = new Vector<T2>();
			for (T2 o : globalSelection) {
				_globalSelection.add(o);
			}
		} else {
			_globalSelection = null;
		}
	}

	public FlexoActionType<A, T1, T2> getActionType() {
		return _actionType;
	}

	public String getLocalizedName() {
		if (getActionType() != null) {
			return getActionType().getLocalizedName();
		}
		return null;
	}

	public String getLocalizedDescription() {
		if (getActionType() != null) {
			return getActionType().getLocalizedDescription();
		}
		return null;
	}

	/**
	 * Sets focused object
	 */
	public void setFocusedObject(T1 focusedObject) {
		_focusedObject = focusedObject;
	}

	/**
	 * Return focused object, according to the one used in action constructor (see FlexoAction factory). If no focused object was defined,
	 * and global selection is not empty, return first object in the selection
	 * 
	 * @return a FlexoModelObject instance, representing focused object
	 */
	public T1 getFocusedObject() {
		if (_focusedObject != null) {
			return _focusedObject;
		}
		if (_globalSelection != null && _globalSelection.size() > 0) {
			return (T1) _globalSelection.firstElement();
		}
		return null;
	}

	public Vector<T2> getGlobalSelection() {
		return _globalSelection;
	}

	public A doAction() {
		if (_editor != null) {
			_editor.performAction((A) this, null);
		} else {
			try {
				doActionInContext();
			} catch (FlexoException e) {
				e.printStackTrace();
			}
		}
		return (A) this;
	}

	public ExecutionStatus getExecutionStatus() {
		return executionStatus;
	}

	public boolean hasActionExecutionSucceeded() {
		return getExecutionStatus().hasActionExecutionSucceeded();
	}

	public FlexoException getThrownException() {
		return thrownException;
	}

	public A doActionInContext() throws FlexoException {
		if (!getActionType().isEnabled(getFocusedObject(), getGlobalSelection())) {
			throw new InactiveFlexoActionException(getActionType(), getFocusedObject(), getGlobalSelection());
		}
		try {
			executionStatus = ExecutionStatus.EXECUTING_CORE;
			doAction(getContext());
			executionStatus = ExecutionStatus.HAS_SUCCESSFULLY_EXECUTED;
		} catch (FlexoException e) {
			executionStatus = ExecutionStatus.FAILED_EXECUTION;
			thrownException = e;
			throw e;
		}
		return (A) this;
	}

	public void hideFlexoProgress() {
		if (getFlexoProgress() != null) {
			getFlexoProgress().hideWindow();
			setFlexoProgress(null);
		}
	}

	protected abstract void doAction(Object context) throws FlexoException;

	public Object getContext() {
		return _context;
	}

	public void setContext(Object context) {
		_context = context;
	}

	public Object getInvoker() {
		return _invoker;
	}

	public void setInvoker(Object invoker) {
		_invoker = invoker;
	}

	public FlexoProgressFactory getFlexoProgressFactory() {
		if (getEditor() != null) {
			return getEditor().getFlexoProgressFactory();
		}
		return null;
	}

	public FlexoProgress getFlexoProgress() {
		return _flexoProgress;
	}

	public void setFlexoProgress(FlexoProgress flexoProgress) {
		_flexoProgress = flexoProgress;
	}

	public FlexoProgress makeFlexoProgress(String title, int steps) {
		if (getFlexoProgressFactory() != null) {
			setFlexoProgress(getFlexoProgressFactory().makeFlexoProgress(title, steps));
			return getFlexoProgress();
		}
		return null;
	}

	public void setProgress(String stepName) {
		if (getFlexoProgress() != null) {
			getFlexoProgress().setProgress(stepName);
		}
	}

	public void resetSecondaryProgress(int steps) {
		if (getFlexoProgress() != null) {
			getFlexoProgress().resetSecondaryProgress(steps);
		}
	}

	public void setSecondaryProgress(String stepName) {
		if (getFlexoProgress() != null) {
			getFlexoProgress().setSecondaryProgress(stepName);
		}
	}

	public Vector<FlexoObject> getGlobalSelectionAndFocusedObject() {
		return getGlobalSelectionAndFocusedObject(getFocusedObject(), getGlobalSelection());
	}

	public static Vector<FlexoObject> getGlobalSelectionAndFocusedObject(FlexoObject focusedObject,
			Vector<? extends FlexoObject> globalSelection) {
		Vector<FlexoObject> v = globalSelection != null ? new Vector<FlexoObject>(globalSelection.size() + 1) : new Vector<FlexoObject>(1);
		if (globalSelection != null) {
			v.addAll(globalSelection);
		}
		if (focusedObject != null && !v.contains(focusedObject)) {
			v.add(focusedObject);
		}
		return v;
	}

	public static <T extends FlexoObject> List<T> getGlobalSelection(T focusedObject, List<T> globalSelection) {
		Vector<T> v = globalSelection != null ? new Vector<T>(globalSelection.size() + 1) : new Vector<T>(1);
		if (globalSelection != null) {
			v.addAll(globalSelection);
		}
		if (focusedObject != null && !v.contains(focusedObject)) {
			v.add(focusedObject);
		}
		return v;
	}

	public static boolean isHomogeneousFlexoConceptInstanceSelection(FlexoObject focusedObject, Vector<FlexoObject> selection) {
		if (focusedObject instanceof FlexoConceptInstance) {
			// Flag indicating if the whole selection is composed of FlexoConceptInstance OF SAME TYPE (ie same FlexoConcept)
			boolean isHomogeneousFlexoConceptInstanceSelection = true;
			FlexoConceptInstance fci = (FlexoConceptInstance) focusedObject;
			FlexoConcept commonConcept = fci.getFlexoConcept();
			Vector<FlexoObject> globalSelection = getGlobalSelectionAndFocusedObject(focusedObject, selection);
			for (FlexoObject o : globalSelection) {
				if (!(o instanceof FlexoConceptInstance)) {
					isHomogeneousFlexoConceptInstanceSelection = false;
					break;
				}
				if (((FlexoConceptInstance) o).getFlexoConcept() != commonConcept) {
					isHomogeneousFlexoConceptInstanceSelection = false;
					break;
				}
			}
			return isHomogeneousFlexoConceptInstanceSelection;
		}
		return false;
	}

	public FlexoEditor getEditor() {
		return _editor;
	}

	private FlexoAction<?, ?, ?> ownerAction;
	private final List<FlexoAction<?, ?, ?>> embeddedActions = new ArrayList<FlexoAction<?, ?, ?>>();

	public FlexoAction<?, ?, ?> getOwnerAction() {
		return ownerAction;
	}

	protected void setOwnerAction(FlexoAction<?, ?, ?> ownerAction) {
		this.ownerAction = ownerAction;
	}

	public List<FlexoAction<?, ?, ?>> getEmbeddedActions() {
		return embeddedActions;
	}

	protected void addToEmbeddedActions(FlexoAction<?, ?, ?> embeddedAction) {
		embeddedActions.add(embeddedAction);
	}

	protected void removeFromEmbeddedActions(FlexoAction<?, ?, ?> embeddedAction) {
		embeddedActions.remove(embeddedAction);
	}

	public boolean isEmbedded() {
		return getOwnerAction() != null;
	}

	public String toSimpleString() {
		return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
	}

	@Override
	public String toString() {
		boolean isFirst = true;
		StringBuilder returned = new StringBuilder();
		returned.append("FlexoAction: ").append(getClass().getName()).append("[");
		returned.append("]");
		return returned.toString();
	}

	/**
	 * Hook that might be overriden in sub-classes while implementing dynamic reference replacement scheme
	 * 
	 * @param propertyKey
	 * @param newValue
	 * @param oldValue
	 *            TODO
	 * @param originalValue
	 *            TODO
	 */
	protected void replacedSinglePropertyValue(String propertyKey, Object newValue, Object oldValue, Object originalValue) {
	}

	/**
	 * Hook that might be overriden in sub-classes while implementing dynamic reference replacement scheme
	 * 
	 * @param propertyKey
	 * @param index
	 * @param newValue
	 * @param oldValue
	 *            TODO
	 * @param originalValue
	 *            TODO
	 */
	protected void replacedVectorPropertyValue(String propertyKey, int index, Object newValue, Object oldValue, Object originalValue) {
	}

	// TODO: Should be refactored with injectors
	public FlexoServiceManager getServiceManager() {
		if (getEditor() != null) {
			return getEditor().getServiceManager();
		} else if (getFocusedObject() instanceof FlexoProjectObject) {
			return ((FlexoProjectObject) getFocusedObject()).getServiceManager();
		}
		return null;
	}

	/**
	 * Return flag indicating if this action is valid to be executed (true if the parameters of action are well set)
	 * 
	 * @return
	 */
	public boolean isValid() {
		return true;
	}

	public FlexoActionCompoundEdit getCompoundEdit() {
		return compoundEdit;
	}

	public void setCompoundEdit(FlexoActionCompoundEdit compoundEdit) {
		this.compoundEdit = compoundEdit;
		compoundEdit.setAction(this);
	}

}
