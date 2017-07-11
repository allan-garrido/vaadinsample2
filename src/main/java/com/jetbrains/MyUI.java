package com.jetbrains;

import javax.servlet.annotation.WebServlet;
import javax.xml.soap.Text;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
//import de.steinwedel.messagebox.MessageBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Title("Allan Garrido - Ejercicio de la U")

public class MyUI extends UI {
    Profesor p = new Profesor();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout mainLayout = new VerticalLayout();

        final HorizontalLayout profesorLayout = new HorizontalLayout();
        final TextField nombreProfesorTXT = new TextField("Nombre: ");
        final TextField especialidadProfesorTXT = new TextField("Especialidad: ");
        final Button agregarProfesorBTN = new Button("Agregar profesor");

        profesorLayout.addComponents(nombreProfesorTXT,especialidadProfesorTXT,agregarProfesorBTN);

        final HorizontalLayout estudianteLayout = new HorizontalLayout();
        final TextField nombreEstudianteTXT = new TextField("Nombre: ");
        final TextField edadEstudianteTXT = new TextField("Edad: ");
        final Button agregarEstudianteBTN = new Button("Agregar estudiante");
        final Grid<Estudiante> listaEGrid = new Grid<>();

        estudianteLayout.addComponents(nombreEstudianteTXT,edadEstudianteTXT,agregarEstudianteBTN,listaEGrid);

        final HorizontalLayout asignaturasLayout = new HorizontalLayout();
        final TextField nombreAsignaturaTXT = new TextField("Asigatura: ");
        final TextField notaAsignaturaTXT = new TextField("Nota: ");
        final TextField cantevalAsignaturaTXT = new TextField("Cantidad de evaluaciones: ");
        final Button agregarAsignaturaBTN = new Button("Agregar asignatura");
        final Grid listadoAsignaturas = new Grid();

        asignaturasLayout.addComponents(nombreAsignaturaTXT,notaAsignaturaTXT,cantevalAsignaturaTXT,
                agregarAsignaturaBTN,listadoAsignaturas);



/*
        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            mainLayout.addComponent(new Label("Thanks " + nombreProfesorTXT.getValue()
                    + ", it works!"));
        });
*/
        
        mainLayout.addComponents(new Label("Datos del profesor"),profesorLayout,
                                 new Label("Datos del estudiante"),estudianteLayout,
                                 new Label("Datos de las asignaturas"),asignaturasLayout);

        // Aciones de los botones "Listeners"

        agregarProfesorBTN.addClickListener( e -> {
            p.setNombre(nombreProfesorTXT.getValue());
            p.setEspecialidad(especialidadProfesorTXT.getValue());

           Notification.show("Profesor"+p.getNombre()+" de especialidad "+p.getEspecialidad()+" Agregado.");

        });

        agregarEstudianteBTN.addClickListener( e-> {
            Estudiante e1 = new Estudiante();

            e1.setNombre(nombreEstudianteTXT.getValue());
            e1.setEdad(Integer.parseInt(edadEstudianteTXT.getValue()));

            p.addEstudiante(e1);

            listaEGrid.removeAllColumns();
            listaEGrid.setItems(p.getEstudiantes());
            listaEGrid.addColumn(Estudiante::getNombre).setCaption("Nombre");
            listaEGrid.addColumn(Estudiante::getEdad).setCaption("Edad");



            nombreEstudianteTXT.setValue("");
            edadEstudianteTXT.setValue("");

           Notification.show(e1.getNombre()+" de "+e1.getEdad()+" años agregado.");
        });

        listaEGrid.addSelectionListener( e -> {
            Set<Estudiante> selE = e.getAllSelectedItems();

        });

        setContent(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
