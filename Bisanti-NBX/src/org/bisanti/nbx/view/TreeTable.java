/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bisanti.nbx.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.netbeans.swing.outline.Outline;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Property;
import org.openide.nodes.Node.PropertySet;

/**
 *
 * An extension of {@link OutlineView} with some minor UI improvements:
 *<br><br>
 * 1) Replaces the default empty border with the default table border
 * <br>
 * 2) Enables the alternative show/hide columns pop-up (this is really a
 * personal preference, not a bug)
 * <br>
 * 3) Provides a visual distinction for a column that has its values filtered
 * <br><br>
 *
 * This class was created for a tutorial found at: jasonbisanti.blogspot.com
 *
 * @author Jason Bisanti
 */
public class TreeTable extends OutlineView
{
    /**
     * Default constructor that initializes the Tree Table column with the name
     * "Nodes".
     */
    public TreeTable()
    {
        this(null);
    }

    /**
     * Initializes the Tree Table column name with parameter nodesColumnLabel.
     * @param nodesColumnLabel {@link String}
     */
    public TreeTable(String nodesColumnLabel)
    {
        super(nodesColumnLabel);

        // Bug Fix #1: Add a Scroll Pane Border instead of an EmptyBorder
        super.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));

        // Bug Fix #2: Provide the alternative popup for showing/hiding columns
        final Outline outline = super.getOutline();
        outline.setPopupUsedFromTheCorner(true);

        // Bug Fix #3 (Part 1): Set a HeaderRenderer for each column that is
        // added that differentiates when table columns are sorted and rows are
        // filtered        
        final TableColumnModel columnModel = outline.getColumnModel();

        columnModel.addColumnModelListener(new TableColumnModelListener()
        {
            @Override
            public void columnAdded(TableColumnModelEvent e)
            {
                // Whenever a column is added, set our custom header renderer
                TableColumn column = columnModel.getColumn(e.getToIndex());
                column.setHeaderRenderer(new TreeTableColumnRenderer());
            }

            @Override
            public void columnRemoved(TableColumnModelEvent e){}

            @Override
            public void columnMoved(TableColumnModelEvent e){}

            @Override
            public void columnMarginChanged(ChangeEvent e){}

            @Override
            public void columnSelectionChanged(ListSelectionEvent e){}
        });

        // Bug Fix #3 (Part 2): Add a listener for the PropertyChange that is
        // fired when a new row is filtered
        outline.addPropertyChangeListener(Outline.PROP_QUICK_FILTER,
                new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                outline.getTableHeader().repaint();
            }
        });

    }

    /**
     * Adds columns pertaining to each {@link Property} for all {@link Node}s.
     * This method obtains the Properties from parameter rootContext and assumes
     * that the {@link PropertySet}s to be used are located at index 0 for
     * {@link Node} method <i>getPropertySets()</i>.
     *
     * @param rootContext Root {@link Node}
     */
    @SuppressWarnings("rawtypes")
    public void initialize(Node rootContext)
    {
        // Obtain the properties from which we'll set the column names,
        // display names, and descriptions.
        Property[] properties =
                rootContext.getPropertySets()[0].getProperties();

        if (properties != null)
        {
            for (Property col : properties)
            {
                super.addPropertyColumn(col.getName(), 
                        col.getDisplayName(),
                        col.getShortDescription());
            }
        }
    }
    
    
}
