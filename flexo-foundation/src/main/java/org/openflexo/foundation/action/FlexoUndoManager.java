/*
 * (c) Copyright 2010-2011 AgileBirds
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openflexo.foundation.action;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.swing.undo.UndoableEdit;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.PamelaResourceModelFactory;
import org.openflexo.foundation.resource.PamelaResource;
import org.openflexo.model.ModelProperty;
import org.openflexo.model.undo.AddCommand;
import org.openflexo.model.undo.AtomicEdit;
import org.openflexo.model.undo.CompoundEdit;
import org.openflexo.model.undo.RemoveCommand;
import org.openflexo.model.undo.SetCommand;
import org.openflexo.model.undo.UndoManager;
import org.openflexo.toolbox.HasPropertyChangeSupport;
import org.openflexo.toolbox.StringUtils;

/**
 * An Openflexo-infrastructure specific UndoManager which manage undo though FlexoAction wrapping.<br>
 * 
 * More precisely, {@link CompoundEdit} managed by this {@link UndoManager} are {@link FlexoActionCompoundEdit} instances.<br>
 * Those instances should reference a {@link FlexoAction}, but sometimes this is not the case (when the developper has decided that the edit
 * was at really low-level and not embedded in a FlexoAction)
 * 
 * See {@link UndoManager} documentation for more details
 * 
 * @author sylvain
 * 
 */
@SuppressWarnings("serial")
public class FlexoUndoManager extends UndoManager {

	private static final Logger logger = Logger.getLogger(FlexoUndoManager.class.getPackage().getName());

	public static final String ACTION_HISTORY = "actionHistory";

	private FlexoAction<?, ?, ?> actionBeeingCurrentlyExecuted;
	private final List<IgnoreHandler> ignoreHandlers;

	public FlexoUndoManager() {
		ignoreHandlers = new ArrayList<IgnoreHandler>();
	}

	/**
	 * Adds an {@link IgnoreHandler} to the set of ignore handlers for this object, provided that it is not the same as some
	 * {@link IgnoreHandler} already in the set.
	 * 
	 * @param ignoreHandler
	 *            an {@link IgnoreHandler} to be added.
	 */
	public void addToIgnoreHandlers(IgnoreHandler ignoreHandler) {
		ignoreHandlers.add(ignoreHandler);
	}

	/**
	 * Remove an {@link IgnoreHandler} to the set of ignore handlers for this object
	 * 
	 * @param ignoreHandler
	 *            an {@link IgnoreHandler} to be removed.
	 */
	public void removeFromIgnoreHandlers(IgnoreHandler ignoreHandler) {
		ignoreHandlers.remove(ignoreHandler);
	}

	/**
	 * Called when a FlexoAction is about to be executed
	 * 
	 * @param action
	 *            : the FlexoAction that will be executed
	 */
	public void actionWillBePerformed(FlexoAction<?, ?, ?> action) {
		willDo(action);
	}

	/**
	 * Called when a FlexoAction has just been successfully executed
	 * 
	 * @param action
	 *            : the FlexoAction that has just been successfully executed
	 */
	public <A extends FlexoAction<A, T1, T2>, T1 extends FlexoObject, T2 extends FlexoObject> void actionHasBeenPerformed(A action,
			boolean success) {
		if (success) {
			hasSuccessfullyDone(action);
		} else {
			compensateFailedAction(action);
		}
	}

	@Override
	public synchronized FlexoActionCompoundEdit startRecording(String presentationName) {
		return (FlexoActionCompoundEdit) super.startRecording(presentationName);
	}

	/**
	 * Returns the the next significant FlexoAction to be undone if <code>undo</code> is invoked. This returns <code>null</code> if there
	 * are no edits to be undone, or if next edit to be undone do not refer to any FlexoAction (a pathological case where an edit has been
	 * done outside FlexoAction context - this means that an abnormal situation occurs, please warn it)
	 * 
	 * @return the next significant edit to be undone
	 */
	public FlexoAction<?, ?, ?> actionToBeUndone() {
		CompoundEdit e = editToBeUndone();
		if (e instanceof FlexoActionCompoundEdit) {
			return ((FlexoActionCompoundEdit) e).getAction();
		}
		logger.warning("Edit to be undone do not refer to any FlexoAction !");
		return null;
	}

