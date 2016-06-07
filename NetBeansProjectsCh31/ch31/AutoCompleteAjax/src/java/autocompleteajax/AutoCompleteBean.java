/*
 * This is a simple example of Auto Completion in JSF 2 and Ajax
 * by Jerry Zhou for the course of Java Web Applications
 */

package autocompleteajax;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author zhou
 */
@ManagedBean
@ViewScoped
public class AutoCompleteBean
{
    private static final String names[] = {"Alice", "Allen", "Jack", "Jay", "Jeff", "Jerry"};
    private static final String STYLE_SHOW = "width: 120px";
    private static final String STYLE_HIDE = "display: none";

    private String searchText;
    private String selected;
    private List<SelectItem> options = new ArrayList<SelectItem>();

    public String getSearchText()
    {
        return searchText;
    }

    public void setSearchText(String searchText)
    {
        this.searchText = searchText;
        fillOptions();
    }

    public String getSelected()
    {
        return selected;
    }

    public void setSelected(String selected)
    {
        this.selected = selected;
        // If selected, set it to searchText
        if(selected != null && !selected.isEmpty())
        {
            this.searchText = selected;
        }
        options.clear();
    }

    public List<SelectItem> getOptions()
    {
        return options;
    }

    public String getTextStyle()
    {
        return STYLE_SHOW;
    }

    public String getListStyle()
    {
        // Hide the option list if it is empty
        return (options.size() > 0? STYLE_SHOW: STYLE_HIDE);
    }

    public String getResults()
    {
        if(searchText == null || searchText.isEmpty())
            return "";

        // Compose the search results
        String results = "Results: ";
        {
            for(String name: names)
            {
                if(name.toLowerCase().indexOf(searchText.toLowerCase()) >= 0)
                {
                    results += name + " ";
                }
            }
        }

        return results;
    }

    private void fillOptions()
    {
        // Repopulate the option list
        options.clear();

        if(searchText != null && !searchText.isEmpty())
        {
            for(String name : names)
            {
                if(name.toLowerCase().startsWith(searchText.toLowerCase()))
                {
                    options.add(new SelectItem(name));
                }
            }
        
            // Work around: the list box becomes dropdown when size is 1
            if(options.size() == 1)
            {
                options.add(new SelectItem(""));
            }
        }
        
        // Initially nothing selected
        selected = null;
    }
}
