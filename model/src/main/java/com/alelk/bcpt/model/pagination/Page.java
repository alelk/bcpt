package com.alelk.bcpt.model.pagination;

import com.alelk.bcpt.model.dto.BcptDto;
import com.alelk.bcpt.model.util.Util;

import java.util.List;

/**
 * Page
 *
 * Created by Alex Elkin on 10.10.2017.
 */
public class Page<T extends BcptDto> {

    private int pageNumber;
    private int itemsPerPage;
    private Long itemsCount;
    private Long pagesCount;
    private List<T> items;
    private List<SortBy> sortByList;
    private List<Filter> filterList;

    public Page() {
    }

    public Page(int pageNumber, int itemsPerPage, List<T> items, Long totalItemsCount) {
        this(pageNumber, itemsPerPage, items, totalItemsCount, null, null);
    }

    public Page(int pageNumber, int itemsPerPage, List<T> items, Long totalItemsCount, List<SortBy> sortByList, List<Filter> filterList) {
        this.pageNumber = pageNumber;
        this.itemsPerPage = itemsPerPage;
        this.items = items;
        this.sortByList = sortByList;
        this.filterList = filterList;
        setItemsCount(totalItemsCount);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setPagesCount(Long pagesCount) {
        this.pagesCount = pagesCount;
    }

    public List<SortBy> getSortByList() {
        return sortByList;
    }

    public void setSortByList(List<SortBy> sortByList) {
        this.sortByList = sortByList;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }

    public Long getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Long itemsCount) {
        this.itemsCount = itemsCount;
        this.pagesCount = itemsPerPage == 0 ? null : (itemsCount / itemsPerPage) + (itemsCount%itemsPerPage > 0 ? 1 : 0);
    }

    public Long getPagesCount() {
        return pagesCount;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", itemsPerPage=" + itemsPerPage +
                ", items=" + (items != null ? "[" + items.size() + " elements]" : null) +
                ", sortByList=" + Util.toString(sortByList) +
                ", filterList=" + Util.toString(filterList) +
                '}';
    }
}