	/**
	 * Returns the the next significant edit to be redone if <code>redo</code> is invoked. This returns <code>null</code> if there are no
	 * edits to be redone, or if edit to be redone do not refer to any FlexoAction (a pathological case where an edit has been done outside
	 * FlexoAction context - this means that an abnormal situation occurs, please warn it)
	 * 
	 * @return the next significant edit to be redone
	 */
	public FlexoAction<?, ?, ?> actionToBeRedone() {
		CompoundEdit e = editToBeRedone();
		if (e instanceof FlexoActionCompoundEdit) {
			return ((FlexoActionCompoundEdit) e).getAction();
		}
		logger.warning("Edit to be redone do not refer to any FlexoAction !");
		return null;
	}

	/**
	 * Called when a FlexoAction is about to be executed
	 * 
	 * @param action
	 *            : the FlexoAction that will be executed
	 */
	private void willDo(FlexoAction<?, ?, ?> action) {
		if (!action.isEmbedded()) {
			if (action.getCompoundEdit() != null) {
				// CompoundEdit has already been initialized
			} else {
				actionBeeingCurrentlyExecuted = action;
				FlexoActionCompoundEdit compoundEdit = startRecording(action.getLocalizedName());
				action.setCompoundEdit(compoundEdit);
			}
		} else {
			// embedded action
			if (getCurrentEdition() instanceof FlexoActionCompoundEdit) {
				((FlexoActionCompoundEdit) getCurrentEdition()).willDoEmbeddedAction(action);
			}
		}
	}

	/**
	 * Called when a FlexoAction has just been successfully executed
	 * 
	 * @param action
	 *            : the FlexoAction that has just been successfully executed
	 */
	private void hasSuccessfullyDone(FlexoAction<?, ?, ?> action) {
		if (!action.isEmbedded()) {
			stopRecording(getCurrentEdition());
			actionBeeingCurrentlyExecuted = null;
			getPropertyChangeSupport().firePropertyChange(ACTION_HISTORY, null, action);
		} else {
			// embedded action
			if (getCurrentEdition() instanceof FlexoActionCompoundEdit) {
				((FlexoActionCompoundEdit) getCurrentEdition()).hasDoneEmbeddedAction(action);
			}
		}
	}

	/**
	 * Called when a FlexoAction has just been executed but returned with failure status. We try here to compensate all edits.
	 * 
	 * @param action
	 *            : the FlexoAction that has just been executed but returned with failure status
	 */
	private void compensateFailedAction(FlexoAction<?, ?, ?> action) {
		if (!action.isEmbedded()) {
			CompoundEdit currentEdition = getCurrentEdition();
			stopRecording(currentEdition);
			actionBeeingCurrentlyExecuted = null;
			if (canUndo()) {
				undo();
				currentEdition.die();
			}
		}
	}

	@Override
	protected FlexoActionCompoundEdit makeCompoundEdit(String presentationName) {
		return new FlexoActionCompoundEdit(actionBeeingCurrentlyExecuted, presentationName);
	}

	@Override
	public boolean isIgnorable(UndoableEdit edit) {
		for (IgnoreHandler ih : ignoreHandlers) {
			if (ih.isIgnorable(edit)) {
				return true;
			}
		}
		// Debug
		if (getCurrentEdition() == null || getCurrentEdition().getPresentationName().equals(UNIDENTIFIED_RECORDING)) {
			// We are on an unidentified recording
			logger.warning("Received edit outside legal UNDO declaration: " + edit);
			// Thread.dumpStack();
			/*if (edit instanceof SetCommand) {
				if (((SetCommand) edit).getModelProperty().getPropertyIdentifier().equals("mouseClickControls")) {
					System.out.println("Celui la je l'ignore");
					return true;
				}
			}*/
		}
		return false;
	}

	@Override
	protected void fireAddEdit(UndoableEdit anEdit) {
		getPropertyChangeSupport().firePropertyChange("canUndo()", !canUndo(), canUndo());
		getPropertyChangeSupport().firePropertyChange("canRedo()", !canRedo(), canRedo());
		getPropertyChangeSupport().firePropertyChange("edits", null, anEdit);
		if (editToBeUndone() instanceof FlexoActionCompoundEdit) {
			((FlexoActionCompoundEdit) editToBeUndone()).fireActiveStatusChange();
		}
		int index = getEdits().indexOf(editToBeUndone());
		if (index > 0 && index < getEdits().size()) {
			UndoableEdit previousEdit = getEdits().get(index - 1);
			if (previousEdit instanceof FlexoActionCompoundEdit) {
				((FlexoActionCompoundEdit) previousEdit).fireActiveStatusChange();
			}
		}
	}

