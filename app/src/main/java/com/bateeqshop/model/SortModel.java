package com.bateeqshop.model;

/**
 * Created by Daniel.Nababan on 5/30/2016.
 */
public class SortModel
{
    private String sortBy;
    private String sortText;
    private boolean asc;
    private boolean selected;

    public SortModel(String sortBy,String sortText, boolean asc, boolean selected){
        this.sortBy = sortBy;
        this.sortText = sortText;
        this.asc = asc;
        this.selected = selected;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String getSortText() {
        return sortText;
    }

    public void setSortText(String sortText) {
        this.sortText = sortText;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
