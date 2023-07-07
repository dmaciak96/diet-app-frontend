package pl.daveproject.frontendservice.component;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.lang3.StringUtils;

public class DeleteConfirmDialog extends ConfirmDialog {

    public DeleteConfirmDialog(String headerSuffix) {
        this.setHeader(getTranslation("delete-dialog.header-prefix") + StringUtils.SPACE + headerSuffix);
        this.setText(getTranslation("delete-dialog.text"));

        this.setCancelable(true);
        this.setCancelText(getTranslation("delete-dialog.cancel-text"));
        this.addCancelListener(event -> this.close());

        this.setConfirmText(getTranslation("delete-dialog.confirm-text"));
        this.setConfirmButtonTheme("error primary");
    }

    @Override
    public Registration addConfirmListener(ComponentEventListener<ConfirmEvent> listener) {
        return super.addConfirmListener(listener);
    }
}