	@Override
	protected void fireUndo() {

		super.fireUndo();

		// We override here the default behaviour of UndoManager by notifying all
		// was is required for GUI to correctely react to this new status. This includes
		// result of canUndo() and canRedo(), as well as 'isActive' status for each concerned FlexoActionCompoundEdit

		getPropertyChangeSupport().firePropertyChange("canUndo()", !canUndo(), canUndo());
		getPropertyChangeSupport().firePropertyChange("canRedo()", !canRedo(), canRedo());

		if (editToBeUndone() instanceof FlexoActionCompoundEdit) {
			((FlexoActionCompoundEdit) editToBeUndone()).fireActiveStatusChange();
		}
		if (editToBeRedone() instanceof FlexoActionCompoundEdit) {
			((FlexoActionCompoundEdit) editToBeRedone()).fireActiveStatusChange();
		}
	}

	@Override
	protected void fireRedo() {

		super.fireRedo();

		// We override here the default behaviour of UndoManager by notifying all
		// was is required for GUI to correctely react to this new status. This includes
		// result of canUndo() and canRedo(), as well as 'isActive' status for each concerned FlexoActionCompoundEdit

		getPropertyChangeSupport().firePropertyChange("canUndo()", !canUndo(), canUndo());
		getPropertyChangeSupport().firePropertyChange("canRedo()", !canRedo(), canRedo());
		// getPropertyChangeSupport().firePropertyChange("edits", null, getEdits());
		if (editToBeUndone() instanceof FlexoActionCompoundEdit) {
			((FlexoActionCompoundEdit) editToBeUndone()).fireActiveStatusChange();
		}
		if (editToBeRedone() instanceof FlexoActionCompoundEdit) {
			((FlexoActionCompoundEdit) editToBeRedone()).fireActiveStatusChange();
		}
		int index = getEdits().indexOf(editToBeUndone());
		if (index > 0 && index < getEdits().size()) {
			UndoableEdit previousEdit = getEdits().get(index - 1);
			if (previousEdit instanceof FlexoActionCompoundEdit) {
				((FlexoActionCompoundEdit) previousEdit).fireActiveStatusChange();
			}
		}
	}

	/**
	 * An Openflexo-specific CompoundEdit wrapping all edits of a FlexoAction<br>
	 * Note that at the creation of this {@link CompoundEdit}, the {@link FlexoAction} might be null and set later<br>
	 * This allows to deal with actions requiring some work on model before to really execute it.
	 * 
	 * @author sylvain
	 * 
	 */
	public class FlexoActionCompoundEdit extends CompoundEdit implements HasPropertyChangeSupport {

		private FlexoAction<?, ?, ?> action;
		private final PropertyChangeSupport pcSupport;

		private final StackTraceElement[] stackTrace;

		private FlexoActionCompoundEdit owner = null;
		private final List<FlexoActionCompoundEdit> embeddedFlexoActionCompoundEdits;
		private FlexoActionCompoundEdit currentEmbeddedFlexoActionCompoundEdit = null;

		public FlexoActionCompoundEdit(FlexoAction<?, ?, ?> action, String presentationName) {
			super(action != null ? action.getLocalizedName() : presentationName);
			this.action = action;
			pcSupport = new PropertyChangeSupport(this);
			stackTrace = new Exception().getStackTrace();
			embeddedFlexoActionCompoundEdits = new ArrayList<FlexoActionCompoundEdit>();
		}

		public FlexoActionCompoundEdit(FlexoActionCompoundEdit owner, FlexoAction<?, ?, ?> action) {
			super(action.getLocalizedName());
			this.action = action;
			pcSupport = new PropertyChangeSupport(this);
			stackTrace = new Exception().getStackTrace();
			embeddedFlexoActionCompoundEdits = new ArrayList<FlexoActionCompoundEdit>();
			this.owner = owner;
		}

		@Override
		public PropertyChangeSupport getPropertyChangeSupport() {
			return pcSupport;
		}

		@Override
		public String getDeletedProperty() {
			return null;
		}

		/**
		 * Called when an embedded FlexoAction is about to be executed
		 * 
		 * @param action
		 *            : the FlexoAction that will be executed
		 */
		private void willDoEmbeddedAction(FlexoAction<?, ?, ?> action) {
			if (action.getOwnerAction() == getAction()) {
				System.out.println("OK, j'execute bien " + action + " au sein de " + getAction());
				currentEmbeddedFlexoActionCompoundEdit = new FlexoActionCompoundEdit(this, action);
			}
		}

