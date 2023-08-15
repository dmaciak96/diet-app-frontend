package pl.daveproject.frontendservice.bmi;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.lang3.StringUtils;
import pl.daveproject.frontendservice.bmi.model.Bmi;
import pl.daveproject.frontendservice.bmi.model.UnitSystem;
import pl.daveproject.frontendservice.bmi.service.BmiService;
import pl.daveproject.frontendservice.component.DeleteConfirmDialog;
import pl.daveproject.frontendservice.component.grid.CrudGrid;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;

import java.time.format.DateTimeFormatter;

@PermitAll
@Route(value = "/bmi", layout = AfterLoginAppLayout.class)
public class BmiView extends VerticalLayout implements HasDynamicTitle {

    private final CrudGrid<Bmi, BmiFilter> bmiGrid;
    private final BmiService bmiService;

    public BmiView(BmiService bmiService,
                   BmiDataProvider bmiDataProvider,
                   BmiFilter bmiFilter) {
        this.bmiService = bmiService;
        this.bmiGrid = new CrudGrid<>(bmiDataProvider, bmiFilter, false);

        createGridColumns();
        setOnNewClickListener();
        setOnDeleteClickListener();
        add(bmiGrid);
    }

    private void createGridColumns() {
        bmiGrid.getGrid()
                .addColumn(e -> e.getAddedDate().format(DateTimeFormatter.ISO_DATE),
                        BmiDataProvider.ADDED_DATE_SORTING_KEY)
                .setHeader(getTranslation("bmi-view.added-date"))
                .setSortable(true)
                .setResizable(true);

        bmiGrid.getGrid()
                .addColumn(Bmi::getValue,
                        BmiDataProvider.VALUE_SORTING_KEY)
                .setHeader(getTranslation("bmi-view.value"))
                .setSortable(true)
                .setResizable(true);

        bmiGrid.getGrid()
                .addColumn(e -> switch (e.getUnit()) {
                            case METRIC -> e.getWeight() + " kg";
                            case IMPERIAL -> e.getWeight() + " lbs";
                        },
                        BmiDataProvider.WEIGHT_SORTING_KEY)
                .setHeader(getTranslation("bmi-view.weight"))
                .setSortable(true)
                .setResizable(true);

        bmiGrid.getGrid()
                .addColumn(e -> switch (e.getUnit()) {
                            case METRIC -> e.getHeight() + " m";
                            case IMPERIAL -> e.getHeight() + " ft";
                        },
                        BmiDataProvider.HEIGHT_SORTING_KEY)
                .setHeader(getTranslation("bmi-view.height"))
                .setSortable(true)
                .setResizable(true);

        bmiGrid.getGrid()
                .addColumn(e -> getTranslation(e.getRate().getTranslationKey()),
                        BmiDataProvider.RATE_SORTING_KEY)
                .setHeader(StringUtils.EMPTY)
                .setSortable(true)
                .setResizable(true);
    }

    private void setOnNewClickListener() {
        bmiGrid.addOnClickListener(event ->
                createAndOpenBmiDialog(Bmi.builder().unit(UnitSystem.METRIC).build()));
    }

    private void createAndOpenBmiDialog(Bmi bmi) {
        var bmiDialog = new BmiDialog(bmiService, bmi);
        add(bmiDialog);
        bmiDialog.open();
        bmiDialog.addOpenedChangeListener(e -> {
            if (!e.isOpened()) {
                bmiGrid.refresh();
            }
        });
    }

    private void setOnDeleteClickListener() {
        bmiGrid.deleteOnClickListener(event -> {
            var selected = bmiGrid.getGrid()
                    .getSelectedItems()
                    .stream()
                    .findFirst();
            selected.ifPresent(this::createAndOpenDeleteDialog);
        });
    }

    private void createAndOpenDeleteDialog(Bmi bmi) {
        var confirmDialog = new DeleteConfirmDialog(getTranslation("delete-dialog.header-bmi-suffix"));
        confirmDialog.open();
        confirmDialog.addConfirmListener(event -> {
            bmiService.delete(bmi.getId());
            bmiGrid.refresh();
            confirmDialog.close();
        });
    }

    @Override
    public String getPageTitle() {
        return getTranslation("bmi-view.title");
    }
}
