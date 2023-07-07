package pl.daveproject.frontendservice.product;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import pl.daveproject.frontendservice.component.CrudToolbar;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;
import pl.daveproject.frontendservice.product.model.Product;
import pl.daveproject.frontendservice.product.service.ProductDataProvider;
import pl.daveproject.frontendservice.product.service.ProductFilter;

@Route(value = "/products", layout = AfterLoginAppLayout.class)
public class ProductView extends VerticalLayout implements HasDynamicTitle {

    private final ProductFilter productFilter;
    private final ProductDataProvider productDataProvider;
    private final ConfigurableFilterDataProvider<Product, Void, ProductFilter> filterDataProvider;
    private final Grid<Product> productGrid;
    private final CrudToolbar toolbar;

    public ProductView(ProductFilter productFilter,
                       ProductDataProvider productDataProvider) {
        this.productFilter = productFilter;
        this.productDataProvider = productDataProvider;
        this.filterDataProvider = productDataProvider.withConfigurableFilter();
        this.productGrid = createGrid();
        this.toolbar = new CrudToolbar();
        add(toolbar, createSearchTextField(), productGrid);
    }

    private Grid<Product> createGrid() {
        var grid = new Grid<Product>();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        createGridColumns(grid);
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

    private void createGridColumns(Grid<Product> grid) {
        grid.addColumn(Product::getName, ProductDataProvider.NAME_SORTING_KEY)
                .setHeader(getTranslation("products-page.grid-label-name"))
                .setSortable(true)
                .setResizable(true);

        grid.addColumn(product -> (double) Math.round(product.getKcal() * 100) / 100, ProductDataProvider.KCAL_SORTING_KEY)
                .setHeader(getTranslation("products-page.grid-label-kcal"))
                .setSortable(true)
                .setResizable(true);

        grid.addColumn(product -> getTranslation(product.getType().getTranslationKey()), ProductDataProvider.TYPE_SORTING_KEY)
                .setHeader(getTranslation("products-page.grid-label-type"))
                .setSortable(true)
                .setResizable(true);
    }

    private TextField createSearchTextField() {
        var searchField = new TextField();
        searchField.setPlaceholder(getTranslation("common.search"));
        searchField.setWidthFull();
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            productFilter.setSearchValue(e.getValue());
            filterDataProvider.setFilter(productFilter);
        });
        return searchField;
    }

    @Override
    public String getPageTitle() {
        return getTranslation("products-page.title");
    }
}
