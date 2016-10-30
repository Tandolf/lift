package se.andolf.entities;

import org.neo4j.ogm.annotation.NodeEntity;
import se.andolf.api.ImageFormat;

/**
 * Created by Thomas on 2016-06-11.
 */
@NodeEntity
public class Image {

    public Image() {}

    private String name;
    private int width;
    private int height;
    private ImageFormat format;
    private String hash;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageFormat getFormat() {
        return format;
    }

    public void setFormat(ImageFormat format) {
        this.format = format;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