		/**
		 * Called when an embedded FlexoAction has been executed
		 * 
		 * @param action
		 *            : the FlexoAction that will be executed
		 */
		private void hasDoneEmbeddedAction(FlexoAction<?, ?, ?> action) {
			if (action.getOwnerAction() == getAction()) {
				System.out.println("OK, j'ai fini d'executer " + action + " au sein de " + getAction());
				embeddedFlexoActionCompoundEdits.add(currentEmbeddedFlexoActionCompoundEdit);
				currentEmbeddedFlexoActionCompoundEdit = null;
			}
		}

		@Override
		public boolean addEdit(UndoableEdit anEdit) {
			// Always aggreate edits in root
			boolean returned = super.addEdit(anEdit);
			// But also store edits in embedded FlexoActionCompoundEdit
			if (currentEmbeddedFlexoActionCompoundEdit != null) {
				currentEmbeddedFlexoActionCompoundEdit.addEdit(anEdit);
			}
			return returned;
		}

		public List<FlexoActionCompoundEdit> getEmbeddedFlexoActionCompoundEdits() {
			return embeddedFlexoActionCompoundEdits;
		}

		public FlexoActionCompoundEdit getCurrentEmbeddedFlexoActionCompoundEdit() {
			return currentEmbeddedFlexoActionCompoundEdit;
		}

		/**
		 * Fire 'isActive' status notification
		 */
		public void fireActiveStatusChange() {
			getPropertyChangeSupport().firePropertyChange("isActive", !isActive(), isActive());
			getPropertyChangeSupport().firePropertyChange("presentationName", null, getPresentationName());
		}

		public FlexoAction<?, ?, ?> getAction() {
			return action;
		}

		public void setAction(FlexoAction<?, ?, ?> action) {
			this.action = action;
		}

		public boolean isEmbedded() {
			return owner != null;
		}

		public boolean isActive() {
			if (isEmbedded()) {
				return owner.isActive();
			}
			return FlexoUndoManager.this.editToBeUndone() == this;
		}

		public PamelaResource<?, ?> getResource(AtomicEdit<?> edit) {
			if (edit.getModelFactory() instanceof PamelaResourceModelFactory) {
				return ((PamelaResourceModelFactory<?>) edit.getModelFactory()).getResource();
			}
			return null;
		}

		public ModelProperty<?> getProperty(AtomicEdit<?> edit) {
			if (edit instanceof SetCommand) {
				return ((SetCommand<?>) edit).getModelProperty();
			} else if (edit instanceof AddCommand) {
				return ((AddCommand<?>) edit).getModelProperty();
			} else if (edit instanceof RemoveCommand) {
				return ((RemoveCommand<?>) edit).getModelProperty();
			}
			return null;
		}

		public Object getOldValue(AtomicEdit<?> edit) {
			if (edit instanceof SetCommand) {
				return ((SetCommand<?>) edit).getOldValue();
			} else if (edit instanceof AddCommand) {
				return null;
			} else if (edit instanceof RemoveCommand) {
				return ((RemoveCommand<?>) edit).getRemovedValue();
			}
			return null;
		}

		public Object getNewValue(AtomicEdit<?> edit) {
			if (edit instanceof SetCommand) {
				return ((SetCommand<?>) edit).getNewValue();
			} else if (edit instanceof AddCommand) {
				return ((AddCommand<?>) edit).getAddedValue();
			} else if (edit instanceof RemoveCommand) {
				return null;
			}
			return null;
		}

		public String getStackTraceAsString() {
			if (_stackTraceAsString != null) {
				return _stackTraceAsString;
			} else if (stackTrace != null) {
				StringBuilder returned = new StringBuilder();
				int beginAt;
				beginAt = 6;
				for (int i = beginAt; i < stackTrace.length; i++) {
					// returned += ("\tat " + stackTrace[i] + "\n");
					returned.append("\t").append("at ").append(stackTrace[i]).append('\n');
				}
				return returned.toString();
			} else {
				return "StackTrace not available";
			}
		}

		private String _stackTraceAsString;

		public void printStackTrace() {
			System.err.println("Stack trace for '" + this + "':");
			StringTokenizer st = new StringTokenizer(getStackTraceAsString(), StringUtils.LINE_SEPARATOR);
			while (st.hasMoreTokens()) {
				System.err.println("\t" + st.nextToken());
			}
		}

	}

	/**
	 * Interface implemented by a delegate that filter undoable edits that should be ignored
	 * 
	 * @author sylvain
	 * 
	 */
	public static interface IgnoreHandler {
		public boolean isIgnorable(UndoableEdit edit);
	}

	// Try to avoid using it, since this might be really dangerous
	@Deprecated
	public static class IgnoreAll implements IgnoreHandler {
		@Override
		public boolean isIgnorable(UndoableEdit edit) {
			return true;
		}
	}
}
