package com.rkarp.admin.component;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/*
 * This component extends a CustomField and implements all the necessary
 * functionality so that it can be used just like any other Field.
 */
@SuppressWarnings({ "serial", "unchecked" })
public final class OptionalSelect<T> extends CustomField<T> {

    private final CheckBox checkBox;
    private final ComboBox comboBox;
    private final HorizontalLayout content;

    @Override
    protected Component initContent() {
        return content;
    }

    public OptionalSelect() {
        content = new HorizontalLayout();
        content.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
        content.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        comboBox = new ComboBox();
        comboBox.setTextInputAllowed(false);
        //comboBox.setNullSelectionAllowed(false);
        comboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
        comboBox.setWidth(10.0f, Unit.EM);
        comboBox.setEnabled(false);
        comboBox.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
//                final Property.ValueChangeEvent event) {
//                    setValue((T) event.getProperty().getValue());
//                }
            }
        });
        content.addComponent(comboBox);

        checkBox = new CheckBox("Subscribe to newsletter", false);
//        checkBox.setPropertyDataSource(
//                new MethodProperty<Boolean>(comboBox, "enabled"));
        checkBox.addValueChangeListener(new ValueChangeListener<Boolean>() {
            @Override
            public void valueChange(ValueChangeEvent<Boolean> event) {
//                final Property.ValueChangeEvent event) {
//                    if ((Boolean) event.getProperty().getValue()) {
//                        if (comboBox.getValue() == null) {
//                            Iterator<?> iterator = comboBox.getItemIds().iterator();
//                            if (iterator.hasNext()) {
//                                comboBox.setValue(iterator.next());
//                            }
//                        }
//                    } else {
//                        setValue(null);
//                    }
//                }
            }
        });

        content.addComponent(checkBox, 0);
    }

    @Override
    protected void doSetValue(T newValue) {
        super.setValue(newValue);
        comboBox.setValue(newValue);
        checkBox.setValue(newValue != null);
    }

    public void addOption(final T itemId, final String caption) {
        comboBox.setItems(itemId);
        comboBox.setCaption(caption);
    }

    public Class<? extends T> getType() {
        return (Class<? extends T>) Object.class;
    }

    @Override
    public T getValue() {
        return null;
    }
}
