package org.lhyf.config.pojo;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_pub_item")
public class PubItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pub_namespace_id")
    private Integer pubNamespaceId;

    private String property;

    private String value;

    private String intro;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_by")
    private String updateBy;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return pub_namespace_id
     */
    public Integer getPubNamespaceId() {
        return pubNamespaceId;
    }

    /**
     * @param pubNamespaceId
     */
    public void setPubNamespaceId(Integer pubNamespaceId) {
        this.pubNamespaceId = pubNamespaceId;
    }

    /**
     * @return property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return update_by
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}