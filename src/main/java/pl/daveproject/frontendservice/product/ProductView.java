package pl.daveproject.frontendservice.product;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import pl.daveproject.frontendservice.component.CrudToolbar;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;
import pl.daveproject.frontendservice.product.model.Product;
import pl.daveproject.frontendservice.product.service.ProductService;

@Route(value = "/products", layout = AfterLoginAppLayout.class)
public class ProductView extends VerticalLayout implements HasDynamicTitle {

    private final ProductService productService;
    private final Grid<Product> productGrid;
    private final CrudToolbar toolbar;

    public ProductView(ProductService productService) {
        this.productService = productService;
        this.productGrid = createGrid();
        this.toolbar = new CrudToolbar();
        add(toolbar, productGrid);
    }

    private Grid<Product> createGrid() {
        var grid = new Grid<Product>();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        grid.addColumn(Product::getName)
                .setHeader(getTranslation("products-page.grid-label-name"))
                .setSortable(true)
                .setResizable(true);

        grid.addColumn(product -> (double) Math.round(product.getKcal() * 100) / 100)
                .setHeader(getTranslation("products-page.grid-label-kcal"))
                .setSortable(true)
                .setResizable(true);

        grid.addColumn(product -> getTranslation(product.getType().getTranslationKey()))
                .setHeader(getTranslation("products-page.grid-label-type"))
                .setSortable(true)
                .setResizable(true);

        grid.setItems(productService.findAll());
        return grid;
    }

    @Override
    public String getPageTitle() {
        return getTranslation("products-page.title");
    }
}
