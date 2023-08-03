package pl.daveproject.frontendservice.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ViewDetailsButton extends Button {

  public ViewDetailsButton() {
    setText(getTranslation("component.view-details-button"));
    setIcon(new Icon(VaadinIcon.SEARCH));
  }

}
