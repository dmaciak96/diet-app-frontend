package pl.daveproject.frontendservice.product;

import pl.daveproject.frontendservice.component.CloseableDialog;
import pl.daveproject.frontendservice.component.Translator;

public class ProductDialog extends CloseableDialog implements Translator {

    public ProductDialog() {
        super("products-window.title");
    }
}
