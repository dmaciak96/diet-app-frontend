package pl.daveproject.frontendservice.product;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import pl.daveproject.frontendservice.component.grid.CrudGrid;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;
import pl.daveproject.frontendservice.product.model.Product;
import pl.daveproject.frontendservice.product.service.ProductService;

@Route(value = "/products", layout = AfterLoginAppLayout.class)
public class ProductView extends VerticalLayout implements HasDynamicTitle {

    private final CrudGrid<Product, ProductFilter> productGrid;
    private final ProductService productService;

    public ProductView(ProductFilter productFilter,
                       ProductDataProvider productDataProvider,
                       ProductService productService) {
        this.productGrid = new CrudGrid<>(productDataProvider, productFilter);
        this.productService = productService;
        createGridColumns();
        setOnNewClickListener();
        setOnEditClickListener();
        add(productGrid);
    }

    private void createGridColumns() {
        productGrid.getGrid().addColumn(Product::getName, ProductDataProvider.NAME_SORTING_KEY)
                .setHeader(getTranslation("products-page.grid-label-name"))
                .setSortable(true)
                .setResizable(true);

        productGrid.getGrid().addColumn(product -> (double) Math.round(product.getKcal() * 100) / 100, ProductDataProvider.KCAL_SORTING_KEY)
                .setHeader(getTranslation("products-page.grid-label-kcal"))
                .setSortable(true)
                .setResizable(true);

        productGrid.getGrid().addColumn(product -> getTranslation(product.getType().getTranslationKey()), ProductDataProvider.TYPE_SORTING_KEY)
                .setHeader(getTranslation("products-page.grid-label-type"))
                .setSortable(true)
                .setResizable(true);
    }

    private void setOnNewClickListener() {
        productGrid.addOnClickListener(event ->
                createAndOpenProductDialog(Product.builder().build()));
    }

    private void setOnEditClickListener() {
        productGrid.editOnClickListener(event -> {
            var selectedProduct = productGrid.getGrid()
                    .getSelectedItems()
                    .stream()
                    .findFirst();
            selectedProduct.ifPresent(this::createAndOpenProductDialog);
        });
    }


    private void createAndOpenProductDialog(Product product) {
        var productDialog = new ProductDialog(productService, product);
        add(productDialog);
        productDialog.open();
        productDialog.addOpenedChangeListener(e -> {
            if (!e.isOpened()) {
                productGrid.refresh();
            }
        });
    }

    @Override
    public String getPageTitle() {
        return getTranslation("products-page.title");
    }
}
