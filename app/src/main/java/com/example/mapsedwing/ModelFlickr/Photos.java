package com.example.mapsedwing.ModelFlickr;

import java.util.ArrayList;

public class Photos {
    public int page;
    public int pages;
    public int perpage;
    public int total;
    public ArrayList<Photo> photo;

    public Photos(int page, int pages, int perpage, int total, ArrayList<Photo> photos) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photo = photos;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<Photo> photos) {
        this.photo = photos;
    }
}
