package pl.daveproject.frontendservice.shoppinglist;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import pl.daveproject.frontendservice.component.DeleteConfirmDialog;
import pl.daveproject.frontendservice.component.ViewDetailsButton;
import pl.daveproject.frontendservice.component.grid.CrudGrid;
import pl.daveproject.frontendservice.layout.AfterLoginAppLayout;
import pl.daveproject.frontendservice.recipe.RecipeDataProvider;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingList;
import pl.daveproject.frontendservice.shoppinglist.model.ShoppingListDialogModel;
import pl.daveproject.frontendservice.shoppinglist.service.ShoppingListService;

@PermitAll
@Route(value = "/shopping-lists", layout = AfterLoginAppLayout.class)
public class ShoppingListView extends VerticalLayout implements HasDynamicTitle {

  private final CrudGrid<ShoppingList, ShoppingListFilter> shoppingListGrid;
  private final ShoppingListService shoppingListService;
  private final RecipeDataProvider recipeDataProvider;

  public ShoppingListView(ShoppingListService shoppingListService,
      ShoppingListDataProvider shoppingListDataProvider,
      ShoppingListFilter shoppingListFilter, RecipeDataProvider recipeDataProvider) {
    this.shoppingListService = shoppingListService;
    this.recipeDataProvider = recipeDataProvider;
    this.shoppingListGrid = new CrudGrid<>(shoppingListDataProvider, shoppingListFilter);

    createGridColumns();
    setOnNewClickListener();
    setOnEditClickListener();
    setOnDeleteClickListener();
    add(shoppingListGrid);
  }

  private void createGridColumns() {
    shoppingListGrid.getGrid()
        .addColumn(ShoppingList::getName, ShoppingListDataProvider.NAME_SORTING_KEY)
        .setHeader(getTranslation("shopping-lists-page.grid-label-name"))
        .setSortable(true)
        .setResizable(true);

    shoppingListGrid.getGrid().addColumn(new ComponentRenderer<>(shoppingList -> {
          var viewDetailsButton = new ViewDetailsButton();
          viewDetailsButton.addClickListener(e -> {
            var shoppingListDetailsDialog = new ShoppingListDetailsDialog(shoppingList);
            add(shoppingListDetailsDialog);
            shoppingListDetailsDialog.open();
          });
          return viewDetailsButton;
        }))
        .setHeader(StringUtils.EMPTY)
        .setSortable(false)
        .setResizable(false);
  }

  private void setOnNewClickListener() {
    shoppingListGrid.addOnClickListener(event ->
        createAndOpenRecipeDialog(ShoppingListDialogModel.builder()
            .recipes(List.of())
            .build()));
  }

  private void setOnEditClickListener() {
    shoppingListGrid.editOnClickListener(event -> {
      var selectedProduct = shoppingListGrid.getGrid()
          .getSelectedItems()
          .stream()
          .findFirst();
      selectedProduct.ifPresent(
          e -> createAndOpenRecipeDialog(shoppingListService.toDialogModel(e)));
    });
  }

  private void createAndOpenRecipeDialog(ShoppingListDialogModel shoppingListDialogModel) {
    var shoppingListDialog = new ShoppingListDialog(shoppingListService, shoppingListDialogModel, recipeDataProvider);
    add(shoppingListDialog);
    shoppingListDialog.open();
    shoppingListDialog.addOpenedChangeListener(e -> {
      if (!e.isOpened()) {
        shoppingListGrid.refresh();
      }
    });
  }

  private void setOnDeleteClickListener() {
    shoppingListGrid.deleteOnClickListener(event -> {
      var selectedProduct = shoppingListGrid.getGrid()
          .getSelectedItems()
          .stream()
          .findFirst();
      selectedProduct.ifPresent(this::createAndOpenDeleteDialog);
    });
  }

  private void createAndOpenDeleteDialog(ShoppingList shoppingList) {
    var confirmDialogSuffix = "%s \"%s\"".formatted(
        getTranslation("delete-dialog.header-shopping-list-suffix"),
        shoppingList.getName());
    var confirmDialog = new DeleteConfirmDialog(confirmDialogSuffix);
    confirmDialog.open();
    confirmDialog.addConfirmListener(event -> {
      shoppingListService.delete(shoppingList.getId());
      shoppingListGrid.refresh();
      confirmDialog.close();
    });
  }

  @Override
  public String getPageTitle() {
    return getTranslation("shopping-lists-page.title");
  }
}
