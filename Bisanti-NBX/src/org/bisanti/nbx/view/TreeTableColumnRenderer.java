/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bisanti.nbx.view;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import org.netbeans.swing.etable.ETable;
import org.netbeans.swing.etable.ETableColumn;

/*
 * A custom {@link TableCellRenderer} for a {@link JTableHeader} that adds
 * an ascending/descending arrow when column data is sorted and sets bold
 * font when column data is filtered.
 */
public class TreeTableColumnRenderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        // Update our base renderer
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (table instanceof ETable)
        {
            // If we're rendering a filtered column, bold the text
            if (((ETable) table).getQuickFilterColumn() == table.convertColumnIndexToModel(column))
            {
                super.setFont(table.getFont().deriveFont(Font.BOLD));
            }
            
            TableColumn col = table.getColumnModel().getColumn(column);
            if (col instanceof ETableColumn)
            {
                ETableColumn eColumn = (ETableColumn) col;
                
                // If column is sorted, prepend the up/down arrow
                if (eColumn.isSorted())
                {
                    if (eColumn.isAscending())
                    {
                        super.setIcon(UIManager.getIcon("Table.ascendingSortIcon"));
                    } 
                    else
                    {
                        super.setIcon(UIManager.getIcon("Table.descendingSortIcon"));
                    }
                }
            }
        }
        
        return this;
    }

}
