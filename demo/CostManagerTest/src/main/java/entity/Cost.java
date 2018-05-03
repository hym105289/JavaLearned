package entity;

import java.io.Serializable;

/**
 * Created by jinhua.chen on 2018/4/20.
 */
public class Cost implements Serializable{
    private Integer costId;
    private String name;
    // 基本时长
    private Double baseDuration;
    // 基本费用
    private Double baseCost;
    // 单位费用
    private Double unitCost;
    // 状态(枚举): 0-开通,1-暂停
    private String status;
    // 资费说明
    private String descr;
//    // 创建时间
//    private Timestamp creatTime;
//    // 开通时间
//    private Timestamp startTime;
    // 资费类型(枚举):1-包月,2-套餐,3-计时.
    private String costType;

    public Integer getCostId() {
        return costId;
    }

    public void setCostId(Integer costId) {
        this.costId = costId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBaseDuration() {
        return baseDuration;
    }

    public void setBaseDuration(Double baseDuration) {
        this.baseDuration = baseDuration;
    }

    public Double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(Double baseCost) {
        this.baseCost = baseCost;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

//    public Timestamp getCreatTime() {
//        return creatTime;
//    }
//
//    public void setCreatTime(Timestamp creatTime) {
//        this.creatTime = creatTime;
//    }
//
//    public Timestamp getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Timestamp startTime) {
//        this.startTime = startTime;
//    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }
}
