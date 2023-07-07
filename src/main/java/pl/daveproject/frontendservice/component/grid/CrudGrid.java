package pl.daveproject.frontendservice.component.grid;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;

@Getter
public class CrudGrid<MODEL, FILTER extends GridDataFilter> extends VerticalLayout {
    private final Grid<MODEL> grid;
    private final CrudToolbar toolbar;
    private final ConfigurableFilterDataProvider<MODEL, Void, FILTER> filterDataProvider;
    private final AbstractBackEndDataProvider<MODEL, FILTER> dataProvider;
    private final FILTER dataFilter;

    public CrudGrid(AbstractBackEndDataProvider<MODEL, FILTER> dataProvider, FILTER dataFilter) {
        this.dataFilter = dataFilter;
        this.dataProvider = dataProvider;
        this.filterDataProvider = dataProvider.withConfigurableFilter();
        this.grid = createGrid();
        this.toolbar = new CrudToolbar();
        add(toolbar, createSearchTextField(), grid);
    }

    private Grid<MODEL> createGrid() {
        var grid = new Grid<MODEL>();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.setItems(filterDataProvider);
        grid.addSelectionListener(selectionEvent -> {
            var optionalSelection = selectionEvent.getFirstSelectedItem();
            if (optionalSelection.isPresent()) {
                toolbar.setEnabledOnDelete(true);
                toolbar.setEnabledOnEdit(true);
            } else {
                toolbar.setEnabledOnDelete(false);
                toolbar.setEnabledOnEdit(false);
            }
        });
        return grid;
    }

    private TextField createSearchTextField() {
        var searchField = new TextField();
        searchField.setPlaceholder(getTranslation("common.search"));
        searchField.setWidth("50%");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            dataFilter.setSearchValue(e.getValue());
            filterDataProvider.setFilter(dataFilter);
        });
        return searchField;
    }
}
