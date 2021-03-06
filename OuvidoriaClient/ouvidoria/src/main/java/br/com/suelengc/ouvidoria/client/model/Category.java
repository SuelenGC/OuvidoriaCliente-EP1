package br.com.suelengc.ouvidoria.client.model;

public class Category {
    private Long id;
    private String abbreviation;
    private String name;

    public Category() {

    }

    public Category(String categoryAbbreviation, String categoryName) {
        this.abbreviation = categoryAbbreviation;
        this.name = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return abbreviation + " - " + name;
    }
}
