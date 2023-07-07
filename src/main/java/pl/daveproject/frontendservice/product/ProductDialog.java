package pl.daveproject.frontendservice.product;

import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;
import pl.daveproject.frontendservice.component.WebdietFormWrapper;
import pl.daveproject.frontendservice.product.model.Product;
import pl.daveproject.frontendservice.product.service.ProductService;

@Slf4j
public class ProductDialog extends CloseableDialog implements Translator {

    private final WebdietFormWrapper productForm;
    private final ProductService productService;

    public ProductDialog(ProductService productService, Product product) {
        super("products-window.title");
        this.productService = productService;
        this.productForm = new WebdietFormWrapper(new ProductForm(product), false);
        add(productForm);
        saveOrUpdateProductOnSave();
    }

    private void saveOrUpdateProductOnSave() {
        var form = (ProductForm) productForm.getFormLayout();
        form.addSaveListener(e -> {
            log.info("Creating/Updating product: {}", e.getProduct().getName());
            var createdProduct = productService.saveOrUpdate(e.getProduct());
            log.info("Product {} successfully created/updated", createdProduct.getName());
            this.close();
        });
    }
}
