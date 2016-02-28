/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO.statistics;

/**
 *
 * @author Felipe
 */
public class JobYearDTO {
    private Integer bd;
    private Long ctr;

    public Integer getBd() {
        return bd;
    }

    public void setBd(Integer bd) {
        this.bd = bd;
    }

    public Long getCount() {
        return ctr;
    }

    public void setCount(Long count) {
        this.ctr = count;
    }

    @Override
    public String toString() {
        return "JobYearDTO{" + "year=" + bd + ", count=" + ctr + '}';
    }
    
    
}
