package com.lsl.kunmming.community.pojo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaginationDTO implements Serializable{

    private List<QuestionDTO> questionDTOS;
    private boolean showFirstPage;
    private boolean showEndPage;
    private boolean showPrevious;
    private boolean showNext;
    private Integer page;
    private  Integer totalPage;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    private List<Integer> pages = new ArrayList<>();

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public List<QuestionDTO> getQuestionDTOS() {
        return questionDTOS;
    }

    public void setQuestionDTOS(List<QuestionDTO> questionDTOS) {
        this.questionDTOS = questionDTOS;
    }


    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    @Override
    public String toString() {
        return "PaginationDTO{" +
                "questionDTOS=" + questionDTOS +
                ", showFirstPage=" + showFirstPage +
                ", showEndPage=" + showEndPage +
                ", showPrevious=" + showPrevious +
                ", showNext=" + showNext +
                ", page=" + page +
                ", totalPage=" + totalPage +
                ", pages=" + pages +
                '}';
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public void setPagenation(Integer totalCount, Integer page, Integer size) {
         totalPage = 0;

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        pages.add(page);
        for (int i = 1; i <=3; i++) {

            if (page - i > 0) {
               pages.add(0,page-i);

            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //当前为第一页的时候展示有上一页
        if (page == 1) {
            showPrevious = false;

        } else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;

        } else {
            showEndPage = true;
        }


    }
}
