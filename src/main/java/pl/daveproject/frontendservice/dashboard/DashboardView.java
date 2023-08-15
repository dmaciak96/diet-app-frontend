package pl.daveproject.frontendservice.dashboard;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.lang3.StringUtils;
import pl.daveproject.frontendservice.bmi.model.BmiRate;
import pl.daveproject.frontendservice.bmi.service.BmiService;
import pl.daveproject.frontendservice.caloricneeds.service.TotalCaloricNeedsService;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;

import java.awt.*;

@PermitAll
@Route(value = "/dashboard", layout = AfterLoginAppLayout.class)
public class DashboardView extends HorizontalLayout implements HasDynamicTitle {

    private final BmiService bmiService;
    private final TotalCaloricNeedsService totalCaloricNeedsService;

    public DashboardView(BmiService bmiService,
                         TotalCaloricNeedsService totalCaloricNeedsService) {
        this.bmiService = bmiService;
        this.totalCaloricNeedsService = totalCaloricNeedsService;

        add(createBmiLayout());
        add(createTotalCaloricNeedsLayout());
    }

    private VerticalLayout createBmiLayout() {
        var bmiLayout = new VerticalLayout();
        bmiLayout.setWidth("50%");
        bmiLayout.add(createLatestBmiLayout());
        return bmiLayout;
    }

    private VerticalLayout createLatestBmiLayout() {
        var latestBmiLayout = new VerticalLayout();
        latestBmiLayout.addClassName(LumoUtility.JustifyContent.CENTER);
        latestBmiLayout.addClassName(LumoUtility.AlignItems.CENTER);

        latestBmiLayout.setWidthFull();

        var latestBmi = bmiService.findLatest();
        if(latestBmi.isPresent()) {
            var titleText = new NativeLabel(getTranslation("bmi-view.value"));
            titleText.addClassName(LumoUtility.FontSize.XXLARGE);

            var valueText = new NativeLabel(latestBmi.get().getValue() + " (" + getTranslation(latestBmi.get().getRate().getTranslationKey()) + ")");
            valueText.addClassName(LumoUtility.FontSize.LARGE);

            if(latestBmi.get().getRate() == BmiRate.CORRECT_VALUE) {
                valueText.addClassName(LumoUtility.TextColor.SUCCESS);
            } else {
                valueText.addClassName(LumoUtility.TextColor.ERROR);
            }

            latestBmiLayout.add(titleText);
            latestBmiLayout.add(valueText);
        } else {
            var emptyBmiMessage = new NativeLabel(getTranslation("dashboard-missing-bmi"));
            emptyBmiMessage.addClassName(LumoUtility.FontSize.MEDIUM);
            emptyBmiMessage.addClassName(LumoUtility.TextColor.ERROR);
            latestBmiLayout.add(emptyBmiMessage);
        }
        return latestBmiLayout;
    }

    private VerticalLayout createTotalCaloricNeedsLayout() {
        var totalCaloricNeedsLayout = new VerticalLayout();
        totalCaloricNeedsLayout.setWidth("50%");
        totalCaloricNeedsLayout.add(createLatestTotalCaloricNeedsLayout());
        return totalCaloricNeedsLayout;
    }

    private VerticalLayout createLatestTotalCaloricNeedsLayout() {
        var latestTotalCaloricNeedsLayout = new VerticalLayout();
        latestTotalCaloricNeedsLayout.addClassName(LumoUtility.JustifyContent.CENTER);
        latestTotalCaloricNeedsLayout.addClassName(LumoUtility.AlignItems.CENTER);
        latestTotalCaloricNeedsLayout.setWidthFull();

        var totalCaloricNeedsLatest = totalCaloricNeedsService.findLatest();
        if(totalCaloricNeedsLatest.isPresent()) {
            var titleText = new NativeLabel(getTranslation("total-caloric-needs.title"));
            titleText.addClassName(LumoUtility.FontSize.XXLARGE);

            var valueText = new NativeLabel(totalCaloricNeedsLatest.get().getValue() + " kcal");
            valueText.addClassName(LumoUtility.FontSize.LARGE);
            latestTotalCaloricNeedsLayout.add(titleText, valueText);
        } else {
            var emptyTotalCaloricNeedsMessage = new NativeLabel(getTranslation("dashboard-missing-total-caloric-needs"));
            emptyTotalCaloricNeedsMessage.addClassName(LumoUtility.FontSize.MEDIUM);
            emptyTotalCaloricNeedsMessage.addClassName(LumoUtility.TextColor.ERROR);
            latestTotalCaloricNeedsLayout.add(emptyTotalCaloricNeedsMessage);
        }

        return latestTotalCaloricNeedsLayout;
    }


    @Override
    public String getPageTitle() {
        return getTranslation("dashboard-page.title");
    }
}
