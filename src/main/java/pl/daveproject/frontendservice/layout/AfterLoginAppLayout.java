package pl.daveproject.frontendservice.layout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.applicationUser.ApplicationUserService;
import pl.daveproject.frontendservice.exception.UserNotLoginException;
import pl.daveproject.frontendservice.EmptyView;
import pl.daveproject.frontendservice.component.WebdietNotification;
import pl.daveproject.frontendservice.component.type.WebdietNotificationType;
import pl.daveproject.frontendservice.dashboard.DashboardView;
import pl.daveproject.frontendservice.login.LoginView;
import pl.daveproject.frontendservice.login.service.LoginService;
import pl.daveproject.frontendservice.product.ProductView;
import pl.daveproject.frontendservice.recipe.RecipeView;
import pl.daveproject.frontendservice.shoppinglist.ShoppingListView;

@Slf4j
public class AfterLoginAppLayout extends AbstractAppLayout {

    private final ApplicationUserService applicationUserService;
    private final LoginService loginService;

    public AfterLoginAppLayout(ApplicationUserService applicationUserService,
                               LoginService loginService) {
        super(true);
        this.applicationUserService = applicationUserService;
        this.loginService = loginService;
        addToNavbar(createAvatar());
        addToDrawer(getMenuTabs());
    }

    private HorizontalLayout createAvatar() {
        var avatar = new Avatar();
        try {
            var currentUser = applicationUserService.findCurrentUser();
            avatar = new Avatar(currentUser.getFullName());
        } catch (JsonProcessingException e) {
            WebdietNotification.show(getTranslation("error-message.unexpected"), WebdietNotificationType.ERROR);
            log.error("Jwt token processing error: ", e);
        } catch (UserNotLoginException e) {
            WebdietNotification.show(getTranslation("error-message.user-not-login"), WebdietNotificationType.ERROR);
            log.error("Login user not found for current session: ", e);
        }

        var layout = new HorizontalLayout(createAvatarMenuBar(avatar));
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        layout.addClassNames(LumoUtility.Margin.Right.MEDIUM);
        return layout;
    }

    private MenuBar createAvatarMenuBar(Avatar avatar) {
        var menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        var subMenu = menuBar.addItem(avatar).getSubMenu();
        subMenu.addItem(getTranslation("avatar.settings"), e -> {
            //TODO: Create application settings view
            UI.getCurrent().navigate(EmptyView.class);
        });
        subMenu.addItem(getTranslation("avatar.logout"), e -> {
            loginService.logout();
            UI.getCurrent().navigate(LoginView.class);
        });
        return menuBar;
    }

    private Tabs getMenuTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.DASHBOARD, getTranslation("side-menu.dashboard"), DashboardView.class),
                createTab(VaadinIcon.CONNECT, getTranslation("side-menu.products"), ProductView.class),
                createTab(VaadinIcon.COFFEE, getTranslation("side-menu.recipes"), RecipeView.class),
                createTab(VaadinIcon.CART, getTranslation("side-menu.shopping-lists"), ShoppingListView.class));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String menuLabel, Class<? extends Component> viewClass) {
        var icon = viewIcon.create();
        icon.addClassNames("menu-icon");

        var link = new RouterLink();
        link.add(icon, new Span(menuLabel));
        link.setRoute(viewClass);
        link.setTabIndex(-1);

        return new Tab(link);
    }
}
