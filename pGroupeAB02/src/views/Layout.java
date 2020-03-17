package views;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Layout {
    private Layout(){}

    public static Node fill(Node node)
    {
        HBox.setHgrow(node, Priority.ALWAYS);
        VBox.setVgrow(node, Priority.ALWAYS);

        return node;
    }

    public static Region width(double width, Region region)
    {
        region.setMaxWidth(width);

        return region;
    }

    public static Region height(double height, Region region)
    {
        region.setMaxHeight(height);

        return region;
    }

    public static Region vertical(double spacing, Node... nodes)
    {
        return new VBox(spacing, nodes);
    }

    public static  Region horizontal(double spacing, Node... nodes)
    {
        return new HBox(spacing, nodes);
    }

    public static  Region horizontal(double spacing, Pos alignment, Node... nodes)
    {
        HBox hbox = new HBox(spacing, nodes);

        hbox.setAlignment(alignment);

        return hbox;
    }

    public static Region verticallyCentered(Node node)
    {
        VBox vbox = new VBox(node);

        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    public static Region horizontallyCentered(Node node)
    {
        HBox hbox = new HBox(node);

        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }
}
